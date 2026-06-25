"use client";

import { useRef, useState } from "react";
import { Upload, X, Loader2, ImageIcon } from "lucide-react";

export default function ImageUpload({
  value,
  onChange,
  label,
  aspect = "square",
}: {
  value?: string | null;
  onChange: (url: string | null) => void;
  label?: string;
  aspect?: "square" | "banner";
}) {
  const inputRef = useRef<HTMLInputElement>(null);
  const [uploading, setUploading] = useState(false);
  const [error, setError] = useState("");

  async function handleFile(file: File) {
    setError("");
    setUploading(true);
    try {
      const fd = new FormData();
      fd.append("file", file);
      const res = await fetch("/api/upload", { method: "POST", body: fd });
      const data = await res.json();
      if (!res.ok) throw new Error(data.error || "Gagal mengunggah");
      onChange(data.url);
    } catch (e: any) {
      setError(e.message);
    } finally {
      setUploading(false);
    }
  }

  const boxClass =
    aspect === "banner" ? "aspect-[3/1] w-full" : "h-28 w-28";

  return (
    <div>
      {label && <label className="label">{label}</label>}
      <div className="flex items-center gap-3">
        <div
          className={`relative ${boxClass} shrink-0 overflow-hidden rounded-xl border-2 border-dashed border-slate-300 bg-slate-50`}
        >
          {value ? (
            <>
              {/* eslint-disable-next-line @next/next/no-img-element */}
              <img src={value} alt="preview" className="h-full w-full object-cover" />
              <button
                type="button"
                onClick={() => onChange(null)}
                className="absolute right-1 top-1 rounded-full bg-red-500 p-1 text-white shadow hover:bg-red-600"
              >
                <X size={14} />
              </button>
            </>
          ) : (
            <div className="flex h-full w-full flex-col items-center justify-center text-slate-400">
              <ImageIcon size={aspect === "banner" ? 28 : 22} />
            </div>
          )}
        </div>

        <div>
          <button
            type="button"
            onClick={() => inputRef.current?.click()}
            disabled={uploading}
            className="btn-secondary"
          >
            {uploading ? (
              <Loader2 size={16} className="animate-spin" />
            ) : (
              <Upload size={16} />
            )}
            {value ? "Ganti" : "Unggah"}
          </button>
          <p className="mt-1 text-xs text-slate-400">JPG, PNG, WEBP (maks 5MB)</p>
          {error && <p className="mt-1 text-xs text-red-500">{error}</p>}
        </div>
      </div>

      <input
        ref={inputRef}
        type="file"
        accept="image/*"
        className="hidden"
        onChange={(e) => {
          const f = e.target.files?.[0];
          if (f) handleFile(f);
          e.target.value = "";
        }}
      />
    </div>
  );
}
