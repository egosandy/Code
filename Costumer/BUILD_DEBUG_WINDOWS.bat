@echo off
setlocal
cd /d "%~dp0"
call gradlew --stop
call gradlew clean assembleDebug --stacktrace --warning-mode all
pause
