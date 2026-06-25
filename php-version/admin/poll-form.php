<?php
require_once __DIR__ . '/../lib/layout.php';
require_once __DIR__ . '/../lib/polls.php';
require_admin();

$bp = base_path();
$id = (int)($_GET['id'] ?? 0);
$isEdit = false;
$initial = [
    'id' => 0,
    'title' => '', 'description' => '', 'bannerImage' => null,
    'type' => 'single', 'maxChoices' => 1, 'isActive' => true, 'isPrivate' => false,
    'showResults' => 'always', 'allowComments' => true, 'startDate' => '', 'endDate' => '',
    'candidates' => [
        ['name' => '', 'description' => '', 'photo' => null, 'ballotNo' => 1, 'extraFields' => []],
        ['name' => '', 'description' => '', 'photo' => null, 'ballotNo' => 2, 'extraFields' => []],
    ],
];

if ($id > 0) {
    $poll = get_poll_by_id($id);
    if ($poll) {
        $isEdit = true;
        $cands = get_candidates($id);
        $initial = [
            'id' => (int)$poll['id'],
            'title' => $poll['title'],
            'description' => $poll['description'] ?? '',
            'bannerImage' => $poll['banner_image'],
            'type' => $poll['type'],
            'maxChoices' => (int)$poll['max_choices'],
            'isActive' => (bool)$poll['is_active'],
            'isPrivate' => (bool)$poll['is_private'],
            'showResults' => $poll['show_results'],
            'allowComments' => (bool)$poll['allow_comments'],
            'startDate' => $poll['start_date'] ? str_replace(' ', 'T', substr($poll['start_date'], 0, 16)) : '',
            'endDate' => $poll['end_date'] ? str_replace(' ', 'T', substr($poll['end_date'], 0, 16)) : '',
            'candidates' => array_map(fn($c) => [
                'id' => (int)$c['id'],
                'name' => $c['name'],
                'description' => $c['description'] ?? '',
                'photo' => $c['photo'],
                'ballotNo' => (int)$c['ballot_no'],
                'extraFields' => $c['extra'],
            ], $cands),
        ];
    }
}

