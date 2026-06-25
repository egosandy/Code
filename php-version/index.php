<?php
require_once __DIR__ . '/lib/layout.php';
require_once __DIR__ . '/lib/polls.php';

$bp = base_path();
$polls = list_polls(true); // hanya publik

render_head('PollingKita — Buat & Bagikan Polling Pemilihan Suara',
    'Platform polling modern untuk pemilihan suara, pilkades, dan voting online. 1 perangkat 1 suara dengan deteksi anti-vote ganda.');
render_navbar(false);
?>
<main>
    <!-- Hero -->
    <section class="mx-auto max-w-6xl px-4 py-16 sm:py-24 text-center">
        <span class="badge mb-4" style="background:#d1fae5;color:#047857">🛡️ 1 Perangkat = 1 Suara</span>
        <h1 class="text-4xl sm:text-6xl font-extrabold tracking-tight">Buat <span class="text-brand-600">Polling Pemilihan</span> dalam Hitungan Menit</h1>
        <p class="mx-auto mt-5 max-w-2xl text-lg text-slate-600">Platform polling modern untuk pemilihan kepala desa, ketua organisasi, atau voting apa pun. Upload foto kandidat, bagikan link, dan kumpulkan suara dengan aman — lengkap dengan <strong>deteksi anti-vote ganda</strong>.</p>
        <div class="mt-8 flex flex-col sm:flex-row items-center justify-center gap-3">
            <a href="<?= $bp ?>/admin/index.php" class="btn btn-primary px-6 py-3 text-base">🗳️ Buat Polling Sekarang</a>
            <a href="#polls" class="btn btn-secondary px-6 py-3 text-base">Lihat Polling →</a>
        </div>
    </section>

    <!-- Fitur -->
    <section class="mx-auto max-w-6xl px-4 pb-8">
        <div class="grid sm:grid-cols-2 lg:grid-cols-4 gap-4">
            <?php
            $features = [
                ['🛡️', 'Anti Vote Ganda', 'Deteksi perangkat dengan fingerprint browser + IP. Satu perangkat hanya bisa memilih sekali.'],
                ['🔗', 'Link Dibagikan', 'Bagikan link unik via WhatsApp atau media sosial. Siapa pun bisa langsung memilih.'],
                ['📊', 'Hasil Real-time', 'Pantau perolehan suara secara langsung dengan grafik & persentase yang jelas.'],
                ['📱', 'Responsif & Modern', 'Tampilan optimal di HP, tablet, maupun desktop. Cepat dan mudah digunakan.'],
            ];
            foreach ($features as $f): ?>
                <div class="card p-5">
                    <span class="flex h-11 w-11 items-center justify-center rounded-xl bg-brand-50 text-xl mb-3"><?= $f[0] ?></span>
                    <h3 class="font-bold"><?= $f[1] ?></h3>
                    <p class="text-sm text-slate-500 mt-1"><?= $f[2] ?></p>
                </div>
            <?php endforeach; ?>
        </div>
    </section>

    <!-- Daftar polling -->
    <section id="polls" class="mx-auto max-w-6xl px-4 py-12">
        <div class="mb-6">
            <h2 class="text-2xl font-extrabold">Polling Terbaru</h2>
            <p class="text-slate-500">Ikut berpartisipasi dalam pemilihan berikut</p>
        </div>

        <?php if (count($polls) === 0): ?>
            <div class="card flex flex-col items-center justify-center py-16 text-center">
                <div class="text-4xl mb-3">🗳️</div>
                <p class="font-semibold text-slate-600">Belum ada polling</p>
                <p class="text-sm text-slate-400 mb-4">Buat polling pertama Anda dari panel admin.</p>
                <a href="<?= $bp ?>/admin/index.php" class="btn btn-primary">Ke Panel Admin</a>
            </div>
        <?php else: ?>
            <div class="grid sm:grid-cols-2 lg:grid-cols-3 gap-4">
                <?php foreach ($polls as $p): $status = poll_status($p); ?>
                    <a href="<?= $bp ?>/vote.php?slug=<?= urlencode($p['slug']) ?>" class="card overflow-hidden transition hover:-translate-y-1 hover:shadow-lg block">
                        <div class="relative h-32" style="background:linear-gradient(135deg,#10b981,#047857)">
                            <?php if ($p['banner_image']): ?><img src="<?= e($p['banner_image']) ?>" class="w-full h-full object-cover"><?php endif; ?>
                            <span class="badge absolute left-3 top-3" style="background:rgba(255,255,255,.9);color:#047857"><?= status_badge($status) ?></span>
                        </div>
                        <div class="p-4">
                            <h3 class="font-bold line-clamp-2"><?= e($p['title']) ?></h3>
                            <?php if ($p['description']): ?><p class="mt-1 text-sm text-slate-500 line-clamp-2"><?= e($p['description']) ?></p><?php endif; ?>
                            <div class="mt-3 flex items-center gap-3 text-xs text-slate-500">
                                <span><?= (int)$p['candidate_count'] ?> kandidat</span><span>•</span>
                                <span><?= format_number($p['voter_count']) ?> suara</span>
                            </div>
                        </div>
                    </a>
                <?php endforeach; ?>
            </div>
        <?php endif; ?>
    </section>
</main>
<?php render_foot();
