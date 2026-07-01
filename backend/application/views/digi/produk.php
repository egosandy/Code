 <div class="container-fluid mt-xl-50 mt-sm-30 mt-15 px-xxl-65 px-xl-20">
    <div class="row grid-margin">
        <div class="col-12">
            <div class="card">
                <div class="card-body">
                    <?php if ($this->session->flashdata('tambah')) : ?>
                        <div class="alert alert-success" role="alert">
                            <?php echo $this->session->flashdata('tambah'); ?>
                        </div>
                    <?php endif; ?>
                    <?php if ($this->session->flashdata('demo')) : ?>
                        <div class="alert alert-danger" role="alert">
                            <?php echo $this->session->flashdata('demo'); ?>
                        </div>
                    <?php endif; ?>
                    <?php if ($this->session->flashdata('hapus')) : ?>
                        <div class="alert alert-danger" role="alert">
                            <?php echo $this->session->flashdata('hapus'); ?>
                        </div>
                    <?php endif; ?>
                    <div>
                        <button type="button" class="btn btn-info" data-toggle="modal" data-target="#addModal"><i class="mdi mdi-plus-circle-outline"></i>Tambah</button>
                    </div>
                    <br>
                    <div class="table-responsive">
                        <table id="mwtable" class="table table-striped table-hover dt-responsive display nowrap" data-info="false" cellspacing="0" width="100%">
                            <thead>
                                <tr>
                                    <th>No</th>
                                    <th>Operator</th>
                                    <th>Kode</th>
                                    <th>Nama</th>
                                    <th>HPP</th>
                                    <th>Harga</th>
                                    <th>Status</th>
                                    <th>Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                <?php $i = 1;
                                foreach ($produk as $s) { ?>
                                    <tr>
                                        <td><?= $i; ?></td>
                                        
                                        <td><?= $s['nama_operator']; ?></td>
                                        <td><?= $s['kode']; ?></td>
                                        <td><?= $s['nama']; ?></td>
                                        <td><?= $s['hpp']; ?></td>
                                        <td><?= $s['harga']; ?></td>
                                        <td>
                                            <?php if ($s['status'] == 1) { ?>
                                                <label class="badge badge-success">Active</label>
                                            <?php } else { ?>
                                                <label class="badge badge-danger">Non Active</label>
                                            <?php } ?>
                                        </td>
                                        <td>
                                            <a href="#">
                                                <button class="btn btn-outline-primary btn-edit"
                                                data-id="<?= $s['id']; ?>"
                                                data-operator="<?= $s['operator']; ?>"
                                                data-kode="<?= $s['kode']; ?>"
                                                data-hpp="<?= $s['hpp']; ?>"
                                                data-harga="<?= $s['harga']; ?>"
                                                data-deskripsi="<?= $s['deskripsi']; ?>"
                                                data-nama="<?= $s['nama']; ?>">
                                                    Edit
                                            </button>
                                            </a>
                                            <a href="<?= base_url(); ?>digi/delprod/<?= $s['id']; ?>" onclick="return confirm ('are you sure?')">
                                            <button class="btn btn-outline-danger">Delete</button></a>
                                        </td>
                                    <?php $i++;
                                } ?>
                                    </tr>

                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- ADD MODAL -->
