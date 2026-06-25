"use client";

import { useEffect, useState } from "react";
import Link from "next/link";
import {
  Plus,
  Users,
  BarChart3,
  Trash2,
  ExternalLink,
  Settings,
  Loader2,
  Vote,
} from "lucide-react";
import AdminHeader from "@/components/AdminHeader";
import { formatDate, formatNumber, pollStatus } from "@/lib/utils";

interface PollRow {
  id: string;
  slug: string;
  title: string;
  isActive: boolean;
  isPrivate: boolean;
  startDate: string | null;
  endDate: string | null;
  createdAt: string;
  _count: { ballots: number; candidates: number; comments: number };
}

export default function AdminDashboard() {
  const [polls, setPolls] = useState<PollRow[]>([]);
  const [loading, setLoading] = useState(true);
  const [deleting, setDeleting] = useState<string | null>(null);

  async function load() {
    setLoading(true);
    const res = await fetch("/api/admin/polls");
    const data = await res.json();
    setPolls(data.polls || []);
    setLoading(false);
  }

  useEffect(() => {
    load();
  }, []);

  async function remove(id: string, title: string) {
    if (!confirm(`Hapus polling "${title}"? Semua suara akan terhapus permanen.`)) return;
    setDeleting(id);
    await fetch(`/api/admin/polls/${id}`, { method: "DELETE" });
    setDeleting(null);
    load();
  }

  const totalVotes = polls.reduce((s, p) => s + p._count.ballots, 0);
  const activeCount = polls.filter((p) => pollStatus(p as any) === "active").length;

  return (
    <>
      <AdminHeader />
      <main className="mx-auto max-w-6xl px-4 py-8">
        <div className="mb-6 flex flex-col gap-4 sm:flex-row sm:items-center sm:justify-between">
          <div>
            <h1 className="text-2xl font-extrabold text-slate-900">Dashboard Polling</h1>
            <p className="text-slate-500">Kelola semua polling dan pantau hasilnya</p>
          </div>
          <Link href="/admin/poll/new" className="btn-primary">
            <Plus size={18} /> Buat Polling Baru
          </Link>
        </div>

        {/* Statistik */}
        <div className="mb-6 grid grid-cols-3 gap-3">
          <StatCard icon={<Vote />} label="Total Polling" value={polls.length} />
          <StatCard icon={<BarChart3 />} label="Polling Aktif" value={activeCount} />
          <StatCard icon={<Users />} label="Total Suara" value={totalVotes} />
        </div>

        {loading ? (
          <div className="flex justify-center py-20">
            <Loader2 className="animate-spin text-brand-500" size={32} />
          </div>
        ) : polls.length === 0 ? (
          <div className="card flex flex-col items-center justify-center py-16 text-center">
            <Vote size={40} className="mb-3 text-slate-300" />
            <p className="font-semibold text-slate-600">Belum ada polling</p>
            <p className="mb-4 text-sm text-slate-400">Mulai dengan membuat polling pertama Anda.</p>
            <Link href="/admin/poll/new" className="btn-primary">
              <Plus size={18} /> Buat Polling
            </Link>
          </div>
        ) : (
          <div className="space-y-3">
            {polls.map((p) => {
              const status = pollStatus(p as any);
              return (
                <div key={p.id} className="card flex flex-col gap-4 p-4 sm:flex-row sm:items-center">
                  <div className="min-w-0 flex-1">
                    <div className="flex items-center gap-2">
                      <h3 className="truncate font-bold text-slate-900">{p.title}</h3>
                      <StatusBadge status={status} />
                      {p.isPrivate && (
                        <span className="badge bg-slate-100 text-slate-500">Privat</span>
                      )}
                    </div>
                    <div className="mt-1 flex flex-wrap items-center gap-x-4 gap-y-1 text-xs text-slate-500">
                      <span>{p._count.candidates} kandidat</span>
                      <span>{formatNumber(p._count.ballots)} suara</span>
                      <span>{p._count.comments} komentar</span>
                      <span>Dibuat {formatDate(p.createdAt)}</span>
                    </div>
                  </div>
                  <div className="flex shrink-0 items-center gap-2">
                    <Link
                      href={`/vote/${p.slug}`}
                      target="_blank"
                      className="btn-secondary"
                      title="Buka halaman voting"
                    >
                      <ExternalLink size={16} />
                    </Link>
                    <Link href={`/admin/poll/${p.id}`} className="btn-secondary" title="Kelola & hasil">
                      <Settings size={16} />
                      <span className="hidden sm:inline">Kelola</span>
                    </Link>
                    <button
                      onClick={() => remove(p.id, p.title)}
                      disabled={deleting === p.id}
                      className="btn-secondary text-red-600"
                      title="Hapus"
                    >
                      {deleting === p.id ? (
                        <Loader2 size={16} className="animate-spin" />
                      ) : (
                        <Trash2 size={16} />
                      )}
                    </button>
                  </div>
                </div>
              );
            })}
          </div>
        )}
      </main>
    </>
  );
}

function StatCard({
  icon,
  label,
  value,
}: {
  icon: React.ReactNode;
  label: string;
  value: number;
}) {
  return (
    <div className="card flex items-center gap-3 p-4">
      <span className="flex h-11 w-11 items-center justify-center rounded-xl bg-brand-50 text-brand-600">
        {icon}
      </span>
      <div>
        <div className="text-2xl font-extrabold text-slate-900">{formatNumber(value)}</div>
        <div className="text-xs text-slate-500">{label}</div>
      </div>
    </div>
  );
}

function StatusBadge({ status }: { status: string }) {
  const map: Record<string, string> = {
    active: "bg-brand-100 text-brand-700",
    scheduled: "bg-amber-100 text-amber-700",
    ended: "bg-slate-100 text-slate-500",
    closed: "bg-red-100 text-red-600",
  };
  const label: Record<string, string> = {
    active: "● Aktif",
    scheduled: "Terjadwal",
    ended: "Berakhir",
    closed: "Ditutup",
  };
  return <span className={`badge ${map[status]}`}>{label[status]}</span>;
}
