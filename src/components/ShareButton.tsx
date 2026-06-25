"use client";

import { useState } from "react";
import { Share2, Copy, Check, MessageCircle } from "lucide-react";

export default function ShareButton({ url, title }: { url: string; title: string }) {
  const [copied, setCopied] = useState(false);

  async function copy() {
    try {
      await navigator.clipboard.writeText(url);
      setCopied(true);
      setTimeout(() => setCopied(false), 2000);
    } catch {
      // fallback
      window.prompt("Salin link ini:", url);
    }
  }

  async function nativeShare() {
    if (navigator.share) {
      try {
        await navigator.share({ title, url, text: `Ayo ikut memilih: ${title}` });
      } catch {
        /* dibatalkan */
      }
    } else {
      copy();
    }
  }

  const waUrl = `https://wa.me/?text=${encodeURIComponent(`Ayo ikut memilih: ${title}\n${url}`)}`;

  return (
    <div className="flex flex-wrap items-center gap-2">
      <button onClick={copy} className="btn-secondary">
        {copied ? <Check size={16} className="text-brand-600" /> : <Copy size={16} />}
        {copied ? "Tersalin!" : "Salin Link"}
      </button>
      <a href={waUrl} target="_blank" rel="noopener noreferrer" className="btn-secondary">
        <MessageCircle size={16} className="text-green-600" />
        WhatsApp
      </a>
      <button onClick={nativeShare} className="btn-primary">
        <Share2 size={16} />
        Bagikan
      </button>
    </div>
  );
}
