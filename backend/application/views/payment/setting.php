<!-- partial -->
<div class="content-wrapper">
    <div class="card">
                <div class="card-body">
                    <?php if ($this->session->flashdata()) : ?>
                        <div class="alert alert-success" role="alert">
                            <?php echo $this->session->flashdata('ubah'); ?>
                        </div>
                    <?php endif; ?>
                    <h4 class="card-title">
                        
                        Setting Payment Gateway</h4>
                    <?= form_open_multipart('payments/setting'); ?>
                    
                    <div class="form-group">
                        <label for="newstitle">Nama</label>
                        <input type="text" class="form-control" id="newstitle" name="nama" value="<?= $nama ?>" required>
                    </div>
                    <div class="form-group">
                        <label for="newstitle">Api Key Devekopment</label>
                        <input type="text" class="form-control" id="newstitle" name="key_demo" value="<?= $key_demo ?>" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="link">Api Key Production</label>
                        <input type="text" class="form-control" id="linktes" name="key_production" value="<?= $key_production ?>">
                    </div>
                    
                    <div class="form-group">
                        <label for="newscategory">Demo</label>
                        <select class="form-control custom-select  mt-15" name="is_demo" style="width:100%">
                            <option value="0" <?php if ($status == 0) { ?>selected<?php } ?>>Tidak</option>
                            <option value="1" <?php if ($status == 1) { ?>selected<?php } ?>>Ya</option>
                        </select>
                    </div>
                    
                    <div class="form-group">
                        <label for="newscategory">Status</label>
                        <select class="form-control custom-select  mt-15" name="status" style="width:100%">
                            <option value="0" <?php if ($status == 0) { ?>selected<?php } ?>>Nonactive</option>
                            <option value="1" <?php if ($status == 1) { ?>selected<?php } ?>>Active</option>
                        </select>
                    </div>

                    <button type="submit" class="btn btn-success mr-2">Submit</button>
                    <a href="<?= base_url() ?>payments" class="btn btn-danger">Cancel</a>
                    <?= form_close(); ?>
                </div>
            </div>
</div>
<!-- end of content wrapper -->
