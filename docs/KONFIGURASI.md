# Panduan Konfigurasi

Semua konfigurasi runtime disimpan **di database** (tabel `app_settings`, `payment_setting`,
`digi_setting`), sehingga sebagian besar tidak perlu ubah kode. Base URL disetel di kode Android.

## 1. Base URL API (Android)

Ubah **hanya jika domain berubah**. Nilai saat ini: `https://www.rc-drive.com/`.

| Aplikasi | File |
|---|---|
| Customer | `Costumer/app/src/main/java/com/rcdriver/cs/constants/Constants.java` → `BASE_URL` |
| Driver | `Rcdriver/app/src/main/java/com/rcdriver/dr/constants/Constants.java` → `BASE_URL` |
| Merchant | `Mitra/app/src/main/java/com/rcdriver/mt/constants/Constants.java` → `BASE_URL` |

Turunannya (`CONNECTION = BASE_URL + "api/"`, `IMAGES*`) mengikuti otomatis. Jika API masih
HTTP (bukan HTTPS), `usesCleartextTraffic="true"` + `network_security_config.xml` sudah aktif.

## 2. Xendit (Payment — satu-satunya gateway aktif)

Konfigurasi via DB, **tanpa ubah kode**:

- Tabel `payment_setting`: `key_production`, `key_demo`, `is_demo` (1=demo/sandbox, 0=produksi),
  `status` (1=aktif).
- Callback yang harus terdaftar di Dashboard Xendit:
  - E-wallet: `https://<domain>/api/payment/callback_ewallet`
  - VA created: `https://<domain>/api/payment/callback_va_created`
  - VA/fixed paid: `https://<domain>/api/payment/callback_fixed_paid`
- Kode: `backend/application/controllers/api/Payment.php` + `models/Func_model.php`
  (endpoint `https://api.xendit.co/...`, auth Basic dengan `api_key:`).

**Menonaktifkan gateway lain (Midtrans/PayPal/Stripe/PayU/Mobilepulsa) dengan aman** — set flag
di `app_settings`: `midtrans_aktif=0`, `paypal_active=0`, `stripe_active=0`,
`mobilepulsa_pass`/status kosong. Jangan hapus kolom/kode (masih direferensikan aplikasi).

## 3. Digiflazz (PPOB — satu-satunya PPOB aktif)

- Tabel `digi_setting`: `username`, `key_production`, `key_development`, `is_demo`.
- Signature dibentuk otomatis: `MD5(username + key + ref_id)`.
- Callback Digiflazz → `https://<domain>/api/digital/callback_digi`.
- Endpoint Digiflazz: `https://api.digiflazz.com/v1/transaction` (di `Digital_model.php`).

## 4. Firebase / FCM

- Setiap app memakai `google-services.json` (plugin `com.google.gms.google-services`).
  Ganti dengan file dari project Firebase Anda (package: `com.rcdriver.cs`,
  `com.rcdriver.dr`, `com.rcdriver.mt`) di masing-masing `*/app/`.
- Server key FCM disimpan di `app_settings.fcm_key` (dipakai backend saat kirim notifikasi
  order). Pengiriman via `https://fcm.googleapis.com/fcm/send`.
- Penerima notif: `utils/api/service/MessagingService.java` di tiap app. Token device
  didaftarkan ke backend lewat `update_token_post` / `device_notif_post`.

## 5. Google Maps

- API key Maps di `app_settings.map_key` (dikirim ke app via endpoint `mapkey_post`).
- Driver juga punya `resValue "string","google_maps_api_key"` di `Rcdriver/app/build.gradle`
  (buildType release) dan Mapbox token di `Constants.MAPBOX_ACCESS_TOKEN`.

## 6. Keystore signing

Password keystore ter-hardcode di `app/build.gradle` (contoh: `1234567`). File `.jks`
(`rc.jks`, `mt.jks`, `rcdr.jks`) diharapkan di root tiap project. Untuk rilis produksi,
pindahkan kredensial ke `keystore.properties` yang tidak di-commit.
