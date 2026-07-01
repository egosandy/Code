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
          <h4 class="card-title">Tambah Tipe Kendaraan</h4>

          <?= form_open_multipart('partnerjob/editpartnerjob/' . $partnerjob['id']); ?>
          
          <input type="hidden" name="id" value="<?= $partnerjob['id'] ?>">
          <div class="form-group">
            <label for="status_berita">Ikon map</label>
            <select class="form-control custom-select  mt-15" style="width:100%" name="icon" id="statusjob">
              <option value="1" <?php if ($partnerjob['icon'] == '1') { ?>selected<?php } ?>>Motor</option>
              <option value="2" <?php if ($partnerjob['icon'] == '2') { ?>selected<?php } ?>>Mobil</option>
              <option value="3" <?php if ($partnerjob['icon'] == '3') { ?>selected<?php } ?>>Truck</option>
              <option value="4" <?php if ($partnerjob['icon'] == '4') { ?>selected<?php } ?>>Kurir Sepeda</option>
              <option value="5" <?php if ($partnerjob['icon'] == '5') { ?>selected<?php } ?>>HatchBack</option>
              <option value="6" <?php if ($partnerjob['icon'] == '6') { ?>selected<?php } ?>>SUV</option>
              <option value="7" <?php if ($partnerjob['icon'] == '7') { ?>selected<?php } ?>>VAN</option>
              <option value="8" <?php if ($partnerjob['icon'] == '8') { ?>selected<?php } ?>>Sepeda</option>
              <option value="9" <?php if ($partnerjob['icon'] == '9') { ?>selected<?php } ?>>Bajaj</option>
            </select>
          </div>
          <div class="form-group">
            <label for="title">Tipe Kendaraan</label>
            <input type="text" class="form-control" name="driver_job" id="job" placeholder="enter job title" value="<?= $partnerjob['driver_job'] ?>" required>
          </div>
          <div class="form-group">
            <label for="status_berita">Status Tipe Kendaraan</label>
            <select class="form-control custom-select  mt-15" style="width:100%" name="status_job" id="statusjob">
              <option value="1" <?php if ($partnerjob['status_job'] == '1') { ?>selected<?php } ?>>Active</option>
              <option value="0" <?php if ($partnerjob['status_job'] == '0') { ?>selected<?php } ?>>NonActive</option>
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
<!-- end of content wrapper -->