<?php
require_once __DIR__ . '/../lib/polls.php';

if ($_SERVER['REQUEST_METHOD'] !== 'POST') {
    json_out(['error' => 'Metode tidak diizinkan'], 405);
}

$body = read_json_body();
$slug = $body['slug'] ?? '';
$candidateIds = $body['candidateIds'] ?? [];
$fingerprint = $body['fingerprint'] ?? null;

if (!$slug || !is_array($candidateIds) || count($candidateIds) === 0) {
    json_out(['error' => 'Pilihan tidak valid'], 400);
}

$poll = get_poll_by_slug($slug);
if (!$poll) {
    json_out(['error' => 'Polling tidak ditemukan'], 404);
}

// Validasi status
$status = poll_status($poll);
if ($status !== 'active') {
    $msg = $status === 'scheduled' ? 'Polling belum dibuka'
        : ($status === 'ended' ? 'Polling sudah berakhir' : 'Polling sudah ditutup');
    json_out(['error' => $msg], 403);
}

// Validasi jumlah pilihan
$type = $poll['type'];
$maxChoices = (int)$poll['max_choices'];
if ($type === 'single' && count($candidateIds) !== 1) {
    json_out(['error' => 'Hanya boleh memilih 1 kandidat'], 400);
}
if ($type === 'multiple' && count($candidateIds) > $maxChoices) {
    json_out(['error' => "Maksimal $maxChoices pilihan"], 400);
}

// Validasi kandidat milik polling ini
$validIds = [];
foreach (get_candidates((int)$poll['id']) as $c) {
    $validIds[(int)$c['id']] = true;
}
$chosen = [];
foreach (array_unique(array_map('intval', $candidateIds)) as $cid) {
    if (isset($validIds[$cid])) $chosen[] = $cid;
}
if (count($chosen) === 0) {
    json_out(['error' => 'Kandidat tidak valid'], 400);
}

// ---- Deteksi perangkat (anti vote ganda) ----
$ip = client_ip();
$ua = $_SERVER['HTTP_USER_AGENT'] ?? '';
$deviceHash = device_hash($fingerprint, $ip, $ua);

$pdo = db();

// Cek apakah sudah memilih
$check = $pdo->prepare('SELECT id FROM ballots WHERE poll_id = ? AND device_hash = ?');
$check->execute([(int)$poll['id'], $deviceHash]);
if ($check->fetch()) {
    json_out(['error' => 'Perangkat ini sudah memberikan suara pada polling ini.', 'alreadyVoted' => true], 409);
}

// Simpan surat suara + pilihan (transaksi). Unique constraint mencegah race condition.
try {
    $pdo->beginTransaction();
    $ins = $pdo->prepare('INSERT INTO ballots (poll_id, device_hash, fingerprint, ip_hash, user_agent) VALUES (?,?,?,?,?)');
    $ins->execute([
        (int)$poll['id'],
        $deviceHash,
        $fingerprint ? substr($fingerprint, 0, 100) : null,
        hash_value($ip),
        substr($ua, 0, 255),
    ]);
    $ballotId = (int)$pdo->lastInsertId();

    $vins = $pdo->prepare('INSERT INTO votes (ballot_id, candidate_id) VALUES (?, ?)');
    foreach ($chosen as $cid) {
        $vins->execute([$ballotId, $cid]);
    }
    $pdo->commit();
} catch (PDOException $ex) {
    if ($pdo->inTransaction()) $pdo->rollBack();
    // 23000 = integrity constraint violation (unique) → sudah memilih
    if ($ex->getCode() === '23000') {
        json_out(['error' => 'Perangkat ini sudah memberikan suara.', 'alreadyVoted' => true], 409);
    }
    json_out(['error' => 'Gagal menyimpan suara'], 500);
}

json_out(['ok' => true, 'selectedCandidateIds' => $chosen]);
