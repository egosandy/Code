# Cara Build Aplikasi Android

Berlaku untuk ketiga project: `Costumer/` (Customer), `Rcdriver/` (Driver), `Mitra/` (Merchant).

## Prasyarat
- **Android Studio** terbaru (Ladybug/Meerkat atau lebih baru).
- **JDK 17** (AGP 8.11.1 mensyaratkan JDK 17+).
- **Android SDK Platform 36** + Build-Tools 36.x (SDK Manager → "Android 16 / API 36").
- Koneksi internet (dependency dari `google()`, `mavenCentral()`, `jitpack.io`).

## Konfigurasi yang sudah diterapkan
- `compileSdk = 36`, `targetSdk = 36` (ketiga app).
- AGP `8.11.1`, Gradle `8.13`.

## Langkah build (per aplikasi)
1. Buka folder project (mis. `Costumer/`) di Android Studio → **Open**.
2. Letakkan file signing di root project:
   - Customer: `rc.jks` • Driver: `rcdr.jks` • Merchant: `mt.jks`
   (password default di `app/build.gradle` = `1234567`).
3. Letakkan `google-services.json` (dari Firebase console) di `app/`.
4. **Gradle Sync**. Jika ada dependency lama gagal resolve, lihat "Troubleshooting".
5. Build:
   - Debug APK: `./gradlew :app:assembleDebug`
   - Release AAB: `./gradlew :app:bundleRelease`

Dari CLI (contoh Customer):
```bash
cd Costumer
./gradlew clean :app:assembleDebug
```

## Verifikasi manual yang disarankan (karena tidak bisa diuji otomatis di sini)
- Login/register → home tampil.
- Buat order ride/food → status `accept → near/start → finish` sinkron di Customer & Driver.
- Notifikasi FCM masuk (izinkan POST_NOTIFICATIONS saat diminta di Android 13+).
- Topup via Xendit membuka halaman pembayaran & callback meng-update saldo.
- Transaksi PPOB Digiflazz muncul & status terbaca.
- Uji di emulator **Android 16 (API 36)** untuk memastikan edge-to-edge tidak memotong UI.

## Troubleshooting
- **Dependency GitHub tua gagal (paypal-sdk, ion, android-async-http, dsb.):** pastikan
  `maven { url 'https://jitpack.io' }` ada (sudah ada). Jika benar-benar hilang dari
  repositori, cari mirror atau versi pengganti fungsi-setara (lihat catatan risiko di
  `AUDIT_REPORT.md` Bagian 6).
- **Realm 16 KB / native lib:** sudah `realm-gradle-plugin:10.19.0` + `useLegacyPackaging=false`.
- **Java version:** set Gradle JDK ke 17 (Settings → Build Tools → Gradle → Gradle JDK).
