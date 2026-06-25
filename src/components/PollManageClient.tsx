"use client";

import { useEffect, useState } from "react";
import Link from "next/link";
import {
  ArrowLeft,
  BarChart3,
  Pencil,
  Loader2,
  Trophy,
  Users,
  Vote,
  RefreshCw,
  ExternalLink,
} from "lucide-react";
import ShareButton from "./ShareButton";
import ResultsChart from "./ResultsChart";
import PollForm, { PollFormData } from "./PollForm";
import { formatNumber, percentage } from "@/lib/utils";

interface Candidate {
  id: string;
  name: string;
  description?: string | null;
  photo?: string | null;
  ballotNo: number;
  order: number;
  extraFields?: string | null;
  _count: { votes: number };
}
interface PollDetail {
  id: string;
  slug: string;
  title: string;
  description?: string | null;
  bannerImage?: string | null;
  type: string;
  maxChoices: number;
  isActive: boolean;
  isPrivate: boolean;
  showResults: string;
  allowComments: boolean;
  startDate: string | null;
  endDate: string | null;
  candidates: Candidate[];
  comments: { id: string; name: string; message: string; createdAt: string }[];
  _count: { ballots: number; comments: number };
}

export default function PollManageClient({ id }: { id: string }) {
  const [poll, setPoll] = useState<PollDetail | null>(null);
  const [loading, setLoading] = useState(true);
  const [tab, setTab] = useState<"results" | "edit">("results");
  const [refreshing, setRefreshing] = useState(false);

  async function load() {
    const res = await fetch(`/api/admin/polls/${id}`);
    if (res.ok) {
      const data = await res.json();
      setPoll(data.poll);
    }
    setLoading(false);
  }

  useEffect(() => {
    load();
  }, [id]);

  async function refresh() {
    setRefreshing(true);
    await load();
    setRefreshing(false);
  }

  if (loading) {
    return (
      <div className="flex justify-center py-20">
        <Loader2 className="animate-spin text-brand-500" size={32} />
      </div>
    );
  }
  if (!poll) {
    return <p className="py-20 text-center text-slate-500">Polling tidak ditemukan.</p>;
  }

  const baseUrl = process.env.NEXT_PUBLIC_BASE_URL || "";
  const shareUrl =
    typeof window !== "undefined"
      ? `${window.location.origin}/vote/${poll.slug}`
      : `${baseUrl}/vote/${poll.slug}`;

  const totalVotes = poll.candidates.reduce((s, c) => s + c._count.votes, 0);
  const sorted = [...poll.candidates].sort((a, b) => b._count.votes - a._count.votes);
  const leader = totalVotes > 0 ? sorted[0] : null;
  const chartData = poll.candidates
    .sort((a, b) => a.order - b.order)
    .map((c) => ({ name: `${c.ballotNo}. ${c.name}`, suara: c._count.votes }));

  const initialForm: PollFormData = {
    id: poll.id,
    title: poll.title,
    description: poll.description || "",
    bannerImage: poll.bannerImage || null,
    type: poll.type === "multiple" ? "multiple" : "single",
    maxChoices: poll.maxChoices,
    isActive: poll.isActive,
    isPrivate: poll.isPrivate,
    showResults: poll.showResults as any,
    allowComments: poll.allowComments,
    startDate: poll.startDate ? poll.startDate.slice(0, 16) : "",
    endDate: poll.endDate ? poll.endDate.slice(0, 16) : "",
    candidates: poll.candidates
      .sort((a, b) => a.order - b.order)
      .map((c) => ({
        id: c.id,
        name: c.name,
        description: c.description || "",
        photo: c.photo || null,
        ballotNo: c.ballotNo,
        extraFields: c.extraFields ? JSON.parse(c.extraFields) : [],
      })),
  };

  return (
    <div>
      <Link
        href="/admin"
        className="mb-4 inline-flex items-center gap-1 text-sm font-medium text-slate-500 hover:text-slate-700"
      >
        <ArrowLeft size={16} /> Kembali ke Dashboard
      </Link>

      <div className="mb-4 flex flex-col gap-3 sm:flex-row sm:items-start sm:justify-between">
        <div>
          <h1 className="text-2xl font-extrabold text-slate-900">{poll.title}</h1>
          {poll.description && <p className="mt-1 text-slate-500">{poll.description}</p>}
        </div>
        <Link href={`/vote/${poll.slug}`} target="_blank" className="btn-secondary shrink-0">
          <ExternalLink size={16} /> Buka Voting
        </Link>
      </div>

      {/* Share */}
      <div className="card mb-6 p-4">
        <p className="mb-2 text-sm font-semibold text-slate-700">Link untuk dibagikan:</p>
        <div className="mb-3 truncate rounded-lg bg-slate-100 px-3 py-2 font-mono text-sm text-slate-600">
          {shareUrl}
        </div>
        <ShareButton url={shareUrl} title={poll.title} />
      </div>

      {/* Tabs */}
      <div className="mb-5 flex gap-2 rounded-xl bg-slate-100 p-1">
        <button
          onClick={() => setTab("results")}
          className={`flex flex-1 items-center justify-center gap-2 rounded-lg py-2 text-sm font-semibold transition ${
            tab === "results" ? "bg-white text-brand-700 shadow-sm" : "text-slate-500"
          }`}
        >
          <BarChart3 size={16} /> Hasil
        </button>
        <button
          onClick={() => setTab("edit")}
          className={`flex flex-1 items-center justify-center gap-2 rounded-lg py-2 text-sm font-semibold transition ${
            tab === "edit" ? "bg-white text-brand-700 shadow-sm" : "text-slate-500"
          }`}
        >
          <Pencil size={16} /> Edit
        </button>
      </div>

      {tab === "results" ? (
        <div className="space-y-6">
          {/* Statistik */}
          <div className="grid grid-cols-3 gap-3">
            <Stat icon={<Users />} label="Pemilih" value={formatNumber(poll._count.ballots)} />
            <Stat icon={<Vote />} label="Total Suara" value={formatNumber(totalVotes)} />
            <Stat
              icon={<Trophy />}
              label="Unggul"
              value={leader ? `No. ${leader.ballotNo}` : "—"}
            />
          </div>

          <div className="card p-5">
            <div className="mb-4 flex items-center justify-between">
              <h2 className="font-bold text-slate-800">Grafik Perolehan Suara</h2>
              <button onClick={refresh} className="btn-secondary text-sm">
                <RefreshCw size={14} className={refreshing ? "animate-spin" : ""} /> Perbarui
              </button>
            </div>
            {totalVotes === 0 ? (
              <p className="py-10 text-center text-slate-400">Belum ada suara masuk.</p>
            ) : (
              <ResultsChart data={chartData} />
            )}
          </div>

          {/* Ranking detail */}
          <div className="card p-5">
            <h2 className="mb-4 font-bold text-slate-800">Peringkat Kandidat</h2>
            <div className="space-y-3">
              {sorted.map((c, i) => {
                const pct = percentage(c._count.votes, totalVotes);
                return (
                  <div key={c.id} className="flex items-center gap-3">
                    <span
                      className={`flex h-8 w-8 shrink-0 items-center justify-center rounded-lg text-sm font-bold ${
                        i === 0 && totalVotes > 0
                          ? "bg-amber-100 text-amber-700"
                          : "bg-slate-100 text-slate-500"
                      }`}
                    >
                      {i + 1}
                    </span>
                    {c.photo ? (
                      // eslint-disable-next-line @next/next/no-img-element
                      <img
                        src={c.photo}
                        alt={c.name}
                        className="h-10 w-10 shrink-0 rounded-lg object-cover"
                      />
                    ) : (
                      <div className="flex h-10 w-10 shrink-0 items-center justify-center rounded-lg bg-slate-100 font-bold text-slate-400">
                        {c.ballotNo}
                      </div>
                    )}
                    <div className="min-w-0 flex-1">
                      <div className="flex items-center justify-between">
                        <span className="truncate font-semibold text-slate-800">{c.name}</span>
                        <span className="ml-2 shrink-0 text-sm font-bold text-brand-700">{pct}%</span>
                      </div>
                      <div className="mt-1 h-2 overflow-hidden rounded-full bg-slate-100">
                        <div
                          className="h-full rounded-full bg-gradient-to-r from-brand-500 to-brand-600 transition-all duration-700"
                          style={{ width: `${pct}%` }}
                        />
                      </div>
                    </div>
                    <span className="w-16 shrink-0 text-right text-sm text-slate-500">
                      {formatNumber(c._count.votes)}
                    </span>
                  </div>
                );
              })}
            </div>
          </div>

          {/* Komentar */}
          {poll.comments.length > 0 && (
            <div className="card p-5">
              <h2 className="mb-4 font-bold text-slate-800">
                Komentar ({poll.comments.length})
              </h2>
              <div className="space-y-2">
                {poll.comments.map((c) => (
                  <div key={c.id} className="rounded-lg bg-slate-50 p-3 text-sm">
                    <span className="font-semibold text-slate-700">{c.name}:</span>{" "}
                    <span className="text-slate-600">{c.message}</span>
                  </div>
                ))}
              </div>
            </div>
          )}
        </div>
      ) : (
        <PollForm initial={initialForm} />
      )}
    </div>
  );
}

function Stat({
  icon,
  label,
  value,
}: {
  icon: React.ReactNode;
  label: string;
  value: string;
}) {
  return (
    <div className="card flex items-center gap-3 p-4">
      <span className="flex h-10 w-10 items-center justify-center rounded-xl bg-brand-50 text-brand-600">
        {icon}
      </span>
      <div className="min-w-0">
        <div className="truncate text-xl font-extrabold text-slate-900">{value}</div>
        <div className="text-xs text-slate-500">{label}</div>
      </div>
    </div>
  );
}
