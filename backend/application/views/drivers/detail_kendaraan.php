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
                    <h4 class="card-title">Detail Kendaraan</h4>
                    <?= form_open_multipart('driver/ubahdatak'); ?>
                   <div class="form-group">
						<label for="brand">Nomor STNK</label>
						<input type="text" class="form-control" name="no_stnk" id="no_stnk" value="<?= $driver['no_stnk'] ?>" required>
					</div>
                    <input id="uploadProfile" type="file" class="dropify" name="foto" onchange="PreviewProfile();" data-max-file-size="3mb" data-default-file="<?= base_url('images/fotoberkas/stnk/') . $driver['foto_stnk'] ?>" /><br>
                        <img id="ProfilePreview" src="<?= base_url('images/fotoberkas/stnk/') . $driver['foto_stnk'] ?>" style="width: 300px; height: 200px;" />
                            <script type="text/javascript">
                                function PreviewProfile() {
                                    var oFReader = new FileReader();
                                    oFReader.readAsDataURL(document.getElementById("uploadProfile").files[0]);
                                                                            
                                        oFReader.onload = function (oFREvent) {
                                        document.getElementById("ProfilePreview").src = oFREvent.target.result;
                                    };
                                };
                                                                            
                    </script>
                        
                    <div class="form-group">
                     
						<label for="Job Service">Kendaraan</label>
							<select class="form-control custom-select" name="jenis" style="width:100%">\
							<?php foreach ($driverjob as $drj) { ?>
								<option value="<?= $drj['id'] ?>" <?php if ($driver['jenis'] == $drj['id']) { ?>selected<?php } ?>><?= $drj['driver_job'] ?></option>
							<?php } ?>
						</select>
					</div>
					<div class="form-group">
						<label for="brand">Merk Kendaraan</label>
						<input type="text" class="form-control" name="merek" id="brand" value="<?= $driver['merek'] ?>" required>
					</div>
					<div class="form-group">
						<label for="variantvehicle">Tipe Kendaraan</label>
						<input type="text" class="form-control" name="tipe" id="variantvehicle" value="<?= $driver['tipe'] ?>" required>
					</div>
					<div class="form-group">
						<label for="vehiclecolor">Warna Kendaraan</label>
						<input type="text" class="form-control" name="warna" id="vehiclecolor" value="<?= $driver['warna'] ?>" required>
					</div>
					<div class="form-group">
					<label for="vehicleregistration">Plat Nomor</label>
						<input type="text" class="form-control" name="nomor_kendaraan" id="vehicleregistration" value="<?= $driver['nomor_kendaraan'] ?>" required>
					</div>
					<div class="form-group mt-5">
					    <input type="hidden" name="id" value=<?= $driver['id_k'] ?>>
                         <input type="hidden" name="id_driver" value=<?= $driver['id_driver'] ?>>
						<button type="submit" class="btn btn-success mr-2">Perbarui</button>
						<button class="btn btn-outline-danger">Batal</button>
						
					</div>
					<?= form_close(); ?>
                </div>
            </div>
        </div>
    </div>
</div>