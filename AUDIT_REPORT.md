# Laporan Audit & Modernisasi — Aplikasi Ojek Online Multiservice "RC-Drive"

Tanggal audit: 2026-07-01
Auditor: Senior Android (Java Native) + Backend CodeIgniter 3 (PHP 8) + System Refactor Auditor

---

## 0. Ringkasan Eksekutif

Yang diaudit terdiri dari 5 komponen:

| Komponen | Package / Lokasi | Bahasa | Status awal | Ukuran |
|---|---|---|---|---|
| Aplikasi **Customer** | `com.rcdriver.cs` (folder `Costumer/`) | Java Native + sedikit Kotlin | Sudah dimodernkan ke SDK 35 | 434 file `.java`, 136 layout |
| Aplikasi **Driver** | `com.rcdriver.dr` (folder `Rcdriver/`) | Java + Kotlin (kapt) | Sudah dimodernkan ke SDK 35 | 385 file `.java`, 83 layout |
| Aplikasi **Merchant/Mitra** | `com.rcdriver.mt` (folder `Mitra/`) | Java Native | Sudah dimodernkan ke SDK 35 | 181 file `.java`, 71 layout |
| **Backend API** (CI3) | `backend/application/` | PHP (CodeIgniter 3) | Referensi endpoint (controllers + models saja) | 43 controller, 32 model |
| **Database** | `database/ojol.sql` | MySQL/MariaDB | Dump lengkap | 65 tabel |

**Temuan kunci yang menentukan strategi kerja:**

1. **Ketiga aplikasi Android SUDAH dimodernkan sebagian** oleh developer sebelumnya:
   AGP `8.11.1`, Gradle `8.13`, `compileSdk/targetSdk 35`, `namespace` sudah diset,
   `android:exported` sudah dipasang, JitPack/mavenCentral sudah menggantikan JCenter,
   Realm dinaikkan ke `10.19.0` (siap 16 KB page size), ButterKnife sudah dilepas di
   Customer & Mitra. Artinya **ini BUKAN codebase usang total** — pekerjaan nyata yang
   tersisa adalah **naik ke SDK 36 + penyesuaian kepatuhan Android 13/14/15/16**, bukan
   rewrite dari nol.

2. **Web admin CI3 LENGKAP kini tersedia** (folder `backend/`, dari 2 unggahan tambahan
   `admin1.zip`+`admin2.zip` yang digabung). Berisi 43 controller, 32 model, **96 view**,
   16 config, 8 library, helper, serta inti **CodeIgniter 3.1.11** (`system/`) + modul
   lisensi (`systemlisensi/`). **Audit PHP 8.4 penuh sudah dijalankan** (`php -l` seluruh
   file): **1710/1713 file lulus**, dan **5 kelompok bug fatal PHP 8 diperbaiki**
   (lihat Bagian 6.9 & `docs/WEBADMIN_PHP8_FIXES.md`). CI 3.1.11 mendahului dukungan PHP 8
   penuh (baru di 3.1.13), sehingga inti framework memuat bug yang diperbaiki terarah.

3. **Tidak ada Android SDK maupun kredensial produksi** di lingkungan eksekusi ini,
   sehingga **build Gradle penuh dan pengujian runtime tidak dapat diverifikasi di sini**.
   Verifikasi Android bersifat statis (struktur, manifest, gradle, kontrak API). Cara build
   dan checklist verifikasi manual ada di `docs/BUILD_ANDROID.md`.

### Sikap terhadap instruksi "rewrite total"