render_head(($isEdit ? 'Edit' : 'Buat') . ' Polling — PollingKita');
render_navbar(true);
?>
<main class="mx-auto max-w-3xl px-4 py-8">
    <a href="<?= $bp ?>/admin/index.php" class="inline-flex items-center gap-1 text-sm font-medium text-slate-500 hover:text-slate-700 mb-4">← Kembali ke Dashboard</a>
    <h1 class="text-2xl font-extrabold mb-6"><?= $isEdit ? 'Edit Polling' : 'Buat Polling Baru' ?></h1>

    <div class="space-y-6">
        <!-- Detail -->
        <section class="card p-5">
            <h2 class="font-bold mb-4">⚙️ Detail Polling</h2>
            <div class="space-y-4">
                <div>
                    <label class="label">Judul / Pertanyaan *</label>
                    <input id="f-title" class="input" maxlength="160" placeholder="Contoh: Pemilihan Kepala Desa Sukamaju 2026">
                </div>
                <div>
                    <label class="label">Deskripsi</label>
                    <textarea id="f-description" class="input" rows="3" placeholder="Jelaskan tujuan polling, aturan, atau informasi tambahan..."></textarea>
                </div>
                <div>
                    <label class="label">Banner / Gambar Header (opsional)</label>
                    <div id="banner-box"></div>
                </div>
            </div>
        </section>

        <!-- Kandidat -->
        <section class="card p-5">
            <div class="flex items-center justify-between mb-4">
                <h2 class="font-bold">📋 Kandidat / Pilihan</h2>
                <span id="cand-count" class="text-sm text-slate-400"></span>
            </div>
            <div id="candidates" class="space-y-4"></div>
            <button type="button" onclick="addCandidate()" class="btn btn-secondary w-full mt-4">+ Tambah Kandidat</button>
        </section>

        <!-- Pengaturan -->
        <section class="card p-5">
            <h2 class="font-bold mb-4">⚙️ Pengaturan</h2>
            <div class="space-y-4">
                <div class="grid sm:grid-cols-2 gap-4">
                    <div>
                        <label class="label">Tipe Pemilihan</label>
                        <select id="f-type" class="input" onchange="toggleMax()">
                            <option value="single">Pilih 1 kandidat</option>
                            <option value="multiple">Bisa pilih lebih dari 1</option>
                        </select>
                    </div>
                    <div id="max-wrap" class="hidden">
                        <label class="label">Maksimal Pilihan</label>
                        <input id="f-maxChoices" type="number" min="1" value="1" class="input">
                    </div>
                    <div>
                        <label class="label">Tampilkan Hasil</label>
                        <select id="f-showResults" class="input">
                            <option value="always">Selalu tampilkan</option>
                            <option value="afterVote">Setelah memilih</option>
                            <option value="afterClose">Setelah ditutup</option>
                        </select>
                    </div>
                </div>
                <div class="grid sm:grid-cols-2 gap-4">
                    <div>
                        <label class="label">Tanggal Mulai (opsional)</label>
                        <input id="f-startDate" type="datetime-local" class="input">
                    </div>
                    <div>
                        <label class="label">Tanggal Selesai (opsional)</label>
                        <input id="f-endDate" type="datetime-local" class="input">
                    </div>
                </div>
                <div class="grid sm:grid-cols-3 gap-3">
                    <?php foreach ([['isActive', 'Aktif', 'Polling dapat menerima suara'], ['isPrivate', 'Privat', 'Sembunyikan dari beranda'], ['allowComments', 'Izinkan Komentar', 'Pengunjung bisa berkomentar']] as $t): ?>
                        <label class="flex items-start gap-2 rounded-xl border border-slate-200 p-3 cursor-pointer">
                            <input type="checkbox" id="f-<?= $t[0] ?>" class="mt-1 accent-emerald-600 w-4 h-4">
                            <span>
                                <span class="block text-sm font-semibold"><?= $t[1] ?></span>
                                <span class="block text-xs text-slate-500"><?= $t[2] ?></span>
                            </span>
                        </label>
                    <?php endforeach; ?>
                </div>
            </div>
        </section>

        <div id="form-error" class="hidden rounded-xl border border-red-200 bg-red-50 px-4 py-3 text-sm text-red-700"></div>

        <div class="flex gap-3">
            <a href="<?= $bp ?>/admin/index.php" class="btn btn-secondary flex-1 py-3 text-center">Batal</a>
            <button type="button" id="save-btn" onclick="savePoll()" class="btn btn-primary py-3" style="flex:2">💾 <?= $isEdit ? 'Simpan Perubahan' : 'Buat Polling' ?></button>
        </div>
    </div>
</main>

<script>
const BP = <?= json_encode($bp) ?>;
const IS_EDIT = <?= $isEdit ? 'true' : 'false' ?>;
let data = <?= json_encode($initial, JSON_UNESCAPED_UNICODE) ?>;

// ---- init form fields ----
function initForm() {
    document.getElementById('f-title').value = data.title;
    document.getElementById('f-description').value = data.description;
    document.getElementById('f-type').value = data.type;
    document.getElementById('f-maxChoices').value = data.maxChoices;
    document.getElementById('f-showResults').value = data.showResults;
    document.getElementById('f-startDate').value = data.startDate;
    document.getElementById('f-endDate').value = data.endDate;
    document.getElementById('f-isActive').checked = data.isActive;
    document.getElementById('f-isPrivate').checked = data.isPrivate;
    document.getElementById('f-allowComments').checked = data.allowComments;
    renderBanner();
    renderCandidates();
    toggleMax();
}

function toggleMax() {
    const t = document.getElementById('f-type').value;
    document.getElementById('max-wrap').classList.toggle('hidden', t !== 'multiple');
}

