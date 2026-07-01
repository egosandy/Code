<!-- partial -->
 <div class="container-fluid mt-xl-50 mt-sm-30 mt-15 px-xxl-65 px-xl-20">
  <div class="card">
    <div class="card-body">
      <div>
        <a class="btn btn-info" href="<?= base_url(); ?>donasi/tambah"><i class="mdi mdi-plus-circle-outline"></i>Add Donasi</a>
      </div>
      <br>
      <?php if ($this->session->flashdata('demo') or $this->session->flashdata('hapus') or $this->session->flashdata('error')) : ?>
        <div class="alert alert-danger" role="alert">
          <?php echo $this->session->flashdata('demo'); ?>
          <?php echo $this->session->flashdata('hapus'); ?>
        </div>
      <?php endif; ?>
      <?php if ($this->session->flashdata('ubah') or $this->session->flashdata('tambah')) : ?>
        <div class="alert alert-succees" role="alert">
          <?php echo $this->session->flashdata('ubah'); ?>
          <?php echo $this->session->flashdata('tambah'); ?>
        </div>
      <?php endif; ?>
      <h4 class="card-title">Donasi</h4>
      <div class="row">
        <div class="col-12">
          <div class="table-responsive">
            <table id="order-listing" class="table">
              <thead>
                <tr>
                  <th>No</th>
                  <th>Image</th>
                  <th>Nama Lmbaga</th>
                  <th>Judul Campaign</th>
                  <th>Total</th>
                  <th>Status</th>
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                <?php $i = 1;
                foreach ($donasi as $pr) { ?>
                  <tr>
                    <td><?= $i ?></td>
                    <td>
                      <img width="80" height="80" src="<?= base_url('images/promo/') . $pr['gambar']; ?>">
                    </td>
                     <td><?= $pr['nama_lembaga']; ?></td>
                    <td><?= $pr['judul']; ?></td>
                   
                    <td><?= rupiah($pr['total']); ?></td>
                    <td>
                      <?php if ($pr['status'] == 1) { ?>
                        <label class="badge badge-success">Active</label>
                      <?php } else { ?>
                        <label class="badge badge-danger">Non Active</label>
                      <?php } ?>
                    </td>
                    <td>
                        
                    <a href="<?= base_url(); ?>donasi/detail/<?= $pr['id']; ?>">
                        <button class="btn btn-outline-success">View</button></a>        
                        
                      <a href="<?= base_url(); ?>donasi/ubah/<?= $pr['id']; ?>">
                        <button class="btn btn-outline-primary">Edit</button></a>
                        
                     <?php if($pr['total'] <= 0){ ?>
                         <a href="<?= base_url(); ?>donasi/hapus/<?= $pr['id']; ?>" onclick="return confirm ('are you sure?')">
                        <button class="btn btn-outline-danger">Delete</button></a>
                     <?php } ?>
                      
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