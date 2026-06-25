<?php
require_once __DIR__ . '/../lib/layout.php';
require_once __DIR__ . '/../lib/polls.php';
require_admin();

$bp = base_path();
$id = (int)($_GET['id'] ?? 0);
$poll = get_poll_by_id($id);
if (!$poll) {
    render_head('Tidak ditemukan');
    render_navbar(true);
    echo '<main class="mx-auto max-w-3xl px-4 py-20 text-center text-slate-500">Polling tidak ditemukan.</main>';
    render_foot();
    exit;
}

$candidates = get_candidates($id);
$totalVotes = array_sum(array_map(fn($c) => $c['votes'], $candidates));
$voters = total_voters($id);
$sorted = $candidates;
usort($sorted, fn($a, $b) => $b['votes'] - $a['votes']);
$leader = ($totalVotes > 0 && count($sorted)) ? $sorted[0] : null;
$shareUrl = base_url() . '/vote.php?slug=' . urlencode($poll['slug']);

// data untuk chart
$chartLabels = array_map(fn($c) => $c['ballot_no'] . '. ' . $c['name'], $candidates);
$chartData = array_map(fn($c) => $c['votes'], $candidates);

// komentar
$cst = db()->prepare('SELECT name, message, created_at FROM comments WHERE poll_id = ? ORDER BY created_at DESC, id DESC');
$cst->execute([$id]);
$comments = $cst->fetchAll();

