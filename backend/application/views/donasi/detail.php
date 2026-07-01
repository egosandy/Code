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
													<img src="<?= base_url('images/promo/') . $gambar ?>" alt="user" class="avatar-img rounded-circle">
												</div>
											</div>
											<div class="media-body">
												<div class="text-white text-capitalize display-6 mb-5 font-weight-400"><?= $nama_lembaga ?></div>
												<div class="font-14 text-white"><span class="mr-5"><span class="font-weight-500 pr-5">Phone</span><span class="mr-5"><?= $phone ?></span></span><span><span class="font-weight-500 pr-5">Kontak</span><span><?= $phone ?></span></span></div>
											</div>
										</div>
									</div>
									<div class="col-lg-6">
										<div class="button-list">
											<a href="#" class="btn btn-dark btn-wth-icon icon-wthot-bg btn-rounded"><span class="btn-text"><?= $total ?></span><span class="icon-label"><i class="icon ion-md-wallet"></i> </span></a>
											
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
										<a class="d-flex h-60p align-items-center nav-link" id="donatur-tab" data-toggle="tab" href="#donatur" role="tab" aria-controls="service">Donatur</a>
									</li>
									<li class="nav-item">
										<a class="d-flex h-60p align-items-center nav-link" id="wd-tab" data-toggle="tab" href="#wd" role="tab" aria-controls="avatar">Withdraw</a>
									</li>
								
								</ul>
							</div>	
						</div>	
						<div class="tab-content mt-sm-60 mt-30">
							<div class="tab-pane fade show active" role="tabpanel">
								<div class="container-fluid px-xxl-65 px-xl-20">
									<div class="row">
										<div class="col-lg-12">
											<div class="card card-profile-feed">
                                                <div class="card-header card-header-action">
													<h5 class="card-title">Informasi Donasi</h5>
												</div>
												<div class="card-body">
													<div class="tab-content" id="myTabContent">

    												<!-- driver info form -->
    												<div class="tab-pane fade show active" id="info" role="tabpanel" aria-labelledby="info">
    									
    													<input type="hidden" name="id" value="">
    													<div class="form-group">
    														<label for="name">Nama Lembaga</label>
    														<input type="text" class="form-control" name="lembaga" value="<?= $nama_lembaga ?>" readonly>
    													</div>
    													
    													<div class="form-group">
    														<label for="name">Alamat</label>
    														<input type="text" class="form-control" name="judul" value="<?= $alamat ?>" readonly>
    													</div>
    													
    													<div class="form-group">
    														<label for="name">Telepon</label>
    														<input type="text" class="form-control" name="judul" value="<?= $phone ?>" readonly>
    													</div>
    													
    													<div class="form-group">
    														<label for="name">Judul</label>
    														<input type="text" class="form-control" name="judul" value="<?= $judul ?>" readonly>
    													</div>
    
    													<div class="form-group">
    														<label for="email">Deskripsi</label>
    														<input type="text" rows="5" class="form-control" name="email" value="<?= $deskripsi ?>" readonly>
    													</div>
    
    												
    								                </div>
												
													<div class="tab-pane fade" id="donatur" role="tabpanel" aria-labelledby="donatur-tab">
														<div class="row">
															<div class="col-12">
																<div class="table-responsive">
																	<table id="tabwallet" class="table table-hover w-100 display pb-30" data-info="false">
																		<thead>
																			<tr>
																				<th>No.</th>
																				<th>Id</th>
																				<th>Nama</th>
																				<th>Jumlah</th>
																				<th>Waktu</th>
																			
																			</tr>
																		</thead>
																		<tbody>
																			<?php $i = 1;
																			foreach ($donatur as $wl) { 
																			     if($wl->masuk > 0) { ?>
																				<tr>
																					<td><?= $i ?></td>
																					<td><?= $wl->invoice; ?></td>
																					<td><?= $wl->nama_pengirim; ?></td>
																					<td><?= rupiah($wl->masuk); ?></td>
                                                                                    <td><?= $wl->regtime; ?></td>
																				</tr>
																			<?php $i++;
																			     }
																			} ?>
																		</tbody>
																	</table>
																</div>
															</div>
														</div>

													</div>
												
													<!-- tab content ends -->

													<div class="tab-pane fade" id="wd" role="tabpanel" aria-labelledby="wd-tab">
													    <div class="row">
													        <div class="col-4">
        													            <?= form_open_multipart('donasi/withdraw'); ?>
        
                														<input type="hidden" name="id" value="<?= $id ?>">
                														
                														<div class="form-group">
                    														<label for="name">Jumlah disalurkan</label>
                    														<input type="number" class="form-control" name="jumlah" placeholder="0"  required>
                    													</div>
                    													
                    													<div class="form-group">
                    														<label for="name">Keterangan</label>
                    														<input type="text" class="form-control" name="keterangan" placeholder="enter keterangan"  required>
                    													</div>
                
                														<div>
                															<label>Foto </label>
                															<input id="uploadKTP" type="file" class="dropify" name="foto" onchange="PreviewKtp();" data-max-file-size="3mb" required /><br>
                                                                             <img id="KtpPreview"  style="width: 300px; height: 200px;" />
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
                													
                													
                
                														<div class="form-group mt-5">
                															<button type="submit" class="btn btn-success mr-2">Submit</button>
                															<button class="btn btn-outline-danger">Batal</button>
                														</div>
                														<?= form_close(); ?>
													        </div>
													        <div class="col-8">
													            	<div class="table-responsive">
																	<table id="tabwallet" class="table table-hover w-100 display pb-30" data-info="false">
																		<thead>
																			<tr>
																				<th>No.</th>
																				<th>Foto</th>
																				<th>Id</th>
																				<th>Jumlah</th>
																				<th>Waktu</th>
																			    <th>User</th>
																			</tr>
																		</thead>
																		<tbody>
																			<?php $i = 1;
																			foreach ($wd as $wx) { ?>
																			  
																			    
																				<tr>
																					<td><?= $i ?></td>
																					<td><img width="80" height="80" src="<?= base_url('images/promo/') . $wx->foto; ?>"></td>
																					<td><?= $wx->invoice; ?></td>
																					<td><?= rupiah($wx->jumlah); ?></td>
                                                                                    <td><?= $wx->regtime; ?></td>
                                                                                    <td><?= $wx->regalias; ?></td>
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
										
									</div>
								</div>
							</div>
						</div>	
					</div>
                </div>
                <!-- /Row -->
            </div>