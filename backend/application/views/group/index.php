<!-- partial -->
<div class="content-wrapper">
  <div class="card">
    <div class="card-body">
      <?php if ($this->session->flashdata('demo') or $this->session->flashdata('hapus') or $this->session->flashdata('error')) : ?>
        <div class="alert alert-danger" role="alert">
          <?php echo $this->session->flashdata('demo'); ?>
          <?php echo $this->session->flashdata('hapus'); ?>
           <?php echo $this->session->flashdata('error'); ?>
        </div>
      <?php endif; ?>
      <?php if ($this->session->flashdata('ubah') or $this->session->flashdata('tambah') or $this->session->flashdata('sukses')) : ?>
        <div class="alert alert-success" role="alert">
          <?php echo $this->session->flashdata('ubah'); ?>
          <?php echo $this->session->flashdata('tambah'); ?>
           <?php echo $this->session->flashdata('sukses'); ?>
        </div>
      <?php endif; ?>
      
      <div>
        <a class="btn btn-info" href="<?= base_url(); ?>group/tambah"><i class="mdi mdi-plus-circle-outline"></i>Tambah Role</a>
      </div>
      <br>
      <div class="row">
        <div class="col-12">
          <div class="table-responsive">
            
            <table id="order-listing" class="table">
              <thead>
                <tr>
                  <th>No</th>
                  <th>Nama</th>
                  <th>Keterangan</th>
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                <?php $i = 1;
                foreach ($group as $pr) { ?>
                  <tr>
                    <td><?= $i ?></td>
                    <td><?= $pr['nama']; ?></td>
                     <td><?= $pr['keterangan']; ?></td>
                    <td>
                      <a href="<?= base_url(); ?>group/ubah/<?= $pr['id']; ?>">
                        <button class="btn btn-primary"><i class="fa fa-edit"></i></button></a>

                
    
                      <a href="#" onclick="return confirm ('are you sure?')">
                        <button class="btn btn-danger"><i class="fa fa-trash"></i></button>
                      </a>
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
<div class="modal fade" id="editModal" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <?= form_open_multipart('agen/topup'); ?>
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Reset password</h5>
                <button type="button" class="close" data-dismiss="modal"></button>
            </div>
            <div class="modal-body">
                <div id="locker"  class="form-group">
                        <label for="number">Username</label>
                        <input type="text" class="form-control username" name="username" disabled>
                </div>
                <div id="locker"  class="form-group">
                        <label for="number">Nama</label>
                        <input type="text" class="form-control nama" name="nama" disabled>
                </div>
                
            </div>
            <div class="modal-footer">
                <input type="hidden" name="id" class="id">
                <input type="hidden" name="nama" class="nama">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                <button type="submit" class="btn btn-primary">Reset</button>
            </div>
        </div>
         <?= form_close();?>
        
    </div>
</div>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script>
    $(document).ready(function(){
 
        // get Edit Product
        $('.btn-edit').on('click',function(){
            // get data from button edit
            const id = $(this).data('id');
            const nama = $(this).data('nama');
            const username = $(this).data('username');
            // Set data to Form Edit
            $('.id').val(id);
            $('.nama').val(nama);
            $('.username').val(username);
          
            // Call Modal Edit
            $('#editModal').modal('show');
        });
 
        
         
    });
</script>