# RC-Drive — Aplikasi Ojek Online Multiservice

Repositori hasil **audit + modernisasi** aplikasi ojek online multiservice (ride, kirim,
jasa, sewa, warung/food, sedekah, PPOB Digiflazz, payment Xendit, wallet).

## Isi
| Folder | Keterangan |
|---|---|
| `Costumer/` | Aplikasi Android **Customer** (`com.rcdriver.cs`) — Java Native |
| `Rcdriver/` | Aplikasi Android **Driver** (`com.rcdriver.dr`) — Java/Kotlin |
| `Mitra/` | Aplikasi Android **Merchant** (`com.rcdriver.mt`) — Java Native |
| `backend/application/` | Backend CodeIgniter 3 (controllers + models = acuan endpoint API) |
| `database/ojol.sql` | Dump database (65 tabel) |
| `AUDIT_REPORT.md` | **Laporan audit & modernisasi lengkap — baca ini dulu** |
| `docs/` | Kontrak API, konfigurasi, cara build, cara deploy, changelog |

## Dokumen penting
- **`AUDIT_REPORT.md`** — ringkasan eksekutif, peta struktur, kontrak API, database, bug, cakupan.
- `docs/API_CONTRACT.md` — ±215 endpoint (path + method).
- `docs/KONFIGURASI.md` — Base URL, Xendit, Digiflazz, Firebase/FCM, Maps.
- `docs/BUILD_ANDROID.md` — cara build ke Android SDK 36.
- `docs/DEPLOY_BACKEND.md` — cara deploy CI3 PHP 8.
- `docs/CHANGELOG_MODERNISASI.md` — daftar perubahan.

## Status singkat
- Ketiga app dinaikkan ke **compileSdk/targetSdk 36** (AGP 8.11.1, Gradle 8.13).
- Customer: izin `POST_NOTIFICATIONS` + media Android 13+ ditambahkan.
- **Xendit** (payment) & **Digiflazz** (PPOB) dipertahankan penuh; gateway/PPOB lain
  dinonaktifkan lewat konfigurasi DB, bukan dihapus.
- URL, endpoint, parameter, logika, dan UI **tidak diubah**.

> Catatan: build Android & runtime tidak dapat diverifikasi di lingkungan audit (tanpa
> Android SDK/kredensial). Lihat `AUDIT_REPORT.md` Bagian 12 untuk detail.
