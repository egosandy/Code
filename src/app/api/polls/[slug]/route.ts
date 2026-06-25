import { NextRequest, NextResponse } from "next/server";
import { prisma } from "@/lib/db";
import { pollStatus } from "@/lib/utils";

// GET publik: data polling + hasil (menghormati pengaturan showResults)
export async function GET(_req: NextRequest, { params }: { params: { slug: string } }) {
  const poll = await prisma.poll.findUnique({
    where: { slug: params.slug },
    include: {
      candidates: {
        orderBy: { order: "asc" },
        include: { _count: { select: { votes: true } } },
      },
      _count: { select: { ballots: true } },
    },
  });

  if (!poll) {
    return NextResponse.json({ error: "Polling tidak ditemukan" }, { status: 404 });
  }

  const status = pollStatus(poll);
  const totalVoters = poll._count.ballots;

  const candidates = poll.candidates.map((c) => ({
    id: c.id,
    name: c.name,
    description: c.description,
    photo: c.photo,
    ballotNo: c.ballotNo,
    extraFields: c.extraFields ? JSON.parse(c.extraFields) : [],
    votes: c._count.votes,
  }));

  return NextResponse.json({
    poll: {
      id: poll.id,
      slug: poll.slug,
      title: poll.title,
      description: poll.description,
      bannerImage: poll.bannerImage,
      type: poll.type,
      maxChoices: poll.maxChoices,
      showResults: poll.showResults,
      allowComments: poll.allowComments,
      startDate: poll.startDate,
      endDate: poll.endDate,
      status,
      totalVoters,
      candidates,
    },
  });
}
