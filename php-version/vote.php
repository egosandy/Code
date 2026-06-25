<?php
require_once __DIR__ . '/lib/layout.php';
require_once __DIR__ . '/lib/polls.php';

$bp = base_path();
$slug = $_GET['slug'] ?? '';
$poll = get_poll_by_slug($slug);

if (!$poll) {
    render_head('Polling tidak ditemukan');
    render_navbar(false);
    echo '<main class="mx-auto max-w-2xl px-4 py-20 text-center"><div class="text-5xl mb-4">🔍</div><p class="text-xl font-bold">Polling tidak ditemukan</p><p class="text-slate-500 mt-2">Link mungkin salah atau polling telah dihapus.</p><a href="' . $bp . '/index.php" class="btn btn-primary mt-6 inline-flex">Ke Beranda</a></main>';
    render_foot();
    exit;
}

$candidates = get_candidates((int)$poll['id']);
$status = poll_status($poll);
$shareUrl = base_url() . '/vote.php?slug=' . urlencode($poll['slug']);

$initial = [
    'id' => (int)$poll['id'],
    'slug' => $poll['slug'],
    'title' => $poll['title'],
    'type' => $poll['type'],
    'maxChoices' => (int)$poll['max_choices'],
    'showResults' => $poll['show_results'],
    'allowComments' => (bool)$poll['allow_comments'],
    'status' => $status,
    'totalVoters' => total_voters((int)$poll['id']),
    'candidates' => array_map(fn($c) => [
        'id' => (int)$c['id'],
        'name' => $c['name'],
        'description' => $c['description'],
        'photo' => $c['photo'],
        'ballotNo' => (int)$c['ballot_no'],
        'extraFields' => $c['extra'],
        'votes' => (int)$c['votes'],
    ], $candidates),
];

render_head($poll['title'] . ' — PollingKita', $poll['description'] ?? '');
render_navbar(false);
?>
<main class="mx-auto max-w-2xl px-4 py-8">
    <!-- Header polling -->
    <div class="card overflow-hidden mb-6">
        <div class="relative h-40 sm:h-52" style="background:linear-gradient(135deg,#10b981,#047857)">
            <?php if ($poll['banner_image']): ?><img src="<?= e($poll['banner_image']) ?>" class="w-full h-full object-cover"><?php endif; ?>
        </div>
        <div class="p-5">
            <h1 class="text-2xl sm:text-3xl font-extrabold"><?= e($poll['title']) ?></h1>
            <?php if ($poll['description']): ?><p class="mt-2 text-slate-600"><?= e($poll['description']) ?></p><?php endif; ?>
            <div class="mt-4 border-t border-slate-100 pt-4">
                <p class="text-sm font-semibold text-slate-700 mb-2">Bagikan polling ini:</p>
                <div class="flex flex-wrap gap-2">
                    <button onclick="copyLink()" class="btn btn-secondary">📋 Salin Link</button>
                    <a href="https://wa.me/?text=<?= urlencode('Ayo ikut memilih: ' . $poll['title'] . "\n" . $shareUrl) ?>" target="_blank" class="btn btn-secondary">💬 WhatsApp</a>
                    <button onclick="nativeShare()" class="btn btn-primary">↗️ Bagikan</button>
                </div>
            </div>
        </div>
    </div>

    <!-- Status / info -->
    <div id="status-box" class="mb-6"></div>

    <!-- Statistik -->
    <div class="grid grid-cols-3 gap-3 mb-6">
        <div class="card p-3 flex items-center gap-3"><span class="text-lg">👥</span><div class="min-w-0"><div id="stat-voters" class="text-lg font-extrabold leading-tight">0</div><div class="text-xs text-slate-500">Pemilih</div></div></div>
        <div class="card p-3 flex items-center gap-3"><span class="text-lg">🗳️</span><div class="min-w-0"><div id="stat-votes" class="text-lg font-extrabold leading-tight">0</div><div class="text-xs text-slate-500">Total Suara</div></div></div>
        <div class="card p-3 flex items-center gap-3"><span class="text-lg">🏆</span><div class="min-w-0"><div id="stat-leader" class="text-lg font-extrabold leading-tight">—</div><div class="text-xs text-slate-500">Unggul</div></div></div>
    </div>

    <!-- Kandidat -->
    <div id="candidates" class="space-y-3"></div>

    <!-- Error -->
    <div id="vote-error" class="hidden mt-4 rounded-xl border border-red-200 bg-red-50 px-4 py-3 text-sm text-red-700"></div>

    <!-- Submit -->
    <div id="submit-wrap" class="sticky bottom-4 z-10 mt-6 hidden">
        <button id="submit-btn" onclick="submitVote()" class="btn btn-primary w-full py-3.5 text-base" style="box-shadow:0 10px 25px rgba(16,185,129,.3)">🗳️ Kirim Suara</button>
        <p id="multi-hint" class="hidden mt-2 text-center text-xs text-slate-500"></p>
    </div>

    <!-- Komentar -->
    <?php if ($poll['allow_comments']): ?>
    <div class="card p-5 mt-6">
        <h3 class="font-bold mb-4">💬 Komentar (<span id="comment-count">0</span>)</h3>
        <form onsubmit="addComment(event)" class="mb-5 space-y-2">
            <input id="c-name" class="input" placeholder="Nama Anda" maxlength="50">
            <div class="flex gap-2">
                <input id="c-msg" class="input flex-1" placeholder="Tulis komentar..." maxlength="500">
                <button class="btn btn-primary px-4">➤</button>
            </div>
        </form>
        <div id="comments" class="space-y-3"></div>
    </div>
    <?php endif; ?>
