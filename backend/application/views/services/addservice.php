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
                        Tambah Layanan</h4>
                    <?= form_open_multipart('services/addservice'); ?>
                    <div class="form-group">
                        <input type="file" class="dropify" name="icon" data-max-file-size="3mb" required />
                    </div>
                    <div class="form-group">
                        <label for="newstitle">Nama</label>
                        <input type="text" class="form-control" id="newstitle" name="fitur" required>
                    </div>
                    <div class="form-group">
                        <label for="newscategory">Tipe Layanan</label>
                        <select class="form-control custom-select  mt-15" name="home" style="width:100%">
                            <option value="1" >Transportasi</option>
                            <option value="2" >Pengiriman</option>
                            <option value="3" >Rental</option>
                            <option value="4" >Kurir</option>
                            <option value="5" >Ppob</option>
                            <option value="6" >Promosi</option>
                             <option value="7" >Maintanance</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="newstitle">Harga</label>
                        <input type="text"  class="form-control" id="newstitle" name="biaya"  required>
                    </div>
                    <div class="form-group">
                        <label for="newstitle">Diskon (%)</label>
                        <input type="text" class="form-control" id="newstitle" name="nilai" placeholder="ex 10%">
                    </div>
                    <div class="form-group">
                        <label for="newscategory">Jarak</label>
                        <select class="form-control custom-select  mt-15" name="keterangan_biaya" style="width:100%">
                            <!-- <option value="KM">Kilometers</option> -->
                            <option value="KM" >Kilometer</option>
                            <option value="Hr" >Perjam</option>
                            <option value="Dy" >Perhari</option>
                            <option value="Mg" >Perminggu</option>
                            <option value="Bl" >Perbulan</option>
                            <!-- <option value="Hr">An Hour</option> -->
                        </select>
                    </div>
                    
                     
                    <div class="form-group">
                        <label for="newscategory">Tipe Komisi Driver</label>
                        <select class="form-control custom-select  mt-15" name="komisi_tipe" style="width:100%">
                            <!-- <option value="KM">Kilometers</option> -->
                            <option value="1" >Persen</option>
                            <option value="2" >Nominal</option>
                            <!-- <option value="Hr">An Hour</option> -->
                        </select>
                    </div>
                    
                    <div class="form-group">
                        <label for="newstitle">Komisi Driver</label>
                        <input type="text" class="form-control" id="newstitle" name="komisi" placeholder="ex 10%" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="newscategory">Tipe Komisi Merchant</label>
                         <select class="form-control custom-select  mt-15" name="komisi_mitra_tipe" style="width:100%">
                            <!-- <option value="KM">Kilometers</option> -->
                            <option value="1" >Persen</option>
                            <option value="2" >Nominal</option>
                            <!-- <option value="Hr">An Hour</option> -->
                        </select>
                    </div>
                    
                    <div class="form-group">
                        <label for="newstitle">Komisi Merchant</label>
                        <input type="text" class="form-control" id="newstitle" name="komisi_mitra" placeholder="ex 10%" required>
                    </div>
                    
                    
                    <div class="form-group">
                        <label for="newscategory">Kendaraan</label>
                        <select class="form-control custom-select  mt-15" name="driver_job" style="width:100%">
                            <?php foreach ($driverjob as $drj) { ?>
                                <option value="<?= $drj['id'] ?>"><?= $drj['driver_job'] ?></option>
                            <?php } ?>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="newstitle">Minimal Harga</label>
                        <input type="text"  class="form-control" id="newstitle" name="biaya_minimum" required>
                    </div>
                    <div class="form-group">
                        <label for="newstitle">Jarak Driver</label>
                        <input type="text" class="form-control" id="newstitle" name="jarak_minimum" required>
                    </div>
                    <div class="form-group">
                        <label for="newstitle">Maks Jarak Order</label>
                        <input type="text" class="form-control" id="newstitle" name="maks_distance" required>
                    </div>
                    <div class="form-group">
                        <label for="newstitle">Minimal Saldo</label>
                        <input type="text"  class="form-control" id="newstitle" name="wallet_minimum" required>
                    </div>
                   
                    <div class="form-group">
                        <label for="newstitle">Deskripsi</label>
                        <input type="text" class="form-control" id="newstitle" name="keterangan" required>
                    </div>
                    
                     <div class="form-group">
                        <label for="newscategory">Order Pending</label>
                        <select class="form-control custom-select" name="is_pending" style="width:100%">
                            <!-- <option value="KM">Kilometers</option> -->
                            <option value="1">Ya</option>
                            <option value="0">Tidak</option>
                            <!-- <option value="Hr">An Hour</option> -->
                        </select>
                    </div>
                    
                    <div class="form-group">
                         <label for="newstitle">Warna Background : </label><br>
                        <input type="color" id="head" name="background" value="#e4e0ff" required>
                    </div>
                    <div class="form-group">
                        <label for="newscategory">Status</label>
                        <select class="form-control custom-select  mt-15" name="active" style="width:100%">
                            <option value="0" >Nonactive</option>
                            <option value="1" >Active</option>
                        </select>
                    </div>

                    <button type="submit" class="btn btn-success mr-2">Submit</button>
                    <a href="<?= base_url() ?>services" class="btn btn-danger">Cancel</a>
                    <?= form_close(); ?>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- end of content wrapper -->