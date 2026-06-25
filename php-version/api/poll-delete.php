<?php
// Hapus polling (admin)
require_once __DIR__ . '/../lib/polls.php';
require_admin_api();

if ($_SERVER['REQUEST_METHOD'] !== 'POST') {
    json_out(['error' => 'Metode tidak diizinkan'], 405);
}

$b = read_json_body();
$id = (int)($b['id'] ?? 0);
if ($id <= 0) json_out(['error' => 'ID tidak valid'], 400);

$pdo = db();
try {
    $pdo->beginTransaction();
    // hapus manual demi kompatibilitas (SQLite tanpa FK cascade aktif default)
    $cand = $pdo->prepare('SELECT id FROM candidates WHERE poll_id = ?');
    $cand->execute([$id]);
    foreach ($cand->fetchAll() as $c) {
        $pdo->prepare('DELETE FROM votes WHERE candidate_id = ?')->execute([(int)$c['id']]);
    }
    $bal = $pdo->prepare('SELECT id FROM ballots WHERE poll_id = ?');
    $bal->execute([$id]);
    foreach ($bal->fetchAll() as $bb) {
        $pdo->prepare('DELETE FROM votes WHERE ballot_id = ?')->execute([(int)$bb['id']]);
    }
    $pdo->prepare('DELETE FROM ballots WHERE poll_id = ?')->execute([$id]);
    $pdo->prepare('DELETE FROM candidates WHERE poll_id = ?')->execute([$id]);
    $pdo->prepare('DELETE FROM comments WHERE poll_id = ?')->execute([$id]);
    $pdo->prepare('DELETE FROM polls WHERE id = ?')->execute([$id]);
    $pdo->commit();
} catch (Throwable $ex) {
    if ($pdo->inTransaction()) $pdo->rollBack();
    json_out(['error' => 'Gagal menghapus'], 500);
}

json_out(['ok' => true]);
