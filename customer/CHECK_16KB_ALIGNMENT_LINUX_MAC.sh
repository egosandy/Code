#!/usr/bin/env bash
set -euo pipefail
APK_OR_AAB="${1:-app/build/outputs/apk/debug/app-debug.apk}"
if [ ! -f "$APK_OR_AAB" ]; then
  echo "File tidak ditemukan: $APK_OR_AAB"
  echo "Contoh: ./CHECK_16KB_ALIGNMENT_LINUX_MAC.sh app/build/outputs/apk/debug/app-debug.apk"
  exit 1
fi
if ! command -v readelf >/dev/null 2>&1; then
  echo "readelf belum ada. Install binutils atau gunakan APK Analyzer Android Studio."
  exit 1
fi
TMP_DIR="$(mktemp -d)"
trap 'rm -rf "$TMP_DIR"' EXIT
unzip -q "$APK_OR_AAB" 'lib/*/*.so' 'base/lib/*/*.so' -d "$TMP_DIR" 2>/dev/null || true
FOUND=0
BAD64=0
INFO32=0
while IFS= read -r so; do
  FOUND=1
  min_align=$(python3 - "$so" <<'PY'
import subprocess, sys, re
so = sys.argv[1]
out = subprocess.check_output(['readelf', '-lW', so], text=True, errors='replace')
aligns = []
for line in out.splitlines():
    if re.match(r'\s*LOAD\s+', line):
        m = re.search(r'0x[0-9a-fA-F]+\s*$', line)
        if m:
            aligns.append(int(m.group(0), 16))
print(min(aligns) if aligns else 0)
PY
)
  rel="${so#$TMP_DIR/}"
  case "$rel" in
    lib/arm64-v8a/*|lib/x86_64/*|base/lib/arm64-v8a/*|base/lib/x86_64/*)
      if [ "$min_align" -ge 16384 ]; then
        echo "ALIGNED 16KB : $rel"
      else
        echo "UNALIGNED 64 : $rel (align=$min_align)"
        BAD64=1
      fi
      ;;
    *)
      if [ "$min_align" -ge 16384 ]; then
        echo "ALIGNED 32bit: $rel"
      else
        echo "INFO 32bit   : $rel (align=$min_align; 16KB requirement fokus 64-bit)"
        INFO32=1
      fi
      ;;
  esac
done < <(find "$TMP_DIR" -name '*.so' | sort)
if [ "$FOUND" -eq 0 ]; then
  echo "Tidak ada native library .so di APK/AAB. Aman dari isu 16KB native library."
  exit 0
fi
if [ "$BAD64" -eq 0 ]; then
  echo "Hasil: semua library 64-bit yang dicek sudah 16 KB aligned."
fi
exit "$BAD64"
