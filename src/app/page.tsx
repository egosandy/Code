import Link from "next/link";
import { ArrowRight, ShieldCheck, Share2, BarChart3, Smartphone, Vote } from "lucide-react";
import Navbar from "@/components/Navbar";
import Footer from "@/components/Footer";
import { prisma } from "@/lib/db";
import { formatNumber, pollStatus } from "@/lib/utils";

export const dynamic = "force-dynamic";

async function getPolls() {
  const polls = await prisma.poll.findMany({
    where: { isPrivate: false },
    orderBy: { createdAt: "desc" },
    take: 12,
    include: { _count: { select: { ballots: true, candidates: true } } },
  });
  return polls;
}

export default async function HomePage() {
  const polls = await getPolls();

  return (
    <>
      <Navbar />
      <main>
        {/* Hero */}
        <section className="relative overflow-hidden">
          <div className="mx-auto max-w-6xl px-4 py-16 sm:py-24">
            <div className="mx-auto max-w-3xl text-center">
              <span className="badge mb-4 bg-brand-100 text-brand-700">
                <ShieldCheck size={14} /> 1 Perangkat = 1 Suara
              </span>
              <h1 className="text-4xl font-extrabold tracking-tight text-slate-900 sm:text-6xl">
                Buat <span className="text-brand-600">Polling Pemilihan</span> dalam Hitungan Menit
              </h1>
              <p className="mx-auto mt-5 max-w-2xl text-lg text-slate-600">
                Platform polling modern untuk pemilihan kepala desa, ketua organisasi, atau voting
                apa pun. Upload foto kandidat, bagikan link, dan kumpulkan suara dengan aman —
                lengkap dengan <strong>deteksi anti-vote ganda</strong>.
              </p>
              <div className="mt-8 flex flex-col items-center justify-center gap-3 sm:flex-row">
                <Link href="/admin" className="btn-primary px-6 py-3 text-base">
                  <Vote size={18} /> Buat Polling Sekarang
                </Link>
                <Link href="#polls" className="btn-secondary px-6 py-3 text-base">
                  Lihat Polling <ArrowRight size={18} />
                </Link>
              </div>
            </div>
          </div>
        </section>

        {/* Fitur */}
        <section className="mx-auto max-w-6xl px-4 pb-8">
          <div className="grid gap-4 sm:grid-cols-2 lg:grid-cols-4">
            <Feature
              icon={<ShieldCheck />}
              title="Anti Vote Ganda"
              desc="Deteksi perangkat dengan fingerprint browser + IP. Satu perangkat hanya bisa memilih sekali."
            />
            <Feature
              icon={<Share2 />}
              title="Link Dibagikan"
              desc="Bagikan link unik via WhatsApp atau media sosial. Siapa pun bisa langsung memilih."
            />
            <Feature
              icon={<BarChart3 />}
              title="Hasil Real-time"
              desc="Pantau perolehan suara secara langsung dengan grafik & persentase yang jelas."
            />
            <Feature
              icon={<Smartphone />}
              title="Responsif & Modern"
              desc="Tampilan optimal di HP, tablet, maupun desktop. Cepat dan mudah digunakan."
            />
          </div>
        </section>

        {/* Daftar polling */}
        <section id="polls" className="mx-auto max-w-6xl px-4 py-12">
          <div className="mb-6 flex items-end justify-between">
            <div>
              <h2 className="text-2xl font-extrabold text-slate-900">Polling Terbaru</h2>
              <p className="text-slate-500">Ikut berpartisipasi dalam pemilihan berikut</p>
            </div>
          </div>

          {polls.length === 0 ? (
            <div className="card flex flex-col items-center justify-center py-16 text-center">
              <Vote size={40} className="mb-3 text-slate-300" />
              <p className="font-semibold text-slate-600">Belum ada polling</p>
              <p className="text-sm text-slate-400">Buat polling pertama Anda dari panel admin.</p>
              <Link href="/admin" className="btn-primary mt-4">
                Ke Panel Admin
              </Link>
            </div>
          ) : (
            <div className="grid gap-4 sm:grid-cols-2 lg:grid-cols-3">
              {polls.map((p) => {
                const status = pollStatus(p);
                return (
                  <Link
                    key={p.id}
                    href={`/vote/${p.slug}`}
                    className="card group overflow-hidden transition-all hover:-translate-y-1 hover:shadow-lg"
                  >
                    <div className="relative h-32 bg-gradient-to-br from-brand-500 to-brand-700">
                      {p.bannerImage && (
                        // eslint-disable-next-line @next/next/no-img-element
                        <img
                          src={p.bannerImage}
                          alt={p.title}
                          className="h-full w-full object-cover"
                        />
                      )}
                      <span
                        className={`badge absolute left-3 top-3 ${
                          status === "active"
                            ? "bg-white/90 text-brand-700"
                            : "bg-slate-800/80 text-white"
                        }`}
                      >
                        {status === "active"
                          ? "● Berlangsung"
                          : status === "scheduled"
                            ? "Terjadwal"
                            : status === "ended"
                              ? "Berakhir"
                              : "Ditutup"}
                      </span>
                    </div>
                    <div className="p-4">
                      <h3 className="line-clamp-2 font-bold text-slate-900 group-hover:text-brand-700">
                        {p.title}
                      </h3>
                      {p.description && (
                        <p className="mt-1 line-clamp-2 text-sm text-slate-500">{p.description}</p>
                      )}
                      <div className="mt-3 flex items-center gap-4 text-xs text-slate-500">
                        <span>{p._count.candidates} kandidat</span>
                        <span>•</span>
                        <span>{formatNumber(p._count.ballots)} suara</span>
                      </div>
                    </div>
                  </Link>
                );
              })}
            </div>
          )}
        </section>
      </main>
      <Footer />
    </>
  );
}

function Feature({
  icon,
  title,
  desc,
}: {
  icon: React.ReactNode;
  title: string;
  desc: string;
}) {
  return (
    <div className="card p-5">
      <span className="mb-3 flex h-11 w-11 items-center justify-center rounded-xl bg-brand-50 text-brand-600">
        {icon}
      </span>
      <h3 className="font-bold text-slate-900">{title}</h3>
      <p className="mt-1 text-sm text-slate-500">{desc}</p>
    </div>
  );
}
