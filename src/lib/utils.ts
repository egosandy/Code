import { customAlphabet } from "nanoid";

const nano = customAlphabet("abcdefghijklmnopqrstuvwxyz0123456789", 8);

/** Buat slug ramah-URL dari judul + suffix acak agar unik. */
export function generateSlug(title: string): string {
  const base = title
    .toLowerCase()
    .trim()
    .replace(/[^a-z0-9\s-]/g, "")
    .replace(/\s+/g, "-")
    .replace(/-+/g, "-")
    .slice(0, 40)
    .replace(/^-|-$/g, "");
  return `${base || "polling"}-${nano()}`;
}

export function cn(...classes: (string | false | null | undefined)[]): string {
  return classes.filter(Boolean).join(" ");
}

export function formatDate(date: Date | string): string {
  const d = typeof date === "string" ? new Date(date) : date;
  return new Intl.DateTimeFormat("id-ID", {
    day: "numeric",
    month: "long",
    year: "numeric",
    hour: "2-digit",
    minute: "2-digit",
  }).format(d);
}

export function formatNumber(n: number): string {
  return new Intl.NumberFormat("id-ID").format(n);
}

export function percentage(value: number, total: number): number {
  if (total === 0) return 0;
  return Math.round((value / total) * 1000) / 10;
}

/** Status polling berdasarkan tanggal & flag aktif. */
export function pollStatus(poll: {
  isActive: boolean;
  startDate?: Date | string | null;
  endDate?: Date | string | null;
}): "active" | "scheduled" | "ended" | "closed" {
  if (!poll.isActive) return "closed";
  const now = new Date();
  if (poll.startDate && new Date(poll.startDate) > now) return "scheduled";
  if (poll.endDate && new Date(poll.endDate) < now) return "ended";
  return "active";
}