</main>

<script src="https://openfpcdn.io/fingerprintjs/v4/iife.min.js"></script>
<script>
const BP = <?= json_encode($bp) ?>;
const SHARE_URL = <?= json_encode($shareUrl) ?>;
let poll = <?= json_encode($initial, JSON_UNESCAPED_UNICODE) ?>;
let fingerprint = '';
let selected = [];
let voted = false;
let votedFor = [];
let loading = true;

function fmt(n){ return new Intl.NumberFormat('id-ID').format(n); }
function pct(v,t){ return t===0?0:Math.round((v/t)*1000)/10; }
function esc(s){ return (s||'').replace(/&/g,'&amp;').replace(/</g,'&lt;').replace(/>/g,'&gt;'); }

function totals(){ return poll.candidates.reduce((s,c)=>s+c.votes,0); }
function leader(){ const t=totals(); if(t===0) return null; return poll.candidates.reduce((a,b)=>b.votes>a.votes?b:a); }
function canShowResults(){
    return poll.showResults==='always'
        || (poll.showResults==='afterVote' && voted)
        || (poll.showResults==='afterClose' && poll.status!=='active');
}
const isOpen = () => poll.status==='active';

function renderStatus(){
    const box = document.getElementById('status-box');
    if (voted) {
        box.innerHTML = `<div class="flex items-center gap-3 rounded-2xl border border-brand-200 bg-brand-50 p-4 text-brand-800"><span class="text-xl">✅</span><div><p class="font-semibold">Terima kasih! Suara Anda sudah tercatat.</p><p class="text-sm" style="color:#047857aa">Satu perangkat hanya dapat memberikan satu suara untuk menjaga keadilan polling.</p></div></div>`;
    } else if (isOpen()) {
        box.innerHTML = `<div class="flex items-center gap-3 rounded-2xl border border-slate-200 bg-white p-4 text-slate-600"><span class="text-xl">🛡️</span><p class="text-sm">Polling ini dilindungi <strong>deteksi perangkat</strong>. Setiap perangkat hanya bisa memilih sekali.</p></div>`;
    } else {
        const labels = { scheduled:'Polling belum dibuka', ended:'Polling telah berakhir', closed:'Polling ditutup' };
        box.innerHTML = `<div class="flex items-center gap-3 rounded-2xl border border-slate-200 bg-slate-100 p-4 text-slate-600"><span class="text-xl">ℹ️</span><p class="font-medium">${labels[poll.status]||''}</p></div>`;
    }
}

