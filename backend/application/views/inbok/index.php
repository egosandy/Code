<!-- partial -->
<div class="container-fluid mt-xl-50 mt-sm-30 mt-15 px-xxl-65 px-xl-20">
    <div class="row justify-content-md-center">
        <div class="col-md-6 grid-margin stretch-card">
            <div class="card">
                <div class="card-body">
                    <?php if ($this->session->flashdata('demo')) : ?>
                        <div class="alert alert-danger" role="alert">
                            <?php echo $this->session->flashdata('demo'); ?>
                        </div>
                    <?php endif; ?>
                    <h4 class="card-title">Kirim Pesan</h4>
                    <?= form_open_multipart('inbok/kirim'); ?>



                   <label for="">Pilih Pelanggan</label>
                <div class="form-group">
                    <select class="form-control custom-select  mt-15" style="width:60%" name="id">
                        <?php foreach ($user as $ft) { ?>
                             <option value="<?= $ft['id'] ?>"><?= $ft['fullnama'] ?></option>
                        <?php } ?>
                    </select>
                </div>
                    <div class="form-group">
            <label for="newstitle">Title</label>
            <input type="text" class="form-control" id="newstitle" placeholder="Judul" name="title" required>
          </div>
          <div class="form-group">
            <label for="newscontent">Konten</label>
          <div class="form-group">
            <label for="message">Isi Konten</label>
            <textarea id="textarea" name="konten" 
            placeholder = "Masukan Isi Pesan Disini"
            required>Konten</textarea>
          </div>
          <button type="submit" class="btn btn-success">Send<i class="mdi mdi-send ml-1"></i></button>
                    <?= form_close(); ?>
                </div>
            </div>
        </div>
    </div>
</div>


<script>
    function admSelectCheck(nameSelect) {
        console.log(nameSelect);
        if (nameSelect) {
            pelangganValue = document.getElementById("pelanggan").value;
            driverValue = document.getElementById("driver").value;
            mitraValue = document.getElementById("mitra").value;
            console.log(mitraValue);
            if (pelangganValue == nameSelect.value) {
                document.getElementById("pelanggancheck").style.display = "block";
                document.getElementById("drivercheck").style.display = "none";
                document.getElementById("mitracheck").style.display = "none";
            } else {
                document.getElementById("pelanggancheck").style.display = "block";
            }
        }
    }
</script>