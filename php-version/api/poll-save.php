<?php
// Buat / perbarui polling (admin). Body JSON.
require_once __DIR__ . '/../lib/polls.php';
require_admin_api();

if ($_SERVER['REQUEST_METHOD'] !== 'POST') {
    json_out(['error' => 'Metode tidak diizinkan'], 405);
}

$b = read_json_body();
$id = isset($b['id']) ? (int)$b['id'] : 0;
$title = trim($b['title'] ?? '');
$description = trim($b['description'] ?? '');
$bannerImage = $b['bannerImage'] ?? null;
$type = ($b['type'] ?? 'single') === 'multiple' ? 'multiple' : 'single';
$maxChoices = max(1, (int)($b['maxChoices'] ?? 1));
$isActive = !empty($b['isActive']) ? 1 : 0;
$isPrivate = !empty($b['isPrivate']) ? 1 : 0;
$showResults = in_array($b['showResults'] ?? 'always', ['always', 'afterVote', 'afterClose'], true) ? $b['showResults'] : 'always';
$allowComments = !empty($b['allowComments']) ? 1 : 0;
$startDate = !empty($b['startDate']) ? str_replace('T', ' ', $b['startDate']) . ':00' : null;
$endDate = !empty($b['endDate']) ? str_replace('T', ' ', $b['endDate']) . ':00' : null;
$candidates = is_array($b['candidates'] ?? null) ? $b['candidates'] : [];

if (mb_strlen($title) < 3) {
    json_out(['error' => 'Judul polling minimal 3 karakter'], 400);
}
$valid = array_values(array_filter($candidates, fn($c) => trim($c['name'] ?? '') !== ''));
if (count($valid) < 2) {
    json_out(['error' => 'Minimal 2 kandidat / pilihan dengan nama terisi'], 400);
}
if ($type === 'single') $maxChoices = 1;

$pdo = db();

try {
    $pdo->beginTransaction();

    if ($id > 0) {
        // ---- update ----
        $poll = get_poll_by_id($id);
        if (!$poll) {
            $pdo->rollBack();
            json_out(['error' => 'Polling tidak ditemukan'], 404);
        }
        $pdo->prepare('UPDATE polls SET title=?, description=?, banner_image=?, type=?, max_choices=?, is_active=?, is_private=?, show_results=?, allow_comments=?, start_date=?, end_date=? WHERE id=?')
            ->execute([$title, $description ?: null, $bannerImage ?: null, $type, $maxChoices, $isActive, $isPrivate, $showResults, $allowComments, $startDate, $endDate, $id]);

        // sinkronisasi kandidat
        $keepIds = [];
        foreach ($valid as $c) {
            if (!empty($c['id'])) $keepIds[] = (int)$c['id'];
        }
        // hapus kandidat yang dibuang + votenya
        $existing = $pdo->prepare('SELECT id FROM candidates WHERE poll_id = ?');
        $existing->execute([$id]);
        foreach ($existing->fetchAll() as $row) {
            if (!in_array((int)$row['id'], $keepIds, true)) {
                $pdo->prepare('DELETE FROM votes WHERE candidate_id = ?')->execute([(int)$row['id']]);
                $pdo->prepare('DELETE FROM candidates WHERE id = ?')->execute([(int)$row['id']]);
            }
        }
        // update / insert
        $upd = $pdo->prepare('UPDATE candidates SET name=?, description=?, photo=?, ballot_no=?, sort_order=?, extra_fields=? WHERE id=? AND poll_id=?');
        $insC = $pdo->prepare('INSERT INTO candidates (poll_id, name, description, photo, ballot_no, sort_order, extra_fields) VALUES (?,?,?,?,?,?,?)');
        foreach ($valid as $i => $c) {
            $extra = encode_extra($c['extraFields'] ?? []);
            $ballotNo = isset($c['ballotNo']) ? (int)$c['ballotNo'] : $i + 1;
            if (!empty($c['id'])) {
                $upd->execute([trim($c['name']), trim($c['description'] ?? '') ?: null, $c['photo'] ?? null, $ballotNo, $i, $extra, (int)$c['id'], $id]);
            } else {
                $insC->execute([$id, trim($c['name']), trim($c['description'] ?? '') ?: null, $c['photo'] ?? null, $ballotNo, $i, $extra]);
            }
        }
        $pdo->commit();
        json_out(['ok' => true, 'id' => $id, 'slug' => $poll['slug']]);
    } else {
        // ---- create ----
        $slug = gen_slug($title);
        $pdo->prepare('INSERT INTO polls (slug, title, description, banner_image, type, max_choices, is_active, is_private, show_results, allow_comments, start_date, end_date) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)')
            ->execute([$slug, $title, $description ?: null, $bannerImage ?: null, $type, $maxChoices, $isActive, $isPrivate, $showResults, $allowComments, $startDate, $endDate]);
        $pollId = (int)$pdo->lastInsertId();

        $insC = $pdo->prepare('INSERT INTO candidates (poll_id, name, description, photo, ballot_no, sort_order, extra_fields) VALUES (?,?,?,?,?,?,?)');
        foreach ($valid as $i => $c) {
            $extra = encode_extra($c['extraFields'] ?? []);
            $ballotNo = isset($c['ballotNo']) ? (int)$c['ballotNo'] : $i + 1;
            $insC->execute([$pollId, trim($c['name']), trim($c['description'] ?? '') ?: null, $c['photo'] ?? null, $ballotNo, $i, $extra]);
        }
        $pdo->commit();
        json_out(['ok' => true, 'id' => $pollId, 'slug' => $slug]);
    }
} catch (Throwable $ex) {
    if ($pdo->inTransaction()) $pdo->rollBack();
    json_out(['error' => 'Gagal menyimpan: ' . $ex->getMessage()], 500);
}

function encode_extra($fields): ?string
{
    if (!is_array($fields)) return null;
    $clean = [];
    foreach ($fields as $f) {
        $label = trim($f['label'] ?? '');
        $value = trim($f['value'] ?? '');
        if ($label !== '' && $value !== '') $clean[] = ['label' => $label, 'value' => $value];
    }
    return count($clean) ? json_encode($clean, JSON_UNESCAPED_UNICODE) : null;
}
