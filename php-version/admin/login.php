<?php
require_once __DIR__ . '/../lib/layout.php';

$error = '';
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $username = trim($_POST['username'] ?? '');
    $password = $_POST['password'] ?? '';
    $st = db()->prepare('SELECT * FROM admins WHERE username = ?');
    $st->execute([$username]);
    $admin = $st->fetch();
    if ($admin && password_verify($password, $admin['password'])) {
        session_regenerate_id(true);
        $_SESSION['admin_id'] = (int)$admin['id'];
        $_SESSION['admin_username'] = $admin['username'];
        redirect(base_path() . '/admin/index.php');
    } else {
        $error = 'Username atau password salah';
    }
}

if (is_logged_in()) redirect(base_path() . '/admin/index.php');
$bp = base_path();
render_head('Masuk Admin — PollingKita');
?>
<main class="min-h-screen flex items-center justify-center px-4">
    <div class="w-full max-w-sm">
        <a href="<?= $bp ?>/index.php" class="flex items-center justify-center gap-2 mb-6">
            <span class="flex h-11 w-11 items-center justify-center rounded-xl text-white text-xl" style="background:linear-gradient(135deg,#10b981,#047857)">🗳️</span>
            <span class="text-2xl font-extrabold">Polling<span class="text-brand-600">Kita</span></span>
        </a>
        <div class="card p-6">
            <h1 class="text-xl font-bold">Masuk Admin</h1>
            <p class="text-sm text-slate-500 mb-5">Kelola polling dan lihat hasil pemilihan</p>
            <form method="post" class="space-y-4">
                <div>
                    <label class="label">Username</label>
                    <input name="username" class="input" placeholder="admin" required autofocus>
                </div>
                <div>
                    <label class="label">Password</label>
                    <input type="password" name="password" class="input" placeholder="••••••••" required>
                </div>
                <?php if ($error): ?>
                    <div class="rounded-xl border border-red-200 bg-red-50 px-4 py-2.5 text-sm text-red-700"><?= e($error) ?></div>
                <?php endif; ?>
                <button class="btn btn-primary w-full py-3">Masuk</button>
            </form>
        </div>
        <p class="mt-4 text-center text-xs text-slate-400">Default: <code class="bg-slate-100 px-1 rounded">admin</code> / <code class="bg-slate-100 px-1 rounded">admin123</code> (ubah di config.php)</p>
    </div>
</main>
<?php render_foot();
