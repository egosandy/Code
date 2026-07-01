# Changelog Modernisasi

Semua perubahan **tidak menyentuh** logika bisnis, URL, endpoint, nama parameter, struktur
response, sistem login/order/wallet/Xendit/Digiflazz, maupun tampilan UI.

## Android — SDK 36

### `Costumer/app/build.gradle`
- `compileSdkVersion 35` → `36`
- `targetSdkVersion 35` → `36`

### `Rcdriver/app/build.gradle`
- `compileSdkVersion 35` → `36`
- `targetSdkVersion 35` → `36`

### `Mitra/app/build.gradle`
- `compileSdk 35` → `36`
- `targetSdk 35` → `36`

## Android — Kepatuhan Manifest (hanya Customer, menyamai Driver & Merchant yang sudah benar)

### `Costumer/app/src/main/AndroidManifest.xml`
- **+** `android.permission.POST_NOTIFICATIONS` — wajib Android 13+ agar notifikasi FCM
  order tampil pada target SDK 36 (sebelumnya HILANG; sudah ada di Driver & Merchant).
- **+** `android.permission.READ_MEDIA_IMAGES` — pengganti granular READ_EXTERNAL_STORAGE
  (Android 13+) untuk pilih foto profil.
- `WRITE_EXTERNAL_STORAGE` diberi `android:maxSdkVersion="29"`.
- `READ_EXTERNAL_STORAGE` diberi `android:maxSdkVersion="32"`.

## Backend Web Admin (CodeIgniter 3.1.11) — perbaikan PHP 8

Web admin lengkap ditambahkan di `backend/` (gabungan `admin1.zip`+`admin2.zip`).
Diaudit dengan `php -l` (PHP 8.4): 1710/1713 file lulus. **5 kelompok bug fatal PHP 8
diperbaiki** (perilaku identik, hanya mengganti konstruksi yang dihapus PHP 8):

- `system/libraries/Profiler.php` (3 baris): `$this->_compile_{$x}` → `$this->{"_compile_".$x}`
- `systemlisensi/libraries/Profiler.php`: sama.
- `application/models/Ci_ext_model.php`: `return true;` di body class → kelas kosong valid.
- `application/libraries/class.phpmailer.php`: `each()` → `foreach`; hapus/netralkan
  `get_magic_quotes_runtime()`/`set_magic_quotes_runtime()`.
- `application/libraries/class.smtp.php` (2 baris): `each()` → `foreach`.

Perbaikan runtime (ditemukan saat boot nyata di PHP 8.4 + MariaDB):
- `system/core/Exceptions.php` + `systemlisensi/core/Exceptions.php`: konstanta `E_STRICT`
  deprecated (PHP 8.4) → nilai numerik `2048`.
- **`index.php`: default `ENVIRONMENT` `'development'` → `'production'`** (KRITIS) — di PHP 8.2+
  output deprecation "Creation of dynamic property" mematahkan semua `redirect()`
  save/update/delete; mode production menyembunyikan E_DEPRECATED sehingga CRUD berfungsi.
- `index.php`: `~E_STRICT` → `~2048` pada error_reporting.

CRUD (create/update/delete) & upload gambar **terverifikasi berjalan** di PHP 8.4.
Total 8 perbaikan PHP 8 web admin. Detail: `docs/WEBADMIN_PHP8_FIXES.md`.

## Database
- **Tidak ada perubahan skema.** `database/ojol.sql` disertakan sebagai acuan (65 tabel).

## Keamanan (redaksi khusus repo Git)
- Kredensial nyata (Google API key, DB user/pass, Stripe/Xendit token, FCM server key,
  Mapbox token, GitHub token) **diredaksi di salinan Git** agar lolos GitHub Push Protection.
  **File ZIP yang dikirim mempertahankan nilai asli** agar bisa langsung dijalankan.
  `github.properties` di-untrack (berisi GitHub PAT — sebaiknya dirotasi).

## Housekeeping repo
- Artefak build dihapus dari salinan repo (folder `build/`, `.gradle/`, `.idea/`, `release/`,
  file `*.apk/*.aab/*.iml`) agar repositori ramping. **Source, `libs/`, dan resource utuh.**

---

Untuk konteks lengkap lihat `AUDIT_REPORT.md`.
