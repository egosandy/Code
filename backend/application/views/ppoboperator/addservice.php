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
                        Tambah Data</h4>
                    <?= form_open_multipart('ppoboperator/addservice'); ?>
                    <div class="form-group">
                        <input type="file" class="dropify" name="ikon" data-max-file-size="3mb" required />
                    </div>
                    <div class="form-group">
                        <label for="newstitle">Operator</label>
                        <input type="text" class="form-control" id="newstitle" name="nama" required>
                    </div>
                    <div class="form-group">
                        <label for="newstitle">Kode</label>
                        <input type="text" class="form-control" id="newstitle" name="kode" required>
                    </div>
                    <div class="form-group">
                        <label for="newscategory">Tipe</label>
                        <select class="form-control custom-select  mt-15" name="tipe" style="width:100%">
                            <option value="data" >Data</option>
                            <option value="pulsa" >Pulsa</option>
                            <option value="etoll" >Etoll</option>
                            <option value="pln" >PLN</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="newscategory">Status</label>
                        <select class="form-control custom-select  mt-15" name="status" style="width:100%">
                            <option value="0" >Nonactive</option>
                            <option value="1" >Active</option>
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