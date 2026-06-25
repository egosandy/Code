import { NextRequest, NextResponse } from "next/server";
import { prisma } from "@/lib/db";
import { buildDeviceHash, getClientIp } from "@/lib/device";

// POST: cek apakah perangkat ini sudah memilih pada polling tertentu
export async function POST(req: NextRequest, { params }: { params: { slug: string } }) {
  try {
    const { fingerprint } = await req.json().catch(() => ({ fingerprint: undefined }));
    const poll = await prisma.poll.findUnique({
      where: { slug: params.slug },
      select: { id: true },
    });
    if (!poll) return NextResponse.json({ error: "Polling tidak ditemukan" }, { status: 404 });

    const ip = getClientIp(req);
    const userAgent = req.headers.get("user-agent") || "";
    const deviceHash = buildDeviceHash({ fingerprint, ip, userAgent });

    const ballot = await prisma.ballot.findUnique({
      where: { pollId_deviceHash: { pollId: poll.id, deviceHash } },
      include: { votes: { select: { candidateId: true } } },
    });

    return NextResponse.json({
      voted: !!ballot,
      selectedCandidateIds: ballot?.votes.map((v) => v.candidateId) ?? [],
    });
  } catch (e) {
    console.error(e);
    return NextResponse.json({ error: "Terjadi kesalahan" }, { status: 500 });
  }
}
