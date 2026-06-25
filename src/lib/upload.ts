import { writeFile, mkdir } from "fs/promises";
import path from "path";
import { customAlphabet } from "nanoid";

const nano = customAlphabet("abcdefghijklmnopqrstuvwxyz0123456789", 12);
const UPLOAD_DIR = path.join(process.cwd(), "public", "uploads");

const MAX_SIZE = 5 * 1024 * 1024; // 5 MB
const ALLOWED = ["image/jpeg", "image/png", "image/webp", "image/gif"];

export async function saveImage(file: File): Promise<string> {
  if (!ALLOWED.includes(file.type)) {
    throw new Error("Format gambar tidak didukung (gunakan JPG, PNG, WEBP, atau GIF)");
  }
  if (file.size > MAX_SIZE) {
    throw new Error("Ukuran gambar maksimal 5 MB");
  }

  const bytes = Buffer.from(await file.arrayBuffer());
  await mkdir(UPLOAD_DIR, { recursive: true });

  let outBuffer: Buffer = bytes;
  let ext = "jpg";

  // Kompres & resize dengan sharp jika tersedia.
  try {
    const sharp = (await import("sharp")).default;
    outBuffer = await sharp(bytes)
      .rotate()
      .resize(800, 800, { fit: "inside", withoutEnlargement: true })
      .webp({ quality: 82 })
      .toBuffer();
    ext = "webp";
  } catch {
    // fallback: simpan apa adanya
    ext = (file.type.split("/")[1] || "jpg").replace("jpeg", "jpg");
  }

  const filename = `${nano()}.${ext}`;
  await writeFile(path.join(UPLOAD_DIR, filename), outBuffer);
  return `/uploads/${filename}`;
}
