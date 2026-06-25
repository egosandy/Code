import { PrismaClient } from "@prisma/client";
import bcrypt from "bcryptjs";

const prisma = new PrismaClient();

async function main() {
  const username = process.env.ADMIN_USERNAME || "admin";
  const password = process.env.ADMIN_PASSWORD || "admin123";
  const hash = await bcrypt.hash(password, 10);

  await prisma.admin.upsert({
    where: { username },
    update: { password: hash },
    create: { username, password: hash, name: "Administrator" },
  });
  console.log(`✅ Admin '${username}' siap. Login dengan password yang Anda set di .env`);

  // Contoh polling demo (pemilihan kepala desa)
  const existing = await prisma.poll.findUnique({ where: { slug: "pilkades-demo" } });
  if (!existing) {
    await prisma.poll.create({
      data: {
        slug: "pilkades-demo",
        title: "Pemilihan Kepala Desa Sukamaju 2026",
        description:
          "Pilih calon kepala desa pilihan Anda. Satu warga satu suara. Suara Anda menentukan masa depan desa kita!",
        type: "single",
        maxChoices: 1,
        isActive: true,
        showResults: "always",
        allowComments: true,
        candidates: {
          create: [
            {
              name: "H. Ahmad Suryadi",
              ballotNo: 1,
              order: 1,
              description: "Pengusaha & tokoh masyarakat",
              extraFields: JSON.stringify([
                { label: "Visi", value: "Desa Maju, Mandiri, dan Sejahtera" },
                { label: "Asal Dusun", value: "Dusun Krajan" },
              ]),
            },
            {
              name: "Hj. Siti Nurhaliza",
              ballotNo: 2,
              order: 2,
              description: "Mantan Sekretaris Desa",
              extraFields: JSON.stringify([
                { label: "Visi", value: "Pelayanan Prima untuk Warga" },
                { label: "Asal Dusun", value: "Dusun Sumber" },
              ]),
            },
            {
              name: "Budi Santoso, S.E.",
              ballotNo: 3,
              order: 3,
              description: "Aktivis pemuda & pegiat UMKM",
              extraFields: JSON.stringify([
                { label: "Visi", value: "Ekonomi Kreatif Berbasis Digital" },
                { label: "Asal Dusun", value: "Dusun Mekar" },
              ]),
            },
          ],
        },
      },
    });
    console.log("✅ Polling demo 'pilkades-demo' dibuat.");
  }
}

main()
  .then(() => prisma.$disconnect())
  .catch(async (e) => {
    console.error(e);
    await prisma.$disconnect();
    process.exit(1);
  });
