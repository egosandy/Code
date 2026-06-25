<?php
require_once __DIR__ . '/db.php';

if (session_status() === PHP_SESSION_NONE) {
    session_start();
}

/* ============== Output ============== */
function json_out($data, int $code = 200): void
{
    http_response_code($code);
    header('Content-Type: application/json; charset=utf-8');
    echo json_encode($data, JSON_UNESCAPED_UNICODE | JSON_UNESCAPED_SLASHES);
    exit;
}

function e(?string $s): string
{
    return htmlspecialchars($s ?? '', ENT_QUOTES, 'UTF-8');
}

function read_json_body(): array
{
    $raw = file_get_contents('php://input');
    $data = json_decode($raw, true);
    return is_array($data) ? $data : [];
}

/* ============== Auth ============== */
function is_logged_in(): bool
{
    return !empty($_SESSION['admin_id']);
}

function require_admin(): void
{
    if (!is_logged_in()) {
        header('Location: ' . base_path() . '/admin/login.php');
        exit;
    }
}

function require_admin_api(): void
{
    if (!is_logged_in()) {
        json_out(['error' => 'Tidak terautentikasi'], 401);
    }
}

/* ============== URL ============== */
/** Path dasar aplikasi (folder tempat app dipasang), mis. "" atau "/polling". */
function base_path(): string
{
    // Direktori root aplikasi relatif terhadap document root
    $scriptDir = str_replace('\\', '/', dirname($_SERVER['SCRIPT_NAME'] ?? ''));
    // Jika berada di subfolder /admin atau /api, naik satu level
    $scriptDir = preg_replace('#/(admin|api)$#', '', $scriptDir);
    return rtrim($scriptDir, '/');
}

function base_url(): string
{
    if (BASE_URL) return rtrim(BASE_URL, '/');
    $proto = (!empty($_SERVER['HTTPS']) && $_SERVER['HTTPS'] !== 'off') ? 'https' : 'http';
    $host = $_SERVER['HTTP_HOST'] ?? 'localhost';
    return $proto . '://' . $host . base_path();
}

function redirect(string $path): void
{
    header('Location: ' . $path);
    exit;
}

/* ============== Device detection (anti vote ganda) ============== */
function client_ip(): string
{
    foreach (['HTTP_CF_CONNECTING_IP', 'HTTP_X_FORWARDED_FOR', 'HTTP_X_REAL_IP', 'REMOTE_ADDR'] as $k) {
        if (!empty($_SERVER[$k])) {
            $parts = explode(',', $_SERVER[$k]);
            return trim($parts[0]);
        }
    }
    return '0.0.0.0';
}

function hash_value(string $value): string
{
    return hash('sha256', $value . '::' . DEVICE_SALT);
}

/**
 * Membuat device hash gabungan fingerprint + IP + UA.
 * Fingerprint (FingerprintJS) jadi sinyal utama bila tersedia.
 */
function device_hash(?string $fingerprint, string $ip, string $ua): string
{
    $primary = ($fingerprint && strlen($fingerprint) > 8) ? $fingerprint : ($ip . '|' . $ua);
    return hash('sha256', $primary . '::' . DEVICE_SALT);
}

/* ============== Util ============== */
function gen_slug(string $title): string
{
    $base = strtolower(trim($title));
    $base = preg_replace('/[^a-z0-9\s-]/', '', $base);
    $base = preg_replace('/\s+/', '-', $base);
    $base = preg_replace('/-+/', '-', $base);
    $base = substr($base, 0, 40);
    $base = trim($base, '-');
    if ($base === '') $base = 'polling';
    return $base . '-' . substr(bin2hex(random_bytes(6)), 0, 8);
}

function format_number(int $n): string
{
    return number_format($n, 0, ',', '.');
}

function percentage(int $value, int $total): float
{
    if ($total === 0) return 0;
    return round(($value / $total) * 1000) / 10;
}

function format_date(?string $date): string
{
    if (!$date) return '-';
    $ts = strtotime($date);
    $bulan = ['', 'Januari', 'Februari', 'Maret', 'April', 'Mei', 'Juni',
        'Juli', 'Agustus', 'September', 'Oktober', 'November', 'Desember'];
    return date('j', $ts) . ' ' . $bulan[(int)date('n', $ts)] . ' ' . date('Y H:i', $ts);
}

/** Status polling berdasarkan flag aktif & tanggal. */
function poll_status(array $poll): string
{
    if (empty($poll['is_active'])) return 'closed';
    $now = time();
    if (!empty($poll['start_date']) && strtotime($poll['start_date']) > $now) return 'scheduled';
    if (!empty($poll['end_date']) && strtotime($poll['end_date']) < $now) return 'ended';
    return 'active';
}

/* ============== Upload gambar ============== */
function save_image(array $file): string
{
    if (!empty($file['error']) && $file['error'] !== UPLOAD_ERR_OK) {
        throw new RuntimeException('Gagal mengunggah file');
    }
    if ($file['size'] > 5 * 1024 * 1024) {
        throw new RuntimeException('Ukuran gambar maksimal 5 MB');
    }
    $mime = function_exists('mime_content_type')
        ? mime_content_type($file['tmp_name'])
        : ($file['type'] ?? '');
    $map = ['image/jpeg' => 'jpg', 'image/png' => 'png', 'image/webp' => 'webp', 'image/gif' => 'gif'];
    if (!isset($map[$mime])) {
        throw new RuntimeException('Format gambar tidak didukung (JPG, PNG, WEBP, GIF)');
    }

    if (!is_dir(UPLOAD_DIR)) @mkdir(UPLOAD_DIR, 0775, true);
    $ext = $map[$mime];
    $name = bin2hex(random_bytes(8)) . '.' . $ext;
    $dest = UPLOAD_DIR . '/' . $name;

    // Coba kompres/resize dengan GD bila tersedia
    if (function_exists('imagecreatefromstring') && $mime !== 'image/gif') {
        $img = @imagecreatefromstring(file_get_contents($file['tmp_name']));
        if ($img !== false) {
            $w = imagesx($img);
            $h = imagesy($img);
            $max = 800;
            $scale = min(1, $max / max($w, $h));
            $nw = (int)($w * $scale);
            $nh = (int)($h * $scale);
            $resized = imagecreatetruecolor($nw, $nh);
            imagealphablending($resized, false);
            imagesavealpha($resized, true);
            imagecopyresampled($resized, $img, 0, 0, 0, 0, $nw, $nh, $w, $h);
            if ($ext === 'png') {
                imagepng($resized, $dest, 6);
            } elseif ($ext === 'webp') {
                imagewebp($resized, $dest, 82);
            } else {
                imagejpeg($resized, $dest, 82);
            }
            imagedestroy($img);
            imagedestroy($resized);
            return 'uploads/' . $name;
        }
    }

    // Fallback: simpan apa adanya
    if (!move_uploaded_file($file['tmp_name'], $dest)) {
        // fallback untuk lingkungan non-HTTP upload
        copy($file['tmp_name'], $dest);
    }
    return 'uploads/' . $name;
}
