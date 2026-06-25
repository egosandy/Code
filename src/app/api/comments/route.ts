import { NextRequest, NextResponse } from "next/server";
import { prisma } from "@/lib/db";

// GET: daftar komentar untuk sebuah polling (?slug=...)
export async function GET(req: NextRequest) {
  const slug = req.nextUrl.searchParams.get("slug");
  if (!slug) return NextResponse.json({ error: "slug wajib" }, { status: 400 });

  const poll = await prisma.poll.findUnique({ where: { slug }, select: { id: true } });
  if (!poll) return NextResponse.json({ comments: [] });

  const comments = await prisma.comment.findMany({
    where: { pollId: poll.id },
    orderBy: { createdAt: "desc" },
    take: 100,
  });
  return NextResponse.json({ comments });
}

// POST: tambah komentar
export async function POST(req: NextRequest) {
  try {
    const { slug, name, message } = await req.json();
    if (!slug || !name?.trim() || !message?.trim()) {
      return NextResponse.json({ error: "Nama dan komentar wajib diisi" }, { status: 400 });
    }

    const poll = await prisma.poll.findUnique({
      where: { slug },
      select: { id: true, allowComments: true },
    });
    if (!poll) return NextResponse.json({ error: "Polling tidak ditemukan" }, { status: 404 });
    if (!poll.allowComments) {
      return NextResponse.json({ error: "Komentar dinonaktifkan" }, { status: 403 });
    }

    const comment = await prisma.comment.create({
      data: {
        pollId: poll.id,
        name: name.trim().slice(0, 50),
        message: message.trim().slice(0, 500),
      },
    });
    return NextResponse.json({ comment });
  } catch (e) {
    return NextResponse.json({ error: "Gagal menyimpan komentar" }, { status: 500 });
  }
}
