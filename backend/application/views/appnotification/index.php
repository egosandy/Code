<!-- partial -->
 <div class="container-fluid mt-xl-50 mt-sm-30 mt-15 px-xxl-65 px-xl-20">
  <div class="row ">
    <div class="col-md-8 offset-md-2 grid-margin stretch-card">
      <div class="card">
        <div class="card-body">
          <h4 class="card-title">Kirim Notifikasi</h4>
          <?php if ($this->session->flashdata()) : ?>
            <div class="alert alert-success" role="alert">
              <?php echo $this->session->flashdata('send'); ?>
            </div>
          <?php endif; ?>
          <?= form_open_multipart('appnotification/send'); ?>
          <div class="form-group">
            <label for="newscategory">App Tujuan</label>
            <select class="form-control custom-select  mt-15" style="width:100%" name='topic'>
              <option value="pelanggan">Users</option>
              <option value="driver">Drivers</option>
              <option value="mitra">Merchant Partner</option>
              <option value="ouride">All</option>
            </select>
          </div>
          <div class="form-group">
            <label for="title">Judul</label>
            <input type="text" class="form-control" placeholder="notification" name="title" required>
          </div>
          <div class="form-group">
            <label for="message">Pesan</label>
            <textarea id="textarea" name="message" 
            placeholder = "Masukan Isi Pesan Disini"
            required> </textarea>
          </div>

          <button type="submit" class="btn btn-success">Send<i class="mdi mdi-send ml-1"></i></button>

          <?= form_close(); ?>
        </div>
      </div>
    </div>
  </div>
</div>
<!-- end of content wrapper -->