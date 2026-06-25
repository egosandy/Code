# 🗳️ PollingKita

Aplikasi web **polling / pemilihan suara** modern & responsif — cocok untuk pemilihan kepala desa, ketua organisasi, ketua RT/RW, atau voting online apa pun.

Admin membuat polling lengkap dengan **foto kandidat, nomor urut, deskripsi, dan field kustom** (visi, asal dusun, dll), lalu **membagikan link**. Siapa pun dapat memilih, dengan jaminan **1 perangkat = 1 suara** berkat deteksi perangkat anti-vote ganda.

![status](https://img.shields.io/badge/status-ready-success) ![next](https://img.shields.io/badge/Next.js-14-black) ![license](https://img.shields.io/badge/license-MIT-blue)

---

## ✨ Fitur Utama

| Fitur | Keterangan |
|------|-----------|
| 🛡️ **Anti-vote ganda** | Deteksi perangkat via *fingerprint browser* (FingerprintJS) + IP + User-Agent. Dikunci di level database (unique constraint), aman dari race condition. |
| 📸 **Upload foto & custom input** | Foto kandidat, nomor urut, deskripsi, dan field tambahan dinamis (visi, asal, jabatan, dll). Gambar otomatis dikompres (WebP) via `sharp`. |
| 🔗 **Link dibagikan** | Tiap polling punya URL unik. Tombol bagikan ke WhatsApp / native share / salin link. |
| 📊 **Hasil real-time** | Grafik batang (Recharts), persentase, peringkat kandidat, dan total pemilih. Opsi tampilkan hasil: selalu / setelah memilih / setelah ditutup. |
| 🗳️ **Pilihan tunggal / ganda** | Mode "pilih 1" atau "pilih maksimal N". |
| ⏰ **Jadwal & status** | Tanggal mulai & selesai, status otomatis (aktif / terjadwal / berakhir / ditutup). |
| 💬 **Komentar** | Pengunjung dapat berkomentar (opsional per polling). |
| 🔐 **Panel admin** | Login aman (JWT httpOnly cookie + bcrypt), dashboard statistik, CRUD polling. |
| 📱 **Responsif & modern** | Tailwind CSS, animasi Framer Motion, optimal di HP/tablet/desktop. |

---

## 🚀 Cara Menjalankan

### 1. Prasyarat
- Node.js 18+ (direkomendasikan 20/22)

### 2. Instalasi
```bash
npm install
```

### 3. Konfigurasi environment
Salin `.env.example` ke `.env` lalu sesuaikan:
```bash
cp .env.example .env
```
```env
DATABASE_URL="file:./dev.db"          # SQLite (default). Untuk produksi pakai postgresql://...
JWT_SECRET="string-acak-panjang"       # WAJIB diganti di produksi
ADMIN_USERNAME="admin"
ADMIN_PASSWORD="admin123"              # ganti!
DEVICE_SALT="salt-acak"
NEXT_PUBLIC_BASE_URL="http://localhost:3000"
```

### 4. Siapkan database & data awal
```bash
npm run db:push     # buat tabel sesuai schema
npm run db:seed     # buat akun admin + 1 polling demo (pilkades-demo)
```

### 5. Jalankan
```bash
npm run dev         # mode pengembangan → http://localhost:3000
# atau
npm run build && npm start   # mode produksi
```

---

## 🧭 Cara Pakai

1. **Login admin** → buka `/admin` (default: `admin` / `admin123`).
2. **Buat polling** → klik *Buat Polling Baru*. Isi judul, deskripsi, banner, lalu tambahkan kandidat (foto, nomor urut, info kustom). Atur tipe pilihan, jadwal, dan opsi hasil.
3. **Bagikan link** → di halaman kelola polling, salin link atau bagikan via WhatsApp.
4. **Pemilih memilih** → buka link `/vote/<slug>`, pilih kandidat, kirim suara. Perangkat yang sama tidak bisa memilih dua kali.
5. **Pantau hasil** → tab *Hasil* menampilkan grafik & peringkat real-time.

---

## 🗂️ Struktur Proyek

```
src/
├── app/
│   ├── page.tsx                  # Beranda + daftar polling publik
│   ├── vote/[slug]/page.tsx      # Halaman voting publik
│   ├── admin/
│   │   ├── login/page.tsx        # Login admin
│   │   ├── page.tsx              # Dashboard
│   │   └── poll/{new,[id]}/      # Buat & kelola polling
│   └── api/                      # Route handler (auth, polls, vote, upload, comments)
├── components/                   # Navbar, VoteClient, PollForm, ResultsChart, dll
├── lib/
│   ├── db.ts                     # Prisma client
│   ├── auth.ts                   # Sesi JWT
│   ├── device.ts                 # Hash perangkat (anti vote ganda)
│   ├── upload.ts                 # Upload & kompres gambar
│   └── utils.ts                  # Helper
└── middleware.ts                 # Proteksi route admin
prisma/
├── schema.prisma                 # Model: Admin, Poll, Candidate, Ballot, Vote, Comment
└── seed.ts                       # Data awal
```

---

## 🔒 Bagaimana Deteksi Anti-Vote Ganda Bekerja

1. Browser menghitung **fingerprint** unik (FingerprintJS — stabil walau ganti jaringan).
2. Server menggabungkan fingerprint + IP + User-Agent menjadi **`deviceHash`** (SHA-256 + salt rahasia).
3. Tabel `Ballot` punya **unique constraint `(pollId, deviceHash)`** → satu perangkat hanya bisa membuat satu surat suara per polling. Race condition diblok di level DB (Prisma `P2002`).
4. IP disimpan dalam bentuk **hash** demi privasi.

> Catatan: tidak ada metode device-detection yang 100% anti-akal-akalan (incognito + clear data + ganti perangkat masih mungkin). Untuk pemilihan resmi, kombinasikan dengan verifikasi identitas (mis. OTP nomor HP) bila diperlukan.

---

## 🛠️ Teknologi

- **Next.js 14** (App Router, TypeScript) — framework full-stack
- **Tailwind CSS** + **Framer Motion** — UI responsif & animasi
- **Prisma ORM** + **SQLite** (default; mudah pindah ke PostgreSQL/MySQL)
- **FingerprintJS** — deteksi perangkat
- **Recharts** — visualisasi hasil
- **jose** + **bcryptjs** — autentikasi
- **sharp** — kompresi gambar

---

## 🌐 Deploy ke Produksi

1. Set `DATABASE_URL` ke PostgreSQL dan ubah `provider` di `prisma/schema.prisma` menjadi `postgresql`.
2. Ganti `JWT_SECRET`, `ADMIN_PASSWORD`, `DEVICE_SALT` dengan nilai acak yang aman.
3. Set `NEXT_PUBLIC_BASE_URL` ke domain Anda.
4. Jalankan `npm run db:push` (atau `prisma migrate deploy`) lalu `npm run build && npm start`.
5. Untuk penyimpanan gambar di platform serverless (Vercel), gunakan object storage (S3/R2) — folder `public/uploads` bersifat ephemeral di sebagian platform.

---

Dibuat untuk pemilihan yang **adil, modern, dan transparan**. 🇮🇩
