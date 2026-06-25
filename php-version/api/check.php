<?php
// Cek apakah perangkat ini sudah memilih (untuk menampilkan status di halaman vote)
require_once __DIR__ . '/../lib/polls.php';

if ($_SERVER['REQUEST_METHOD'] !== 'POST') {
    json_out(['error' => 'Metode tidak diizinkan'], 405);
}

$body = read_json_body();
$slug = $body['slug'] ?? '';
$fingerprint = $body['fingerprint'] ?? null;

$poll = get_poll_by_slug($slug);
if (!$poll) {
    json_out(['error' => 'Polling tidak ditemukan'], 404);
}

$ip = client_ip();
$ua = $_SERVER['HTTP_USER_AGENT'] ?? '';
$deviceHash = device_hash($fingerprint, $ip, $ua);

$selected = device_ballot((int)$poll['id'], $deviceHash);

json_out([
    'voted' => $selected !== null,
    'selectedCandidateIds' => $selected ?? [],
]);
