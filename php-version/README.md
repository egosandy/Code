# 🗳️ PollingKita — Versi PHP + JavaScript

Aplikasi web **polling / pemilihan suara** modern & responsif, dibuat dengan **PHP murni + JavaScript** (tanpa framework, tanpa Node.js, tanpa build step). Cocok untuk **shared hosting biasa** (cPanel) maupun VPS.

Fitur sama persis seperti versi Next.js: admin membuat polling lengkap dengan foto kandidat, nomor urut, deskripsi & field kustom; bagikan link; pemilih memberikan suara dengan jaminan **1 perangkat = 1 suara**.

---

## ✨ Fitur

- 🛡️ **Anti-vote ganda** — fingerprint browser (FingerprintJS) + IP + User-Agent → `device_hash`, dikunci unique constraint `(poll_id, device_hash)` di database (aman dari race condition via transaksi).
- 📸 **Upload foto & input kustom** — foto kandidat, nomor urut, deskripsi, field tambahan dinamis (visi, asal dusun, dll). Gambar otomatis dikompres/diperkecil via GD.
- 🔗 **Link dibagikan** — tiap polling punya URL unik + tombol WhatsApp / salin / native share.
- 📊 **Hasil real-time** — grafik batang (Chart.js), persentase, peringkat kandidat.
- 🗳️ **Pilihan tunggal / ganda**, jadwal mulai-selesai, opsi tampilan hasil, komentar publik.
- 🔐 **Panel admin** — login aman (PHP session + `password_hash`), dashboard, CRUD polling.
- 📱 **Responsif & modern** — Tailwind (CDN), tanpa proses build.

---

## 🧩 Teknologi

| Bagian | Teknologi |
|--------|-----------|
| Backend | PHP 7.4+ / 8.x (PDO) |
| Database | **MySQL** (hosting) atau **SQLite** (tanpa server DB) |
| Frontend | HTML + Tailwind CSS (CDN) + JavaScript murni |
| Grafik | Chart.js (CDN) |
| Device detection | FingerprintJS (CDN) |
| Gambar | GD (kompresi) |

---

## 🚀 Instalasi Cepat (Lokal / VPS)

1. Salin semua file ke folder web (mis. `htdocs/polling` atau `public_html`).
2. Buka **`config.php`**, atur database:
   - **SQLite (paling mudah, tanpa setup):** biarkan `DB_DRIVER = 'sqlite'`.
   - **MySQL:** set `DB_DRIVER = 'mysql'` lalu isi `DB_HOST`, `DB_NAME`, `DB_USER`, `DB_PASS` (buat database kosong terlebih dulu).
   - **Ganti** `DEVICE_SALT` dan `ADMIN_PASSWORD`!
3. Buka di browser: **`http://domain-anda/install.php`** → membuat tabel + akun admin + polling demo.
4. **Hapus `install.php`** setelah selesai.
5. Login admin di `http://domain-anda/admin/login.php` (default `admin` / `admin123`).

> Pastikan folder `uploads/` (dan `data/` jika pakai SQLite) **dapat ditulis** — `chmod 755` atau `775`.

---

## 🗂️ Struktur File

```
php-version/
├── config.php            # Konfigurasi (DB, secret, base URL)
├── install.php           # Installer sekali jalan (buat tabel + admin + demo)
├── index.php             # Beranda + daftar polling
├── vote.php              # Halaman voting publik (?slug=...)
├── admin/
│   ├── login.php         # Login
│   ├── logout.php
│   ├── index.php         # Dashboard
│   ├── poll-form.php     # Buat / edit polling
│   └── poll-manage.php   # Hasil + grafik + share
├── api/
│   ├── vote.php          # Kirim suara (deteksi perangkat)
│   ├── check.php         # Cek status sudah memilih
│   ├── poll.php          # Data + hasil (JSON)
│   ├── poll-save.php     # Simpan polling (admin)
│   ├── poll-delete.php   # Hapus polling (admin)
│   ├── comment.php       # Komentar
│   └── upload.php        # Upload gambar (admin)
├── lib/                  # db.php, helpers.php, layout.php, polls.php
├── uploads/              # Foto kandidat & banner (writable)
└── data/                 # File SQLite (jika dipakai, writable)
```

---

## 🔒 Cara Deteksi Anti-Vote Ganda

1. Browser menghitung **fingerprint** unik (FingerprintJS).
2. Server menggabungkan fingerprint + IP + User-Agent → **`device_hash`** (SHA-256 + salt rahasia).
3. Tabel `ballots` punya **`UNIQUE(poll_id, device_hash)`** → satu perangkat hanya bisa satu surat suara per polling. Penyimpanan dibungkus **transaksi**; duplikat ditolak otomatis (kode error `23000`).
4. IP disimpan sebagai **hash** demi privasi.

> Catatan: tidak ada metode device-detection yang 100% anti-akal-akalan (incognito + hapus data + ganti perangkat masih mungkin). Untuk pemilihan resmi, tambahkan verifikasi identitas (mis. OTP nomor HP).

---

## ⚙️ Keamanan Produksi

- Ganti `DEVICE_SALT`, `ADMIN_PASSWORD` di `config.php`.
- Hapus `install.php` setelah instalasi.
- Pakai HTTPS.
- File `.htaccess` sudah melindungi folder `lib/`, `data/`, dan file database.

Selamat menggunakan! 🇮🇩
