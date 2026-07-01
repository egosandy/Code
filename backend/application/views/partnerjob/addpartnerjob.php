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
          <?= form_open_multipart('partnerjob/addpartnerjob'); ?>
          <div class="form-group">
            <label for="status_berita">Ikon Map</label>
            <select class="form-control custom-select  mt-15" style="width:100%" name="icon" id="statusjob">
              <option value="1">Motor</option>
              <option value="2">Mobil</option>
              <option value="3">Truck</option>
              <option value="4">Kurir Sepeda</option>
              <option value="5">HatchBack</option>
              <option value="6">SUV</option>
              <option value="7">VAN</option>
              <option value="8">Sepeda</option>
              <option value="9">Bajaj</option>
            </select>
          </div>
          <div class="form-group">
            <label for="title">Tipe Kendaraan</label>
            <input type="text" class="form-control" name="driver_job" id="job" placeholder="enter job title" required>
          </div>
          <div class="form-group">
            <label for="status_berita">Status Tipe Kendaraan</label>
            <select class="form-control custom-select  mt-15" style="width:100%" name="status_job" id="statusjob">
              <option value="1">Active</option>
              <option value="0">NonActive</option>
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