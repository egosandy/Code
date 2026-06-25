<?php
// Upload gambar (hanya admin)
require_once __DIR__ . '/../lib/helpers.php';
require_admin_api();

if ($_SERVER['REQUEST_METHOD'] !== 'POST') {
    json_out(['error' => 'Metode tidak diizinkan'], 405);
}
if (empty($_FILES['file'])) {
    json_out(['error' => 'Tidak ada file'], 400);
}

try {
    $path = save_image($_FILES['file']);
    json_out(['url' => base_path() . '/' . $path]);
} catch (Throwable $ex) {
    json_out(['error' => $ex->getMessage()], 400);
}
