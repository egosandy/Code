import { NextRequest, NextResponse } from "next/server";
import { prisma } from "@/lib/db";

// GET: detail polling + hasil suara (untuk admin)
export async function GET(_req: NextRequest, { params }: { params: { id: string } }) {
  const poll = await prisma.poll.findUnique({
    where: { id: params.id },
    include: {
      candidates: {
        orderBy: { order: "asc" },
        include: { _count: { select: { votes: true } } },
      },
      _count: { select: { ballots: true, comments: true } },
      comments: { orderBy: { createdAt: "desc" } },
    },
  });
  if (!poll) return NextResponse.json({ error: "Polling tidak ditemukan" }, { status: 404 });
  return NextResponse.json({ poll });
}

interface CandidateInput {
  id?: string;
  name: string;
  description?: string;
  photo?: string;
  ballotNo?: number;
  extraFields?: { label: string; value: string }[];
}

// PUT: update polling (termasuk kandidat — sinkronisasi penuh)
export async function PUT(req: NextRequest, { params }: { params: { id: string } }) {
  try {
    const body = await req.json();
    const existing = await prisma.poll.findUnique({
      where: { id: params.id },
      include: { candidates: true },
    });
    if (!existing) {
      return NextResponse.json({ error: "Polling tidak ditemukan" }, { status: 404 });
    }

    const {
      title,
      description,
      bannerImage,
      type,
      maxChoices,
      isActive,
      isPrivate,
      showResults,
      allowComments,
      startDate,
      endDate,
      candidates,
    } = body as {
      title?: string;
      description?: string;
      bannerImage?: string | null;
      type?: string;
      maxChoices?: number;
      isActive?: boolean;
      isPrivate?: boolean;
      showResults?: string;
      allowComments?: boolean;
      startDate?: string | null;
      endDate?: string | null;
      candidates?: CandidateInput[];
    };

    await prisma.poll.update({
      where: { id: params.id },
      data: {
        ...(title !== undefined ? { title: title.trim() } : {}),
        ...(description !== undefined ? { description: description?.trim() || null } : {}),
        ...(bannerImage !== undefined ? { bannerImage: bannerImage || null } : {}),
        ...(type !== undefined ? { type: type === "multiple" ? "multiple" : "single" } : {}),
        ...(maxChoices !== undefined ? { maxChoices: Math.max(1, Number(maxChoices) || 1) } : {}),
        ...(isActive !== undefined ? { isActive } : {}),
        ...(isPrivate !== undefined ? { isPrivate } : {}),
        ...(showResults !== undefined ? { showResults } : {}),
        ...(allowComments !== undefined ? { allowComments } : {}),
        ...(startDate !== undefined ? { startDate: startDate ? new Date(startDate) : null } : {}),
        ...(endDate !== undefined ? { endDate: endDate ? new Date(endDate) : null } : {}),
      },
    });

    // Sinkronisasi kandidat jika dikirim
    if (Array.isArray(candidates)) {
      const valid = candidates.filter((c) => c.name && c.name.trim());
      const keepIds = valid.filter((c) => c.id).map((c) => c.id!) as string[];

      // hapus kandidat yang dibuang (otomatis menghapus votenya via cascade)
      await prisma.candidate.deleteMany({
        where: { pollId: params.id, id: { notIn: keepIds.length ? keepIds : ["__none__"] } },
      });

      // update / buat
      for (let i = 0; i < valid.length; i++) {
        const c = valid[i];
        const data = {
          name: c.name.trim(),
          description: c.description?.trim() || null,
          photo: c.photo || null,
          ballotNo: c.ballotNo ?? i + 1,
          order: i,
          extraFields:
            c.extraFields && c.extraFields.length
              ? JSON.stringify(c.extraFields.filter((f) => f.label && f.value))
              : null,
        };
        if (c.id) {
          await prisma.candidate.update({ where: { id: c.id }, data });
        } else {
          await prisma.candidate.create({ data: { ...data, pollId: params.id } });
        }
      }
    }

    const updated = await prisma.poll.findUnique({
      where: { id: params.id },
      include: { candidates: { orderBy: { order: "asc" } } },
    });
    return NextResponse.json({ poll: updated });
  } catch (e) {
    console.error(e);
    return NextResponse.json({ error: "Gagal memperbarui polling" }, { status: 500 });
  }
}

// DELETE: hapus polling
export async function DELETE(_req: NextRequest, { params }: { params: { id: string } }) {
  try {
    await prisma.poll.delete({ where: { id: params.id } });
    return NextResponse.json({ ok: true });
  } catch (e) {
    return NextResponse.json({ error: "Gagal menghapus polling" }, { status: 500 });
  }
}
