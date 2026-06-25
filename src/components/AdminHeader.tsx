"use client";

import Link from "next/link";
import { useRouter } from "next/navigation";
import { Vote, LogOut, Home } from "lucide-react";

export default function AdminHeader() {
  const router = useRouter();

  async function logout() {
    await fetch("/api/auth/logout", { method: "POST" });
    router.push("/admin/login");
    router.refresh();
  }

  return (
    <header className="sticky top-0 z-40 border-b border-slate-200 bg-white/80 backdrop-blur-lg">
      <div className="mx-auto flex max-w-6xl items-center justify-between px-4 py-3">
        <Link href="/admin" className="flex items-center gap-2">
          <span className="flex h-9 w-9 items-center justify-center rounded-xl bg-gradient-to-br from-brand-500 to-brand-700 text-white">
            <Vote size={20} />
          </span>
          <div>
            <span className="text-lg font-extrabold text-slate-900">
              Polling<span className="text-brand-600">Kita</span>
            </span>
            <span className="ml-2 rounded-md bg-slate-100 px-1.5 py-0.5 text-xs font-semibold text-slate-500">
              Admin
            </span>
          </div>
        </Link>
        <div className="flex items-center gap-2">
          <Link href="/" className="btn-secondary" title="Lihat situs">
            <Home size={16} />
            <span className="hidden sm:inline">Situs</span>
          </Link>
          <button onClick={logout} className="btn-secondary text-red-600">
            <LogOut size={16} />
            <span className="hidden sm:inline">Keluar</span>
          </button>
        </div>
      </div>
    </header>
  );
}
