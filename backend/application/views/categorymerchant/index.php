<!-- partial -->
            <div class="container-fluid mt-xl-50 mt-sm-30 mt-15 px-xxl-65 px-xl-20">
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
                <button class="btn btn-info" id="tombolAdd"><i class="mdi mdi-plus-circle-outline"></i>Tambah Kategori</button>
            </div>
            <br>
            <div id="tempatData"></div>

            <div id="elementform" style="display:none;">

                <h4 class="card-title">Ubah Kategori</h4>
                <br>
                <?= form_open_multipart('categorymerchant/ubahcm'); ?>
                <input type="hidden" class="form-control" id="valid" name="id_kategori_merchant" style="width:60%" value="" required>
                <input type="file" accept="image/*" name="foto_kategori" onchange="loadFile(event)">
                <img id="output" width="250" height="250"/>
                <div class="form-group">
                    <label for="valnam">Nama Kategori</label>
                    <input type="text" class="form-control" id="valnam" name="nama_kategori" style="width:60%" value="" required>
                </div>
                <label for="">Layanan</label>
                <div class="form-group">
                    <select class="form-control custom-select  mt-15" style="width:60%" name="id_fitur">
                        <?php foreach ($fitur as $ft) { ?>
                            <option id="<?= $ft['fitur'] ?>" value="<?= $ft['id_fitur'] ?>"><?= $ft['fitur'] ?></option>
                        <?php } ?>
                    </select>
                </div>
                <label for="">Status</label>
                <div class="form-group">
                    <select class="form-control custom-select  mt-15" style="width:60%" name="status_kategori">
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

           
            <div class="row">
                <div class="col-12">
                    <div class="table-responsive">
                        <h1 id="jumlah" style="display: none;"><?= count($catmer) ?></h1>
                        <table id="tabwallet" class="table table-striped table-hover dt-responsive display nowrap" data-info="false" cellspacing="0" width="100%">
                            <thead>
                                <tr>
                                    <th>No</th>
                                    <th>Nama Kategori</th>
                                    <th>Fitur</th>
                                    <th>Status</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <?php $i = 1;
                                foreach ($catmer as $cm) { ?>
                                    <tr>
                                        <h1 id="idkat<?= $i ?>" style="display:none;"><?= $cm['id_kategori_merchant']; ?></h1>
                                        <h1 id="statm<?= $i ?>" style="display:none;"><?= $cm['status_kategori']; ?></h1>
                                        <td><?= $i ?></td>
                                        <td id="namkat<?= $i ?>"><?= $cm['nama_kategori']; ?></td>
                                        <td id="fitur<?= $i ?>"><?= $cm['fitur']; ?></td>
                                        <td>
                                            <div>
                                                <?php if ($cm['status_kategori'] == 1) { ?>
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
                                            <a href="<?= base_url(); ?>categorymerchant/hapus/<?= $cm['id_kategori_merchant']; ?>" onclick="return confirm ('are you sure Delete this merchant?')">
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
  var loadFile = function(event) {
    var reader = new FileReader();
    reader.onload = function(){
      var output = document.getElementById('output');
      output.src = reader.result;
    };
    reader.readAsDataURL(event.target.files[0]);
  };
</script>
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
        return `<h4 class="card-title">Add Category Merchant</h4>
                <br>
                <?= form_open_multipart('categorymerchant/tambahcm'); ?>
                 <div class="form-group">
                        <input type="file" accept="image/*" onchange="loadFile(event)" class="dropify" name="foto_kategori" data-max-file-size="3mb" required />
                        <img id="output" width="250" height="250"/>
                    </div>
                <div class="form-group">
                    <label for="newstitle">Nama Kategori</label>
                    <input type="text" class="form-control" id="newstitle" name="nama_kategori" style="width:60%" required>
                </div>
                <label for="">Layanan</label>
                <div class="form-group">
                    <select class="form-control" style="width:60%" name="id_fitur">
                        <?php foreach ($fitur as $ft) { ?>
                            <option value="<?= $ft['id_fitur'] ?>"><?= $ft['fitur'] ?></option>
                        <?php } ?>
                    </select>
                </div>
                <label for="">Status</label>
                <div class="form-group">
                    <select class="form-control custom-select  mt-15" style="width:60%" name="status_kategori">
                        <option value="1">Active</option>
                        <option value="0">Non Active</option>
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
            let fitur = document.getElementById(`fitur${i}`).innerHTML
            let statm = document.getElementById(`statm${i}`).innerHTML


            let editdoc = document.getElementById('elementform');
            editdoc.style = "display:block;";
            document.getElementById('valnam').value = namkat;
            document.getElementById(`${fitur}`).selected = true;
            document.getElementById(`status${statm}`).selected = true;
            document.getElementById(`valid`).value = idkat;



        }



    }

    function balikan() {
        document.getElementById('elementform').style = "display:none;";
    }
</script>