import { Vote } from "lucide-react";

export default function Footer() {
  return (
    <footer className="mt-20 border-t border-slate-200 bg-white">
      <div className="mx-auto max-w-6xl px-4 py-10">
        <div className="flex flex-col items-center justify-between gap-4 sm:flex-row">
          <div className="flex items-center gap-2">
            <span className="flex h-8 w-8 items-center justify-center rounded-lg bg-gradient-to-br from-brand-500 to-brand-700 text-white">
              <Vote size={16} />
            </span>
            <span className="font-bold text-slate-800">
              Polling<span className="text-brand-600">Kita</span>
            </span>
          </div>
          <p className="text-center text-sm text-slate-500">
            Platform polling modern dengan deteksi anti-vote ganda. Dibuat untuk pemilihan yang
            adil & transparan.
          </p>
          <p className="text-sm text-slate-400">© {new Date().getFullYear()} PollingKita</p>
        </div>
      </div>
    </footer>
  );
}
