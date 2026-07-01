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
                    <h4 class="card-title">Ubah Poin</h4>
                    <?= form_open_multipart('poin/editpoin/' . $poin['kode']); ?>

                    <div class="form-group">
                    <input type="hidden" name="kode" value=<?= $poin['kode']?>>
                        <input type="file" class="dropify" name="image_poin" data-default-file="<?= base_url('images/poin/'. $poin['image_poin']); ?>"/>
                    </div>
                    <div class="form-group">
                        <label for="birthdate">Nama</label>
                        <input type="text" class="form-control" id="nama" name="nama" placeholder="Input Nama Poin" value="<?= $poin['nama'] ?>" required>
                    </div>
                    <div class="form-group">
                        <label for="birthdate">Keterangan</label>
                        <input type="text" class="form-control" id="keterangan" name="keterangan" placeholder="Input Keterangan" value="<?= $poin['keterangan'] ?>" required>
                    </div>
                    <div class="form-group">
                        <label for="birthdate">Poin</label>
                        <input type="number" class="form-control" id="poin" name="poin" placeholder="Input Poin" value="<?= $poin['poin'] ?>" required>
                    </div>
                     <div class="form-group">
                        <label for="birthdate">Nilai/Harga</label>
                        <input type="number" class="form-control" id="nilai" name="nilai" placeholder="Input Nilai" value="<?= $poin['nilai'] ?>" required>
                    </div>
                    <div class="form-group">
                        <label for="birthdate">Masa Aktif</label>
                        <input type="date" class="form-control" id="expire" name="expire" placeholder="" value="<?= $poin['expire'] ?>" required>
                    </div>
                    <div class="form-group">
                        <label for="gender">Tipe</label>
                        <select class="form-control custom-select  mt-15" name="isdriver" style="width:100%" value="<?= $poin['isdriver'] ?>">
                            <option value="1">Driver</option>
                            <option value="0">Customer</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="gender">status</label>
                        <select class="form-control custom-select  mt-15" name="status" style="width:100%" value="<?= $poin['status'] ?>">
                            <option value="1">Active</option>
                            <option value="0">Nonactive</option>
                        </select>
                    </div>
                    <button type="submit" class="btn btn-success mr-2">Submit</button>
                    <a class="btn btn-danger text-white" href="<?= base_url(); ?>poin">Cancel</a>
                    <?= form_close(); ?>
                </div>
            </div>
        </div>
    </div>
</div>