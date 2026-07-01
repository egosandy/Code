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
                    <h4 class="card-title">Update Group Level</h4>
                    <?= form_open_multipart('group/ubah/'. $id); ?>

                    <div class="form-group">
                        <input type="hidden" name="id" value=<?= $id ?>>
                        <label for="name">Nama</label>
                        <input type="text" class="form-control" id="name" name="nama" value="<?= $nama; ?>" required>
                    </div>

                    <div class="form-group">
                        <label for="desc">Keterangan</label>
                        <input type="text" class="form-control" id="desc" name="keterangan" value="<?= $keterangan; ?>" required>
                    </div>

                    <div class="row">
                        <div class="col-12">
                            <div class="form-group">
                                <label for="check"> Setting menu untuk role admin</label>
                         
                                <?php foreach ($allmenu as $value) {
                                $checked = in_array($value->menu_kode, $menu_checked) ? "checked" : "";
                                if(strlen($value->menu_kode) == 4){ ?>
                                    <div class="form-check form-check-flat form-check-primary">
                                         <input type="checkbox" value="<?= $value->menu_kode ?>" <?= $checked ?>  name="label[]">
                                        <label class="form-check-label"><?= $value->menu_label; ?>
                                       
                                    </div>
                                    

                                <?php }else{ ?>
                                    <div class="col col-md-6">
                                        <div class="form-check">
                                            <input type="checkbox" value="<?= $value->menu_kode ?>" <?= $checked ?> name="label[]">
                                            <label class="form-check-label"><?= $value->menu_label; ?>
                                            
                                        </div>
                                    </div>
                                    
                                <?php }

                                
                                } ?>

                            </div>
                        </div>
                    </div>

                    

                    <button type="submit" class="btn btn-success mr-2">Submit</button>
                    <a class="btn btn-danger text-white" href="<?= base_url(); ?>group">Cancel</a>
                    <?= form_close(); ?>
                </div>
            </div>
        </div>
    </div>
</div>