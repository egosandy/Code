import { NextRequest, NextResponse } from "next/server";
import { prisma } from "@/lib/db";
import { buildDeviceHash, getClientIp, hashValue } from "@/lib/device";
import { pollStatus } from "@/lib/utils";

// POST: kirim suara
// body: { slug, candidateIds: string[], fingerprint?: string }
export async function POST(req: NextRequest) {
  try {
    const { slug, candidateIds, fingerprint } = await req.json();

    if (!slug || !Array.isArray(candidateIds) || candidateIds.length === 0) {
      return NextResponse.json({ error: "Pilihan tidak valid" }, { status: 400 });
    }

    const poll = await prisma.poll.findUnique({
      where: { slug },
      include: { candidates: { select: { id: true } } },
    });
    if (!poll) {
      return NextResponse.json({ error: "Polling tidak ditemukan" }, { status: 404 });
    }

    // Validasi status polling
    const status = pollStatus(poll);
    if (status !== "active") {
      const msg =
        status === "scheduled"
          ? "Polling belum dibuka"
          : status === "ended"
            ? "Polling sudah berakhir"
            : "Polling sudah ditutup";
      return NextResponse.json({ error: msg }, { status: 403 });
    }

    // Validasi jumlah pilihan
    if (poll.type === "single" && candidateIds.length !== 1) {
      return NextResponse.json({ error: "Hanya boleh memilih 1 kandidat" }, { status: 400 });
    }
    if (poll.type === "multiple" && candidateIds.length > poll.maxChoices) {
      return NextResponse.json(
        { error: `Maksimal ${poll.maxChoices} pilihan` },
        { status: 400 }
      );
    }

    // Validasi kandidat milik polling ini
    const validIds = new Set(poll.candidates.map((c) => c.id));
    const chosen = [...new Set(candidateIds)].filter((id: string) => validIds.has(id));
    if (chosen.length === 0) {
      return NextResponse.json({ error: "Kandidat tidak valid" }, { status: 400 });
    }

    // --- Deteksi perangkat (anti vote ganda) ---
    const ip = getClientIp(req);
    const userAgent = req.headers.get("user-agent") || "";
    const deviceHash = buildDeviceHash({ fingerprint, ip, userAgent });

    // Cek apakah perangkat sudah memilih
    const existing = await prisma.ballot.findUnique({
      where: { pollId_deviceHash: { pollId: poll.id, deviceHash } },
    });
    if (existing) {
      return NextResponse.json(
        { error: "Perangkat ini sudah memberikan suara pada polling ini.", alreadyVoted: true },
        { status: 409 }
      );
    }

    // Simpan surat suara + pilihan secara atomik.
    // Unique constraint (pollId, deviceHash) menjamin tidak ada race condition.
    try {
      await prisma.ballot.create({
        data: {
          pollId: poll.id,
          deviceHash,
          fingerprint: fingerprint || null,
          ipHash: hashValue(ip),
          userAgent: userAgent.slice(0, 255),
          votes: { create: chosen.map((id: string) => ({ candidateId: id })) },
        },
      });
    } catch (err: any) {
      // P2002 = unique constraint violation → sudah memilih (race)
      if (err?.code === "P2002") {
        return NextResponse.json(
          { error: "Perangkat ini sudah memberikan suara.", alreadyVoted: true },
          { status: 409 }
        );
      }
      throw err;
    }

    return NextResponse.json({ ok: true, selectedCandidateIds: chosen });
  } catch (e) {
    console.error(e);
    return NextResponse.json({ error: "Gagal menyimpan suara" }, { status: 500 });
  }
}
