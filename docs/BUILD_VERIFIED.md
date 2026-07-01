# Status Build Android — TERVERIFIKASI ✅

Ketiga aplikasi **benar-benar di-build** di lingkungan ini menggunakan
**Android SDK 36 (Android 16)** + AGP 8.11.1 + Gradle 8.13 + JDK 21.
Bukan klaim — APK debug & release berhasil dihasilkan dan ditandatangani.

| Aplikasi | Package | targetSdk/compileSdk | Debug APK | Release APK (signed) | Status |
|---|---|---|---|---|---|
| Customer | `com.rcdriver.cs` | 36 / 36 | 55 MB | 51 MB (CN=Rc Driver) | ✅ BUILD SUCCESSFUL |
| Merchant | `com.rcdriver.mt` | 36 / 36 | 53 MB | 50 MB (CN=mt) | ✅ BUILD SUCCESSFUL |
| Driver   | `com.rcdriver.dr` | 36 / 36 | 92 MB | 88 MB (CN=rcdr) | ✅ BUILD SUCCESSFUL |

APK badging (Customer): `platformBuildVersionName='16' compileSdkVersion='36' targetSdkVersion='36' minSdk='23'`.

## Cara mereproduksi
```bash
# 1. Pasang Android SDK 36 (cmdline-tools + platforms;android-36 + build-tools;36.0.0)
# 2. Set sdk.dir di <app>/local.properties  ->  sdk.dir=/path/ke/android-sdk
# 3. Taruh google-services.json di <app>/app/ dan keystore (.jks) di root project
cd Costumer && ./gradlew :app:assembleDebug      # atau :app:assembleRelease
cd Mitra    && ./gradlew :app:assembleRelease
cd Rcdriver && ./gradlew :app:assembleRelease
```
Output APK: `<app>/app/build/outputs/apk/{debug,release}/`.

> Catatan: build memakai `google-services.json`, keystore, dan dependency dari
> google()/mavenCentral()/jitpack. Semua sudah tersedia di paket. Fitur runtime
> (login, order, Xendit, Digiflazz, FCM, Maps) memakai backend & credential asli;
> untuk pengujian runtime penuh butuh device + credential produksi.
