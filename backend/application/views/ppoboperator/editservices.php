<!-- partial -->
 <div class="container-fluid mt-xl-50 mt-sm-30 mt-15 px-xxl-65 px-xl-20">
    <div class="row ">
        <div class="col-md-8 offset-md-2 grid-margin stretch-card">
            <div class="card">
                <div class="card-body">
                    <h4 class="card-title">
                        <?php if ($this->session->flashdata()) : ?>
                            <div class="alert alert-danger" role="alert">
                                <?php echo $this->session->flashdata('demo'); ?>
                            </div>
                        <?php endif; ?>
                        Layanan</h4>
                    <?= form_open_multipart('ppoboperator/ubah/' . $kode); ?>
                    <input type="hidden" name="kode" value='<?= $kode ?>'>
                    <div class="form-group">
                        <input type="file" class="dropify" name="ikon" data-max-file-size="3mb" data-default-file="<?= base_url('images/ppob/') . $ikon ?>" />
                    </div>
                    <div class="form-group">
                        <label for="newstitle">Nama Operator</label>
                        <input type="text" class="form-control" id="newstitle" name="nama" value="<?= $nama ?>" required>
                    </div>
                    <div class="form-group">
                        <label for="service_tipe">Tipe</label>
                        <select class="form-control custom-select  mt-15" name="tipe" style="width:100%">
                            <option value="data" <?php if ($tipe == 'data') { ?>selected<?php } ?>>Data</option>
                            <option value="pulsa" <?php if ($tipe == 'pulsa') { ?>selected<?php } ?>>Pulsa</option>
                            <option value="game" <?php if ($tipe == 'etoll') { ?>selected<?php } ?>>Etoll</option>
                            <option value="pln" <?php if ($tipe == 'pln') { ?>selected<?php } ?>>Pln</option>
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
                    <a href="<?= base_url() ?>ppoboperator" class="btn btn-danger">Cancel</a>
                    <?= form_close(); ?>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- end of content wrapper -->