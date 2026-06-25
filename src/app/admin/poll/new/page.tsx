import Link from "next/link";
import { ArrowLeft } from "lucide-react";
import AdminHeader from "@/components/AdminHeader";
import PollForm from "@/components/PollForm";

export default function NewPollPage() {
  return (
    <>
      <AdminHeader />
      <main className="mx-auto max-w-3xl px-4 py-8">
        <Link
          href="/admin"
          className="mb-4 inline-flex items-center gap-1 text-sm font-medium text-slate-500 hover:text-slate-700"
        >
          <ArrowLeft size={16} /> Kembali ke Dashboard
        </Link>
        <h1 className="mb-6 text-2xl font-extrabold text-slate-900">Buat Polling Baru</h1>
        <PollForm />
      </main>
    </>
  );
}
