<!-- partial -->

 <div class="container-fluid mt-xl-50 mt-sm-30 mt-15 px-xxl-65 px-xl-20">
    <div class="row ">
        <div class="col-md-8 offset-md-2 grid-margin stretch-card">
            <div class="card">
                <div class="card-body">
                    
                    <h4 class="card-title">
                        <?php if ($this->session->flashdata('demo')) : ?>
                            <div class="alert alert-danger" role="alert">
                                <?php echo $this->session->flashdata('demo'); ?>
                            </div>
                        <?php endif; ?>
                        <?php if ($this->session->flashdata('error')) : ?>
                        <div class="alert alert-danger" role="alert">
                            <?php echo $this->session->flashdata('error'); ?>
                        </div>
                        <?php endif; ?>
                        Tambah Data</h4>
                    <?= form_open_multipart('admin/tambah'); ?>
                    <div class="form-group">
                        <input type="file" class="dropify" name="foto" data-max-file-size="3mb" required />
                    </div>
                    <div class="form-group">
                        <label for="id_level">Level</label>
                        <select class="form-control custom-select  mt-15" style="width:100%" name="id_level" id = "id_level" >
                          <?php foreach ($level as $nw) { ?>
                            <option value="<?= $nw['id'] ?>"><?= $nw['nama'] ?></option>
                          <?php } ?>
                        </select>
                     </div>
                    <div class="form-group">
                        <label for="username">Username</label>
                        <input type="text" class="form-control" id="username" name="username" required>
                    </div>
                    <div class="form-group">
                        <label for="username">Name</label>
                        <input type="text" class="form-control" id="nama" name="nama" required>
                    </div>
                    <div class="form-group">
                        <label for="email">Email</label>
                        <input type="text" class="form-control" id="email" name="email" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="email">Password</label>
                        <input type="password" class="form-control" id="password" name="password" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="newscategory">Status</label>
                        <select class="form-control custom-select  mt-15" name="status" style="width:100%">
                            <option value="0" >Nonactive</option>
                            <option value="1" >Active</option>
                        </select>
                    </div>

                    <button type="submit" class="btn btn-success mr-2">Submit</button>
                    <a href="<?= base_url() ?>admin" class="btn btn-danger">Cancel</a>
                    <?= form_close(); ?>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- end of content wrapper -->