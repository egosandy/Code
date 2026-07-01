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
        <a class="btn btn-info" href="<?= base_url(); ?>promocode/addpromocode"><i class="mdi mdi-plus-circle-outline"></i>Tambah Operator</a>
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
      
      <h4 class="card-title">Daftar Operator</h4>
      <div class="row">
        <div class="col-12">
          <div class="table-responsive">
            <table id="order-listing" class="table table-striped table-hover dt-responsive display nowrap" data-info="false" cellspacing="0" width="100%">
              <thead>
                <tr>
                  <th>No</th>
                  <th>Fitur</th>
                  <th>Tipe</th>
                  <th>Kode_OP</th>
                  <th>Status</th>
                  <th>Kelola</th>
                </tr>
              </thead>
              <tbody>
                <?php $i = 1;
                foreach ($ppob as $prc) { ?>
                  <tr>
                    <td><?= $i ?></td>
                    <td><?= $prc['fitur']; ?></td>
                    <td class="text-primary"><?= $prc['tipe']; ?></td>
                    <td class="text-primary"><?= $prc['operator']; ?></td>
                    <td>
                      <?php if ($prc['status'] == 1) { ?>
                        <label class="badge badge-success">Active</label>
                      <?php } else { ?>
                        <label class="badge badge-danger">Non Active</label>
                      <?php } ?>
                    </td>
                    <td>
                      <a href="<?= base_url(); ?>ppob/editpromocode/<?= $prc['id_fitur']; ?>">
                        <button class="btn btn-outline-primary">Edit</button></a>
                      <a href="<?= base_url(); ?>ppob/hapus/<?= $prc['id_fitur']; ?>" onclick="return confirm ('are you sure?')">
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