<div class="modal fade" id="addModal" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <?= form_open_multipart('digi/addproduk'); ?>
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Tambah</h5>
                <button type="button" class="close" data-dismiss="modal"></button>
            </div>
            
            <div class="modal-body">
                
                <div class="form-group">
                        <label for="newscategory">Operator</label>
                        <select class="form-control custom-select mt-15" name="operator" style="width:100%">
                            <?php foreach ($operator as $pr) { ?>
                                <option value="<?= $pr['id'] ?>"><?= $pr['nama'] ?></option>
                            <?php } ?>
                        </select>
                </div>
                
               
                <div id="locker"  class="form-group">
                        <label for="kode">Kode</label>
                        <input type="text" class="form-control" name="kode" id="kode" required>
                </div>
                
                <div id="locker"  class="form-group">
                        <label for="nama">Nama</label>
                        <input type="text" class="form-control" name="nama" id="nama" required>
                </div>
                
                <div id="locker"  class="form-group">
                        <label for="nama">Deskripsi</label>
                        <input type="text" class="form-control" name="deskripsi" id="deskripsi" required>
                </div>
                
                <div id="locker"  class="form-group">
                        <label for="number">HPP</label>
                        <input type="number" class="form-control" name="hpp" id="hpp" required>
                </div>
                
                <div id="locker"  class="form-group">
                        <label for="number">Harga</label>
                        <input type="number" class="form-control" name="harga" id="harga" required>
                </div>

                <div class="form-group">
                        <label for="gender">Status</label>
                        <select class="form-control custom-select mt-15" name="status" style="width:100%">
                            <option value="1">Active</option>
                            <option value="0">Nonactive</option>
                        </select>
                </div>
                
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                <button type="submit" class="btn btn-primary">Simpan</button>
            </div>
        </div>
         <?= form_close();?>
        
    </div>
</div>
<!-- EDIT MODAL -->
<div class="modal fade" id="editModal" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <?= form_open_multipart('digi/editproduk'); ?>
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Update</h5>
                <button type="button" class="close" data-dismiss="modal"></button>
            </div>
            <div class="modal-body">
                <div id="locker"  class="form-group">
                        <label for="number">ID</label>
                        <input type="text" class="form-control id" name="id"  disabled>
                </div>
                
                  <div class="form-group">
                        <label for="newscategory">Operator</label>
                        <select class="form-control custom-select mt-15 operator" name="operator" style="width:100%">
                            <?php foreach ($operator as $pr) { ?>
                                <option value="<?= $pr['id'] ?>"><?= $pr['nama'] ?></option>
                            <?php } ?>
                        </select>
                </div>
               
                <div id="locker"  class="form-group">
                        <label for="kode">Kode</label>
                        <input type="text" class="form-control kode" name="kode" id="kode" required>
                </div>
                
                <div id="locker"  class="form-group">
                        <label for="nama">Nama</label>
                        <input type="text" class="form-control nama" name="nama" id="nama" required>
                </div>
                
                 <div id="locker"  class="form-group">
                        <label for="nama">Deskripsi</label>
                        <input type="text" class="form-control deskripsi" name="deskripsi" id="deskripsi" required>
                </div>
                
                <div id="locker"  class="form-group">
                        <label for="number">HPP</label>
                        <input type="number" class="form-control hpp" name="hpp" id="hpp" required>
                </div>
                
                <div id="locker"  class="form-group">
                        <label for="number">Harga</label>
                        <input type="number" class="form-control harga" name="harga" id="harga" required>
                </div>

              
                 <div class="form-group">
                        <label for="gender">Status</label>
                        <select class="form-control custom-select mt-15" name="status" style="width:100%">
                            <option value="1">Active</option>
                            <option value="0">Nonactive</option>
                        </select>
                </div>
                
            </div>
            <div class="modal-footer">
                <input type="hidden" name="id" class="id">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                <button type="submit" class="btn btn-primary">Update</button>
            </div>
        </div>
         <?= form_close();?>
        
    </div>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script>
    $(document).ready(function(){
 
        // get Edit Product
        $('.btn-edit').on('click',function(){
            // get data from button edit
            const id = $(this).data('id');
            const kode = $(this).data('kode');
            const nama = $(this).data('nama');
            const hpp = $(this).data('hpp');
            const harga = $(this).data('harga');
            const deskripsi = $(this).data('deskripsi');
            const operator = $(this).data('operator');
         
            // Set data to Form Edit
            $('.id').val(id);
            $('.nama').val(nama);
            $('.kode').val(kode);
            $('.operator').val(operator);
            $('.hpp').val(hpp);
            $('.deskripsi').val(deskripsi);
            $('.harga').val(harga);
          
            // Call Modal Edit
            $('#editModal').modal('show');
        });
 
        
         
    });
</script>