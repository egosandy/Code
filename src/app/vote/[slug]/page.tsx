import { notFound } from "next/navigation";
import type { Metadata } from "next";
import Navbar from "@/components/Navbar";
import Footer from "@/components/Footer";
import VoteClient from "@/components/VoteClient";
import ShareButton from "@/components/ShareButton";
import { prisma } from "@/lib/db";
import { pollStatus } from "@/lib/utils";

export const dynamic = "force-dynamic";

async function getPoll(slug: string) {
  const poll = await prisma.poll.findUnique({
    where: { slug },
    include: {
      candidates: {
        orderBy: { order: "asc" },
        include: { _count: { select: { votes: true } } },
      },
      _count: { select: { ballots: true } },
    },
  });
  return poll;
}

export async function generateMetadata({
  params,
}: {
  params: { slug: string };
}): Promise<Metadata> {
  const poll = await prisma.poll.findUnique({ where: { slug: params.slug } });
  if (!poll) return { title: "Polling tidak ditemukan" };
  return {
    title: `${poll.title} — PollingKita`,
    description: poll.description || "Ikut memilih sekarang!",
  };
}

export default async function VotePage({ params }: { params: { slug: string } }) {
  const poll = await getPoll(params.slug);
  if (!poll) notFound();

  const status = pollStatus(poll);
  const baseUrl = process.env.NEXT_PUBLIC_BASE_URL || "";
  const shareUrl = `${baseUrl}/vote/${poll.slug}`;

  const initialPoll = {
    id: poll.id,
    slug: poll.slug,
    title: poll.title,
    description: poll.description,
    bannerImage: poll.bannerImage,
    type: poll.type,
    maxChoices: poll.maxChoices,
    showResults: poll.showResults,
    allowComments: poll.allowComments,
    status,
    totalVoters: poll._count.ballots,
    candidates: poll.candidates.map((c) => ({
      id: c.id,
      name: c.name,
      description: c.description,
      photo: c.photo,
      ballotNo: c.ballotNo,
      extraFields: c.extraFields ? JSON.parse(c.extraFields) : [],
      votes: c._count.votes,
    })),
  };

  return (
    <>
      <Navbar />
      <main className="mx-auto max-w-2xl px-4 py-8">
        {/* Header polling */}
        <div className="card mb-6 overflow-hidden">
          <div className="relative h-40 bg-gradient-to-br from-brand-500 to-brand-700 sm:h-52">
            {poll.bannerImage && (
              // eslint-disable-next-line @next/next/no-img-element
              <img src={poll.bannerImage} alt={poll.title} className="h-full w-full object-cover" />
            )}
          </div>
          <div className="p-5">
            <h1 className="text-2xl font-extrabold text-slate-900 sm:text-3xl">{poll.title}</h1>
            {poll.description && <p className="mt-2 text-slate-600">{poll.description}</p>}
            <div className="mt-4 border-t border-slate-100 pt-4">
              <p className="mb-2 text-sm font-semibold text-slate-700">Bagikan polling ini:</p>
              <ShareButton url={shareUrl} title={poll.title} />
            </div>
          </div>
        </div>

        <VoteClient initialPoll={initialPoll} />
      </main>
      <Footer />
    </>
  );
}
