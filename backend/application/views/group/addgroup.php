<!-- partial -->
<div class="content-wrapper">
    <div class="row justify-content-md-center">
        <div class="col-md-6 grid-margin stretch-card">
            <div class="card">
                <div class="card-body">
                    <?php if ($this->session->flashdata('demo')) : ?>
                        <div class="alert alert-danger" role="alert">
                            <?php echo $this->session->flashdata('demo'); ?>
                        </div>
                    <?php endif; ?>
                    <h4 class="card-title">Tambah Group Level</h4>
                    <?= form_open_multipart('group/tambah'); ?>

                    <div class="form-group">
                        <label for="name">Nama</label>
                        <input type="text" class="form-control" id="name" name="nama" placeholder="enter name" required>
                    </div>

                    <div class="form-group">
                        <label for="desc">Keterangan</label>
                        <input type="text" class="form-control" id="desc" name="keterangan" placeholder="enter description" required>
                    </div>

                    <div class="row">
                        <div class="col-12">
                            <div class="form-group">
                                <label for="check"> Setting menu untuk role</label>
                                <?php foreach ($allmenu as $value) {
                                if(strlen($value->menu_kode) == 4){ ?>
                                    <div class="form-check">
                                         <input type="checkbox" name="label[]">
                                        <label class="form-check-label"><?= $value->menu_label; ?>
                                       
                                    </div>
                                    

                                <?php }else{ ?>
                                    <div class="col col-md-6">
                                        <div class="form-check">
                                            <input type="checkbox"  name="label[]" value="<?= $value->menu_kode; ?>">
                                            <label class="form-check-label"><?= $value->menu_label; ?>
                                          
                                        </div>
                                    </div>
                                    
                                <?php }

                                
                                } ?>

                            </div>
                        </div>
                    </div>

                    

                    <button type="submit" class="btn btn-success mr-2">Submit</button>
                    <a class="btn btn-danger text-white" href="<?= base_url(); ?>banner">Cancel</a>
                    <?= form_close(); ?>
                </div>
            </div>
        </div>
    </div>
</div>