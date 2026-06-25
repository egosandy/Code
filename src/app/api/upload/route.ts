import { NextRequest, NextResponse } from "next/server";
import { getSession } from "@/lib/auth";
import { saveImage } from "@/lib/upload";

export const runtime = "nodejs";

export async function POST(req: NextRequest) {
  // hanya admin yang boleh upload
  const session = await getSession();
  if (!session) {
    return NextResponse.json({ error: "Tidak terautentikasi" }, { status: 401 });
  }

  try {
    const formData = await req.formData();
    const file = formData.get("file") as File | null;
    if (!file) {
      return NextResponse.json({ error: "Tidak ada file" }, { status: 400 });
    }
    const url = await saveImage(file);
    return NextResponse.json({ url });
  } catch (e: any) {
    return NextResponse.json({ error: e.message || "Gagal mengunggah" }, { status: 400 });
  }
}