function renderStats(){
    const t = totals(), l = leader();
    document.getElementById('stat-voters').textContent = fmt(poll.totalVoters);
    document.getElementById('stat-votes').textContent = fmt(t);
    document.getElementById('stat-leader').textContent = (l && canShowResults()) ? ('No. '+l.ballotNo) : '—';
}

function renderCandidates(){
    const wrap = document.getElementById('candidates');
    const t = totals(), l = leader(), show = canShowResults();
    const clickable = isOpen() && !voted && !loading;
    wrap.innerHTML = poll.candidates.map(c => {
        const isSel = selected.includes(c.id);
        const wasVoted = votedFor.includes(c.id);
        const p = pct(c.votes, t);
        const isLead = l && l.id===c.id && t>0;
        const extras = (c.extraFields||[]).slice(0,2).map(f=>`<span class="text-xs text-slate-500"><span class="font-semibold text-slate-600">${esc(f.label)}:</span> ${esc(f.value)}</span>`).join(' ');
        return `
        <div onclick="${clickable?`toggle(${c.id})`:''}" class="relative overflow-hidden rounded-2xl border bg-white p-4 transition ${clickable?'cursor-pointer hover:shadow-md':''}" style="${(isSel||wasVoted)?'border-color:#10b981;box-shadow:0 0 0 2px rgba(16,185,129,.3)':'border-color:#e2e8f0'}">
            ${show?`<div class="absolute inset-y-0 left-0" style="width:${p}%;background:linear-gradient(to right,#ecfdf5,rgba(209,250,229,.4));transition:width .7s"></div>`:''}
            <div class="relative flex items-center gap-4">
                <div class="flex h-12 w-12 shrink-0 items-center justify-center rounded-xl text-lg font-extrabold" style="${isLead?'background:linear-gradient(135deg,#fbbf24,#f59e0b);color:#fff':'background:#f1f5f9;color:#334155'}">${c.ballotNo}</div>
                ${c.photo?`<img src="${esc(c.photo)}" class="h-16 w-16 shrink-0 rounded-xl border border-slate-200 object-cover">`:`<div class="flex h-16 w-16 shrink-0 items-center justify-center rounded-xl bg-slate-100 text-2xl font-bold text-slate-400">${esc(c.name.charAt(0))}</div>`}
                <div class="min-w-0 flex-1">
                    <div class="flex items-center gap-2"><h3 class="truncate font-bold">${esc(c.name)}</h3>${isLead&&show?'<span class="badge" style="background:#fef3c7;color:#b45309">🏆 Unggul</span>':''}</div>
                    ${c.description?`<p class="truncate text-sm text-slate-500">${esc(c.description)}</p>`:''}
                    ${extras?`<div class="mt-1 flex flex-wrap gap-x-3">${extras}</div>`:''}
                </div>
                <div class="shrink-0 text-right">
                    ${show
                        ? `<div class="text-xl font-extrabold text-brand-700">${p}%</div><div class="text-xs text-slate-500">${fmt(c.votes)} suara</div>`
                        : `<div class="flex h-7 w-7 items-center justify-center border-2 ${poll.type==='single'?'rounded-full':'rounded-md'}" style="${isSel?'border-color:#10b981;background:#10b981;color:#fff':'border-color:#cbd5e1'}">${isSel?'✓':''}</div>`}
                </div>
            </div>
        </div>`;
    }).join('');

    // submit button
    const sw = document.getElementById('submit-wrap');
    if (isOpen() && !voted) {
        sw.classList.remove('hidden');
        const btn = document.getElementById('submit-btn');
        btn.disabled = loading || selected.length===0;
        btn.textContent = loading ? '⏳ Memeriksa perangkat...' : ('🗳️ Kirim Suara' + (poll.type==='multiple'&&selected.length?` (${selected.length})`:''));
        const hint = document.getElementById('multi-hint');
        if (poll.type==='multiple'){ hint.classList.remove('hidden'); hint.textContent = `Anda dapat memilih hingga ${poll.maxChoices} kandidat`; }
    } else {
        sw.classList.add('hidden');
    }
}

function renderAll(){ renderStatus(); renderStats(); renderCandidates(); }

