"use client";

import { useEffect, useState } from "react";
import { MessageCircle, Send, Loader2 } from "lucide-react";
import { formatDate } from "@/lib/utils";

interface Comment {
  id: string;
  name: string;
  message: string;
  createdAt: string;
}

export default function CommentsSection({ slug }: { slug: string }) {
  const [comments, setComments] = useState<Comment[]>([]);
  const [name, setName] = useState("");
  const [message, setMessage] = useState("");
  const [sending, setSending] = useState(false);

  async function load() {
    try {
      const res = await fetch(`/api/comments?slug=${slug}`);
      const data = await res.json();
      setComments(data.comments || []);
    } catch {
      /* */
    }
  }

  useEffect(() => {
    load();
  }, [slug]);

  async function submit(e: React.FormEvent) {
    e.preventDefault();
    if (!name.trim() || !message.trim()) return;
    setSending(true);
    try {
      const res = await fetch("/api/comments", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ slug, name, message }),
      });
      if (res.ok) {
        setMessage("");
        await load();
      }
    } finally {
      setSending(false);
    }
  }

  return (
    <div className="card p-5">
      <h3 className="mb-4 flex items-center gap-2 font-bold text-slate-800">
        <MessageCircle size={18} className="text-brand-600" />
        Komentar ({comments.length})
      </h3>

      <form onSubmit={submit} className="mb-5 space-y-2">
        <input
          value={name}
          onChange={(e) => setName(e.target.value)}
          placeholder="Nama Anda"
          maxLength={50}
          className="input"
        />
        <div className="flex gap-2">
          <input
            value={message}
            onChange={(e) => setMessage(e.target.value)}
            placeholder="Tulis komentar..."
            maxLength={500}
            className="input flex-1"
          />
          <button type="submit" disabled={sending} className="btn-primary px-4">
            {sending ? <Loader2 size={16} className="animate-spin" /> : <Send size={16} />}
          </button>
        </div>
      </form>

      <div className="space-y-3">
        {comments.length === 0 ? (
          <p className="py-4 text-center text-sm text-slate-400">
            Belum ada komentar. Jadilah yang pertama!
          </p>
        ) : (
          comments.map((c) => (
            <div key={c.id} className="rounded-xl bg-slate-50 p-3">
              <div className="flex items-center justify-between">
                <span className="font-semibold text-slate-800">{c.name}</span>
                <span className="text-xs text-slate-400">{formatDate(c.createdAt)}</span>
              </div>
              <p className="mt-1 text-sm text-slate-600">{c.message}</p>
            </div>
          ))
        )}
      </div>
    </div>
  );
}