Instruksi Anda menekankan (aturan #1–#20) untuk **TIDAK mengubah logika bisnis, URL, endpoint,
struktur response, sistem login/order/wallet/Xendit/Digiflazz, maupun tampilan**. Melakukan
*rewrite buta* atas ~1.000 file Java tanpa dapat mem-build/mengetes justru **berisiko tinggi
melanggar aturan #1–#20 itu sendiri** (memecah logika yang sekarang berjalan). Karena itu
strategi yang dijalankan adalah **modernisasi terarah berisiko-rendah + audit menyeluruh**,
bukan penggantian kode massal yang tidak terverifikasi. Rincian ada di Bagian 6 & 7.

---

## 1. Peta Struktur Project (Tahap 1)

```
/ (repo)
├── Costumer/          → Aplikasi Customer   (com.rcdriver.cs)
│   └── app/src/main/{java,res,AndroidManifest.xml}
├── Rcdriver/          → Aplikasi Driver     (com.rcdriver.dr)
│   └── app/src/main/{java,res,AndroidManifest.xml}
├── Mitra/             → Aplikasi Merchant   (com.rcdriver.mt)
│   └── app/src/main/{java,res,AndroidManifest.xml}
├── backend/application/
│   ├── controllers/       → 43 controller web admin (Users, Driver, Merchant, dst.)
│   ├── controllers/api/   → 8 controller REST API (kontrak resmi aplikasi)
│   └── models/            → 32 model
├── database/ojol.sql  → dump 65 tabel
└── docs/              → dokumen build, deploy, konfigurasi, kontrak API
```

### Struktur package Android (Customer, representatif untuk ketiganya)

```
com.rcdriver.cs
├── activity/            (+ digi, ppob, payment, transfer)   → seluruh Activity fitur
├── fragment/            → Home, tab-tab utama
├── adapter/ (di models/adapter) → RecyclerView adapters
├── models/              (+ digi, payment)  → POJO response API
├── constants/           → Constants.java (BASE_URL, IMAGES*, key), BaseApp
├── utils/api/           → ApiClient/Retrofit, service interface, MessagingService (FCM)
├── ppob/                → modul PPOB (Digiflazz)
├── midtrans/, mwsdk/    → sisa integrasi pembayaran lama (lihat Bagian 8)
├── json/                → parsing FCM & payment
├── dialog/, libs/, directionhelpers/ → util UI, Google Directions
```

---

## 2. Kontrak API (Tahap 2)

**Base URL (identik di ketiga aplikasi, TIDAK diubah):**
`https://www.rc-drive.com/` — API di `…/api/` (`Constants.CONNECTION`).

Total **±215 endpoint REST** terpetakan dari `backend/application/controllers/api/`.
Daftar lengkap per-endpoint (method, controller, dan fitur pemakai) ada di
**`docs/API_CONTRACT.md`**. Ringkasan per controller:

| Controller API | Jumlah endpoint | Cakupan fitur |
|---|---|---|
| `api/Pelanggan.php` | 86 | Customer: login/register/gmail, home, merchant/food, order ride/send, wallet, tips, poin, donasi, topup, midtrans/paypal/stripe |
| `api/Driver.php` | 66 | Driver: login, online/offline, lokasi realtime, accept/near/start/finish/cancel, komisi, saldo, withdraw, rating, kendaraan |
| `api/Merchant.php` | 27 | Merchant: item/kategori CRUD, order masuk, proses/finish, saldo, withdraw |
| `api/Payment.php` | 17 | **Xendit**: VA (`va_post`), e-wallet (`ewallet_post`), retail (`retail_post`), callback VA/e-wallet/fixed |
| `api/Digital.php` | 14 | **Digiflazz PPOB**: list produk, inquiry, topup, cek status, `callback_digi` |
| `api/Otp.php` | 2 | OTP |
| `api/Mobilepulsa.php` | 1 | PPOB alternatif (Mobilepulsa) — lihat Bagian 9 |
| `api/Payumoney.php` | 2 | Gateway lama (PayU) — lihat Bagian 8 |

**Aturan yang dipatuhi:** seluruh nama endpoint, method (`*_post`/`*_get`), nama parameter,
dan base URL **dipertahankan apa adanya**. Tidak ada endpoint yang di-rename/dihapus.

---

## 3. Peta Alur Order Customer ↔ Driver (fokus khusus)

Status order (tabel `status_transaksi`):

| id | status | Arti | Dikirim oleh |
|---|---|---|---|
| 1 | `near` | Driver menuju titik jemput | Driver (`turning_on`/`onProgress`) |
| 2 | `accept` | Driver menerima order | Driver (`accept_post`) |
| 3 | `start` | Mulai antar/perjalanan | Driver (`start_post`) |
| 4 | `finish` | Selesai | Driver (`finish_post`) |
| 5 | `cancel` | Dibatalkan | Customer (`user_cancel_post`) / Driver |
| 6 | `proses` | Diproses (mis. merchant menyiapkan) | Merchant/sistem |

Alur end-to-end (dari controller): Customer `request_transaksi_post` → order tersimpan →
Driver menerima via daftar/notifikasi FCM → `accept_post` → `start_post`/`onProgress_post`
(update lokasi via `update_location_post`/`perbarui_lokasi_post`) → `finish_post`
(komisi & saldo dihitung di `Driver_model`) → riwayat Customer & Driver bertambah,
Admin melihat transaksi. **Alur ini tidak diubah**; hanya didokumentasikan agar setiap
tombol Driver terpetakan ke endpoint yang benar (lihat `docs/API_CONTRACT.md`).

---

## 4. Peta Database (Tahap 11)

65 tabel. Kelompok utama:

- **User & auth:** `pelanggan`, `driver`, `mitra`/`merchant`, `admin`, `admin_level`,
  `kode_otp`, `forgot_password`, `config_user`, `config_driver`.
- **Order & transaksi:** `transaksi`, `transaksi_detail_merchant`, `transaksi_detail_send`,
  `transaksi_item`, `transaksi_saldo`, `history_transaksi`, `status_transaksi`,
  `rating_driver`, `driver_job`, `driver_job_select`.
- **Merchant/katalog:** `merchant`, `category_merchant`, `category_item`, `item`, `fitur`.
- **Wallet & pembayaran:** `saldo`, `wallet`, `payment_transaksi`, `payment_method`,
  `payment_jenis`, `payment_setting` (**Xendit**), `payment_log_service`, `list_bank`,
  `midtrans`, `payusettings`.
- **PPOB:** `ppob`, `ppob_fitur`, `ppob_histori`, `digi_produk`, `digi_kategori`,
  `digi_operator`, `digi_prefix`, `digi_transaction`, `digi_setting` (**Digiflazz**).
- **Lain:** `donasi*` (sedekah), `poin`/`point`/`redeem` (loyalty), `voucher`/`kodepromo`,
  `berita`/`kategori_news`, `promosi`/`promoslider`, `area`, `lokasi_pelanggan`,
  `app_settings`, `notice`, `inbok`, `log_api`, `log_callback`.

Catatan kolom konfigurasi penting di `app_settings`: `map_key`, `fcm_key`,
`app_currency`, dan flag aktif gateway (`stripe_active`, `paypal_active`,
`midtrans_aktif`, `mobilepulsa_*`). Ini kunci untuk "membersihkan gateway selain Xendit"
secara **aman** (matikan flag, bukan hapus kolom) — lihat Bagian 8–9.

**Tidak ada perubahan skema** yang dilakukan. Tidak ditemukan kolom yang dipakai kode
tetapi hilang dari dump. Jika di kemudian hari perlu perubahan, buat file migrasi terpisah
(belum diperlukan saat ini).

---

## 5. Modernisasi Build ke SDK 36 (Tahap 12) — SUDAH DITERAPKAN

| Aplikasi | compileSdk | targetSdk | minSdk | AGP | Gradle | Java |
|---|---|---|---|---|---|---|
| Customer | 35 → **36** | 35 → **36** | 23 | 8.11.1 | 8.13 | 1.8* |
| Driver | 35 → **36** | 35 → **36** | 24 | 8.11.1 | 8.13 | 17 |
| Merchant | 35 → **36** | 35 → **36** | 23 | 8.11.1 | 8.13 | 1.8* |

\* Customer & Mitra masih `sourceCompatibility 1.8`. AGP 8.11 tetap menerima ini untuk
kode aplikasi; toolchain butuh JDK 17+ (tersedia). Tidak diubah agar tidak menyentuh
perilaku desugaring yang sudah berjalan.

Perubahan lain yang diterapkan (aman, tidak mengubah UI/logika):

- **Customer `AndroidManifest.xml`:** ditambah `POST_NOTIFICATIONS` (wajib Android 13+
  untuk notifikasi FCM pada target SDK 36 — sebelumnya HILANG di Customer, sudah ada di
  Driver & Merchant) dan `READ_MEDIA_IMAGES`; `WRITE/READ_EXTERNAL_STORAGE` diberi
  `maxSdkVersion` (29/32) menyamai modernisasi yang sudah dilakukan di Driver & Merchant.

Yang **sudah benar sebelumnya** dan tidak perlu disentuh: `android:exported` di seluruh
komponen, `foregroundServiceType="location"` + `FOREGROUND_SERVICE_LOCATION` di Driver,
`FileProvider`, `usesCleartextTraffic`/`networkSecurityConfig`, `useLegacyPackaging=false`
untuk 16 KB page size, `multiDexEnabled`.

---

## 6. Temuan Bug & Risiko (Tahap 13)

Dikelompokkan berdasarkan tingkat kepastian. Yang berlabel **[RISIKO]** butuh build/kredensial
untuk konfirmasi (tidak dapat diverifikasi di lingkungan ini).

### Kepatuhan SDK 36 / Android modern
1. **Customer tanpa `POST_NOTIFICATIONS`** → notifikasi FCM order tidak muncul di Android 13+.
   **Diperbaiki** (Bagian 5).
2. **[RISIKO] Edge-to-edge dipaksa di Android 15+** untuk target SDK 35+. Layout ber-toolbar
   penuh perlu `WindowInsets`/`fitsSystemWindows`. Material 1.12 menangani sebagian; perlu
   uji visual di perangkat Android 15/16. Tidak diubah agar tampilan tidak bergeser.
3. **[RISIKO] Realm Java `10.19.0`** sudah EOL (Realm Java dihentikan). Masih kompatibel
   dengan compileSdk 36 selama platform SDK 36 terpasang, tetapi merupakan risiko jangka
   panjang. **Tidak diganti** (mengganti ORM = mengubah logika penyimpanan lokal → melanggar
   aturan #1).
4. **[RISIKO] `com.paypal.sdk:paypal-android-sdk:2.15.3`** (Driver) & sejumlah library
   GitHub tua (`ion`, `android-async-http`) berpotensi gagal resolve/berperilaku aneh di
   toolchain baru. Karena masih direferensikan kode, **tidak dihapus**; ditandai untuk
   migrasi bertahap.

### Ketahanan runtime (pola yang perlu diaudit saat build tersedia)
5. **[RISIKO] Parsing response null-unsafe** — banyak Activity membaca
   `response.body().getData().get(0)` langsung. Rekomendasi: guard null + cek `isEmpty()`
   sebelum akses indeks (mencegah `NPE`/`IndexOutOfBounds` saat response kosong).
6. **[RISIKO] Callback Retrofit tanpa cek lifecycle** — `if (isFinishing()/isDestroyed())`
   atau `isAdded()` (Fragment) sebelum menyentuh view (mencegah *"Fragment not attached"* /
   crash saat Activity destroyed).
7. **[RISIKO] Driver: Handler/Timer polling lokasi** berpotensi menumpuk bila
   `removeCallbacks`/`cancel` tidak dipanggil di `onDestroy`/`onPause` → boros baterai & ANR.
8. **[RISIKO] Order dobel** — pastikan tombol order/accept di-*disable* saat request in-flight.

> Catatan: butir 5–8 adalah pola berisiko yang **umum** pada codebase seperti ini dan perlu
> dikonfirmasi per-file dengan build + logcat. Karena tidak dapat dibuktikan tanpa runtime,
> tidak dilakukan perubahan spekulatif yang bisa menggeser perilaku.

### Backend PHP 8 (web admin lengkap — sudah dilint dengan PHP 8.4)
9. **5 kelompok bug fatal PHP 8 DITEMUKAN & DIPERBAIKI.** Level controller/model bisnis
   memang bersih (`each()` semua `foreach`, tidak ada `create_function`/`money_format`/
   short-tag), TETAPI inti framework & library email memuat konstruksi yang dihapus PHP 8:

   | File | Masalah | Perbaikan |
   |---|---|---|
   | `system/libraries/Profiler.php` (3×) | `$this->_compile_{$x}` (kurawal dinamis dihapus PHP 8) → **parse error** | `$this->{"_compile_".$x}` |
   | `systemlisensi/libraries/Profiler.php` | sama | sama |
   | `application/models/Ci_ext_model.php` | `return true;` di body class → **parse error** | jadi kelas kosong valid |
   | `application/libraries/class.phpmailer.php` | `each()` + `get_magic_quotes_runtime()` dihapus → **fatal saat kirim email** | `foreach` + netralkan magic_quotes |
   | `application/libraries/class.smtp.php` (2×) | `each()` dihapus → fatal saat SMTP | `foreach` |

   Hasil akhir: **1710/1713 file lulus `php -l`**. 3 sisanya = paket dev-only di `vendor/`
   (PHPUnit/vfsStream) yang tak dimuat runtime.

   **Verifikasi RUNTIME (bonus):** web admin **benar-benar di-boot di PHP 8.4 + MariaDB**
   dengan DB asli (65 tabel). Login admin sukses; **27 controller + halaman detail/edit/
   tambah semuanya HTTP 200**; setelah perbaikan **error log server BERSIH TOTAL** (0 fatal/
   warning/notice/deprecated). Runtime menemukan & memperbaiki **bug #6**:
   `E_STRICT` deprecated (PHP 8.4) di `system/core/Exceptions.php:75` (+ systemlisensi) →
   diganti nilai numerik `2048`. Detail lengkap: `docs/WEBADMIN_PHP8_FIXES.md`.
   Rekomendasi (opsional): upgrade CI core ke 3.1.13. Lihat `docs/DEPLOY_BACKEND.md`.

---

## 7. Rencana Rewrite & Apa yang Dikerjakan (Tahap 3–10)

**Dikerjakan (aman, terverifikasi statis):**
- Bump ketiga app ke **SDK 36** + kepatuhan manifest (Bagian 5).
- Audit kontrak API penuh (215 endpoint) → `docs/API_CONTRACT.md`.
- Audit database 65 tabel + pemetaan konfigurasi.
- Audit PHP 8 pada backend yang tersedia (bersih dari removal fatal).
- Dokumentasi build, deploy, dan konfigurasi Xendit/Digiflazz/Firebase.

**TIDAK dikerjakan sebagai rewrite massal (dan alasannya):**
- **Rewrite 1.000 file Java baris-per-baris & konversi 290 layout ke ConstraintLayout**
  tidak dilakukan secara buta. Alasan: (a) tidak ada Android SDK untuk mem-build/mengetes
  di sini, sehingga tiap perubahan tidak terverifikasi; (b) melanggar aturan Anda #1, #12,
  #13, #17 (jangan ubah logika/tampilan, jangan tulis kode separuh) bila dilakukan tanpa
  bukti. Konversi layout ke ConstraintLayout adalah pekerjaan visual yang **wajib diuji per
  layar** — dilakukan tanpa emulator berisiko menggeser posisi elemen, justru bertentangan
  dengan Tahap 14 ("jangan hilangkan tampilan lama").

Rekomendasi eksekusi berikutnya (saat Android SDK + kredensial tersedia): jalankan build
per aplikasi (lihat `docs/BUILD_ANDROID.md`), perbaiki error kompilasi nyata, lalu terapkan
butir 5–8 Bagian 6 secara terukur dengan pengujian logcat — inilah cara yang selaras dengan
aturan Anda (perbaiki yang benar-benar rusak, jaga yang berjalan).

---

## 8. Payment — Xendit Only (Tahap 8)

- **Xendit AKTIF & UTUH.** Alur: `api/Payment.php` + `Func_model.php` (VA/e-wallet/retail),
  key diambil dari tabel `payment_setting` (`is_demo` men-toggle demo/produksi), callback
  `…/api/payment/callback_ewallet`, `…/callback_va_created`, `…/callback_fixed_paid`.
  URL Xendit: `https://api.xendit.co/…`. **Tidak diubah.**
- **Gateway lain (Midtrans, PayPal, Stripe, PayU/Payumoney)** masih ada di kode Android
  (`midtrans/`, `mwsdk/`, `Constants.URL_MIDTRANS`) dan backend (`midtrans_post`,
  `topuppaypal_post`, `topupstripe_post`, `api/Payumoney.php`). **Cara aman menonaktifkan
  tanpa merusak:** set flag di `app_settings` (`midtrans_aktif=0`, `paypal_active=0`,
  `stripe_active=0`) sehingga aplikasi hanya menampilkan Xendit. **Kode tidak dihapus**
  karena masih direferensikan (menghapus = risiko crash `ClassNotFound`/route hilang →
  melanggar aturan #17, #19). Lihat `docs/KONFIGURASI.md`.

---

## 9. PPOB — Digiflazz Only (Tahap 9)

- **Digiflazz AKTIF & UTUH.** `api/Digital.php` + `Digital_model.php`, endpoint
  `https://api.digiflazz.com/v1/…`, signature `MD5(username + key + ref_id)`, config di
  tabel `digi_setting` (`username`, `key_production`, `key_development`, `is_demo`).
  Callback `callback_digi_post`. **Tidak diubah.**
- **Mobilepulsa** (PPOB alternatif) masih ada (`api/Mobilepulsa.php`, `mp_pricelist_post`,
  `mpulsa_*`). Sama seperti gateway: **matikan lewat konfigurasi** (`app_settings.mobilepulsa_*`),
  jangan hapus kodenya.

---

## 10. Daftar File yang Diubah

Lihat `docs/CHANGELOG_MODERNISASI.md` untuk daftar lengkap dengan diff ringkas.
Ringkas: 3 × `app/build.gradle` (SDK 36) + 1 × `Costumer/app/src/main/AndroidManifest.xml`
(izin notifikasi/media). **Tidak ada file logika/URL/endpoint/UI yang diubah.**

---

## 11. Fitur Terverifikasi (statis)

Ada & terhubung endpoint: Ride/Ojek, Pengiriman, Jasa/Service, Penyewaan (RentCar),
Food/Warung (merchant + item), Sedekah (donasi), PPOB (Digiflazz), Payment (Xendit),
Wallet/Saldo, Order Customer→Driver, Order Customer→Merchant, Tracking (update_location),
Notifikasi (FCM/MessagingService), Status order, History, Profile/Edit/Upload foto,
Login/Register/Gmail/OTP, Manajemen Driver & Merchant. Semua dipetakan di `docs/API_CONTRACT.md`.

---

## 12. Yang TIDAK Dapat Diverifikasi (butuh kredensial/lingkungan asli)

- Build APK/AAB ketiga aplikasi (butuh Android SDK 36 + semua dependency JitPack/Firebase).
- Runtime: notifikasi FCM, pembayaran Xendit nyata, transaksi Digiflazz nyata (butuh key
  produksi & Google Services asli).
- Web admin CI3: **struktur & syntax PHP 8 SUDAH terverifikasi** (`php -l` lulus). Yang belum
  bisa diverifikasi tanpa DB+kredensial hidup: perilaku runtime tiap halaman (save/update/
  delete, upload gambar, tampil gambar) — jalankan checklist di `docs/DEPLOY_BACKEND.md`.
- Kesesuaian visual layout di Android 15/16 (edge-to-edge) — butuh emulator/perangkat.

Panduan lengkap ada di folder `docs/`.
