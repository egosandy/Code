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
                    <?= form_open_multipart('services/ubah/' . $id_fitur); ?>
                    <input type="hidden" name="id_fitur" value='<?= $id_fitur ?>'>
                    <div class="form-group">
                        <input type="file" class="dropify" name="icon" data-max-file-size="3mb" data-default-file="<?= base_url('images/fitur/') . $icon ?>" />
                    </div>
                    <div class="form-group">
                        <label for="newstitle">Nama Layanan</label>
                        <input type="text" class="form-control" id="newstitle" name="fitur" value="<?= $fitur ?>" required>
                    </div>
                    <div class="form-group">
                        <label for="service_tipe">Tipe Layanan</label>
                        <select class="form-control custom-select  mt-15" name="home" style="width:100%">
                            <option value="1" <?php if ($home == '1') { ?>selected<?php } ?>>Transportasi</option>
                            <option value="2" <?php if ($home == '2') { ?>selected<?php } ?>>Pengiriman</option>
                            <option value="3" <?php if ($home == '3') { ?>selected<?php } ?>>Rental</option>
                            <option value="4" <?php if ($home == '4') { ?>selected<?php } ?>>Kurir</option>
                            <option value="5" <?php if ($home == '5') { ?>selected<?php } ?>>Ppob</option>
                            <option value="6" <?php if ($home == '6') { ?>selected<?php } ?>>Promosi</option>
                            <option value="7" <?php if ($home == '7') { ?>selected<?php } ?>>Maintenace</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="newstitle">Harga</label>
                        <input type="text"  class="form-control" id="newstitle" name="biaya" value="<?= rupiah($biaya) ?>" required>
                    </div>
                    <div class="form-group">
                        <label for="newstitle">Diskon (%)</label>
                        <input type="text" class="form-control" id="newstitle" name="nilai" value="<?= $nilai ?>" placeholder="ex 10%">
                    </div>
                    <div class="form-group">
                        <label for="newscategory">Jarak</label>
                        <select class="form-control custom-select  mt-15" name="keterangan_biaya" style="width:100%">
                            <!-- <option value="KM">Kilometers</option> -->
                            <option value="KM" <?php if ($keterangan_biaya == 'KM') { ?>selected<?php } ?>>Kilometer</option>
                            <option value="Hr" <?php if ($keterangan_biaya == 'Hr') { ?>selected<?php } ?>>Perjam</option>
                            <!-- <option value="Hr">An Hour</option> -->
                        </select>
                    </div>
                    
                    <div class="form-group">
                        <label for="newscategory">Tipe Komisi Driver</label>
                        <select class="form-control custom-select  mt-15" name="komisi_tipe" style="width:100%">
                            <!-- <option value="KM">Kilometers</option> -->
                            <option value="1" <?php if ($komisi_tipe == 1) { ?>selected<?php } ?>>Persen</option>
                            <option value="2" <?php if ($komisi_tipe == 2) { ?>selected<?php } ?>>Nominal</option>
                            <!-- <option value="Hr">An Hour</option> -->
                        </select>
                    </div>
                    
                    <div class="form-group">
                        <label for="newstitle">Komisi Driver</label>
                        <input type="text" class="form-control" id="newstitle" name="komisi" value="<?= $komisi ?>" placeholder="ex 10%" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="newscategory">Tipe Komisi Merchant</label>
                        <select class="form-control custom-select  mt-15" name="komisi_mitra_tipe" style="width:100%">
                            <!-- <option value="KM">Kilometers</option> -->
                            <option value="1" <?php if ($komisi_mitra_tipe == 1) { ?>selected<?php } ?>>Persen</option>
                            <option value="2" <?php if ($komisi_mitra_tipe == 2) { ?>selected<?php } ?>>Nominal</option>
                            <!-- <option value="Hr">An Hour</option> -->
                        </select>
                    </div>
                    
                    <div class="form-group">
                        <label for="newstitle">Komisi Merchant</label>
                        <input type="text" class="form-control" id="newstitle" name="komisi_mitra" value="<?= $komisi_mitra ?>" placeholder="ex 10%" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="newscategory">Kendaraan</label>
                        <select class="form-control custom-select  mt-15" name="driver_job" style="width:100%">
                            <?php foreach ($driverjob as $drj) { ?>
                                <option value="<?= $drj['id'] ?>" <?php if ($driver_job == $drj['id']) { ?>selected<?php } ?>><?= $drj['driver_job'] ?></option>
                            <?php } ?>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="newstitle">Min Harga</label>
                        <input type="text"  class="form-control" id="newstitle" name="biaya_minimum" value="<?= rupiah($biaya_minimum) ?>" required>
                    </div>
                    <div class="form-group">
                        <label for="newstitle">Driver Radius</label>
                        <input type="text" class="form-control" id="newstitle" name="jarak_minimum" value="<?= $jarak_minimum ?>" required>
                    </div>
                    <div class="form-group">
                        <label for="newstitle">Maks Jarak Pesanan</label>
                        <input type="text" class="form-control" id="newstitle" name="maks_distance" value="<?= $maks_distance ?>" required>
                    </div>
                    <div class="form-group">
                        <label for="newstitle">Min Saldo</label>
                        <input type="text"  class="form-control" id="newstitle" name="wallet_minimum" value="<?= rupiah($wallet_minimum) ?>" required>
                    </div>
                    
                 
                    
                    <div class="form-group">
                        <label for="newstitle">Deskripsi</label>
                        <input type="text" class="form-control" id="newstitle" name="keterangan" value="<?= $keterangan ?>" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="newscategory">Order Pending</label>
                        <select class="form-control custom-select" name="is_pending" style="width:100%">
                            <!-- <option value="KM">Kilometers</option> -->
                            <option value="1" <?php if ($is_pending == 1) { ?>selected<?php } ?>>Ya</option>
                            <option value="0" <?php if ($is_pending == 0) { ?>selected<?php } ?>>Tidak</option>
                            <!-- <option value="Hr">An Hour</option> -->
                        </select>
                    </div>
                  
                    
                     <div class="form-group">
                        <label for="newstitle">Warna Background : </label><br>
                        <input type="color" id="head" name="background" value="<?= $background ?>" required>
                    </div>

                    <div class="form-group">
                        <label for="newscategory">Status</label>
                        <select class="form-control custom-select  mt-15" name="active" style="width:100%">
                            <option value="0" <?php if ($active == 0) { ?>selected<?php } ?>>Nonactive</option>
                            <option value="1" <?php if ($active == 1) { ?>selected<?php } ?>>Active</option>
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