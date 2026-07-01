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
                    <h4 class="card-title">Add Donasi</h4>
                    <?= form_open_multipart('donasi/tambah'); ?>

                    <div class="form-group">
                        <input type="file" class="dropify" name="foto" data-max-file-size="3mb" required />
                    </div>
                    
                    <div class="form-group">
                        <label for="link">Nama Lembaga</label>
                        <input type="text" class="form-control" id="nama" name="nama" placeholder="enter nama">
                    </div>
                    
                    <div class="form-group">
                        <label for="link">Alanat</label>
                        <input type="text" class="form-control" id="alamat" name="alamat" placeholder="enter alamat">
                    </div>
                    
                    <div class="form-group">
                        <label for="link">Telepon</label>
                        <input type="text" class="form-control" id="phone" name="phone" placeholder="enter phone">
                    </div>
                    
                    <div class="form-group">
                        <label for="link">Judul</label>
                        <input type="text" class="form-control" id="judul" name="judul" placeholder="enter judul">
                    </div>
                    
                    <div class="form-group">
                        <label for="newscontent">Konten</label>
                        <textarea id="textarea" name="deskripsi" required>Konten</textarea>
                     </div>
                    
                     <div class="form-group">
                        <label for="birthdate">Tanggal Awal</label>
                        <input type="date" class="form-control" id="birthdate" name="tanggal_awal" placeholder="Tanggal awal" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="birthdate">Tanggal Akhir</label>
                        <input type="date" class="form-control" id="birthdate" name="tanggal_akhir" placeholder="Tanggal berakhir" required>
                    </div>
                  

                    <div class="form-group">
                        <label for="gender">Status</label>
                        <select class="form-control custom-select  mt-15" name="status" style="width:100%">
                            <option value="1">Active</option>
                            <option value="0">Nonactive</option>
                        </select>
                    </div>
                    <button type="submit" class="btn btn-success mr-2">Submit</button>
                    <a class="btn btn-danger text-white" href="<?= base_url(); ?>donasi">Cancel</a>
                    <?= form_close(); ?>
                </div>
            </div>
        </div>
    </div>
</div>
