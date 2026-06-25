<?php
require_once __DIR__ . '/helpers.php';

function get_poll_by_slug(string $slug): ?array
{
    $st = db()->prepare('SELECT * FROM polls WHERE slug = ?');
    $st->execute([$slug]);
    $p = $st->fetch();
    return $p ?: null;
}

function get_poll_by_id(int $id): ?array
{
    $st = db()->prepare('SELECT * FROM polls WHERE id = ?');
    $st->execute([$id]);
    $p = $st->fetch();
    return $p ?: null;
}

/** Kandidat beserta jumlah suara, terurut. */
function get_candidates(int $pollId): array
{
    $st = db()->prepare(
        'SELECT c.*, (SELECT COUNT(*) FROM votes v WHERE v.candidate_id = c.id) AS votes
         FROM candidates c WHERE c.poll_id = ? ORDER BY c.sort_order ASC, c.id ASC'
    );
    $st->execute([$pollId]);
    $rows = $st->fetchAll();
    foreach ($rows as &$r) {
        $r['votes'] = (int)$r['votes'];
        $r['ballot_no'] = (int)$r['ballot_no'];
        $r['extra'] = $r['extra_fields'] ? (json_decode($r['extra_fields'], true) ?: []) : [];
    }
    return $rows;
}

function total_voters(int $pollId): int
{
    $st = db()->prepare('SELECT COUNT(*) FROM ballots WHERE poll_id = ?');
    $st->execute([$pollId]);
    return (int)$st->fetchColumn();
}

function count_comments(int $pollId): int
{
    $st = db()->prepare('SELECT COUNT(*) FROM comments WHERE poll_id = ?');
    $st->execute([$pollId]);
    return (int)$st->fetchColumn();
}

/** Daftar polling untuk dashboard/beranda dengan ringkasan. */
function list_polls(bool $publicOnly = false): array
{
    $where = $publicOnly ? 'WHERE is_private = 0' : '';
    $rows = db()->query("SELECT * FROM polls $where ORDER BY created_at DESC, id DESC")->fetchAll();
    foreach ($rows as &$p) {
        $p['candidate_count'] = (int)db()->query('SELECT COUNT(*) FROM candidates WHERE poll_id = ' . (int)$p['id'])->fetchColumn();
        $p['voter_count'] = total_voters((int)$p['id']);
        $p['comment_count'] = count_comments((int)$p['id']);
    }
    return $rows;
}

/** Cek apakah perangkat sudah memilih; kembalikan daftar candidate_id terpilih. */
function device_ballot(int $pollId, string $deviceHash): ?array
{
    $st = db()->prepare('SELECT id FROM ballots WHERE poll_id = ? AND device_hash = ?');
    $st->execute([$pollId, $deviceHash]);
    $b = $st->fetch();
    if (!$b) return null;
    $vs = db()->prepare('SELECT candidate_id FROM votes WHERE ballot_id = ?');
    $vs->execute([$b['id']]);
    return array_map(fn($r) => (int)$r['candidate_id'], $vs->fetchAll());
}