// ---- image upload ----
async function uploadImage(file) {
    const fd = new FormData();
    fd.append('file', file);
    const res = await fetch(BP + '/api/upload.php', { method: 'POST', body: fd });
    const j = await res.json();
    if (!res.ok) throw new Error(j.error || 'Gagal mengunggah');
    return j.url;
}

function renderBanner() {
    const box = document.getElementById('banner-box');
    box.innerHTML = `
        <div class="flex items-center gap-3">
            <div class="relative w-full max-w-xs aspect-[3/1] rounded-xl border-2 border-dashed border-slate-300 bg-slate-50 overflow-hidden flex items-center justify-center">
                ${data.bannerImage ? `<img src="${data.bannerImage}" class="w-full h-full object-cover"><button onclick="data.bannerImage=null;renderBanner()" class="absolute top-1 right-1 bg-red-500 text-white rounded-full w-6 h-6">×</button>` : '<span class="text-slate-400 text-2xl">🖼️</span>'}
            </div>
            <div>
                <button type="button" class="btn btn-secondary" onclick="document.getElementById('banner-input').click()">⬆️ ${data.bannerImage ? 'Ganti' : 'Unggah'}</button>
                <input id="banner-input" type="file" accept="image/*" class="hidden" onchange="onBanner(this)">
            </div>
        </div>`;
}
async function onBanner(input) {
    if (!input.files[0]) return;
    try { data.bannerImage = await uploadImage(input.files[0]); renderBanner(); }
    catch (e) { alert(e.message); }
}

// ---- candidates ----
function renderCandidates() {
    const wrap = document.getElementById('candidates');
    document.getElementById('cand-count').textContent = data.candidates.length + ' kandidat';
    wrap.innerHTML = data.candidates.map((c, i) => `
        <div class="rounded-2xl border border-slate-200 bg-slate-50/50 p-4">
            <div class="flex items-center justify-between mb-3">
                <span class="badge" style="background:#d1fae5;color:#047857">Kandidat ${i + 1}</span>
                ${data.candidates.length > 2 ? `<button onclick="removeCandidate(${i})" class="text-red-500">🗑️</button>` : ''}
            </div>
            <div class="flex flex-col sm:flex-row gap-4">
                <div class="shrink-0">
                    <div class="relative w-28 h-28 rounded-xl border-2 border-dashed border-slate-300 bg-white overflow-hidden flex items-center justify-center">
                        ${c.photo ? `<img src="${c.photo}" class="w-full h-full object-cover"><button onclick="setPhoto(${i},null)" class="absolute top-1 right-1 bg-red-500 text-white rounded-full w-6 h-6">×</button>` : '<span class="text-slate-400 text-xl">🖼️</span>'}
                    </div>
                    <button type="button" class="btn btn-secondary mt-2 text-xs w-28" onclick="document.getElementById('photo-${i}').click()">⬆️ Foto</button>
                    <input id="photo-${i}" type="file" accept="image/*" class="hidden" onchange="onPhoto(${i},this)">
                </div>
                <div class="flex-1 space-y-3">
                    <div class="flex gap-3">
                        <div class="w-20">
                            <label class="label">No.</label>
                            <input type="number" min="1" value="${c.ballotNo}" class="input px-2 text-center" oninput="data.candidates[${i}].ballotNo=parseInt(this.value)||${i + 1}">
                        </div>
                        <div class="flex-1">
                            <label class="label">Nama *</label>
                            <input value="${escAttr(c.name)}" class="input" placeholder="Nama kandidat" oninput="data.candidates[${i}].name=this.value">
                        </div>
                    </div>
                    <div>
                        <label class="label">Keterangan singkat</label>
                        <input value="${escAttr(c.description)}" class="input" placeholder="Contoh: Pengusaha & tokoh masyarakat" oninput="data.candidates[${i}].description=this.value">
                    </div>
                    <div id="extra-${i}">${renderExtra(i)}</div>
                    <button type="button" onclick="addExtra(${i})" class="text-sm font-medium text-brand-600 hover:text-brand-700">+ Tambah info kustom (visi, asal, dll)</button>
                </div>
            </div>
        </div>`).join('');
}

