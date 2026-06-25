import type { Metadata, Viewport } from "next";
import { Inter } from "next/font/google";
import "./globals.css";

const inter = Inter({ subsets: ["latin"], variable: "--font-inter" });

export const metadata: Metadata = {
  title: "PollingKita — Buat & Bagikan Polling Pemilihan Suara",
  description:
    "Platform polling modern untuk pemilihan suara, pilkades, dan voting online. 1 perangkat 1 suara dengan deteksi anti-vote ganda.",
  keywords: ["polling", "voting", "pemilihan", "pilkades", "survei", "pemilihan suara"],
  authors: [{ name: "PollingKita" }],
  openGraph: {
    title: "PollingKita — Polling Pemilihan Suara Online",
    description: "Buat polling, bagikan link, dan kumpulkan suara secara aman.",
    type: "website",
  },
};

export const viewport: Viewport = {
  width: "device-width",
  initialScale: 1,
  themeColor: "#059669",
};

export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <html lang="id" className={inter.variable}>
      <body>{children}</body>
    </html>
  );
}
