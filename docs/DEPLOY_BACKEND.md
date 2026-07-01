# Cara Deploy Web Admin / Backend (CodeIgniter 3, PHP 8) ke Hosting

> **Penting:** paket yang Anda unggah (`application.zip`) **hanya berisi
> `application/controllers/` + `application/models/`** sebagai acuan kontrak endpoint.
> Tidak termasuk folder `system/`, `application/config/`, `application/views/`,
> `application/helpers/`, `application/libraries/`, maupun `index.php`. Untuk
> menjalankan web admin utuh Anda perlu menggabungkan file ini ke instalasi CI3 lengkap
> yang sudah berjalan di server (`public_html/admin/`).

## 1. Struktur di hosting (cPanel / shared hosting)
```
public_html/
├── index.php                 (front controller CI3)
├── system/                   (CodeIgniter core — gunakan >= 3.1.13 untuk PHP 8)
├── application/
│   ├── config/               (config.php, database.php, routes.php, rest.php)
│   ├── controllers/          ← dari paket ini
│   ├── controllers/api/      ← dari paket ini (kontrak API aplikasi)
│   ├── models/               ← dari paket ini
│   ├── views/                (web admin — tidak disertakan)
│   ├── helpers/ libraries/
├── images/                   (upload: pelanggan, fotodriver, merchant, ppob, dst.)
```

## 2. Kompatibilitas PHP 8
- Gunakan **CodeIgniter 3.1.13+** (versi ini yang resmi mendukung PHP 8.0/8.1).
- File controller/model yang diberikan **sudah bersih** dari fungsi yang dihapus PHP 8
  (`each()`, `create_function()`, `money_format()`, akses `$var{...}`, short-open-tag) —
  lihat `AUDIT_REPORT.md` Bagian 6.9.
- Set di `php.ini`/hosting: `error_reporting = E_ALL & ~E_DEPRECATED & ~E_NOTICE` untuk
  menghindari warning CI3 lama membanjiri log (opsional).

## 3. Konfigurasi wajib setelah upload
1. `application/config/config.php` → `$config['base_url'] = 'https://<domain>/';`
2. `application/config/database.php` → kredensial DB hasil import `database/ojol.sql`.
3. Import `database/ojol.sql` via phpMyAdmin.
4. Permission folder upload:
   ```bash
   chmod -R 755 images/
   # sub-folder yang dipakai: images/{pelanggan,fotodriver,merchant,kategorimerchant,
   #   itemmerchant,ppob,promo,berita,bank,icon,fitur,poin}
   ```
5. Set konfigurasi runtime lewat tabel DB (lihat `docs/KONFIGURASI.md`):
   `app_settings` (map_key, fcm_key, currency, flag gateway), `payment_setting` (Xendit),
   `digi_setting` (Digiflazz).

## 4. Verifikasi cepat
- `https://<domain>/api/pelanggan/setting` (POST) mengembalikan JSON setting.
- Callback Xendit & Digiflazz dapat diakses publik (tanpa auth Basic yang memblokir).
- Upload gambar dari web admin tersimpan ke `images/<subfolder>/` dan tampil di aplikasi.

## 5. Catatan out-of-scope
Perbaikan halaman web admin (form save/update/delete, tampil gambar) memerlukan folder
`views/` + `config/` yang **tidak disertakan** dalam unggahan. Setelah Anda menyediakan
instalasi CI3 lengkap, audit per-halaman (Tahap 10 & "Fokus Khusus Web Admin") dapat
dilanjutkan mengikuti checklist di prompt.
