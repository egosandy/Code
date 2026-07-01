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
        <a class="btn btn-info" href="<?= base_url(); ?>poin/addpoin"><i class="mdi mdi-plus-circle-outline"></i>Tambah Poin</a>
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
      
      <h4 class="card-title">Daftar Poin</h4>
      <div class="row">
        <div class="col-12">
          <div class="table-responsive">
            <table id="order-listing" class="table table-striped table-hover dt-responsive display nowrap" data-info="false" cellspacing="0" width="100%">
              <thead>
                <tr>
                  <th>No</th>
                  <th>Nama</th>
                  <th>Nilai</th>
                  <th>Masa Aktif</th>
                  <th>Tipe</th>
                  <th>Status</th>
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                <?php $i = 1;
                foreach ($poin as $prc) { ?>
                  <tr>
                    <td><?= $i ?></td>
                    <img style="display:none;" src="<?= base_url('images/poin/') . $prc['image_poin']; ?>">
                    <td><?= $prc['nama']; ?></td>
                    <td class="text-primary">Rp<?= rupiah($prc['nilai']) ?></td>
                    <td class="text-primary"><?= $prc['expire']; ?></td>
                    <td>
                      <?php if ($prc['isdriver'] == 1) { ?>
                        <label class="badge badge-success">Driver</label>
                      <?php } else { ?>
                        <label class="badge badge-danger">Customer</label>
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
                      <a href="<?= base_url(); ?>poin/editpoin/<?= $prc['kode']; ?>">
                        <button class="btn btn-outline-primary">Edit</button></a>
                      <a href="<?= base_url(); ?>poin/hapus/<?= $prc['kode']; ?>" onclick="return confirm ('are you sure?')">
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