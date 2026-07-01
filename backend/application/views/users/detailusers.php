<div class="container-fluid">
     <?php if ($this->session->flashdata('ubah')) : ?>
                        <div class="alert alert-success" role="alert">
                            <?php echo $this->session->flashdata('ubah'); ?>
                        </div>
                    <?php endif; ?>
                    <?php if ($this->session->flashdata('demo')) : ?>
                        <div class="alert alert-danger" role="alert">
                            <?php echo $this->session->flashdata('demo'); ?>
                        </div>
                    <?php endif; ?>
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
													<img src="<?= base_url('images/pelanggan/') . $user['fotopelanggan']; ?>" alt="user" class="avatar-img rounded-circle">
												</div>
											</div>
											<div class="media-body">
												<div class="text-white text-capitalize display-6 mb-5 font-weight-400"><?= $user['fullnama'] ?></div>
												<div class="font-14 text-white"><span class="mr-5"><span class="font-weight-500 pr-5">Email</span><span class="mr-5"><?= $user['email'] ?></span></span><span><span class="font-weight-500 pr-5">Kontak</span><span><?= $user['no_telepon'] ?></span></span></div>
											</div>
										</div>
									</div>
									<div class="col-lg-6">
										<div class="button-list">
											<a href="#" class="btn btn-dark btn-wth-icon icon-wthot-bg btn-rounded"><span class="btn-text"><?= $duit ?>
                                <?= rupiah($user['saldo']) ?></span><span class="icon-label"><i class="icon ion-md-wallet"></i> </span></a>
											
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
										<a class="d-flex h-60p align-items-center nav-link" id="avatar-tab" data-toggle="tab" href="#avatar" role="tab" aria-controls="avatar">Profil</a>
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
													<h5 class="card-title">Informasi Customer.</h5>
												</div>
												<div class="card-body">
													<div class="tab-content" id="myTabContent">

												<!-- driver info form -->
												<div class="tab-pane fade show active" id="info" role="tabpanel" aria-labelledby="info">
												<?= form_open_multipart('users/ubahid'); ?>
                                <input type="hidden" name="id" value="<?= $user['id'] ?>">
													<div class="form-group">
														<label for="name">Name</label>
														<input type="text" class="form-control" name="fullnama" value="<?= $user['fullnama'] ?>" required>
													</div>

													<div class="form-group">
														<label for="email">Email</label>
														<input type="email" class="form-control" name="email" value="<?= $user['email'] ?>" required>
													</div>

													<label class="text-small">Phone Number</label>
													<div class="row">
														<div class="form-group col-2">
															<input type="tel" id="txtPhone" class="form-control" name="countrycode" value="<?= $user['countrycode'] ?>" required>
														</div>
														<div class=" form-group col-10">
															<input type="text" class="form-control" id="phone" name="phone" value="<?= $user['phone'] ?>" required>
														</div>
													</div>

													<div class=" form-group">
															<label for="birthdate">Tanggal Lahir</label>
															<input type="date" class="form-control" name="tgl_lahir" value="<?= $user['tgl_lahir'] ?>" required>
														</div>

														<button type="submit" class="btn btn-success mr-2">Perbarui</button>
														<button class="btn btn-outline-danger">Batal</button>
														<?= form_close(); ?>
												</div>
													<!-- tab content ends -->

													<div class="tab-pane fade" id="avatar" role="tabpanel" aria-labelledby="avatar-tab">
        												 <?= form_open_multipart('users/ubahfoto'); ?>
                                                        <input type="hidden" name="id" value="<?= $user['id'] ?>">
                                                        <label for="fotopelanggan">Foto profile</label>
                                                        <input id="uploadProfile" type="file" name="fotopelanggan" class="dropify" onchange="PreviewProfile();" data-max-file-size="1mb" data-default-file="<?= base_url('images/pelanggan/') . $user['fotopelanggan'] ?>" />
                                                         <img id="ProfilePreview" src="<?= base_url('images/pelanggan/') . $user['fotopelanggan'] ?>" style="width: 300px; height: 200px;" />
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
                                                            <button type="submit" class="btn btn-success mr-2">Update</button>
                                                            <button class="btn btn-outline-danger">Cancel</button>
                                                        </div>
                                                        <?= form_close(); ?>
													</div>

													<div class="tab-pane fade" id="security" role="tabpanel" aria-labelledby="security-tab">
													 <?= form_open_multipart('users/ubahpass'); ?>
                                                    <input type="hidden" name="id" value="<?= $user['id'] ?>">
                                                    <div class="form-group">
                                                        <input type="password" class="form-control" id="new-password" name="password" placeholder="Enter you new password" required>
                                                    </div>
                                                    <div class="form-group mt-5">
                                                        <button type="submit" class="btn btn-success mr-2">Update</button>
                                                        <button class="btn btn-outline-danger">Cancel</button>
                                                    </div>
                                                    <?= form_close(); ?>	
													</div>

													<div class="tab-pane fade" id="transactionhistory" role="tabpanel" aria-labelledby="transactionhistory-tab">
														<div class="row">
															<div class="table-responsive">
																	 <table id="tabhistori" class="table table-hover w-100 display pb-30" data-info="false">
																		<thead>
																			<tr>
                    														<th>No.</th>
                                                                            <th>Nota</th>
                                                                            <th>Fitur</th>
                                                                            <th>Tanggal</th>
                                                                            <th>Biaya</th>
                                                                            <th>Status</th>
                                                                            <th>Actions</th>
																			</tr>
																		</thead>
																		<tbody>
																		<?php $i = 1;
                                                                        foreach ($countorder as $tr) { ?>
                                                                            <tr>
                                                                                <td><?= $i ?></td>
                                                                                <td>#INV-<?= $tr['id'] ?></td>
                                                                                <td><?= $tr['fitur'] ?></td>
                                                                                <td><?= $tr['waktu_order'] ?></td>
                                                                                <td class="text-success">
                                                                                    <?= $duit ?>
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
                                                                                    <a href="<?= base_url(); ?>dashboard/detail/<?= $tr['id_transaksi']; ?>" class="btn btn-outline-primary">View</a>
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
                            
                                                                                        <?php if ($wl['type'] == 'topup' or $wl['type'] == 'Order+') { ?>
                                                                                            <td class="text-success">
                                                                                                <?= $duit ?>
                                                                                                <?= rupiah($wl['jumlah']) ?>
                                                                                            </td>
                            
                                                                                        <?php } else { ?>
                                                                                            <td class="text-danger">
                                                                                                <?= $duit ?>
                                                                                                <?= rupiah($wl['jumlah']) ?>
                                                                                            </td>
                                                                                        <?php } ?>
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
																<img src="<?= base_url('images/pelanggan/') . $user['fotopelanggan']; ?>" alt="user" class="avatar-img rounded-circle">
															</div>
														</div>
														<div class="media-body">
															<div class="text-capitalize font-weight-500 text-dark"><?= $user['fullnama'] ?>
														</div>
															<div class="font-13">Customer</div>
														</div>
													</div>
												
												</div>
												<div class="row text-center">
													<div class="col-4 border-right pr-0">
														<div class="pa-15">
															<span class="d-block display-6 text-dark mb-5"><?= count($countorder) ?></span>
															<span class="d-block text-capitalize font-14">Transaksi</span>
														</div>
													</div>
													<div class="col-4 border-right px-0">
														<div class="pa-15">
															<span class="d-block display-6 text-dark mb-5">
															<?php 
																if ($user['response'] == 0) {
																	echo 'Aktif';
																}
																if ($user['response'] == 1) {
																	echo 'Mencari';
																}
																if ($user['response'] == 2) {
																	echo 'Diproses';
																}
																if ($user['response'] == 3) {
																	echo 'Diantar';
																}
																if ($user['response'] == 4) {
																	echo 'Nonaktif';
																}
																if ($user['response'] == 5) {
																	echo 'Log out';
																}
															
															 ?>
															</span>
															<span class="d-block text-capitalize font-14">App Status</span>
														</div>
													</div>
													<div class="col-4 pl-0">
														<div class="pa-15">
															<span class="d-block display-6 text-dark mb-5">
														<?php if ($user['status'] == 1) {
                                                            echo 'Aktif';
                                                        } else {
                                                            echo 'Diblokir';
                                                        } ?>
														</span>
															<span class="d-block text-capitalize font-14">Status</span>
														</div>
													</div>
												</div>
											
											 </div>

											 <a class="btn btn-primary btn-block mb-15" href="<?= base_url(); ?>users">Kembali</a>
										</div>
									</div>
								</div>
							</div>
						</div>	
					</div>
                </div>
                <!-- /Row -->
            </div>
            <script type="text/javascript">
    $(function() {
        var code = "<?= $user['countrycode'] ?>"; // Assigning value from model.
        $('#txtPhone').val(code);
        $('#txtPhone').intlTelInput({
            autoHideDialCode: true,
            autoPlaceholder: "ON",
            dropdownContainer: document.body,
            formatOnDisplay: true,
            hiddenInput: "full_number",
            initialCountry: "auto",
            nationalMode: true,
            placeholderNumberType: "MOBILE",
            preferredCountries: ['US'],
            separateDialCode: false
        });
        console.log(code)
    });
</script>