function toggle(id){
    hideErr();
    if (poll.type==='single'){ selected=[id]; }
    else {
        if (selected.includes(id)) selected=selected.filter(x=>x!==id);
        else { if (selected.length>=poll.maxChoices){ showErr(`Maksimal ${poll.maxChoices} pilihan`); return; } selected.push(id); }
    }
    renderCandidates();
}

function showErr(m){ const b=document.getElementById('vote-error'); b.textContent=m; b.classList.remove('hidden'); }
function hideErr(){ document.getElementById('vote-error').classList.add('hidden'); }

async function refreshResults(){
    try{
        const r = await fetch(BP + '/api/poll.php?slug=' + encodeURIComponent(poll.slug));
        const j = await r.json();
        if (j.poll) { poll = j.poll; }
    }catch(e){}
}

async function submitVote(){
    if (selected.length===0){ showErr('Silakan pilih terlebih dahulu'); return; }
    const btn = document.getElementById('submit-btn');
    btn.disabled = true; btn.textContent = '⏳ Mengirim suara...';
    try{
        const r = await fetch(BP + '/api/vote.php', {
            method:'POST', headers:{'Content-Type':'application/json'},
            body: JSON.stringify({ slug: poll.slug, candidateIds: selected, fingerprint })
        });
        const j = await r.json();
        if (!r.ok){
            showErr(j.error || 'Gagal memilih');
            if (j.alreadyVoted){ voted=true; await refreshResults(); renderAll(); }
            else { btn.disabled=false; renderCandidates(); }
            return;
        }
        voted = true; votedFor = j.selectedCandidateIds || selected;
        await refreshResults();
        renderAll();
    }catch(e){ showErr('Terjadi kesalahan jaringan'); btn.disabled=false; }
}

// share
function copyLink(){ navigator.clipboard.writeText(SHARE_URL).then(()=>alert('Link tersalin!')).catch(()=>prompt('Salin link:',SHARE_URL)); }
function nativeShare(){ if(navigator.share){ navigator.share({title:poll.title,url:SHARE_URL,text:'Ayo ikut memilih: '+poll.title}).catch(()=>{}); } else copyLink(); }

// comments
async function loadComments(){
    try{
        const r = await fetch(BP + '/api/comment.php?slug=' + encodeURIComponent(poll.slug));
        const j = await r.json();
        const list = j.comments||[];
        document.getElementById('comment-count').textContent = list.length;
        const box = document.getElementById('comments');
        if(!box) return;
        box.innerHTML = list.length? list.map(c=>`<div class="rounded-xl bg-slate-50 p-3"><div class="flex items-center justify-between"><span class="font-semibold text-slate-800">${esc(c.name)}</span><span class="text-xs text-slate-400">${esc(c.created_at)}</span></div><p class="mt-1 text-sm text-slate-600">${esc(c.message)}</p></div>`).join('')
            : '<p class="py-4 text-center text-sm text-slate-400">Belum ada komentar. Jadilah yang pertama!</p>';
    }catch(e){}
}
async function addComment(ev){
    ev.preventDefault();
    const name=document.getElementById('c-name').value.trim();
    const msg=document.getElementById('c-msg').value.trim();
    if(!name||!msg) return;
    const r = await fetch(BP + '/api/comment.php', {method:'POST',headers:{'Content-Type':'application/json'},body:JSON.stringify({slug:poll.slug,name,message:msg})});
    if(r.ok){ document.getElementById('c-msg').value=''; loadComments(); }
}

// init
(async function(){
    renderAll();
    try{
        const fp = await FingerprintJS.load();
        const res = await fp.get();
        fingerprint = res.visitorId;
    }catch(e){ fingerprint=''; }
    try{
        const r = await fetch(BP + '/api/check.php', {method:'POST',headers:{'Content-Type':'application/json'},body:JSON.stringify({slug:poll.slug,fingerprint})});
        const j = await r.json();
        if(j.voted){ voted=true; votedFor=j.selectedCandidateIds||[]; await refreshResults(); }
    }catch(e){}
    loading=false;
    renderAll();
    <?php if ($poll['allow_comments']): ?>loadComments();<?php endif; ?>
})();
</script>
<?php render_foot();
