@echo off
setlocal
cd /d "%~dp0"
echo =====================================================
echo  Asia Driver - Clean Gradle Cache and Build Debug
echo =====================================================
echo.
echo Menutup Gradle daemon...
call gradlew.bat --stop

echo.
echo Menghapus cache project...
if exist ".gradle" rmdir /s /q ".gradle"
if exist "build" rmdir /s /q "build"
if exist "app\build" rmdir /s /q "app\build"
if exist "app\.cxx" rmdir /s /q "app\.cxx"

echo.
echo Menghapus cache transform Gradle user yang sering rusak...
if exist "%USERPROFILE%\.gradle\caches\8.13\transforms" rmdir /s /q "%USERPROFILE%\.gradle\caches\8.13\transforms"
if exist "%USERPROFILE%\.gradle\caches\modules-2\files-2.1\com.android.tools.build\gradle" echo Cache AGP tetap dipertahankan agar tidak download ulang bila sudah ada.

echo.
echo Build debug...
call gradlew.bat clean assembleDebug --stacktrace
if errorlevel 1 (
    echo.
    echo BUILD GAGAL. Buka log di atas dan kirim bagian FATAL/FAILED terbaru.
    pause
    exit /b 1
)

echo.
echo BUILD BERHASIL. APK debug ada di app\build\outputs\apk\debug\
pause
