"use client";

import {
  BarChart,
  Bar,
  XAxis,
  YAxis,
  Tooltip,
  ResponsiveContainer,
  Cell,
  CartesianGrid,
} from "recharts";

const COLORS = [
  "#10b981",
  "#059669",
  "#34d399",
  "#0891b2",
  "#6366f1",
  "#8b5cf6",
  "#ec4899",
  "#f59e0b",
];

export default function ResultsChart({
  data,
}: {
  data: { name: string; suara: number }[];
}) {
  return (
    <ResponsiveContainer width="100%" height={Math.max(240, data.length * 56)}>
      <BarChart data={data} layout="vertical" margin={{ left: 8, right: 24 }}>
        <CartesianGrid strokeDasharray="3 3" horizontal={false} stroke="#e2e8f0" />
        <XAxis type="number" allowDecimals={false} stroke="#94a3b8" fontSize={12} />
        <YAxis
          type="category"
          dataKey="name"
          width={110}
          stroke="#64748b"
          fontSize={12}
          tickLine={false}
        />
        <Tooltip
          cursor={{ fill: "rgba(16,185,129,0.06)" }}
          contentStyle={{
            borderRadius: 12,
            border: "1px solid #e2e8f0",
            fontSize: 13,
          }}
        />
        <Bar dataKey="suara" radius={[0, 8, 8, 0]} barSize={28}>
          {data.map((_, i) => (
            <Cell key={i} fill={COLORS[i % COLORS.length]} />
          ))}
        </Bar>
      </BarChart>
    </ResponsiveContainer>
  );
}
