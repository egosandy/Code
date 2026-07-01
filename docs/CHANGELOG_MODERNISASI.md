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

## Backend / Database
- **Tidak ada perubahan kode.** Backend & SQL disertakan apa adanya sebagai acuan.
- Audit PHP 8 & pemetaan endpoint/tabel didokumentasikan (tidak mengubah file).

## Housekeeping repo
- Artefak build dihapus dari salinan repo (folder `build/`, `.gradle/`, `.idea/`, `release/`,
  file `*.apk/*.aab/*.iml`) agar repositori ramping. **Source, `libs/`, dan resource utuh.**

---

Untuk konteks lengkap lihat `AUDIT_REPORT.md`.
