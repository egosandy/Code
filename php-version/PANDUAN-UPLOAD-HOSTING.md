# 📦 Panduan Upload ke Hosting (cPanel) — Versi PHP

Versi PHP ini **bisa langsung jalan di shared hosting biasa** (yang hanya mendukung PHP + MySQL) — tidak perlu Node.js.

---

## Langkah-langkah (cPanel + MySQL)

### 1. Buat Database MySQL
1. Login cPanel → **MySQL Databases**.
2. Buat database baru, mis. `namauser_polling`.
3. Buat user MySQL + password, lalu **Add User to Database** dengan **ALL PRIVILEGES**.
4. Catat: nama database, username, password, host (biasanya `localhost`).

### 2. Upload File
1. Kompres isi folder `php-version` (atau upload langsung).
2. cPanel → **File Manager** → masuk `public_html` (atau subfolder, mis. `public_html/polling`).
3. **Upload** semua file lalu **Extract** bila berupa ZIP.

### 3. Atur Konfigurasi
Edit **`config.php`** lewat File Manager (klik kanan → Edit):
```php
define('DB_DRIVER', 'mysql');
define('DB_HOST', 'localhost');
define('DB_NAME', 'namauser_polling');
define('DB_USER', 'namauser_dbuser');
define('DB_PASS', 'password_database_anda');

define('DEVICE_SALT', 'ISI-ACAK-PANJANG-UNIK');   // ganti!
define('ADMIN_USERNAME', 'admin');
define('ADMIN_PASSWORD', 'passwordKuatAnda');      // ganti!
```

### 4. Set Izin Folder
Klik kanan folder **`uploads`** → **Change Permissions** → `755` (atau `775` bila perlu).

### 5. Jalankan Installer
Buka di browser: `https://domainanda.com/install.php`
(atau `https://domainanda.com/polling/install.php` bila di subfolder).
Akan muncul tanda ✅ bila berhasil.

### 6. Amankan
- **Hapus** file `install.php` lewat File Manager.
- Selesai! Buka `https://domainanda.com/admin/login.php`.

---

## Alternatif: Tanpa MySQL (SQLite)

Jika hosting mendukung **PDO SQLite** (umumnya iya):
1. Di `config.php` cukup biarkan `define('DB_DRIVER', 'sqlite');`.
2. Pastikan folder **`data/`** dapat ditulis (`chmod 755`/`775`).
3. Jalankan `install.php`. Tidak perlu membuat database MySQL sama sekali.

---

## ✅ Setelah Online

- Beranda: `https://domainanda.com/`
- Login admin: `https://domainanda.com/admin/login.php`
- Buat polling → bagikan link `https://domainanda.com/vote.php?slug=...`

## ❓ Masalah Umum

| Masalah | Solusi |
|---------|--------|
| Halaman putih / Error 500 | Cek `config.php` (kredensial DB). Lihat Error Log di cPanel. |
| Foto gagal upload | Set folder `uploads` ke `755`/`775`. |
| "could not find driver" | Aktifkan ekstensi `pdo_mysql` / `pdo_sqlite` di **Select PHP Version → Extensions**. |
| Link share salah | Isi `BASE_URL` di `config.php` dengan domain lengkap Anda. |

Selamat! Aplikasi polling Anda siap dibagikan. 🗳️
