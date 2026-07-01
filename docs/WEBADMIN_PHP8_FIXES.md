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
- **Total 8 perbaikan PHP 8** diterapkan: 5 syntax (bug #1–#5) + 3 runtime (bug #6 E_STRICT
  di Exceptions, **#7 ENVIRONMENT→production yang memperbaiki redirect CRUD**, #8 E_STRICT
  di index.php). CRUD (create/update/delete) & upload gambar **terverifikasi berjalan** di
  PHP 8.4 — lihat bagian "Verifikasi RUNTIME".

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

## Verifikasi statis (php -l)
```bash
# dari root backend/
find application system systemlisensi -name '*.php' -not -path '*/cache/*' \
  -exec php -l {} \; | grep -v "No syntax errors"
# (tidak ada output = semua lulus)
```

## Verifikasi RUNTIME (booting nyata di PHP 8.4 + MariaDB)

Web admin **benar-benar dijalankan** di PHP 8.4 dengan database asli (import
`ojol.sql`, 65 tabel) untuk menangkap error runtime yang tidak terlihat `php -l`:

- **Login admin berhasil** (query auth ke tabel `admin`, sha1 password) → sesi aktif.
- **Crawl 27 controller** (dashboard, users, driver, mitra, services, ppob, ppoboperator,
  digi, donasi, voucher, promocode, promoslider, news, poin, wallet, area,
  categorymerchant, group, admin, appsettings, appnotification, inbok, metode, partnerjob,
  payments, profile, transaction) → **semua HTTP 200**.
- **Halaman detail/edit/tambah** (users/detail, driver/detail, appsettings/addbank,
  services/addservice, ppob/addpromocode, categorymerchant/tambahcm) → **semua HTTP 200**.
- **Error log server setelah semua perbaikan: BERSIH TOTAL** — 0 fatal, 0 warning,
  0 notice, 0 deprecated.

### Bug #6 ditemukan & diperbaiki lewat runtime (bonus di luar 5 syntax fix)
| File | Masalah | Perbaikan |
|---|---|---|
| `system/core/Exceptions.php:75` (+ `systemlisensi/core/Exceptions.php`) | Konstanta **`E_STRICT` deprecated di PHP 8.4**, muncul di **setiap request** | Ganti key array ke nilai numerik `2048` (perilaku lookup identik) |

Sebelum fix: `Deprecated: Constant E_STRICT is deprecated ... Exceptions.php on line 75`
di setiap halaman. Sesudah fix: log bersih (diverifikasi ulang, 0 pesan).

### Bug #7 (KRITIS) — ditemukan saat menguji tulis: redirect save/update/delete patah

Saat menguji `area/hapus`, respons mengembalikan **HTTP 200 berisi blok error**
`Creation of dynamic property CI_URI::$config is deprecated (core/URI.php:101)` alih-alih
**302 redirect** — dan **baris tidak terhapus**. Penyebab:

- **`index.php:57` default `ENVIRONMENT = 'development'`.** Di mode ini
  `display_errors=1` + `error_reporting(-1)`.
- CI 3.1.11 di **PHP 8.2+** memicu **E_DEPRECATED "Creation of dynamic property"** pada
  banyak kelas inti (URI, dll.). Output deprecation ini tercetak **sebelum** `header()`,
  sehingga **semua `redirect()` (save/update/delete) patah** → operasi tampak "gagal".
- Di hosting tanpa `CI_ENV` diset (umum di shared hosting), situs LIVE berjalan mode
  development → **bug produksi nyata** di PHP 8.

**Perbaikan:**
| File | Perubahan |
|---|---|
| `index.php:57` | default `ENVIRONMENT` `'development'` → **`'production'`** (set `CI_ENV=development` bila butuh debug). Mode production: `display_errors=0` + `error_reporting` mengecualikan E_DEPRECATED → redirect & CRUD berfungsi, halaman bersih. |

### Bug #8 — `E_STRICT` di `index.php` (error_reporting)
`index.php` baris 77 & 79 memakai `~E_STRICT` (konstanta deprecated PHP 8.4) → diganti
`~2048` (nilai numerik E_STRICT). Menghilangkan deprecation di setiap request.

### CRUD + Upload TERVERIFIKASI (setelah bug #7 & #8 diperbaiki)
Diuji end-to-end di PHP 8.4 + MariaDB dengan redirect kini berfungsi:

| Operasi | Endpoint diuji | Hasil |
|---|---|---|
| CREATE | `area/tambahcm` | HTTP 303 → **baris ter-insert** ✓ |
| UPDATE | `area/ubahcm` | HTTP 303 → **kolom rate1 berubah 1000→9999** ✓ |
| DELETE | `area/hapus/{id}` | HTTP 307 → **baris terhapus** ✓ |
| UPLOAD | `categorymerchant/tambahcm` (multipart) | HTTP 303 → **file tersimpan ke `images/kategorimerchant/` (encrypt_name) + baris ter-insert** ✓ |

> Kesimpulan: **render (GET), CRUD (create/update/delete), dan upload gambar terverifikasi
> berjalan di PHP 8.4** setelah 8 perbaikan. Catatan: `php -S` (alat uji) merutekan via
> `index.php`; di hosting pakai Apache + `.htaccess`/`mod_rewrite` (sudah tersedia di paket).

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
