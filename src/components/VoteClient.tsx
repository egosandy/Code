"use client";

import { useEffect, useMemo, useState } from "react";
import { motion, AnimatePresence } from "framer-motion";
import {
  CheckCircle2,
  ShieldCheck,
  Trophy,
  Users,
  Loader2,
  Vote as VoteIcon,
  AlertCircle,
  Info,
} from "lucide-react";
import { formatNumber, percentage } from "@/lib/utils";
import CommentsSection from "./CommentsSection";

interface ExtraField {
  label: string;
  value: string;
}
interface Candidate {
  id: string;
  name: string;
  description?: string | null;
  photo?: string | null;
  ballotNo: number;
  extraFields: ExtraField[];
  votes: number;
}
interface Poll {
  id: string;
  slug: string;
  title: string;
  description?: string | null;
  bannerImage?: string | null;
  type: string;
  maxChoices: number;
  showResults: string;
  allowComments: boolean;
  status: "active" | "scheduled" | "ended" | "closed";
  totalVoters: number;
  candidates: Candidate[];
}

export default function VoteClient({ initialPoll }: { initialPoll: Poll }) {
  const [poll, setPoll] = useState<Poll>(initialPoll);
  const [fingerprint, setFingerprint] = useState<string>("");
  const [selected, setSelected] = useState<string[]>([]);
  const [voted, setVoted] = useState(false);
  const [votedFor, setVotedFor] = useState<string[]>([]);
  const [loading, setLoading] = useState(true);
  const [submitting, setSubmitting] = useState(false);
  const [error, setError] = useState("");

  // Hitung fingerprint perangkat & cek status memilih
  useEffect(() => {
    let mounted = true;
    (async () => {
      let fp = "";
      try {
        const FingerprintJS = (await import("@fingerprintjs/fingerprintjs")).default;
        const agent = await FingerprintJS.load();
        const result = await agent.get();
        fp = result.visitorId;
      } catch {
        fp = "";
      }
      if (!mounted) return;
      setFingerprint(fp);

      try {
        const res = await fetch(`/api/polls/${poll.slug}/me`, {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({ fingerprint: fp }),
        });
        const data = await res.json();
        if (mounted && data.voted) {
          setVoted(true);
          setVotedFor(data.selectedCandidateIds || []);
        }
      } catch {
        /* abaikan */
      }
      if (mounted) setLoading(false);
    })();
    return () => {
      mounted = false;
    };
  }, [poll.slug]);

  const totalVotes = useMemo(
    () => poll.candidates.reduce((s, c) => s + c.votes, 0),
    [poll.candidates]
  );
  const leader = useMemo(() => {
    if (totalVotes === 0) return null;
    return poll.candidates.reduce((a, b) => (b.votes > a.votes ? b : a));
  }, [poll.candidates, totalVotes]);

  const canShowResults =
    poll.showResults === "always" ||
    (poll.showResults === "afterVote" && voted) ||
    (poll.showResults === "afterClose" && poll.status !== "active");

  const isOpen = poll.status === "active";

  function toggle(id: string) {
    setError("");
    if (poll.type === "single") {
      setSelected([id]);
    } else {
      setSelected((prev) => {
        if (prev.includes(id)) return prev.filter((x) => x !== id);
        if (prev.length >= poll.maxChoices) {
          setError(`Maksimal ${poll.maxChoices} pilihan`);
          return prev;
        }
        return [...prev, id];
      });
    }
  }

  async function refreshResults() {
    try {
      const res = await fetch(`/api/polls/${poll.slug}`);
      const data = await res.json();
      if (data.poll) setPoll(data.poll);
    } catch {
      /* */
    }
  }

  async function submit() {
    if (selected.length === 0) {
      setError("Silakan pilih terlebih dahulu");
      return;
    }
    setSubmitting(true);
    setError("");
    try {
      const res = await fetch("/api/vote", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ slug: poll.slug, candidateIds: selected, fingerprint }),
      });
      const data = await res.json();
      if (!res.ok) {
        setError(data.error || "Gagal memilih");
        if (data.alreadyVoted) {
          setVoted(true);
        }
        setSubmitting(false);
        return;
      }
      setVoted(true);
      setVotedFor(data.selectedCandidateIds || selected);
      await refreshResults();
    } catch {
      setError("Terjadi kesalahan jaringan");
    } finally {
      setSubmitting(false);
    }
  }

  const statusBanner = () => {
    if (poll.status === "scheduled")
      return { text: "Polling belum dibuka", cls: "bg-amber-50 text-amber-700 border-amber-200" };
    if (poll.status === "ended")
      return { text: "Polling telah berakhir", cls: "bg-slate-100 text-slate-600 border-slate-200" };
    if (poll.status === "closed")
      return { text: "Polling ditutup", cls: "bg-slate-100 text-slate-600 border-slate-200" };
    return null;
  };
  const banner = statusBanner();

  return (
    <div className="space-y-6">
      {/* Info perangkat / status memilih */}
      {voted ? (
        <motion.div
          initial={{ opacity: 0, y: -10 }}
          animate={{ opacity: 1, y: 0 }}
          className="flex items-center gap-3 rounded-2xl border border-brand-200 bg-brand-50 p-4 text-brand-800"
        >
          <CheckCircle2 className="shrink-0" />
          <div>
            <p className="font-semibold">Terima kasih! Suara Anda sudah tercatat.</p>
            <p className="text-sm text-brand-700/80">
              Satu perangkat hanya dapat memberikan satu suara untuk menjaga keadilan polling.
            </p>
          </div>
        </motion.div>
      ) : isOpen ? (
        <div className="flex items-center gap-3 rounded-2xl border border-slate-200 bg-white p-4 text-slate-600">
          <ShieldCheck className="shrink-0 text-brand-600" />
          <p className="text-sm">
            Polling ini dilindungi <strong>deteksi perangkat</strong>. Setiap perangkat hanya bisa
            memilih sekali.
          </p>
        </div>
      ) : banner ? (
        <div className={`flex items-center gap-3 rounded-2xl border p-4 ${banner.cls}`}>
          <Info className="shrink-0" />
          <p className="font-medium">{banner.text}</p>
        </div>
      ) : null}

      {/* Statistik singkat */}
      <div className="grid grid-cols-2 gap-3 sm:grid-cols-3">
        <Stat icon={<Users size={18} />} label="Total Pemilih" value={formatNumber(poll.totalVoters)} />
        <Stat icon={<VoteIcon size={18} />} label="Total Suara" value={formatNumber(totalVotes)} />
        <Stat
          icon={<Trophy size={18} />}
          label="Sementara Unggul"
          value={leader && canShowResults ? `No. ${leader.ballotNo}` : "—"}
        />
      </div>

      {/* Daftar kandidat */}
      <div className="space-y-3">
        {poll.candidates.map((c, idx) => {
          const isSelected = selected.includes(c.id);
          const wasVotedFor = votedFor.includes(c.id);
          const pct = percentage(c.votes, totalVotes);
          const isLeader = leader?.id === c.id && totalVotes > 0;
          const clickable = isOpen && !voted && !loading;

          return (
            <motion.div
              key={c.id}
              initial={{ opacity: 0, y: 15 }}
              animate={{ opacity: 1, y: 0 }}
              transition={{ delay: idx * 0.05 }}
              onClick={() => clickable && toggle(c.id)}
              className={`group relative overflow-hidden rounded-2xl border bg-white p-4 transition-all ${
                clickable ? "cursor-pointer hover:border-brand-400 hover:shadow-md" : ""
              } ${
                isSelected || wasVotedFor
                  ? "border-brand-500 ring-2 ring-brand-500/30"
                  : "border-slate-200"
              }`}
            >
              {/* Bar hasil di latar */}
              {canShowResults && (
                <div
                  className="absolute inset-y-0 left-0 bg-gradient-to-r from-brand-50 to-brand-100/40 transition-all duration-700"
                  style={{ width: `${pct}%` }}
                />
              )}

              <div className="relative flex items-center gap-4">
                {/* Nomor urut */}
                <div
                  className={`flex h-12 w-12 shrink-0 items-center justify-center rounded-xl text-lg font-extrabold ${
                    isLeader
                      ? "bg-gradient-to-br from-amber-400 to-amber-500 text-white"
                      : "bg-slate-100 text-slate-700"
                  }`}
                >
                  {c.ballotNo}
                </div>

                {/* Foto */}
                {c.photo ? (
                  // eslint-disable-next-line @next/next/no-img-element
                  <img
                    src={c.photo}
                    alt={c.name}
                    className="h-16 w-16 shrink-0 rounded-xl border border-slate-200 object-cover"
                  />
                ) : (
                  <div className="flex h-16 w-16 shrink-0 items-center justify-center rounded-xl bg-slate-100 text-2xl font-bold text-slate-400">
                    {c.name.charAt(0)}
                  </div>
                )}

                {/* Info */}
                <div className="min-w-0 flex-1">
                  <div className="flex items-center gap-2">
                    <h3 className="truncate font-bold text-slate-900">{c.name}</h3>
                    {isLeader && canShowResults && (
                      <span className="badge bg-amber-100 text-amber-700">
                        <Trophy size={12} /> Unggul
                      </span>
                    )}
                  </div>
                  {c.description && (
                    <p className="truncate text-sm text-slate-500">{c.description}</p>
                  )}
                  {c.extraFields.length > 0 && (
                    <div className="mt-1 flex flex-wrap gap-x-3 gap-y-0.5">
                      {c.extraFields.slice(0, 2).map((f, i) => (
                        <span key={i} className="text-xs text-slate-500">
                          <span className="font-semibold text-slate-600">{f.label}:</span> {f.value}
                        </span>
                      ))}
                    </div>
                  )}
                </div>

                {/* Hasil / checkbox */}
                <div className="shrink-0 text-right">
                  {canShowResults ? (
                    <>
                      <div className="text-xl font-extrabold text-brand-700">{pct}%</div>
                      <div className="text-xs text-slate-500">{formatNumber(c.votes)} suara</div>
                    </>
                  ) : (
                    <div
                      className={`flex h-7 w-7 items-center justify-center rounded-full border-2 ${
                        isSelected
                          ? "border-brand-500 bg-brand-500 text-white"
                          : "border-slate-300"
                      } ${poll.type === "single" ? "rounded-full" : "rounded-md"}`}
                    >
                      {isSelected && <CheckCircle2 size={16} />}
                    </div>
                  )}
                </div>
              </div>
            </motion.div>
          );
        })}
      </div>

      {/* Error */}
      <AnimatePresence>
        {error && (
          <motion.div
            initial={{ opacity: 0, height: 0 }}
            animate={{ opacity: 1, height: "auto" }}
            exit={{ opacity: 0, height: 0 }}
            className="flex items-center gap-2 rounded-xl border border-red-200 bg-red-50 px-4 py-3 text-sm text-red-700"
          >
            <AlertCircle size={16} /> {error}
          </motion.div>
        )}
      </AnimatePresence>

      {/* Tombol submit */}
      {isOpen && !voted && (
        <div className="sticky bottom-4 z-10">
          <button
            onClick={submit}
            disabled={submitting || loading || selected.length === 0}
            className="btn-primary w-full py-3.5 text-base shadow-lg shadow-brand-500/30"
          >
            {submitting ? (
              <>
                <Loader2 size={18} className="animate-spin" /> Mengirim suara...
              </>
            ) : loading ? (
              <>
                <Loader2 size={18} className="animate-spin" /> Memeriksa perangkat...
              </>
            ) : (
              <>
                <VoteIcon size={18} /> Kirim Suara
                {poll.type === "multiple" && selected.length > 0 ? ` (${selected.length})` : ""}
              </>
            )}
          </button>
          {poll.type === "multiple" && (
            <p className="mt-2 text-center text-xs text-slate-500">
              Anda dapat memilih hingga {poll.maxChoices} kandidat
            </p>
          )}
        </div>
      )}

      {/* Komentar */}
      {poll.allowComments && <CommentsSection slug={poll.slug} />}
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
    <div className="card flex items-center gap-3 p-3">
      <span className="flex h-10 w-10 items-center justify-center rounded-xl bg-brand-50 text-brand-600">
        {icon}
      </span>
      <div className="min-w-0">
        <div className="truncate text-lg font-extrabold leading-tight text-slate-900">{value}</div>
        <div className="truncate text-xs text-slate-500">{label}</div>
      </div>
    </div>
  );
}
