"use client";

import { useState } from "react";
import { useRouter } from "next/navigation";
import {
  Plus,
  Trash2,
  GripVertical,
  Save,
  Loader2,
  Settings2,
  X,
  ListPlus,
} from "lucide-react";
import ImageUpload from "./ImageUpload";

export interface ExtraField {
  label: string;
  value: string;
}
export interface CandidateForm {
  id?: string;
  name: string;
  description: string;
  photo: string | null;
  ballotNo: number;
  extraFields: ExtraField[];
}
export interface PollFormData {
  id?: string;
  title: string;
  description: string;
  bannerImage: string | null;
  type: "single" | "multiple";
  maxChoices: number;
  isActive: boolean;
  isPrivate: boolean;
  showResults: "always" | "afterVote" | "afterClose";
  allowComments: boolean;
  startDate: string;
  endDate: string;
  candidates: CandidateForm[];
}

const emptyCandidate = (no: number): CandidateForm => ({
  name: "",
  description: "",
  photo: null,
  ballotNo: no,
  extraFields: [],
});

export default function PollForm({ initial }: { initial?: PollFormData }) {
  const router = useRouter();
  const isEdit = !!initial?.id;

  const [data, setData] = useState<PollFormData>(
    initial || {
      title: "",
      description: "",
      bannerImage: null,
      type: "single",
      maxChoices: 1,
      isActive: true,
      isPrivate: false,
      showResults: "always",
      allowComments: true,
      startDate: "",
      endDate: "",
      candidates: [emptyCandidate(1), emptyCandidate(2)],
    }
  );
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState("");

  function update<K extends keyof PollFormData>(key: K, val: PollFormData[K]) {
    setData((d) => ({ ...d, [key]: val }));
  }

  function updateCandidate(idx: number, patch: Partial<CandidateForm>) {
    setData((d) => ({
      ...d,
      candidates: d.candidates.map((c, i) => (i === idx ? { ...c, ...patch } : c)),
    }));
  }

  function addCandidate() {
    setData((d) => ({
      ...d,
      candidates: [...d.candidates, emptyCandidate(d.candidates.length + 1)],
    }));
  }

  function removeCandidate(idx: number) {
    setData((d) => ({
      ...d,
      candidates: d.candidates.filter((_, i) => i !== idx),
    }));
  }

  function addExtra(idx: number) {
    updateCandidate(idx, {
      extraFields: [...data.candidates[idx].extraFields, { label: "", value: "" }],
    });
  }
  function updateExtra(ci: number, fi: number, patch: Partial<ExtraField>) {
    const fields = data.candidates[ci].extraFields.map((f, i) =>
      i === fi ? { ...f, ...patch } : f
    );
    updateCandidate(ci, { extraFields: fields });
  }
  function removeExtra(ci: number, fi: number) {
    updateCandidate(ci, {
      extraFields: data.candidates[ci].extraFields.filter((_, i) => i !== fi),
    });
  }

  async function submit() {
    setError("");
    if (data.title.trim().length < 3) {
      setError("Judul polling minimal 3 karakter");
      return;
    }
    const validCands = data.candidates.filter((c) => c.name.trim());
    if (validCands.length < 2) {
      setError("Minimal 2 kandidat / pilihan dengan nama terisi");
      return;
    }

    setSaving(true);
    try {
      const url = isEdit ? `/api/admin/polls/${data.id}` : "/api/admin/polls";
      const method = isEdit ? "PUT" : "POST";
      const res = await fetch(url, {
        method,
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          ...data,
          startDate: data.startDate || null,
          endDate: data.endDate || null,
        }),
      });
      const result = await res.json();
      if (!res.ok) {
        setError(result.error || "Gagal menyimpan");
        setSaving(false);
        return;
      }
      router.push(`/admin/poll/${result.poll.id}`);
      router.refresh();
    } catch {
      setError("Terjadi kesalahan jaringan");
      setSaving(false);
    }
  }

  return (
    <div className="space-y-6">
      {/* Detail polling */}
      <section className="card p-5">
        <h2 className="mb-4 flex items-center gap-2 font-bold text-slate-800">
          <Settings2 size={18} className="text-brand-600" /> Detail Polling
        </h2>
        <div className="space-y-4">
          <div>
            <label className="label">Judul / Pertanyaan *</label>
            <input
              value={data.title}
              onChange={(e) => update("title", e.target.value)}
              placeholder="Contoh: Pemilihan Kepala Desa Sukamaju 2026"
              maxLength={160}
              className="input"
            />
            <p className="mt-1 text-xs text-slate-400">{160 - data.title.length} karakter tersisa</p>
          </div>
          <div>
            <label className="label">Deskripsi</label>
            <textarea
              value={data.description}
              onChange={(e) => update("description", e.target.value)}
              placeholder="Jelaskan tujuan polling, aturan, atau informasi tambahan..."
              rows={3}
              className="input resize-none"
            />
          </div>
          <ImageUpload
            label="Banner / Gambar Header (opsional)"
            value={data.bannerImage}
            onChange={(url) => update("bannerImage", url)}
            aspect="banner"
          />
        </div>
      </section>

      {/* Kandidat */}
      <section className="card p-5">
        <div className="mb-4 flex items-center justify-between">
          <h2 className="flex items-center gap-2 font-bold text-slate-800">
            <ListPlus size={18} className="text-brand-600" /> Kandidat / Pilihan
          </h2>
          <span className="text-sm text-slate-400">{data.candidates.length} kandidat</span>
        </div>

        <div className="space-y-4">
          {data.candidates.map((c, idx) => (
            <div key={idx} className="rounded-2xl border border-slate-200 bg-slate-50/50 p-4">
              <div className="mb-3 flex items-center justify-between">
                <span className="badge bg-brand-100 text-brand-700">
                  <GripVertical size={12} /> Kandidat {idx + 1}
                </span>
                {data.candidates.length > 2 && (
                  <button
                    type="button"
                    onClick={() => removeCandidate(idx)}
                    className="text-red-500 hover:text-red-600"
                  >
                    <Trash2 size={16} />
                  </button>
                )}
              </div>

              <div className="flex flex-col gap-4 sm:flex-row">
                <ImageUpload
                  value={c.photo}
                  onChange={(url) => updateCandidate(idx, { photo: url })}
                />
                <div className="flex-1 space-y-3">
                  <div className="flex gap-3">
                    <div className="w-20">
                      <label className="label">No. Urut</label>
                      <input
                        type="number"
                        min={1}
                        value={c.ballotNo}
                        onChange={(e) =>
                          updateCandidate(idx, { ballotNo: parseInt(e.target.value) || idx + 1 })
                        }
                        className="input px-2 text-center"
                      />
                    </div>
                    <div className="flex-1">
                      <label className="label">Nama *</label>
                      <input
                        value={c.name}
                        onChange={(e) => updateCandidate(idx, { name: e.target.value })}
                        placeholder="Nama kandidat"
                        className="input"
                      />
                    </div>
                  </div>
                  <div>
                    <label className="label">Keterangan singkat</label>
                    <input
                      value={c.description}
                      onChange={(e) => updateCandidate(idx, { description: e.target.value })}
                      placeholder="Contoh: Pengusaha & tokoh masyarakat"
                      className="input"
                    />
                  </div>

                  {/* Custom fields */}
                  {c.extraFields.length > 0 && (
                    <div className="space-y-2 rounded-xl bg-white p-3">
                      <p className="text-xs font-semibold text-slate-500">Informasi Tambahan</p>
                      {c.extraFields.map((f, fi) => (
                        <div key={fi} className="flex gap-2">
                          <input
                            value={f.label}
                            onChange={(e) => updateExtra(idx, fi, { label: e.target.value })}
                            placeholder="Label (mis. Visi)"
                            className="input w-1/3"
                          />
                          <input
                            value={f.value}
                            onChange={(e) => updateExtra(idx, fi, { value: e.target.value })}
                            placeholder="Isi"
                            className="input flex-1"
                          />
                          <button
                            type="button"
                            onClick={() => removeExtra(idx, fi)}
                            className="btn-secondary px-2 text-red-500"
                          >
                            <X size={16} />
                          </button>
                        </div>
                      ))}
                    </div>
                  )}
                  <button
                    type="button"
                    onClick={() => addExtra(idx)}
                    className="text-sm font-medium text-brand-600 hover:text-brand-700"
                  >
                    + Tambah info kustom (visi, asal, dll)
                  </button>
                </div>
              </div>
            </div>
          ))}
        </div>

        <button type="button" onClick={addCandidate} className="btn-secondary mt-4 w-full">
          <Plus size={18} /> Tambah Kandidat
        </button>
      </section>

      {/* Pengaturan */}
      <section className="card p-5">
        <h2 className="mb-4 flex items-center gap-2 font-bold text-slate-800">
          <Settings2 size={18} className="text-brand-600" /> Pengaturan
        </h2>
        <div className="space-y-4">
          <div className="grid gap-4 sm:grid-cols-2">
            <div>
              <label className="label">Tipe Pemilihan</label>
              <select
                value={data.type}
                onChange={(e) => update("type", e.target.value as "single" | "multiple")}
                className="input"
              >
                <option value="single">Pilih 1 kandidat</option>
                <option value="multiple">Bisa pilih lebih dari 1</option>
              </select>
            </div>
            {data.type === "multiple" && (
              <div>
                <label className="label">Maksimal Pilihan</label>
                <input
                  type="number"
                  min={1}
                  max={data.candidates.length}
                  value={data.maxChoices}
                  onChange={(e) => update("maxChoices", parseInt(e.target.value) || 1)}
                  className="input"
                />
              </div>
            )}
            <div>
              <label className="label">Tampilkan Hasil</label>
              <select
                value={data.showResults}
                onChange={(e) => update("showResults", e.target.value as any)}
                className="input"
              >
                <option value="always">Selalu tampilkan</option>
                <option value="afterVote">Setelah memilih</option>
                <option value="afterClose">Setelah ditutup</option>
              </select>
            </div>
          </div>

          <div className="grid gap-4 sm:grid-cols-2">
            <div>
              <label className="label">Tanggal Mulai (opsional)</label>
              <input
                type="datetime-local"
                value={data.startDate}
                onChange={(e) => update("startDate", e.target.value)}
                className="input"
              />
            </div>
            <div>
              <label className="label">Tanggal Selesai (opsional)</label>
              <input
                type="datetime-local"
                value={data.endDate}
                onChange={(e) => update("endDate", e.target.value)}
                className="input"
              />
            </div>
          </div>

          <div className="grid gap-3 sm:grid-cols-3">
            <Toggle
              label="Aktif"
              desc="Polling dapat menerima suara"
              checked={data.isActive}
              onChange={(v) => update("isActive", v)}
            />
            <Toggle
              label="Privat"
              desc="Sembunyikan dari beranda"
              checked={data.isPrivate}
              onChange={(v) => update("isPrivate", v)}
            />
            <Toggle
              label="Izinkan Komentar"
              desc="Pengunjung bisa berkomentar"
              checked={data.allowComments}
              onChange={(v) => update("allowComments", v)}
            />
          </div>
        </div>
      </section>

      {error && (
        <div className="rounded-xl border border-red-200 bg-red-50 px-4 py-3 text-sm text-red-700">
          {error}
        </div>
      )}

      <div className="sticky bottom-4 flex gap-3">
        <button
          type="button"
          onClick={() => router.back()}
          className="btn-secondary flex-1 py-3"
        >
          Batal
        </button>
        <button
          type="button"
          onClick={submit}
          disabled={saving}
          className="btn-primary flex-[2] py-3 shadow-lg shadow-brand-500/30"
        >
          {saving ? (
            <Loader2 size={18} className="animate-spin" />
          ) : (
            <Save size={18} />
          )}
          {isEdit ? "Simpan Perubahan" : "Buat Polling"}
        </button>
      </div>
    </div>
  );
}

function Toggle({
  label,
  desc,
  checked,
  onChange,
}: {
  label: string;
  desc: string;
  checked: boolean;
  onChange: (v: boolean) => void;
}) {
  return (
    <button
      type="button"
      onClick={() => onChange(!checked)}
      className={`flex items-start gap-3 rounded-xl border p-3 text-left transition ${
        checked ? "border-brand-400 bg-brand-50" : "border-slate-200 bg-white"
      }`}
    >
      <span
        className={`mt-0.5 flex h-5 w-9 shrink-0 items-center rounded-full p-0.5 transition ${
          checked ? "bg-brand-500" : "bg-slate-300"
        }`}
      >
        <span
          className={`h-4 w-4 rounded-full bg-white shadow transition ${
            checked ? "translate-x-4" : ""
          }`}
        />
      </span>
      <span>
        <span className="block text-sm font-semibold text-slate-800">{label}</span>
        <span className="block text-xs text-slate-500">{desc}</span>
      </span>
    </button>
  );
}
