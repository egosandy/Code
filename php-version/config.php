<?php
/**
 * Konfigurasi PollingKita (versi PHP)
 * ------------------------------------
 * Sesuaikan nilai di bawah ini sesuai hosting Anda.
 */

// ===== DATABASE =====
// Pilih driver: 'mysql' (umum di hosting) atau 'sqlite' (tanpa server DB)
define('DB_DRIVER', getenv('DB_DRIVER') ?: 'sqlite');

// --- Pengaturan MySQL (isi jika DB_DRIVER = 'mysql') ---
define('DB_HOST', getenv('DB_HOST') ?: 'localhost');
define('DB_NAME', getenv('DB_NAME') ?: 'pollingkita');
define('DB_USER', getenv('DB_USER') ?: 'root');
define('DB_PASS', getenv('DB_PASS') ?: '');
define('DB_CHARSET', 'utf8mb4');

// --- Pengaturan SQLite (isi jika DB_DRIVER = 'sqlite') ---
// File database akan dibuat otomatis. Pastikan folder dapat ditulis.
define('DB_SQLITE_PATH', __DIR__ . '/data/pollingkita.sqlite');

// ===== KEAMANAN =====
// WAJIB ganti dengan string acak panjang di produksi!
define('DEVICE_SALT', getenv('DEVICE_SALT') ?: 'ganti-dengan-salt-acak-yang-panjang');

// Kredensial admin awal (dibuat saat menjalankan install.php)
define('ADMIN_USERNAME', getenv('ADMIN_USERNAME') ?: 'admin');
define('ADMIN_PASSWORD', getenv('ADMIN_PASSWORD') ?: 'admin123');

// ===== APLIKASI =====
// URL dasar aplikasi (untuk membuat link share). Kosongkan untuk deteksi otomatis.
define('BASE_URL', getenv('BASE_URL') ?: '');

// Folder upload gambar (harus dapat ditulis / chmod 755 atau 775)
define('UPLOAD_DIR', __DIR__ . '/uploads');

// Zona waktu
date_default_timezone_set('Asia/Jakarta');
