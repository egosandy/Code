<?php
// GET ?slug=... daftar komentar | POST tambah komentar
require_once __DIR__ . '/../lib/polls.php';

if ($_SERVER['REQUEST_METHOD'] === 'GET') {
    $slug = $_GET['slug'] ?? '';
    $poll = get_poll_by_slug($slug);
    if (!$poll) json_out(['comments' => []]);
    $st = db()->prepare('SELECT id, name, message, created_at FROM comments WHERE poll_id = ? ORDER BY created_at DESC, id DESC LIMIT 100');
    $st->execute([(int)$poll['id']]);
    json_out(['comments' => $st->fetchAll()]);
}

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $body = read_json_body();
    $slug = $body['slug'] ?? '';
    $name = trim($body['name'] ?? '');
    $message = trim($body['message'] ?? '');
    if (!$slug || $name === '' || $message === '') {
        json_out(['error' => 'Nama dan komentar wajib diisi'], 400);
    }
    $poll = get_poll_by_slug($slug);
    if (!$poll) json_out(['error' => 'Polling tidak ditemukan'], 404);
    if (empty($poll['allow_comments'])) json_out(['error' => 'Komentar dinonaktifkan'], 403);

    $st = db()->prepare('INSERT INTO comments (poll_id, name, message) VALUES (?,?,?)');
    $st->execute([(int)$poll['id'], mb_substr($name, 0, 50), mb_substr($message, 0, 500)]);
    json_out(['ok' => true]);
}

json_out(['error' => 'Metode tidak diizinkan'], 405);
