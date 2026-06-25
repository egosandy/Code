# 📦 Panduan Upload & Hosting PollingKita

Aplikasi ini dibuat dengan **Next.js (Node.js)**, jadi butuh hosting yang mendukung **Node.js 18+**.
Berikut beberapa pilihan dari yang paling mudah:

---

## ✅ Opsi 1 — VPS / Hosting Node.js (cPanel "Setup Node.js App") — DIREKOMENDASIKAN

1. **Upload** semua file (hasil ekstrak ZIP) ke folder aplikasi Anda (mis. `~/pollingkita`).
   Jangan ikut-upload `node_modules` — akan dibuat otomatis.
2. Buat file **`.env`** (salin dari `.env.example`) dan isi:
   ```env
   DATABASE_URL="file:./dev.db"
   JWT_SECRET="ISI-ACAK-PANJANG-MIN-32-KARAKTER"
   ADMIN_USERNAME="admin"
   ADMIN_PASSWORD="passwordKuatAnda"
   DEVICE_SALT="ISI-ACAK-LAIN"
   NEXT_PUBLIC_BASE_URL="https://domainanda.com"
   ```
3. Jalankan perintah (via SSH atau terminal cPanel):
   ```bash
   npm install
   npm run db:push
   npm run db:seed
   npm run build
   npm start
   ```
4. Di cPanel "Setup Node.js App": set **Application startup file** = `node_modules/next/dist/bin/next` dengan argumen `start`, atau gunakan **PM2**:
   ```bash
   npm i -g pm2
   pm2 start "npm start" --name pollingkita
   pm2 save
   ```
5. Arahkan domain ke port aplikasi (default **3000**) lewat reverse proxy / pengaturan cPanel.

---

## ✅ Opsi 2 — Vercel (paling cepat, gratis)

1. Upload folder ini ke repositori GitHub Anda.
2. Buka [vercel.com](https://vercel.com) → **Import Project** → pilih repo.
3. Tambahkan **Environment Variables** (sama seperti `.env` di atas).
4. **PENTING:** Vercel tidak menyimpan file permanen, jadi:
   - Ganti database ke **PostgreSQL** (gratis: Neon / Supabase / Vercel Postgres).
     Edit `prisma/schema.prisma` → ubah `provider = "sqlite"` menjadi `provider = "postgresql"`,
     lalu set `DATABASE_URL` ke connection string Postgres Anda.
   - Untuk foto, gunakan object storage (mis. Cloudinary/S3/R2) bila ingin permanen.
5. Deploy. Selesai — dapat URL `https://namaapp.vercel.app`.

---

## ✅ Opsi 3 — Railway / Render (VPS otomatis)

1. Hubungkan repo GitHub.
2. Set Environment Variables.
3. Build command: `npm install && npm run build`
   Start command: `npm start`
4. Tambahkan database PostgreSQL dari panel mereka & set `DATABASE_URL`.

---

## ⚠️ Tentang Shared Hosting biasa (hanya PHP/HTML)

Hosting yang **hanya** mendukung PHP/HTML (tanpa Node.js) **tidak bisa** menjalankan aplikasi ini secara langsung. Solusinya: pakai Opsi 1/2/3 di atas, atau minta provider mengaktifkan Node.js.

---

## 🔑 Setelah Online

- Login admin: `https://domainanda.com/admin`
- Akun default: `admin` / sesuai `ADMIN_PASSWORD` di `.env`
- **Wajib ganti** `JWT_SECRET`, `ADMIN_PASSWORD`, dan `DEVICE_SALT` agar aman.

Selamat! Aplikasi polling Anda siap dibagikan. 🗳️
