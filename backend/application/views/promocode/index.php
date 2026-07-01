<!-- partial -->
<style>
table {
    display: flex;
    flex-flow: column;
    width: 100%;
}

thead {
    flex: 0 0 auto;
}

tbody {
    flex: 1 1 auto;
    display: block;
    overflow-y: auto;
    overflow-x: hidden;
}

tr {
    width: 100%;
    display: table;
    table-layout: fixed;
}
</style>
 <div class="container-fluid mt-xl-50 mt-sm-30 mt-15 px-xxl-65 px-xl-20">
  <div class="card">
    <div class="card-body">
      <div>
        <a class="btn btn-info" href="<?= base_url(); ?>promocode/addpromocode"><i class="mdi mdi-plus-circle-outline"></i>Tambah Promo</a>
      </div>
      <br>
      <?php if ($this->session->flashdata('demo') or $this->session->flashdata('hapus')) : ?>
        <div class="alert alert-danger" role="alert">
          <?php echo $this->session->flashdata('demo'); ?>
          <?php echo $this->session->flashdata('hapus'); ?>
        </div>
      <?php endif; ?>
      <?php if ($this->session->flashdata('ubah') or $this->session->flashdata('tambah')) : ?>
        <div class="alert alert-success" role="alert">
          <?php echo $this->session->flashdata('ubah'); ?>
          <?php echo $this->session->flashdata('tambah'); ?>
        </div>
      <?php endif; ?>
      
      <h4 class="card-title">Daftar Promo Aplikasi</h4>
      <div class="row">
        <div class="col-12">
          <div class="table-responsive">
            <table id="order-listing" class="table table-striped table-hover dt-responsive display nowrap" data-info="false" cellspacing="0" width="100%">
              <thead>
                <tr>
                  <th>No</th>
                  <th>Nama</th>
                  <th>Kode</th>
                  <th>Diskon</th>
                  <th>Layanan</th>
                  <th>Expired</th>
                  <th>Minimal</th>
                  <th>Status</th>
                  <th>Aksi</th>
                </tr>
              </thead>
              <tbody>
                <?php $i = 1;
                foreach ($promocode as $prc) { ?>
                  <tr>
                    <td><?= $i ?></td>
                    <img style="display:none;" src="<?= base_url('images/promo/') . $prc['image_promo']; ?>">
                    <td><?= $prc['nama_promo']; ?></td>
                    <td class="text-primary"><?= $prc['kode_promo']; ?></td>
                  
                    <?php if ($prc['type_promo'] == 'persen') { ?>
                        <td class="text-danger"><?= $prc['nominal_promo']; ?>%</td>
                        <?php } else { ?>
                        <td class="text-danger">Rp<?= rupiah($prc['nominal_promo']) ?></td>
                        <?php } ?>

                    <td><?= $prc['fitur']; ?></td>
                    <td><?= $prc['expired']; ?></td>
                    <td>
                      <?php if ($prc['minimal'] == 0) { ?>
                        <label class="badge badge-danger">Tidak</label>
                      <?php } else { ?>
                        <label class="badge badge-success">Rp<?= rupiah($prc['minimal']) ?></label>
                      <?php } ?>
                    </td>
                    <td>
                      <?php if ($prc['status'] == 1) { ?>
                        <label class="badge badge-success">Active</label>
                      <?php } else { ?>
                        <label class="badge badge-danger">Non Active</label>
                      <?php } ?>
                    </td>
                    <td>
                      <a href="<?= base_url(); ?>promocode/editpromocode/<?= $prc['id_promo']; ?>">
                        <button class="btn btn-outline-primary">Edit</button></a>
                      <a href="<?= base_url(); ?>promocode/hapus/<?= $prc['id_promo']; ?>" onclick="return confirm ('are you sure?')">
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