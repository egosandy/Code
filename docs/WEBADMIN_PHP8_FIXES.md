# Audit & Perbaikan Web Admin CodeIgniter 3 → PHP 8 (Tahap 10)

Web admin lengkap (folder `backend/`) sudah tersedia dan diaudit penuh dengan **PHP 8.4**
(`php -l` pada seluruh file).

## Ringkasan
- **CodeIgniter versi 3.1.11.** Penting: CI 3.1.11 mendahului dukungan PHP 8 penuh
  (baru resmi di **3.1.13**). Karena itu inti (`system/`) memuat bug syntax PHP 8 yang
  sudah diperbaiki di sini secara terarah.
- Hasil lint akhir: **1710 dari 1713 file PHP LULUS**. 3 sisanya adalah paket
  **dev-only** di `vendor/` (PHPUnit, vfsStream, sebastian/diff) yang **tidak dimuat saat
  runtime** — abaikan atau hapus folder test-nya untuk produksi.

## Bug PHP 8 yang ditemukan & diperbaiki (fatal, wajib)

| # | File | Masalah | Dampak di PHP 8 | Perbaikan |
|---|---|---|---|---|
| 1 | `system/libraries/Profiler.php` (3 baris) | `$this->_compile_{$section}` — sintaks properti dinamis kurung kurawal **dihapus di PHP 8** | **Parse error / fatal** saat file dimuat | Ganti ke `$this->{"_compile_".$section}` |
| 2 | `systemlisensi/libraries/Profiler.php` | Sama seperti #1 (salinan CI untuk modul lisensi) | Parse error / fatal | Sama seperti #1 |
| 3 | `application/models/Ci_ext_model.php` | `return true;` di body class (bukan di dalam method) — sintaks invalid | Parse error bila di-load; file tidak direferensikan | Dijadikan kelas model kosong yang valid |
| 4 | `application/libraries/class.phpmailer.php` | `each()` (baris ~1809) **dihapus di PHP 8**; `get_magic_quotes_runtime()`/`set_magic_quotes_runtime()` (baris ~1641) **dihapus** | **Fatal saat kirim email** (register, lupa password, konfirmasi order) | `each()` → `foreach`; blok magic_quotes dinetralkan (magic quotes sudah mati sejak PHP 5.4) |
| 5 | `application/libraries/class.smtp.php` (2 baris) | `@each($lines)` & `@each($lines_out)` — `each()` dihapus | Fatal saat SMTP mengirim data | `each()` → `foreach` (guard `(array)` untuk yang bisa null) |

Semua perbaikan **menjaga perilaku identik** — hanya mengganti konstruksi bahasa yang
dihapus dengan padanan setara. Tidak ada logika bisnis, query, atau output yang berubah.

## Verifikasi
```bash
# dari root backend/
find application system systemlisensi -name '*.php' -not -path '*/cache/*' \
  -exec php -l {} \; | grep -v "No syntax errors"
# (tidak ada output = semua lulus)
```

## Rekomendasi (opsional, tidak wajib untuk jalan)
1. **Upgrade CI core ke 3.1.13** (drop-in): ganti isi folder `system/` (dan
   `systemlisensi/` jika perlu) dengan rilis 3.1.13 dari codeigniter.com. Ini menutup
   sisa masalah PHP 8 tingkat framework yang mungkin muncul di jalur kode yang tidak
   ter-lint (mis. warning "passing null to non-nullable" pada string helper). Backup dulu.
2. **PHPMailer lama (2010)**: masih berfungsi setelah patch, tapi rawan. Bila ingin,
   migrasi ke PHPMailer 6.x (namespaced) — ini mengubah call site di `Email_model`,
   `Pelanggan_model`, `Func_model`, jadi lakukan dengan pengujian kirim email.
3. **php.ini hosting**: `error_reporting = E_ALL & ~E_DEPRECATED & ~E_NOTICE` agar warning
   CI lama tidak membanjiri layar/log (kosmetik).
4. **Hapus `vendor/phpunit`, `vendor/mikey179`, `vendor/sebastian/diff/tests`** di
   produksi (dev-only, satu-satunya file yang gagal lint).

## Catatan konfigurasi (sudah diaudit)
- `application/config/config.php` → `base_url = https://admin.ogotindonesiateknologi.com/`
  (domain web admin; API aplikasi tetap `rc-drive.com`). Berisi map key & FCM key —
  **di repo Git nilainya diredaksi**; di ZIP nilai asli dipertahankan.
- `application/config/database.php` → kredensial DB **diredaksi di Git**, asli di ZIP.
- CI core aktif `system/`; ada `systemlisensi/` (modul cek lisensi terpisah).
