<!-- partial -->
<div class="content-wrapper">
    <div class="card">
        <div class="card-body">
            <?php if ($this->session->flashdata('demo') or $this->session->flashdata('hapus')) : ?>
                <div class="alert alert-danger" role="alert">
                    <?php echo $this->session->flashdata('demo'); ?>
                    <?php echo $this->session->flashdata('hapus'); ?>
                </div>
            <?php endif; ?>
            <?php if ($this->session->flashdata('ubah') or $this->session->flashdata('tambah')) : ?>
                <div class="alert alert-primary" role="alert">
                    <?php echo $this->session->flashdata('ubah'); ?>
                    <?php echo $this->session->flashdata('tambah'); ?>
                </div>
            <?php endif; ?>
            <div>
                <button class="btn btn-info" id="tombolAdd"><i class="mdi mdi-plus-circle-outline"></i>Tambah Area</button>
            </div>
            <br>
            <div id="tempatData"></div>
            
            <div id="elementform" style="display:none;">

                <h4 class="card-title">Ubah Voucher / Point</h4>
                <br>
                <?= form_open_multipart('area/ubahcm'); ?>
                <input type="hidden" class="form-control" id="valid" name="id" style="width:60%" value="" required>
                <div class="form-group">
                    <label for="valnam">Nama</label>
                    <input type="text" class="form-control" id="valnam" name="nama" style="width:60%" value="" required>
                </div>
                <div class="form-group">
                    <label for="valpoin">Point</label>
                    <input type="text" class="form-control" id="valpoin" name="point" style="width:60%" value="" required>
                </div>
                <label for="">Tipe</label>
                <div class="form-group">
                    <select class="form-control" style="width:60%" name="tipe">
                        <option selected="false" id="tipeSaldo" value="Saldo">Saldo</option>
                        <option selected="false" id="tipeVoucher" value="Voucher">Voucher</option>
                    </select>
                </div>
                <div class="form-group">
                    <select class="form-control" style="width:60%" name="id_promo">
                        <?php foreach ($kodepromo as $kp) { ?>
                            <option id="<?= $kp['kodepromo'] ?>" value="<?= $kp['id_promo'] ?>"><?= $kp['kode_promo'] ?></option>
                        <?php } ?>
                    </select>
                </div>
                 <div class="form-group">
                    <label for="valreward">Reward (Kode / Nominal)</label>
                    <input type="text" class="form-control" id="valreward" name="reward" style="width:60%" value="" required>
                </div>
                <label for="">Status</label>
                <div class="form-group">
                    <select class="form-control" style="width:60%" name="status">
                        <option selected="false" id="status1" value="1">Active</option>
                        <option selected="false" id="status0" value="0">Non Active</option>
                    </select>
                </div>
                <div class="row">
                    <div class="col-7 ">
                        <button type="submit" class="btn btn-success mr-2">Submit</button>
                        <?= form_close(); ?>
                        <span onclick="balikan()" class="btn btn-light">Cancel</span>
                    </div>
                </div>
                <br>
            </div>

           <h4 class="card-title">Voucher & Redeem Point.</h4>
            <div class="row">
                <div class="col-12">
                    <div class="table-responsive">
                        <h1 id="jumlah" style="display: none;"><?= count($catmer) ?></h1>
                        <table id="order-listing" class="table">
                            <thead>
                                <tr>
                                    <th>No</th>
                                    <th>Nama</th>
                                    <th>Point</th>
                                    <th>Tipe</th>
                                    <th>Reward</th>
                                    <th>Status</th>
                                    <th>Aksi</th>
                                </tr>
                            </thead>
                            <tbody>
                                <?php $i = 1;
                                foreach ($catmer as $cm) { ?>
                                    <tr>
                                        <h1 id="idkat<?= $i ?>" style="display:none;"><?= $cm['id']; ?></h1>
                                        <h1 id="statm<?= $i ?>" style="display:none;"><?= $cm['status']; ?></h1>
                                        <h1 id="spoint<?= $i ?>" style="display:none;"><?= $cm['point']; ?></h1>
                                        <h1 id="stipe<?= $i ?>" style="display:none;"><?= $cm['tipe']; ?></h1>
                                        <h1 id="sreward<?= $i ?>" style="display:none;"><?= $cm['reward']; ?></h1>
                                        <td><?= $i ?></td>
                                        <td id="namkat<?= $i ?>"><?= $cm['nama']; ?></td>
                                        <td id="nampoint<?= $i ?>"><label class="badge badge-success"><?= $cm['point']; ?>Point</label></td>
                                        <td>
                                            <div>
                                                <?php if ($cm['tipe'] == "Saldo") { ?>
                                                    <label class="badge badge-success">Saldo
                                                    </label>
                                                <?php } else { ?>
                                                    <label class="btn btn-primary">Voucher
                                                    </label>
                                                <?php } ?>
                                            </div>
                                        </td>
                                        <td>
                                            <div>
                                                <?php if ($cm['tipe'] == "Saldo") { ?>
                                                    <label class="badge badge-success">Rp<?= rupiah($cm['reward']) ?>
                                                    </label>
                                                <?php } else { ?>
                                                    <label class="btn btn-primary"><?= $cm['reward']; ?>
                                                    </label>
                                                <?php } ?>
                                            </div>
                                        </td>
                                        <td>
                                            <div>
                                                <?php if ($cm['status'] == 1) { ?>
                                                    <label class="badge badge-success">Active
                                                    </label>
                                                <?php } else { ?>
                                                    <label class="badge badge-danger">Non Active
                                                    </label>
                                                <?php } ?>
                                            </div>
                                        </td>
                                        <td>
                                            <button onclick="update(<?= $i ?>);" class="btn btn-outline-success">Edit</button>
                                            <a href="<?= base_url(); ?>voucher/hapus/<?= $cm['id']; ?>" onclick="return confirm ('are you sure Delete this merchant?')">
                                                <button class="btn btn-outline-danger">Delete</button></a>
                                        </td>
                                    </tr>

                                <?php $i++;
                                } ?>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- content-wrapper ends -->


