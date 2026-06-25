import { NextRequest, NextResponse } from "next/server";
import bcrypt from "bcryptjs";
import { prisma } from "@/lib/db";
import { createSession, setSessionCookie } from "@/lib/auth";

export async function POST(req: NextRequest) {
  try {
    const { username, password } = await req.json();
    if (!username || !password) {
      return NextResponse.json({ error: "Username dan password wajib diisi" }, { status: 400 });
    }

    const admin = await prisma.admin.findUnique({ where: { username } });
    if (!admin) {
      return NextResponse.json({ error: "Username atau password salah" }, { status: 401 });
    }

    const ok = await bcrypt.compare(password, admin.password);
    if (!ok) {
      return NextResponse.json({ error: "Username atau password salah" }, { status: 401 });
    }

    const token = await createSession({ sub: admin.id, username: admin.username });
    await setSessionCookie(token);

    return NextResponse.json({ ok: true, username: admin.username });
  } catch (e) {
    console.error(e);
    return NextResponse.json({ error: "Terjadi kesalahan server" }, { status: 500 });
  }
}