function renderExtra(ci) {
    const fields = data.candidates[ci].extraFields || [];
    if (!fields.length) return '';
    return `<div class="space-y-2 rounded-xl bg-white p-3">
        <p class="text-xs font-semibold text-slate-500">Informasi Tambahan</p>
        ${fields.map((f, fi) => `
            <div class="flex gap-2">
                <input value="${escAttr(f.label)}" placeholder="Label (mis. Visi)" class="input" style="width:33%" oninput="data.candidates[${ci}].extraFields[${fi}].label=this.value">
                <input value="${escAttr(f.value)}" placeholder="Isi" class="input flex-1" oninput="data.candidates[${ci}].extraFields[${fi}].value=this.value">
                <button onclick="removeExtra(${ci},${fi})" class="btn btn-secondary text-red-500 px-2">×</button>
            </div>`).join('')}
    </div>`;
}

function escAttr(s) { return (s || '').replace(/"/g, '&quot;').replace(/</g, '&lt;'); }
function addCandidate() { data.candidates.push({ name: '', description: '', photo: null, ballotNo: data.candidates.length + 1, extraFields: [] }); renderCandidates(); }
function removeCandidate(i) { data.candidates.splice(i, 1); renderCandidates(); }
function setPhoto(i, v) { data.candidates[i].photo = v; renderCandidates(); }
async function onPhoto(i, input) { if (!input.files[0]) return; try { data.candidates[i].photo = await uploadImage(input.files[0]); renderCandidates(); } catch (e) { alert(e.message); } }
function addExtra(i) { data.candidates[i].extraFields.push({ label: '', value: '' }); document.getElementById('extra-' + i).innerHTML = renderExtra(i); }
function removeExtra(ci, fi) { data.candidates[ci].extraFields.splice(fi, 1); document.getElementById('extra-' + ci).innerHTML = renderExtra(ci); }

// ---- save ----
function collect() {
    return {
        id: data.id || 0,
        title: document.getElementById('f-title').value,
        description: document.getElementById('f-description').value,
        bannerImage: data.bannerImage,
        type: document.getElementById('f-type').value,
        maxChoices: parseInt(document.getElementById('f-maxChoices').value) || 1,
        showResults: document.getElementById('f-showResults').value,
        startDate: document.getElementById('f-startDate').value,
        endDate: document.getElementById('f-endDate').value,
        isActive: document.getElementById('f-isActive').checked,
        isPrivate: document.getElementById('f-isPrivate').checked,
        allowComments: document.getElementById('f-allowComments').checked,
        candidates: data.candidates,
    };
}

async function savePoll() {
    const errBox = document.getElementById('form-error');
    errBox.classList.add('hidden');
    const payload = collect();
    if ((payload.title || '').trim().length < 3) { showErr('Judul polling minimal 3 karakter'); return; }
    if (payload.candidates.filter(c => (c.name || '').trim()).length < 2) { showErr('Minimal 2 kandidat dengan nama terisi'); return; }

    const btn = document.getElementById('save-btn');
    btn.disabled = true; btn.textContent = 'Menyimpan...';
    try {
        const res = await fetch(BP + '/api/poll-save.php', {
            method: 'POST', headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(payload)
        });
        const j = await res.json();
        if (!res.ok) { showErr(j.error || 'Gagal menyimpan'); btn.disabled = false; btn.textContent = '💾 Simpan'; return; }
        window.location.href = BP + '/admin/poll-manage.php?id=' + j.id;
    } catch (e) {
        showErr('Terjadi kesalahan jaringan'); btn.disabled = false; btn.textContent = '💾 Simpan';
    }
}
function showErr(m) { const b = document.getElementById('form-error'); b.textContent = m; b.classList.remove('hidden'); window.scrollTo(0, document.body.scrollHeight); }

initForm();
</script>
<?php render_foot();
