import { createHash } from "crypto";
import { NextRequest } from "next/server";

const SALT = process.env.DEVICE_SALT || "default-device-salt";

/** Ambil IP asli klien dari header (mendukung proxy / load balancer). */
export function getClientIp(req: NextRequest): string {
  const xff = req.headers.get("x-forwarded-for");
  if (xff) return xff.split(",")[0].trim();
  return (
    req.headers.get("x-real-ip") ||
    req.headers.get("cf-connecting-ip") ||
    "0.0.0.0"
  );
}

export function hashValue(value: string): string {
  return createHash("sha256").update(`${value}::${SALT}`).digest("hex");
}

/**
 * Membuat device hash unik untuk mendeteksi perangkat yang sama.
 * Menggabungkan beberapa sinyal agar sulit dimanipulasi:
 *   - fingerprint browser (FingerprintJS visitorId)
 *   - IP address
 *   - user agent
 * Fingerprint adalah sinyal utama (stabil walau ganti jaringan),
 * IP + UA sebagai penguat.
 */
export function buildDeviceHash(params: {
  fingerprint?: string;
  ip: string;
  userAgent: string;
}): string {
  const { fingerprint, ip, userAgent } = params;
  // Jika fingerprint tersedia & valid, jadikan basis utama.
  const primary = fingerprint && fingerprint.length > 8 ? fingerprint : `${ip}|${userAgent}`;
  return createHash("sha256").update(`${primary}::${SALT}`).digest("hex");
}
