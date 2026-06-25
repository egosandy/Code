<?php
// Data polling + hasil (JSON) untuk refresh real-time
require_once __DIR__ . '/../lib/polls.php';

$slug = $_GET['slug'] ?? '';
$poll = get_poll_by_slug($slug);
if (!$poll) {
    json_out(['error' => 'Polling tidak ditemukan'], 404);
}

$candidates = get_candidates((int)$poll['id']);
$out = array_map(function ($c) {
    return [
        'id' => (int)$c['id'],
        'name' => $c['name'],
        'description' => $c['description'],
        'photo' => $c['photo'],
        'ballotNo' => (int)$c['ballot_no'],
        'extraFields' => $c['extra'],
        'votes' => (int)$c['votes'],
    ];
}, $candidates);

json_out([
    'poll' => [
        'id' => (int)$poll['id'],
        'slug' => $poll['slug'],
        'title' => $poll['title'],
        'type' => $poll['type'],
        'maxChoices' => (int)$poll['max_choices'],
        'showResults' => $poll['show_results'],
        'status' => poll_status($poll),
        'totalVoters' => total_voters((int)$poll['id']),
        'candidates' => $out,
    ],
]);
