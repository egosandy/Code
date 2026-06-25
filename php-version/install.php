<?php
/**
 * Installer PollingKita — jalankan SEKALI lewat browser: https://domainanda.com/install.php
 * Membuat tabel, akun admin awal, dan satu polling demo.
 * HAPUS atau ganti nama file ini setelah instalasi selesai.
 */
require_once __DIR__ . '/lib/helpers.php';

$messages = [];
$err = null;

try {
    $pdo = db();
    $isMysql = DB_DRIVER === 'mysql';

    // ---- definisi tipe per-driver ----
    $pk = $isMysql ? 'INT AUTO_INCREMENT PRIMARY KEY' : 'INTEGER PRIMARY KEY AUTOINCREMENT';
    $ts = $isMysql ? 'TIMESTAMP DEFAULT CURRENT_TIMESTAMP' : 'DATETIME DEFAULT CURRENT_TIMESTAMP';
    $eng = $isMysql ? 'ENGINE=InnoDB DEFAULT CHARSET=utf8mb4' : '';

    $stmts = [];

    $stmts[] = "CREATE TABLE IF NOT EXISTS admins (
        id $pk,
        username VARCHAR(50) NOT NULL UNIQUE,
        password VARCHAR(255) NOT NULL,
        name VARCHAR(100),
        created_at $ts
    ) $eng";

    $stmts[] = "CREATE TABLE IF NOT EXISTS polls (
        id $pk,
        slug VARCHAR(80) NOT NULL UNIQUE,
        title VARCHAR(200) NOT NULL,
        description TEXT,
        banner_image VARCHAR(255),
        type VARCHAR(10) DEFAULT 'single',
        max_choices INT DEFAULT 1,
        is_active INT DEFAULT 1,
        is_private INT DEFAULT 0,
        show_results VARCHAR(20) DEFAULT 'always',
        allow_comments INT DEFAULT 1,
        start_date DATETIME NULL,
        end_date DATETIME NULL,
        created_at $ts
    ) $eng";

    $stmts[] = "CREATE TABLE IF NOT EXISTS candidates (
        id $pk,
        poll_id INT NOT NULL,
        name VARCHAR(150) NOT NULL,
        description VARCHAR(255),
        photo VARCHAR(255),
        ballot_no INT DEFAULT 0,
        sort_order INT DEFAULT 0,
        extra_fields TEXT
    ) $eng";

    $stmts[] = "CREATE TABLE IF NOT EXISTS ballots (
        id $pk,
        poll_id INT NOT NULL,
        device_hash VARCHAR(64) NOT NULL,
        fingerprint VARCHAR(100),
        ip_hash VARCHAR(64),
        user_agent VARCHAR(255),
        created_at $ts,
        UNIQUE (poll_id, device_hash)
    ) $eng";

    $stmts[] = "CREATE TABLE IF NOT EXISTS votes (
        id $pk,
        ballot_id INT NOT NULL,
        candidate_id INT NOT NULL
    ) $eng";

    $stmts[] = "CREATE TABLE IF NOT EXISTS comments (
        id $pk,
        poll_id INT NOT NULL,
        name VARCHAR(50) NOT NULL,
        message VARCHAR(500) NOT NULL,
        created_at $ts
    ) $eng";

    foreach ($stmts as $sql) {
        $pdo->exec($sql);
    }
    $messages[] = '✅ Tabel database berhasil dibuat.';

    // index tambahan
    @$pdo->exec("CREATE INDEX idx_candidates_poll ON candidates(poll_id)");
    @$pdo->exec("CREATE INDEX idx_ballots_poll ON ballots(poll_id)");
    @$pdo->exec("CREATE INDEX idx_votes_candidate ON votes(candidate_id)");
    @$pdo->exec("CREATE INDEX idx_votes_ballot ON votes(ballot_id)");
    @$pdo->exec("CREATE INDEX idx_comments_poll ON comments(poll_id)");

    // ---- akun admin ----
    $hash = password_hash(ADMIN_PASSWORD, PASSWORD_DEFAULT);
    $exist = $pdo->prepare('SELECT id FROM admins WHERE username = ?');
    $exist->execute([ADMIN_USERNAME]);
    if ($exist->fetch()) {
        $upd = $pdo->prepare('UPDATE admins SET password = ? WHERE username = ?');
        $upd->execute([$hash, ADMIN_USERNAME]);
        $messages[] = "ℹ️ Akun admin '" . ADMIN_USERNAME . "' sudah ada — password diperbarui.";
    } else {
        $ins = $pdo->prepare('INSERT INTO admins (username, password, name) VALUES (?, ?, ?)');
        $ins->execute([ADMIN_USERNAME, $hash, 'Administrator']);
        $messages[] = "✅ Akun admin '" . ADMIN_USERNAME . "' dibuat.";
    }

    // ---- polling demo ----
    $demo = $pdo->prepare('SELECT id FROM polls WHERE slug = ?');
    $demo->execute(['pilkades-demo']);
    if (!$demo->fetch()) {
        $pdo->prepare("INSERT INTO polls (slug, title, description, type, max_choices, is_active, show_results, allow_comments)
            VALUES (?,?,?,?,?,?,?,?)")
            ->execute([
                'pilkades-demo',
                'Pemilihan Kepala Desa Sukamaju 2026',
                'Pilih calon kepala desa pilihan Anda. Satu warga satu suara. Suara Anda menentukan masa depan desa kita!',
                'single', 1, 1, 'always', 1,
            ]);
        $pollId = (int)$pdo->lastInsertId();
        $cands = [
            ['H. Ahmad Suryadi', 'Pengusaha & tokoh masyarakat', 1,
                json_encode([['label' => 'Visi', 'value' => 'Desa Maju, Mandiri, dan Sejahtera'], ['label' => 'Asal Dusun', 'value' => 'Dusun Krajan']])],
            ['Hj. Siti Nurhaliza', 'Mantan Sekretaris Desa', 2,
                json_encode([['label' => 'Visi', 'value' => 'Pelayanan Prima untuk Warga'], ['label' => 'Asal Dusun', 'value' => 'Dusun Sumber']])],
            ['Budi Santoso, S.E.', 'Aktivis pemuda & pegiat UMKM', 3,
                json_encode([['label' => 'Visi', 'value' => 'Ekonomi Kreatif Berbasis Digital'], ['label' => 'Asal Dusun', 'value' => 'Dusun Mekar']])],
        ];
        $cstmt = $pdo->prepare('INSERT INTO candidates (poll_id, name, description, ballot_no, sort_order, extra_fields) VALUES (?,?,?,?,?,?)');
        foreach ($cands as $i => $c) {
            $cstmt->execute([$pollId, $c[0], $c[1], $c[2], $i, $c[3]]);
        }
        $messages[] = "✅ Polling demo 'pilkades-demo' dibuat.";
    } else {
        $messages[] = "ℹ️ Polling demo sudah ada.";
    }
} catch (Throwable $ex) {
    $err = $ex->getMessage();
}
?>
<!DOCTYPE html>
<html lang="id">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Instalasi PollingKita</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="min-h-screen bg-slate-50 flex items-center justify-center p-4">
    <div class="w-full max-w-lg bg-white rounded-2xl border border-slate-200 shadow-sm p-6">
        <h1 class="text-2xl font-extrabold text-slate-900 mb-1">🗳️ Instalasi PollingKita</h1>
        <p class="text-slate-500 mb-5 text-sm">Driver database: <b><?= e(DB_DRIVER) ?></b></p>

        <?php if ($err): ?>
            <div class="rounded-xl border border-red-200 bg-red-50 p-4 text-red-700 text-sm">
                <b>Gagal:</b> <?= e($err) ?>
                <p class="mt-2 text-red-600/80">Periksa pengaturan database di <code>config.php</code>.</p>
            </div>
        <?php else: ?>
            <div class="space-y-2 mb-5">
                <?php foreach ($messages as $m): ?>
                    <div class="rounded-lg bg-emerald-50 text-emerald-800 px-3 py-2 text-sm"><?= e($m) ?></div>
                <?php endforeach; ?>
            </div>
            <div class="rounded-xl bg-amber-50 border border-amber-200 p-4 text-sm text-amber-800 mb-5">
                ⚠️ <b>Penting:</b> Hapus file <code>install.php</code> ini setelah selesai demi keamanan.
            </div>
            <div class="flex gap-2">
                <a href="index.php" class="flex-1 text-center rounded-xl bg-emerald-600 text-white font-semibold py-3 hover:bg-emerald-700">Buka Beranda</a>
                <a href="admin/login.php" class="flex-1 text-center rounded-xl border border-slate-200 font-semibold py-3 hover:bg-slate-50">Login Admin</a>
            </div>
            <p class="mt-4 text-center text-xs text-slate-400">Login: <code><?= e(ADMIN_USERNAME) ?></code> / <code><?= e(ADMIN_PASSWORD) ?></code></p>
        <?php endif; ?>
    </div>
</body>
</html>