<script>
    const tombol = document.getElementById('tombolAdd');
    tombol.addEventListener("click", function() {
        const isi = document.getElementById('tempatData');
        isi.innerHTML = formAdd();
        const kembali = document.getElementById('cancel');
        kembali.addEventListener("click", function() {
            isi.innerHTML = backlah();
        })
    });

    function backlah() {
        return ``
    }

    function formAdd() {
        return `<h4 class="card-title">Add Area</h4>
                <br>
                <?= form_open_multipart('area/tambahcm'); ?>
                <div class="form-group">
                    <label for="newstitle">Kota</label>
                    <input type="text" class="form-control" id="newstitle" name="kota" style="width:60%" required>
                </div>
                <div class="form-group">
                    <label for="newstitle">Diskon</label>
                    <input type="text" class="form-control" id="newstitle" name="promo" style="width:60%" required>
                </div>
                <div class="form-group">
                    <label for="newstitle">Bintang 3 (Point Driver)</label>
                    <input type="text" class="form-control" id="newstitle" name="rate1" style="width:60%" required>
                </div>
                <div class="form-group">
                    <label for="newstitle">Bintang 4 (Point Driver)</label>
                    <input type="text" class="form-control" id="newstitle" name="rate2" style="width:60%" required>
                </div>
                <div class="form-group">
                    <label for="newstitle">Bintang 5 (Point Driver)</label>
                    <input type="text" class="form-control" id="newstitle" name="rate3" style="width:60%" required>
                </div>
                 <label for="">Status</label>
                <div class="form-group">
                    <select class="form-control" style="width:60%" name="status">
                        <option value="1">Aktif</option>
                        <option value="0">Nonaktif</option>
                    </select>
                </div>
                
                <div class="row">
                    <div class="col-7">
                        <button type="submit" class="btn btn-success mr-2">Submit</button>
                        <?= form_close(); ?>
                        <button id="cancel" class="btn btn-light">Cancel</button>
                    </div>
                </div>
                <br>`
    }
    const jumlah = document.getElementById("jumlah").innerHTML
for (let i = 0; i < jumlah; i++) {

        function update(i) {
            let idkat = document.getElementById(`idkat${i}`).innerHTML
            let namkat = document.getElementById(`namkat${i}`).innerHTML
            let spoint = document.getElementById(`spoint${i}`).innerHTML
            let stipe = document.getElementById(`stipe${i}`).innerHTML
            let sreward = document.getElementById(`sreward${i}`).innerHTML
            let statm = document.getElementById(`statm${i}`).innerHTML


            let editdoc = document.getElementById('elementform');
            editdoc.style = "display:block;";
            document.getElementById('valnam').value = namkat;
            document.getElementById('valpoin').value = spoint;
            document.getElementById(`tipe${stipe}`).selected = true;
            document.getElementById('valreward').value = sreward;
            //document.getElementById(`${fitur}`).selected = true;
            document.getElementById(`status${statm}`).selected = true;
            document.getElementById(`valid`).value = idkat;



        }


    }

    function balikan() {
        document.getElementById('elementform').style = "display:none;";
    }
</script>