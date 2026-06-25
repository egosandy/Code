import { NextRequest, NextResponse } from "next/server";
import { prisma } from "@/lib/db";
import { generateSlug } from "@/lib/utils";

// GET: daftar semua polling + ringkasan suara
export async function GET() {
  const polls = await prisma.poll.findMany({
    orderBy: { createdAt: "desc" },
    include: {
      _count: { select: { ballots: true, candidates: true, comments: true } },
    },
  });
  return NextResponse.json({ polls });
}

interface CandidateInput {
  name: string;
  description?: string;
  photo?: string;
  ballotNo?: number;
  extraFields?: { label: string; value: string }[];
}

// POST: buat polling baru
export async function POST(req: NextRequest) {
  try {
    const body = await req.json();
    const {
      title,
      description,
      bannerImage,
      type = "single",
      maxChoices = 1,
      isActive = true,
      isPrivate = false,
      showResults = "always",
      allowComments = true,
      startDate,
      endDate,
      candidates = [],
    } = body as {
      title: string;
      description?: string;
      bannerImage?: string;
      type?: string;
      maxChoices?: number;
      isActive?: boolean;
      isPrivate?: boolean;
      showResults?: string;
      allowComments?: boolean;
      startDate?: string;
      endDate?: string;
      candidates: CandidateInput[];
    };

    if (!title || title.trim().length < 3) {
      return NextResponse.json({ error: "Judul polling minimal 3 karakter" }, { status: 400 });
    }
    const valid = candidates.filter((c) => c.name && c.name.trim());
    if (valid.length < 2) {
      return NextResponse.json({ error: "Minimal 2 kandidat / pilihan" }, { status: 400 });
    }

    const poll = await prisma.poll.create({
      data: {
        slug: generateSlug(title),
        title: title.trim(),
        description: description?.trim() || null,
        bannerImage: bannerImage || null,
        type: type === "multiple" ? "multiple" : "single",
        maxChoices: type === "multiple" ? Math.max(1, Number(maxChoices) || 1) : 1,
        isActive,
        isPrivate,
        showResults,
        allowComments,
        startDate: startDate ? new Date(startDate) : null,
        endDate: endDate ? new Date(endDate) : null,
        candidates: {
          create: valid.map((c, i) => ({
            name: c.name.trim(),
            description: c.description?.trim() || null,
            photo: c.photo || null,
            ballotNo: c.ballotNo ?? i + 1,
            order: i,
            extraFields:
              c.extraFields && c.extraFields.length
                ? JSON.stringify(c.extraFields.filter((f) => f.label && f.value))
                : null,
          })),
        },
      },
      include: { candidates: true },
    });

    return NextResponse.json({ poll });
  } catch (e) {
    console.error(e);
    return NextResponse.json({ error: "Gagal membuat polling" }, { status: 500 });
  }
}
