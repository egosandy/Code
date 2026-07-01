<!-- partial -->
 <div class="container-fluid mt-xl-50 mt-sm-30 mt-15 px-xxl-65 px-xl-20">
  <div class="row ">
    <div class="col-md-8 offset-md-2 grid-margin stretch-card">
      <div class="card">
        <div class="card-body">
          <?php if ($this->session->flashdata()) : ?>
            <div class="alert alert-danger" role="alert">
              <?php echo $this->session->flashdata('demo'); ?>
            </div>
          <?php endif; ?>
          <h4 class="card-title">Edit donasi</h4>
          <?= form_open_multipart('donasi/ubah/' . $id); ?>
          <div class="form-group">
            <input type="hidden" class="form-control" name="id" id="newstitle" value="<?= $id ?>">
            <div class="form-group">
              <label>Donasi Image</label>
              <input type="file" class="dropify" name="foto" data-max-file-size="3mb" data-default-file="<?= base_url('images/promo/') . $gambar ?>" />
            </div>
            
            <div class="form-group">
                        <label for="link">Nama Lembaga</label>
                        <input type="text" class="form-control" id="nama" name="nama" value="<?= $nama_lembaga ;?>" placeholder="enter nama">
                    </div>
                    
                    <div class="form-group">
                        <label for="link">Alanat</label>
                        <input type="text" class="form-control" id="alamat" name="alamat" value="<?= $alamat ;?>" placeholder="enter alamat">
                    </div>
                    
                    <div class="form-group">
                        <label for="link">Telepon</label>
                        <input type="text" class="form-control" id="phone" name="phone" value="<?= $phone ;?>" placeholder="enter phone">
                    </div>
            
            <div class="form-group">
              <label for="newstitle">Judul</label>
              <input type="text" class="form-control" name="judul" id="judul" value="<?= $judul ;?>" required>
            </div>
            
            <div class="form-group">
              <label for="newscontent">Deskripsi</label>
              <textarea type="text" class="form-control" id="summernoteExample1" placeholder="deskripsi" name="deskripsi" required><?= $deskripsi ?></textarea>
            </div>
            
            <div class="form-group">
                        <label for="birthdate">Tanggal Awal</label>
                        <input type="date" class="form-control" id="birthdate" name="tanggal_awal" value="<?= $tanggal_awal ?>" placeholder="Tanggal awal" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="birthdate">Tanggal Akhir</label>
                        <input type="date" class="form-control" id="birthdate" name="tanggal_akhir" value="<?= $tanggal_akhir ?>" placeholder="Tanggal berakhir" required>
                    </div>
          
            <div class="form-group">
              <label for="newscategory">Status</label>
              <select class="form-control custom-select  mt-15" style="width:100%" name="status">

                <option value="1" <?php if ($status == '1') { ?>selected<?php } ?>>Active</option>
                <option value="2" <?php if ($status == '0') { ?>selected<?php } ?>>NonActive</option>

              </select>
            </div>
            
            <button type="submit" class="btn btn-success mr-2">Submit</button>
            <button class="btn btn-light">Cancel</button>
            <?= form_close(); ?>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
<!-- end of content wrapper -->