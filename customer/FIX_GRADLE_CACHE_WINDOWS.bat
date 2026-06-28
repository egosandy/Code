@echo off
setlocal
cd /d "%~dp0"
echo =======================================================
echo  FIX CACHE GRADLE - Costumer
echo =======================================================
echo Menutup Gradle daemon...
call gradlew --stop

echo Menghapus cache Gradle yang rusak sesuai error metadata.bin...
if exist "%USERPROFILE%\.gradle\caches\8.13\transforms" rmdir /s /q "%USERPROFILE%\.gradle\caches\8.13\transforms"
if exist "%USERPROFILE%\.gradle\caches\8.13\journal-1" rmdir /s /q "%USERPROFILE%\.gradle\caches\8.13\journal-1"
if exist "%USERPROFILE%\.gradle\caches\transforms-*" rmdir /s /q "%USERPROFILE%\.gradle\caches\transforms-*"

echo Membersihkan cache project...
if exist ".gradle" rmdir /s /q ".gradle"
if exist "build" rmdir /s /q "build"
if exist "app\build" rmdir /s /q "app\build"
if exist "app\.cxx" rmdir /s /q "app\.cxx"

echo Sinkron ulang dependency dan build debug...
call gradlew clean --refresh-dependencies --stacktrace
if errorlevel 1 goto fail
call gradlew assembleDebug --stacktrace --warning-mode all
if errorlevel 1 goto fail

echo.
echo BUILD DEBUG BERHASIL.
pause
exit /b 0

:fail
echo.
echo BUILD MASIH GAGAL. Copy log bagian * What went wrong: dan kirim kembali.
pause
exit /b 1
