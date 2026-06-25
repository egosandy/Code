import Link from "next/link";
import { Vote } from "lucide-react";

export default function Navbar() {
  return (
    <header className="sticky top-0 z-40 border-b border-slate-200/70 bg-white/80 backdrop-blur-lg">
      <div className="mx-auto flex max-w-6xl items-center justify-between px-4 py-3">
        <Link href="/" className="flex items-center gap-2">
          <span className="flex h-9 w-9 items-center justify-center rounded-xl bg-gradient-to-br from-brand-500 to-brand-700 text-white shadow-md shadow-brand-500/30">
            <Vote size={20} />
          </span>
          <span className="text-lg font-extrabold tracking-tight text-slate-900">
            Polling<span className="text-brand-600">Kita</span>
          </span>
        </Link>
        <nav className="flex items-center gap-1 sm:gap-2">
          <Link
            href="/"
            className="rounded-lg px-3 py-2 text-sm font-medium text-slate-600 hover:bg-slate-100 hover:text-slate-900"
          >
            Beranda
          </Link>
          <Link
            href="/#polls"
            className="hidden rounded-lg px-3 py-2 text-sm font-medium text-slate-600 hover:bg-slate-100 hover:text-slate-900 sm:block"
          >
            Polling
          </Link>
          <Link href="/admin" className="btn-primary text-sm">
            Admin
          </Link>
        </nav>
      </div>
    </header>
  );
}
