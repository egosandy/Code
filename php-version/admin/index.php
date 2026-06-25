<?php
require_once __DIR__ . '/../lib/layout.php';
require_once __DIR__ . '/../lib/polls.php';
require_admin();

$bp = base_path();
$polls = list_polls(false);
$totalVotes = array_sum(array_map(fn($p) => $p['voter_count'], $polls));
$activeCount = count(array_filter($polls, fn($p) => poll_status($p) === 'active'));

render_head('Dashboard — PollingKita');
render_navbar(true);
?>
<main class="mx-auto max-w-6xl px-4 py-8">
    <div class="flex flex-col sm:flex-row sm:items-center sm:justify-between gap-4 mb-6">
        <div>
            <h1 class="text-2xl font-extrabold">Dashboard Polling</h1>
            <p class="text-slate-500">Kelola semua polling dan pantau hasilnya</p>
        </div>
        <a href="<?= $bp ?>/admin/poll-form.php" class="btn btn-primary">+ Buat Polling Baru</a>
    </div>

    <div class="grid grid-cols-3 gap-3 mb-6">
        <?php
        $stats = [['Total Polling', count($polls)], ['Polling Aktif', $activeCount], ['Total Suara', $totalVotes]];
        foreach ($stats as $s): ?>
            <div class="card p-4 flex items-center gap-3">
                <span class="flex h-11 w-11 items-center justify-center rounded-xl bg-brand-50 text-brand-600 text-xl">📊</span>
                <div>
                    <div class="text-2xl font-extrabold"><?= format_number($s[1]) ?></div>
                    <div class="text-xs text-slate-500"><?= $s[0] ?></div>
                </div>
            </div>
        <?php endforeach; ?>
    </div>

    <?php if (count($polls) === 0): ?>
        <div class="card flex flex-col items-center justify-center py-16 text-center">
            <div class="text-4xl mb-3">🗳️</div>
            <p class="font-semibold text-slate-600">Belum ada polling</p>
            <p class="text-sm text-slate-400 mb-4">Mulai dengan membuat polling pertama Anda.</p>
            <a href="<?= $bp ?>/admin/poll-form.php" class="btn btn-primary">+ Buat Polling</a>
        </div>
    <?php else: ?>
        <div class="space-y-3">
            <?php foreach ($polls as $p): $status = poll_status($p); ?>
                <div class="card p-4 flex flex-col sm:flex-row sm:items-center gap-4" id="poll-<?= (int)$p['id'] ?>">
                    <div class="flex-1 min-w-0">
                        <div class="flex items-center gap-2 flex-wrap">
                            <h3 class="font-bold truncate"><?= e($p['title']) ?></h3>
                            <?= status_badge($status) ?>
                            <?php if ($p['is_private']): ?><span class="badge" style="background:#f1f5f9;color:#64748b">Privat</span><?php endif; ?>
                        </div>
                        <div class="mt-1 flex flex-wrap gap-x-4 gap-y-1 text-xs text-slate-500">
                            <span><?= (int)$p['candidate_count'] ?> kandidat</span>
                            <span><?= format_number($p['voter_count']) ?> suara</span>
                            <span><?= (int)$p['comment_count'] ?> komentar</span>
                            <span>Dibuat <?= format_date($p['created_at']) ?></span>
                        </div>
                    </div>
                    <div class="flex items-center gap-2 shrink-0">
                        <a href="<?= $bp ?>/vote.php?slug=<?= urlencode($p['slug']) ?>" target="_blank" class="btn btn-secondary" title="Buka voting">🔗</a>
                        <a href="<?= $bp ?>/admin/poll-manage.php?id=<?= (int)$p['id'] ?>" class="btn btn-secondary">⚙️ <span class="hidden sm:inline">Kelola</span></a>
                        <button onclick="deletePoll(<?= (int)$p['id'] ?>, '<?= e(addslashes($p['title'])) ?>')" class="btn btn-secondary text-red-600" title="Hapus">🗑️</button>
                    </div>
                </div>
            <?php endforeach; ?>
        </div>
    <?php endif; ?>
</main>
<script>
const BP = <?= json_encode($bp) ?>;
async function deletePoll(id, title) {
    if (!confirm('Hapus polling "' + title + '"? Semua suara akan terhapus permanen.')) return;
    const res = await fetch(BP + '/api/poll-delete.php', {
        method: 'POST', headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({ id })
    });
    if (res.ok) {
        document.getElementById('poll-' + id)?.remove();
    } else {
        alert('Gagal menghapus polling');
    }
}
</script>
<?php render_foot();
