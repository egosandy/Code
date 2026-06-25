<?php
require_once __DIR__ . '/helpers.php';

function render_head(string $title, string $desc = ''): void
{
    $bp = base_path();
    ?>
<!DOCTYPE html>
<html lang="id">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="theme-color" content="#059669">
    <title><?= e($title) ?></title>
    <?php if ($desc): ?><meta name="description" content="<?= e($desc) ?>"><?php endif; ?>
    <meta property="og:title" content="<?= e($title) ?>">
    <meta property="og:description" content="<?= e($desc) ?>">
    <script src="https://cdn.tailwindcss.com"></script>
    <script>
        tailwind.config = {
            theme: {
                extend: {
                    colors: {
                        brand: {
                            50:'#ecfdf5',100:'#d1fae5',200:'#a7f3d0',300:'#6ee7b7',400:'#34d399',
                            500:'#10b981',600:'#059669',700:'#047857',800:'#065f46',900:'#064e3b'
                        }
                    }
                }
            }
        }
    </script>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700;800&display=swap" rel="stylesheet">
    <style>
        body{font-family:'Inter',system-ui,sans-serif;background:#f8fafc;
            background-image:radial-gradient(circle at 15% 50%,rgba(16,185,129,.06),transparent 25%),radial-gradient(circle at 85% 30%,rgba(5,150,105,.05),transparent 25%);background-attachment:fixed}
        .card{border-radius:1rem;border:1px solid #e2e8f0;background:#fff;box-shadow:0 1px 2px rgba(0,0,0,.04)}
        .btn{display:inline-flex;align-items:center;justify-content:center;gap:.5rem;border-radius:.75rem;padding:.625rem 1rem;font-size:.875rem;font-weight:600;transition:all .15s;cursor:pointer;border:0}
        .btn-primary{background:linear-gradient(to right,#059669,#10b981);color:#fff;box-shadow:0 4px 6px rgba(16,185,129,.2)}
        .btn-primary:hover{background:linear-gradient(to right,#047857,#059669)}
        .btn-primary:disabled{opacity:.6;cursor:not-allowed}
        .btn-secondary{background:#fff;color:#334155;border:1px solid #e2e8f0}
        .btn-secondary:hover{background:#f8fafc}
        .input{width:100%;border-radius:.75rem;border:1px solid #e2e8f0;background:#fff;padding:.625rem 1rem;font-size:.875rem;color:#0f172a}
        .input:focus{outline:none;border-color:#10b981;box-shadow:0 0 0 3px rgba(16,185,129,.25)}
        .label{display:block;margin-bottom:.375rem;font-size:.875rem;font-weight:600;color:#334155}
        .badge{display:inline-flex;align-items:center;gap:.25rem;border-radius:9999px;padding:.25rem .625rem;font-size:.75rem;font-weight:600}
    </style>
</head>
<body class="text-slate-900 antialiased">
    <?php
}

function render_navbar(bool $admin = false): void
{
    $bp = base_path();
    ?>
    <header class="sticky top-0 z-40 border-b border-slate-200/70 bg-white/80 backdrop-blur-lg">
        <div class="mx-auto max-w-6xl flex items-center justify-between px-4 py-3">
            <a href="<?= $bp ?>/index.php" class="flex items-center gap-2">
                <span class="flex h-9 w-9 items-center justify-center rounded-xl text-white" style="background:linear-gradient(135deg,#10b981,#047857)">🗳️</span>
                <span class="text-lg font-extrabold tracking-tight">Polling<span class="text-brand-600">Kita</span>
                <?php if ($admin): ?><span class="ml-1 rounded-md bg-slate-100 px-1.5 py-0.5 text-xs font-semibold text-slate-500 align-middle">Admin</span><?php endif; ?>
                </span>
            </a>
            <nav class="flex items-center gap-2">
                <?php if ($admin): ?>
                    <a href="<?= $bp ?>/index.php" class="btn btn-secondary">Situs</a>
                    <a href="<?= $bp ?>/admin/logout.php" class="btn btn-secondary text-red-600">Keluar</a>
                <?php else: ?>
                    <a href="<?= $bp ?>/index.php" class="hidden sm:inline-flex rounded-lg px-3 py-2 text-sm font-medium text-slate-600 hover:bg-slate-100">Beranda</a>
                    <a href="<?= $bp ?>/admin/index.php" class="btn btn-primary">Admin</a>
                <?php endif; ?>
            </nav>
        </div>
    </header>
    <?php
}

function render_foot(): void
{
    ?>
    <footer class="mt-16 border-t border-slate-200 bg-white">
        <div class="mx-auto max-w-6xl px-4 py-8 text-center text-sm text-slate-500">
            <p class="font-bold text-slate-700 mb-1">Polling<span class="text-brand-600">Kita</span></p>
            <p>Platform polling modern dengan deteksi anti-vote ganda — untuk pemilihan yang adil &amp; transparan.</p>
            <p class="mt-2 text-slate-400">© <?= date('Y') ?> PollingKita</p>
        </div>
    </footer>
</body>
</html>
    <?php
}

function status_badge(string $status): string
{
    $map = [
        'active' => ['● Berlangsung', 'background:#d1fae5;color:#047857'],
        'scheduled' => ['Terjadwal', 'background:#fef3c7;color:#b45309'],
        'ended' => ['Berakhir', 'background:#f1f5f9;color:#64748b'],
        'closed' => ['Ditutup', 'background:#fee2e2;color:#dc2626'],
    ];
    [$label, $style] = $map[$status] ?? $map['ended'];
    return '<span class="badge" style="' . $style . '">' . $label . '</span>';
}