render_head('Kelola: ' . $poll['title']);
render_navbar(true);
?>
<main class="mx-auto max-w-3xl px-4 py-8">
    <a href="<?= $bp ?>/admin/index.php" class="inline-flex items-center gap-1 text-sm font-medium text-slate-500 hover:text-slate-700 mb-4">← Kembali ke Dashboard</a>

    <div class="flex flex-col sm:flex-row sm:items-start sm:justify-between gap-3 mb-4">
        <div>
            <h1 class="text-2xl font-extrabold"><?= e($poll['title']) ?></h1>
            <?php if ($poll['description']): ?><p class="text-slate-500 mt-1"><?= e($poll['description']) ?></p><?php endif; ?>
        </div>
        <div class="flex gap-2 shrink-0">
            <a href="<?= $bp ?>/admin/poll-form.php?id=<?= $id ?>" class="btn btn-secondary">✏️ Edit</a>
            <a href="<?= $bp ?>/vote.php?slug=<?= urlencode($poll['slug']) ?>" target="_blank" class="btn btn-secondary">🔗 Voting</a>
        </div>
    </div>

    <!-- Share -->
    <div class="card p-4 mb-6">
        <p class="text-sm font-semibold text-slate-700 mb-2">Link untuk dibagikan:</p>
        <div class="truncate rounded-lg bg-slate-100 px-3 py-2 font-mono text-sm text-slate-600 mb-3" id="share-url"><?= e($shareUrl) ?></div>
        <div class="flex flex-wrap gap-2">
            <button onclick="copyLink()" class="btn btn-secondary">📋 Salin Link</button>
            <a href="https://wa.me/?text=<?= urlencode('Ayo ikut memilih: ' . $poll['title'] . "\n" . $shareUrl) ?>" target="_blank" class="btn btn-secondary">💬 WhatsApp</a>
        </div>
    </div>

    <!-- Statistik -->
    <div class="grid grid-cols-3 gap-3 mb-6">
        <?php
        $stats = [['Pemilih', format_number($voters)], ['Total Suara', format_number($totalVotes)], ['Unggul', $leader ? 'No. ' . $leader['ballot_no'] : '—']];
        foreach ($stats as $s): ?>
            <div class="card p-4 flex items-center gap-3">
                <span class="flex h-10 w-10 items-center justify-center rounded-xl bg-brand-50 text-brand-600">🏆</span>
                <div class="min-w-0">
                    <div class="text-xl font-extrabold truncate"><?= e($s[1]) ?></div>
                    <div class="text-xs text-slate-500"><?= $s[0] ?></div>
                </div>
            </div>
        <?php endforeach; ?>
    </div>

    <!-- Grafik -->
    <div class="card p-5 mb-6">
        <h2 class="font-bold mb-4">Grafik Perolehan Suara</h2>
        <?php if ($totalVotes === 0): ?>
            <p class="text-center text-slate-400 py-10">Belum ada suara masuk.</p>
        <?php else: ?>
            <canvas id="chart" height="<?= max(160, count($candidates) * 50) ?>"></canvas>
        <?php endif; ?>
    </div>

    <!-- Ranking -->
    <div class="card p-5 mb-6">
        <h2 class="font-bold mb-4">Peringkat Kandidat</h2>
        <div class="space-y-3">
            <?php foreach ($sorted as $i => $c): $pct = percentage($c['votes'], $totalVotes); ?>
                <div class="flex items-center gap-3">
                    <span class="flex h-8 w-8 items-center justify-center rounded-lg text-sm font-bold shrink-0" style="<?= $i === 0 && $totalVotes > 0 ? 'background:#fef3c7;color:#b45309' : 'background:#f1f5f9;color:#64748b' ?>"><?= $i + 1 ?></span>
                    <?php if ($c['photo']): ?>
                        <img src="<?= e($c['photo']) ?>" class="h-10 w-10 rounded-lg object-cover shrink-0">
                    <?php else: ?>
                        <div class="h-10 w-10 rounded-lg bg-slate-100 flex items-center justify-center font-bold text-slate-400 shrink-0"><?= $c['ballot_no'] ?></div>
                    <?php endif; ?>
                    <div class="flex-1 min-w-0">
                        <div class="flex items-center justify-between">
                            <span class="font-semibold truncate"><?= e($c['name']) ?></span>
                            <span class="text-sm font-bold text-brand-700 ml-2 shrink-0"><?= $pct ?>%</span>
                        </div>
                        <div class="mt-1 h-2 rounded-full bg-slate-100 overflow-hidden">
                            <div class="h-full rounded-full" style="width:<?= $pct ?>%;background:linear-gradient(to right,#10b981,#059669)"></div>
                        </div>
                    </div>
                    <span class="w-16 text-right text-sm text-slate-500 shrink-0"><?= format_number($c['votes']) ?></span>
                </div>
            <?php endforeach; ?>
        </div>
    </div>

    <?php if (count($comments)): ?>
        <div class="card p-5">
            <h2 class="font-bold mb-4">Komentar (<?= count($comments) ?>)</h2>
            <div class="space-y-2">
                <?php foreach ($comments as $cm): ?>
                    <div class="rounded-lg bg-slate-50 p-3 text-sm">
                        <span class="font-semibold text-slate-700"><?= e($cm['name']) ?>:</span>
                        <span class="text-slate-600"><?= e($cm['message']) ?></span>
                    </div>
                <?php endforeach; ?>
            </div>
        </div>
    <?php endif; ?>
</main>

<?php if ($totalVotes > 0): ?>
<script src="https://cdn.jsdelivr.net/npm/chart.js@4"></script>
<script>
const ctx = document.getElementById('chart');
new Chart(ctx, {
    type: 'bar',
    data: {
        labels: <?= json_encode($chartLabels, JSON_UNESCAPED_UNICODE) ?>,
        datasets: [{
            label: 'Suara',
            data: <?= json_encode($chartData) ?>,
            backgroundColor: ['#10b981','#059669','#34d399','#0891b2','#6366f1','#8b5cf6','#ec4899','#f59e0b'],
            borderRadius: 8,
        }]
    },
    options: {
        indexAxis: 'y',
        plugins: { legend: { display: false } },
        scales: { x: { beginAtZero: true, ticks: { precision: 0 } } }
    }
});
</script>
<?php endif; ?>
<script>
function copyLink() {
    const url = document.getElementById('share-url').textContent;
    navigator.clipboard.writeText(url).then(() => alert('Link tersalin!')).catch(() => prompt('Salin link:', url));
}
</script>
<?php render_foot();
