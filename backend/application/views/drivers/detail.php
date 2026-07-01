<div class="container-fluid">
                <!-- Row -->
                <div class="row">
                    <div class="col-xl-12 pa-0">
                        <div class="profile-cover-wrap overlay-wrap">
                            <div class="profile-cover-img" style="background-image:url('<?= base_url(); ?>dist/img/bgprofil.jpg')"></div>
							<div class="bg-overlay bg-trans-dark-60"></div>
							<div class="container-fluid px-xxl-65 px-xl-20 profile-cover-content py-50">
								<div class="row"> 
									<div class="col-lg-6">
										<div class="media align-items-center">
											<div class="media-img-wrap  d-flex">
												<div class="avatar">
													<img src="<?= base_url('images/fotodriver/') . $driver['foto'] ?>" alt="user" class="avatar-img rounded-circle">
												</div>
											</div>
											<div class="media-body">
												<div class="text-white text-capitalize display-6 mb-5 font-weight-400"><?= $driver['nama_driver'] ?><?php if ($driver['gender'] == 2) { ?>
                                    <i class="mdi mdi-gender-male text-info"></i>
                                <?php } else { ?>
                                    <i class="mdi mdi-gender-female text-info"></i>
                                <?php } ?></div>
												<div class="font-14 text-white"><span class="mr-5"><span class="font-weight-500 pr-5">Email</span><span class="mr-5"><?= $driver['email'] ?></span></span><span><span class="font-weight-500 pr-5">Kontak</span><span><?= $driver['no_telepon'] ?></span></span></div>
											</div>
										</div>
									</div>
									<div class="col-lg-6">
										<div class="button-list">
											<a href="#" class="btn btn-dark btn-wth-icon icon-wthot-bg btn-rounded"><span class="btn-text"><?= $currency['app_currency'] ?>
                                <?= rupiah($driver['saldo']) ?></span><span class="icon-label"><i class="icon ion-md-wallet"></i> </span></a>
											
										</div>
									</div>
								</div>
							</div>
						</div>
                        <div class="bg-white shadow-bottom">
							<div class="container-fluid px-xxl-65 px-xl-20">
								<ul class="nav nav-light nav-tabs" role="tablist">
									<li class="nav-item">
										<a class="d-flex h-60p align-items-center nav-link active" id="info-tab" data-toggle="tab" href="#info" role="tab" aria-controls="info" aria-expanded="true">Info</a>
									</li>
									<li class="nav-item">
										<a class="d-flex h-60p align-items-center nav-link" id="service-tab" data-toggle="tab" href="#job" role="tab" aria-controls="service">Job & Kendaraan</a>
									</li>
									<li class="nav-item">
										<a class="d-flex h-60p align-items-center nav-link" id="avatar-tab" data-toggle="tab" href="#avatar" role="tab" aria-controls="avatar">Profil</a>
									</li>
									<li class="nav-item">
										<a class="d-flex h-60p align-items-center nav-link" id="files-tab" data-toggle="tab" href="#files" role="tab" aria-controls="files">Berkas</a>
									</li>
									<li class="nav-item">
										<a class="d-flex h-60p align-items-center nav-link" id="security-tab" data-toggle="tab" href="#security" role="tab" aria-controls="security">Password</a>
									</li>
									<li class="nav-item">
										<a class="d-flex h-60p align-items-center nav-link" id="transactionhistory-tab" data-toggle="tab" href="#transactionhistory" role="tab" aria-controls="transactionhistory">Transaksi</a>
									</li>
									<li class="nav-item">
										<a class="d-flex h-60p align-items-center nav-link" id="wallet-tab" data-toggle="tab" href="#wallet" role="tab" aria-controls="wallet">Saldo</a>
									</li>
								</ul>
							</div>	
						</div>	
						<div class="tab-content mt-sm-60 mt-30">
							<div class="tab-pane fade show active" role="tabpanel">
								<div class="container-fluid px-xxl-65 px-xl-20">
									<div class="row">
										<div class="col-lg-8">
											<div class="card card-profile-feed">
                                                <div class="card-header card-header-action">
													<h5 class="card-title">Informasi Driver.</h5>
												</div>
												<div class="card-body">
													<div class="tab-content" id="myTabContent">

												<!-- driver info form -->
												<div class="tab-pane fade show active" id="info" role="tabpanel" aria-labelledby="info">
													<?= form_open_multipart('driver/ubahid'); ?>
													<input type="hidden" name="id" value="<?= $driver['id'] ?>">
													<div class="form-group">
														<label for="name">Name</label>
														<input type="text" class="form-control" name="nama_driver" value="<?= $driver['nama_driver'] ?>" required>
													</div>

													<div class="form-group">
														<label for="email">Email</label>
														<input type="email" class="form-control" name="email" value="<?= $driver['email'] ?>" required>
													</div>

													<label class="text-small">Phone Number</label>
													<div class="row">
														<div class="form-group col-2">
															<input type="tel" id="txtPhone" class="form-control" name="countrycode" value="<?= $driver['countrycode'] ?>" required>
														</div>
														<div class=" form-group col-10">
															<input type="text" class="form-control" id="phone" name="phone" value="<?= $driver['phone'] ?>" required>
														</div>
													</div>

													<div class=" form-group">
															<label for="birthdate">Tanggal Lahir</label>
															<input type="date" class="form-control" name="tgl_lahir" value="<?= $driver['tgl_lahir'] ?>" required>
														</div>
														<div class="form-group">
															<label for="gender">J.Kelamin</label>
															<select class="form-control custom-select" style="width:100%" name="gender">
																<option value="Male" <?php if ($driver['gender'] == 'Male') { ?>selected<?php } ?>>Laki-laki</option>
																<option value="Female" <?php if ($driver['gender'] == 'Female') { ?>selected<?php } ?>>Perempuan</option>
															</select>
														</div>
														<div class="form-group">
															<label for="address">Alamat</label>
															<textarea name="alamat_driver" rows="6" class="form-control" required><?= $driver['alamat_driver'] ?></textarea>
														</div>
														<button type="submit" class="btn btn-success mr-2">Perbarui</button>
														<button class="btn btn-outline-danger">Batal</button>
														<?= form_close(); ?>
												</div>
													<!-- tab content ends -->

													<!-- jjob vehicle form -->
													<div class="tab-pane fade" id="job" role="tabpanel" aria-labelledby="service">
														<?= form_open_multipart('driver/ubahkendaraan'); ?>
														<input type="hidden" name="id" value="<?= $driver['id'] ?>">
													
														
														<div class="form-group">
														        <label for="Job Service">Job Custom</label>
    														<?php foreach($driverjob as $djx) {
    														        $checked = in_array($djx['id'], $jobselected) ? "checked" : "";?>
        														    <div class="form-check form-check form-check-primary">
                                                                        <input type="checkbox" value="<?= $djx['id'] ?>" <?= $checked ?> name="job[]">
                                                                        <label class="form-check-label"><?= $djx['driver_job']; ?></label>
                                                                    </div>
    														<?php } ?>
														</div>
													
									<div class="form-group">
															<label for="Job Service">Kendaraan</label>
															<select class="form-control custom-select" name="jenis" style="width:100%">\
																<?php foreach ($driverjob as $drj) { ?>
																	<option value="<?= $drj['id'] ?>" <?php if ($driver['job'] == $drj['id']) { ?>selected<?php } ?>><?= $drj['driver_job'] ?></option>
																<?php } ?>
															</select>
														</div>
														
														<div class="form-group mt-5">
															<button type="submit" class="btn btn-success mr-2">Perbarui</button>
															<button class="btn btn-outline-danger">Batal</button>
														</div>
														<?= form_close(); ?>
															<div class="row">
															<div class="table-responsive">
																	 <table id="tabhistori" class="table table-hover w-100 display pb-30" data-info="false">
																		<thead>
																			<tr>
																				<th>No</th>
																				<th>No STNK</th>
																				<th>Merk</th>
																				<th>Tipe</th>
																				<th>Warna</th>
																				<th>Plat</th>
																				<th>Actions</th>
																			</tr>
																		</thead>
																		<tbody>
																			<?php $i = 1;
																			foreach ($kendaraan as $tr) { ?>
																				<tr>
																					<td><?= $i ?></td>
																					<td><?= $tr['no_stnk'] ?></td>
																					<td><?= $tr['merek'] ?></td>
																					<td><?= $tr['tipe'] ?></td>
																					<td><?= $tr['warna'] ?></td>
																					<td><?= $tr['nomor_kendaraan'] ?></td>
																				
																					<td>
																						<a href="<?= base_url(); ?>driver/detailk/<?= $tr['id_k']; ?>" class="btn btn-outline-primary">Lihat</a>
																					</td>
																				<?php $i++;
																			} ?>
																				</tr>

																		</tbody>
																	</table>
																</div>
														</div>
													</div>
													<!-- tab content ends -->

													<div class="tab-pane fade" id="avatar" role="tabpanel" aria-labelledby="avatar-tab">
														<?= form_open_multipart('driver/ubahfoto'); ?>
														<input type="hidden" name="id" value="<?= $driver['id'] ?>">
														<label>Foto Driver</label>
                                                        <input id="uploadProfile" type="file" class="dropify" name="foto" onchange="PreviewProfile();" data-max-file-size="3mb" data-default-file="<?= base_url('images/fotodriver/') . $driver['foto'] ?>" /><br>
                                                         <img id="ProfilePreview" src="<?= base_url('images/fotodriver/') . $driver['foto'] ?>" style="width: 300px; height: 200px;" />
                                                                               <script type="text/javascript">
                                                                                function PreviewProfile() {
                                                                                    var oFReader = new FileReader();
                                                                                    oFReader.readAsDataURL(document.getElementById("uploadProfile").files[0]);
                                                                            
                                                                                    oFReader.onload = function (oFREvent) {
                                                                                        document.getElementById("ProfilePreview").src = oFREvent.target.result;
                                                                                    };
                                                                                };
                                                                            
                                                                            </script>
														<div class="form-group mt-5">
															<button type="submit" class="btn btn-success mr-2">Perbarui</button>
															<button class="btn btn-outline-danger">Batal</button>
														</div>
														<?= form_close(); ?>
													</div>

													<div class="tab-pane fade" id="files" role="tabpanel" aria-labelledby="files-tab">

														<?= form_open_multipart('driver/ubahcard'); ?>

														<input type="hidden" name="id" value="<?= $driver['id'] ?>">

														<div class="form-group">
															<label for="idcard">No KTP</label>
															<input type="text" class="form-control" name="no_ktp" value="<?= $driver['no_ktp'] ?>" required>
														</div>

														<div>
															<label>Foto KTP</label>
															<input id="uploadKTP" type="file" class="dropify" name="foto_ktp" onchange="PreviewKtp();" data-max-file-size="3mb" data-default-file="<?= base_url('images/fotoberkas/ktp/') . $driver['foto_ktp'] ?>" required /><br>
                                                             <img id="KtpPreview" src="<?= base_url('images/fotoberkas/ktp/') . $driver['foto_ktp'] ?>" style="width: 300px; height: 200px;" />
                                                           <script type="text/javascript">
                                                            function PreviewKtp() {
                                                                var oFReader = new FileReader();
                                                                oFReader.readAsDataURL(document.getElementById("uploadKTP").files[0]);
                                                        
                                                                oFReader.onload = function (oFREvent) {
                                                                    document.getElementById("KtpPreview").src = oFREvent.target.result;
                                                                };
                                                            };
                                                        
                                                        </script>
															<br>
														</div>
														<div class="form-group">
															<label for="idcard">No SIM</label>
															<input type="text" class="form-control" name="id_sim" value="<?= $driver['id_sim'] ?>" required>
														</div>
														<div>
															<label>Foto SIM</label>
															 <input id="uploadSIM" type="file" class="dropify" name="foto_sim" onchange="PreviewSIM();" data-max-file-size="3mb" data-default-file="<?= base_url('images/fotoberkas/sim/') . $driver['foto_sim'] ?>" required /><br>
                                                                 <img id="SimPreview" src="<?= base_url('images/fotoberkas/sim/') . $driver['foto_sim'] ?>" style="width: 300px; height: 200px;" />
                                                               <script type="text/javascript">
                                                                function PreviewSIM() {
                                                                    var oFReader = new FileReader();
                                                                    oFReader.readAsDataURL(document.getElementById("uploadSIM").files[0]);
                                                            
                                                                    oFReader.onload = function (oFREvent) {
                                                                        document.getElementById("SimPreview").src = oFREvent.target.result;
                                                                    };
                                                                };
                                                            
                                                            </script>
														</div>

														<div class="form-group mt-5">
															<button type="submit" class="btn btn-success mr-2">Perbarui</button>
															<button class="btn btn-outline-danger">Batal</button>
														</div>
														<?= form_close(); ?>
													</div>

													<div class="tab-pane fade" id="security" role="tabpanel" aria-labelledby="security-tab">
														<?= form_open_multipart('driver/ubahpassword'); ?>
														<div class="form-group">
															<input type="hidden" name="id" value="<?= $driver['id'] ?>">
															<label for="new-password">Ubah password</label>
															<input type="password" class="form-control" id="new-password" name="password" placeholder="Enter you new password" required>
														</div>
														<div class="form-group mt-5">
															<button type="submit" class="btn btn-success mr-2">Perbarui</button>
															<button class="btn btn-outline-danger">Batal</button>
														</div>
														<?= form_close(); ?>
													</div>

													<div class="tab-pane fade" id="transactionhistory" role="tabpanel" aria-labelledby="transactionhistory-tab">
														<div class="row">
															<div class="table-responsive">
																	 <table id="tabhistori" class="table table-hover w-100 display pb-30" data-info="false">
																		<thead>
																			<tr>
																				<th>No</th>
																				<th>Nota</th>
																				<th>Layanan</th>
																				<th>Tanggal</th>
																				<th>Jumlah</th>
																				<th>Status</th>
																				<th>Actions</th>
																			</tr>
																		</thead>
																		<tbody>
																			<?php $i = 1;
																			foreach ($transaksi as $tr) { ?>
																				<tr>
																					<td><?= $i ?></td>
																					<td>#INV-<?= $tr['id'] ?></td>
																					<td><?= $tr['fitur'] ?></td>
																					<td><?= $tr['waktu_order'] ?></td>
																					<td class="text-success">
																						<?= $currency['app_currency'] ?>
																						<?= rupiah($tr['biaya_akhir']) ?>
																					</td>
																					<td>
																						<?php if ($tr['status'] == '2') { ?>
																							<label class="badge badge-primary"><?= $tr['status_transaksi']; ?></label>
																						<?php } ?>
																						<?php if ($tr['status'] == '3') { ?>
																							<label class="badge badge-success"><?= $tr['status_transaksi']; ?></label>
																						<?php } ?>
																						<?php if ($tr['status'] == '5') { ?>
																							<label class="badge badge-danger"><?= $tr['status_transaksi']; ?></label>
																						<?php } ?>
																						<?php if ($tr['status'] == '4') { ?>
																							<label class="badge badge-info"><?= $tr['status_transaksi']; ?></label>
																						<?php } ?>
																					</td>
																					<td>
																						<a href="<?= base_url(); ?>dashboard/detail/<?= $tr['id_transaksi']; ?>" class="btn btn-outline-primary">Lihat</a>
																					</td>
																				<?php $i++;
																			} ?>
																				</tr>

																		</tbody>
																	</table>
																</div>
														</div>

													</div>

													<div class="tab-pane fade" id="wallet" role="tabpanel" aria-labelledby="wallet-tab">
														<div class="row">
															<div class="col-12">
																<div class="table-responsive">
																	<table id="tabwallet" class="table table-hover w-100 display pb-30" data-info="false">
																		<thead>
																			<tr>
																				<th>No.</th>
																				<th>Id</th>
																				<th>Tipe</th>
																				<th>Tanggal</th>
																				<th>Jumlah</th>
																				<th>Tujuan/Dari</th>
																			</tr>
																		</thead>
																		<tbody>
																			<?php $i = 1;
																			foreach ($wallet as $wl) { ?>
																				<tr>
																					<td><?= $i ?></td>
																					<td><?= $wl['id']; ?></td>
																					<td><?= $wl['type']; ?></td>
																					<td><?= $wl['waktu']; ?></td>
																					<?php if ($wl['type'] == 'topup' or $wl['type'] == 'Order+' or $wl['type'] == 'Transfer+') { ?>
																						<td class="text-success">
																							<?= $currency['app_currency'] ?>
																							<?= rupiah($wl['jumlah']) ?>
																						</td>
																					<?php } else { ?>
																						<td class="text-danger">
																							<?= $currency['app_currency'] ?>
																							<?= rupiah($wl['jumlah']) ?>
																						</td>
																					<?php } ?>
                                                                                    <td><?= $wl['tujuan']; ?></td>
																				</tr>
																			<?php $i++;
																			} ?>
																		</tbody>
																	</table>
																</div>
															</div>
														</div>

													</div>
												</div>
											</div>
											
                                            </div>
											
											
										</div>
										<div class="col-lg-4">
											<div class="card card-profile-feed">
                                                <div class="card-header card-header-action">
													<div class="media align-items-center">
														<div class="media-img-wrap d-flex mr-10">
															<div class="avatar avatar-sm">
																<img src="<?= base_url('images/fotodriver/') . $driver['foto'] ?>" alt="user" class="avatar-img rounded-circle">
															</div>
														</div>
														<div class="media-body">
															<div class="text-capitalize font-weight-500 text-dark"><?= $driver['nama_driver'] ?>
															<?php if ($driver['gender'] == 2) { ?>
																<i class="mdi mdi-gender-male text-info"></i>
															<?php } else { ?>
																<i class="mdi mdi-gender-female text-info"></i>
															<?php } ?></div>
															<div class="font-13">Driver</div>
														</div>
													</div>
												
												</div>
												<div class="row text-center">
													<div class="col-4 border-right pr-0">
														<div class="pa-15">
															<span class="d-block display-6 text-dark mb-5"><?= $countorder ?></span>
															<span class="d-block text-capitalize font-14">Transaksi</span>
														</div>
													</div>
													<div class="col-4 border-right px-0">
														<div class="pa-15">
															<span class="d-block display-6 text-dark mb-5"><?= rupiah($driver['rating'], 1) ?></span>
															<span class="d-block text-capitalize font-14">Rating</span>
														</div>
													</div>
													<div class="col-4 pl-0">
														<div class="pa-15">
															<span class="d-block display-6 text-dark mb-5"><?php if ($driver['status'] == 3) {
																echo 'Banned';
															} elseif ($driver['status'] == 0) {
																echo 'Baru';
															} else {
																if ($driver['status_job'] == 1) {
																	echo 'Aktif';
																}
																if ($driver['status_job'] == 2) {
																	echo 'Memproses';
																}
																if ($driver['status_job'] == 3) {
																	echo 'Pengantaran';
																}
																if ($driver['status_job'] == 4) {
																	echo 'Nonaktif';
																}
																if ($driver['status_job'] == 5) {
																	echo 'Log out';
																}
															} ?></span>
															<span class="d-block text-capitalize font-14">Status</span>
														</div>
													</div>
												</div>
												<ul class="list-group list-group-flush">
                                                    <li class="list-group-item"><span><i class="ion ion-md-calendar font-18 text-light-20 mr-10"></i><span>Tgl Lahir:</span></span><span class="ml-5 text-dark"><?= $driver['tgl_lahir'] ?></span></li>
                                                    <li class="list-group-item"><span><i class="ion ion-md-briefcase font-18 text-light-20 mr-10"></i><span>Job:</span></span><span class="ml-5 text-dark"><?= $driver['driver_job'] ?></span></li>
                                                    <li class="list-group-item"><span><i class="ion ion-md-briefcase font-18 text-light-20 mr-10"></i><span>Kendaraan:</span></span><span class="ml-5 text-dark"><?= $driver['merek'] ?>
													<?= $driver['tipe'] ?>
													<?= $driver['warna'] ?></span></li>
													<li class="list-group-item"><span><i class="ion ion-md-briefcase font-18 text-light-20 mr-10"></i><span>Plat Nomor:</span></span><span class="ml-5 text-dark"><?= $driver['nomor_kendaraan'] ?></span></li>
                                                    <li class="list-group-item"><span><i class="ion ion-md-pin font-18 text-light-20 mr-10"></i><span>Alamat:</span></span><span class="ml-5 text-dark"><?= $driver['alamat_driver'] ?></span></li>
                                                </ul>
											 </div>

											 <a class="btn btn-primary btn-block mb-15" href="<?= base_url(); ?>driver">Kembali</a>
										</div>
									</div>
								</div>
							</div>
						</div>	
					</div>
                </div>
                <!-- /Row -->
            </div>