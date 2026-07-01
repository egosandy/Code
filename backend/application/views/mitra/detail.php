<!-- partial -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?sensor=true&libraries=places">
</script>
<script type="text/javascript" src='https://maps.google.com/maps/api/js?key=<?= google_maps_api ?>&sensor=false&libraries=places'></script>
<script src="<?= base_url(); ?>asset/js/locationpicker.jquery.js"></script>
<div class="container-fluid">
     <?php if ($this->session->flashdata('demo') or $this->session->flashdata('hapus') or $this->session->flashdata('gagal')) : ?>
                        <div class="alert alert-danger" role="alert">
                            <?php echo $this->session->flashdata('demo'); ?>
                            <?php echo $this->session->flashdata('hapus'); ?>
                            <?php echo $this->session->flashdata('gagal'); ?>
                        </div>
                    <?php endif; ?>
                    <?php if ($this->session->flashdata('tambah') or $this->session->flashdata('ubah')) : ?>
                        <div class="alert alert-danger" role="alert">
                            <?php echo $this->session->flashdata('tambah'); ?>
                            <?php echo $this->session->flashdata('ubah'); ?>
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
													<img src="<?= base_url('images/merchant/') . $mitra['foto_merchant'] ?>" alt="user" class="avatar-img rounded-circle">
												</div>
											</div>
											<div class="media-body">
												<div class="text-white text-capitalize display-6 mb-5 font-weight-400"><?= $mitra['nama_mitra'] ?></div>
												<div class="font-14 text-white"><span class="mr-5"><span class="font-weight-500 pr-5">Email</span><span class="mr-5"><?= $mitra['email_mitra'] ?></span></span><span><span class="font-weight-500 pr-5">Kontak</span><span><?= $mitra['telepon_mitra'] ?></span></span></div>
											</div>
										</div>
									</div>
									<div class="col-lg-6">
										<div class="button-list">
											<a href="#" class="btn btn-dark btn-wth-icon icon-wthot-bg btn-rounded"><span class="btn-text"><?= $currency['app_currency'] ?>
                                <?= rupiah($mitra['saldo']) ?></span><span class="icon-label"><i class="icon ion-md-wallet"></i> </span></a>
											
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
										<a class="d-flex h-60p align-items-center nav-link" id="owner-tab" data-toggle="tab" href="#owner" role="tab" aria-controls="owner">Profil</a>
									</li>
									<li class="nav-item">
										<a class="d-flex h-60p align-items-center nav-link" id="identitas-tab" data-toggle="tab" href="#identitas" role="tab" aria-controls="identitas">Identitas</a>
									</li>
										<li class="nav-item">
										<a class="d-flex h-60p align-items-center nav-link" id="pass-tab" data-toggle="tab" href="#pass" role="tab" aria-controls="pass">Ubah Password</a>
									</li>
									<li class="nav-item">
										<a class="d-flex h-60p align-items-center nav-link" id="kategori-tab" data-toggle="tab" href="#kategori" role="tab" aria-controls="identitas">Kategori</a>
									</li>
									<li class="nav-item">
										<a class="d-flex h-60p align-items-center nav-link" id="menu-tab" data-toggle="tab" href="#menu" role="tab" aria-controls="menu">Daftar Menu</a>
									</li>
									<li class="nav-item">
										<a class="d-flex h-60p align-items-center nav-link" id="addmenu-tab" data-toggle="tab" href="#addmenu" role="tab" aria-controls="addmenu">Tambah Menu</a>
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
													<h5 class="card-title">Informasi Mitra.</h5>
												</div>
												<div class="card-body">
													<div class="tab-content" id="myTabContent">

  <!----------------------------------------------------------------------------------------- Tab Info ---------------------------------------------------------------------------------------------->
    												<div class="tab-pane fade show active" id="info" role="tabpanel" aria-labelledby="info">
    											         <?= form_open_multipart('mitra/ubahmerchant/' . $mitra['id_mitra']); ?>
                                                        <input type="hidden" name="id_merchant" value='<?= $mitra['id_merchant'] ?>'>
            
            
                                                        <div class="form-group">
                                                            
                                                                <input id="uploadProfile" type="file" class="dropify" name="foto_merchant" onchange="PreviewProfile();" data-default-file="<?= base_url('images/merchant/') . $mitra['foto_merchant'] ?>" data-max-file-size="3mb" required /><br>
                                                                 <img id="ProfilePreview" src="<?= base_url('images/merchant/') . $mitra['foto_merchant'] ?>" style="width: 300px; height: 200px;" />
                                                               <script type="text/javascript">
                                                                function PreviewProfile() {
                                                                    var oFReader = new FileReader();
                                                                    oFReader.readAsDataURL(document.getElementById("uploadProfile").files[0]);
                                                            
                                                                    oFReader.onload = function (oFREvent) {
                                                                        document.getElementById("ProfilePreview").src = oFREvent.target.result;
                                                                    };
                                                                };
                                                            
                                                            </script>
                                                        </div>
            
            
                                                        <div class="form-group row">
                                                            <div class="col-lg-3">
                                                                <label class=mt-2 for="name">Merchant Name</label>
                                                            </div>
                                                            <div class="col-lg-9">
                                                                <input type="text" class="form-control" id="name" name="nama_merchant" value="<?= $mitra['nama_merchant'] ?>" required>
                                                            </div>
                                                        </div>
                                                        <div class="form-group row">
                                                            <div class="col-lg-3">
                                                                <label class=mt-2 for="ftr">Service</label>
                                                            </div>
                                                            <div class="col-lg-9">
                                                                <select class=" form-control custom-select  mt-15 fiturService" style="width:100%" name="id_fitur">
                                                                    <?php foreach ($fitur as $ftr) { ?>
                                                                        <option id="<?= $ftr['fitur'] ?>" value="<?= $ftr['id_fitur'] ?>" <?php if ($mitra['id_fitur'] == $ftr['id_fitur']) { ?>selected<?php } ?>><?= $ftr['fitur'] ?></option>
                                                                    <?php } ?>
                                                                </select>
                                                            </div>
                                                        </div>
            
                                                        <div class="form-group row">
                                                            <div class="col-lg-3">
                                                                <label class=mt-2 for="ftr">Category Service</label>
                                                            </div>
                                                            <div class="col-lg-9">
                                                                <select class="form-control custom-select  mt-15" style="width:100%" name="category_merchant">
                                                                    <?php foreach ($merchantk as $mck) { ?>
                                                                        <option value="<?= $mck['id_kategori_merchant'] ?>" <?php if ($mck['id_kategori_merchant'] == $mitra['category_merchant']) { ?>selected<?php } ?>><?= $mck['nama_kategori'] ?></option>
                                                                    <?php
                                                                    } ?>
                                                                </select>
                                                            </div>
                                                        </div>
            
            
            
                                                        <div class="form-group">
                                                            <label>Address</label>
                                                            <input type="text" class="form-control" name="alamat_merchant" id="us3-address" />
                                                        </div>
                                                        <div class="form-group">
                                                            <div id="us3" style="width: 100%; height: 400px;"></div>
                                                        </div>
                                                        <div class="form-group row">
                                                            <div class="col">
                                                                <label>Latitude</label>
                                                                <input type="text" name="latitude_merchant" id="us3-lat" class="form-control">
                                                            </div>
                                                            <div class="col">
                                                                <label>Longitude</label>
                                                                <input type="text" name="longitude_merchant" id="us3-lon" class="form-control">
                                                            </div>
                                                        </div>
                                                        <div class="form-group row">
                                                            <div class="col-lg-3">
                                                                <label class=mt-2 for="op">Open</label>
                                                            </div>
                                                            <div class="col-lg-9">
                                                                <input type="time" class="form-control" id="op" name="jam_buka" value="<?= $mitra['jam_buka'] ?>" required>
                                                            </div>
                                                        </div>
                                                        <div class="form-group row">
                                                            <div class="col-lg-3">
                                                                <label class=mt-2 for="cl">Close</label>
                                                            </div>
                                                            <div class="col-lg-9">
                                                                <input type="time" class="form-control" id="cl" name="jam_tutup" value="<?= $mitra['jam_tutup'] ?>" required>
                                                            </div>
                                                        </div>
                                                        <div class="form-group row">
                                                            <div class="col-lg-10">
                                                            </div>
                                                            <div class="col-lg-2">
                                                                <button type="submit" class="btn btn-success ">Update</button>
                                                            </div>
                                                        </div>
            
            
                                                        <?= form_close(); ?>
    												</div>
<!----------------------------------------------------------------------------------------------- tab owner ------------------------------------------------------------------------------------------->

													<div class="tab-pane fade" id="owner" role="tabpanel" aria-labelledby="owner-tab">
        												<?= form_open_multipart('mitra/editmitradetail'); ?>
                                                            <input type="hidden" class="form-control" name="id_mitra" value="<?= $mitra['id_mitra'] ?>">
                                                            <input type="hidden" class="form-control" name="id_merchant" value="<?= $mitra['id_merchant'] ?>">
                                                            <div class="form-group">
                                                                <label for="name">Mitra Name</label>
                                                                <input type="text" class="form-control" id="name" name="nama_mitra" value="<?= $mitra['nama_mitra'] ?>" required>
                                                            </div>
                                                            <div class="form-group">
                                                                <label class=mt-2 for="ftr">Partner</label>
                                                                <select id="pilih" class=" form-control custom-select  mt-15" style="width:100%" name="partner">
                    
                                                                    <option id="partner" value="1" <?php if ($mitra['partner'] == 1) { ?>selected<?php } ?>>Partner</option>
                                                                    <option id="non" value="0" <?php if ($mitra['partner'] == 0) { ?>selected<?php } ?>>non Partner</option>
                                                                </select>
                                                            </div>
                                                            <div class="form-group">
                                                                <label>Address</label>
                                                                <input type="text" class="form-control" name="alamat_mitra" value="<?= $mitra['alamat_mitra'] ?>" required>
                                                            </div>
                                                            <div class="form-group">
                                                                <label>Email</label>
                                                                <input type="email" class="form-control" name="email_mitra" value="<?= $mitra['email_mitra'] ?>" required>
                                                            </div>
                                                            <label class="text-small">Phone Number</label>
                                                            <div class="row">
                                                                <div class="form-group col-2">
                                                                    <input type="tel" id="txtPhone1" class="form-control" name="country_code_mitra" value="<?= $mitra['country_code_mitra'] ?>" required>
                                                                </div>
                                                                <div class=" form-group col-10">
                                                                    <input type="text" class="form-control" name="phone_mitra" value="<?= $mitra['phone_mitra'] ?>"" required>
                                                                </div>
                                                            </div>
                                                            <button type=" submit" class="btn btn-success mr-2">Update
                                                                    </button>
                                                                    <?= form_close(); ?>
													</div>
<!----------------------------------------------------------------------------------------------- tab ubah password ------------------------------------------------------------------------------------------->
													<div class="tab-pane fade" id="pass" role="tabpanel" aria-labelledby="pass-tab">
        												 <?= form_open_multipart('mitra/editmitrapass'); ?>
                                                        <input type="hidden" class="form-control" name="id_mitra" value="<?= $mitra['id_mitra'] ?>">
                                                        <div class="form-group">
                                                            <label for="ic">Masukan Password baru</label>
                                                            <input type="password" class="form-control" id="ic" name="password" placeholder="Enter Your New Password" required>
                                                        </div>
                                                        <button type=" submit" class="btn btn-success mr-2">Update</button>
                                                        <?= form_close(); ?>
													</div>
<!------------------------------------------------------------------------------------------------ tab Kategori ---------------------------------------------------------------------------------------->
													<div class="tab-pane fade" id="kategori" role="tabpanel" aria-labelledby="kategori-tab">
                                                        <button id="tomboltambah" class="btn btn-info">
                                                            <i class="mdi mdi-plus-circle-outline"></i>Add Category
                                                        </button>
                                                        <br>
                                                        <br>
            
                                                        <div id="tambahcategory"></div>
                                                        <br>
                                                        <div id="editcategory" style="display:none;">
                                                            <?= form_open_multipart('mitra/ubahcategoryitem'); ?>
                                                            <input type="hidden" class="form-control" name="id_mitra" value="<?= $mitra['id_mitra'] ?>">
                                                            <input type="hidden" id="foridkat" class="form-control" name="id_kategori_item" value="">
                                                            <h4 class="card-title">Edit Menus Category</h4>
                                                            <div class="form-group">
                                                                <label for="nama">Category Name</label>
                                                                <input type="text" class="form-control" id="fornamkat" name="nama_kategori_item" value"" required>
                                                            </div>
                                                            <div class="row">
                                                                <button type="submit" class="btn btn-success mr-2">Submit</button>
                                                                <?= form_close(); ?>
                                                                <span onclick="kembalikan()" class="btn btn-secondary mr-2">cancel</span>
                                                            </div>
                                                        </div>
                                                        <br>
                                                        <br>
                                                        <h4 id="jumlah" style=display:none;><?= count($itemk) ?></h4>
                                                        <div class="row">
                                                            <div class="col-12">
                                                                <div class="table-responsive">
            
                                                                    <table id="order-listing4" class="table">
                                                                        <thead>
                                                                            <tr>
                                                                                <th>No.</th>
                                                                                <th>Category Name</th>
                                                                                <th>Actions</th>
                                                                            </tr>
                                                                        </thead>
                                                                        <tbody>
                                                                            <?php $i = 1;
                                                                            foreach ($itemk as $itkate) { ?>
                                                                                <h4 id="idkat<?= $i ?>" style=display:none;><?= $itkate['id_kategori_item'] ?></h4>
                                                                                <tr>
                                                                                    <td><?= $i ?></td>
                                                                                    <td id="namkat<?= $i ?>"><?= $itkate['nama_kategori_item'] ?></td>
                                                                                    <td>
                                                                                        <button class="btn btn-outline-primary" onclick="tombedit(<?= $i ?>);">Edit</button>
                                                                                        <a href="<?= base_url(); ?>mitra/hapuscategoryitem/<?= $itkate['id_kategori_item']; ?>" onclick="return confirm ('are you sure?')">
                                                                                            <button class="btn btn-outline-danger">Delete</button></a>
                                                                                    </td>
                                                                                <?php $i++;
                                                                            } ?>
                                                                                </tr>
            
                                                                        </tbody>
                                                                    </table>
                                                                </div>
                                                            </div>
                                                        </div>
													</div>
<!------------------------------------------------------------------------------------------------ tab Tambah Menu ---------------------------------------------------------------------------------------->
													<div class="tab-pane fade" id="menu" role="tabpanel" aria-labelledby="menu-tab">
                                                        <?php $index = 5;
                                                            foreach ($itemk as $itk) { ?>
                                                                <div class="table-responsive">
                                                    <table id="tabwallet2" class="table table-hover w-100 display pb-30" data-info="false">
                                                        <thead>
                                                            <tr>
                                                                <th>No.</th>
                                                                <th>Foto</th>
                                                                <th>Nama</th>
                                                                <th>Harga</th>
                                                                <th>Harga Promo</th>
                                                                <th>status</th>
                                                                <th>Actions</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                            <?php $i = 1;
                                                            foreach ($item as $it) {
                                                                if ($itk['id_kategori_item'] == $it['kategori_item']) { ?>
                                                                    <tr>
                                                                        <td><?= $i ?></td>
                                                                        <td><img src="<?= base_url('images/itemmerchant/') . $it['foto_item']; ?>" width="40" height="40" class="avatar-img rounded-circle"></td>
                                                                        <td id="namaitem<?= $i ?>"><?= $it['nama_item'] ?></td>
                                                                        <?php if ($it['status_promo'] == 0) { ?>
                                                                            <td><?= $currency['app_currency'] ?><?= rupiah($it['harga_item']) ?></td>
                                                                        <?php } else { ?>
                                                                            <td style="text-decoration: line-through;"><?= $currency['app_currency'] ?><?= rupiah($it['harga_item']) ?></td>
                                                                        <?php } ?>
                                                                        <?php if ($it['status_promo'] == 1) { ?>
                                                                            <td class="text-success"><?= $currency['app_currency'] ?><?= rupiah($it['harga_promo']) ?></td>
                                                                        <?php } else { ?>
                                                                            <td><label class="badge badge-danger">Not Promo</label></td>
                                                                        <?php } ?>
                                                                        <?php if ($it['status_item'] == 1) { ?>
                                                                            <td><label class="badge badge-primary">Active</label></td>
                                                                        <?php } else { ?>
                                                                            <td><label class="badge badge-danger">Non Active</label></td>
                                                                        <?php } ?>
                                                                        <td>

                                                                            <a class="btn btn-outline-primary text-red mr-2" href=" <?= base_url(); ?>mitra/edititem/<?= $it['id_item'] ?>">
                                                                                Edit</a>
                                                                            <a class="btn btn-outline-danger text-red mr-2" onclick="return confirm ('Are You Sure Want To Delete This Item?')" href=" <?= base_url(); ?>mitra/hapusitem/<?= $it['id_item'] ?>">
                                                                                Delete</a>
                                                                        </td>
                                                                <?php }
                                                                $i++;
                                                            } ?>
                                                                    </tr>
                                                        </tbody>
                                                    </table>
                                                </div>
                                                            <?php $index++;
                                                            } ?>
													</div>
<!------------------------------------------------------------------------------------------------ tab Tambah Menu ---------------------------------------------------------------------------------------->
													<div class="tab-pane fade" id="addmenu" role="tabpanel" aria-labelledby="addmenu-tab">
                                                       <?= form_open_multipart('mitra/tambahitem'); ?>
                                                        <input type="hidden" class="form-control" name="id_merchant" value="<?= $mitra['id_merchant'] ?>">
                                                        <input type="hidden" class="form-control" name="id_mitra" value="<?= $mitra['id_mitra'] ?>">
                                                        <div class="form-group">
                                                            <label for="name">Menus Name</label>
                                                            <input type="text" class="form-control" id="name" name="nama_item" required>
                                                        </div>
                                                        <div class="form-group">
                                                            <input type="file" class="dropify" name="foto_item" data-max-file-size="3mb" required />
                                                        </div>
                                                        <div class="form-group">
                                                            <label>Category Menus</label>
                                                            <select class="form-control custom-select  mt-15" style="width:100%" name="kategori_item">
                                                                <?php foreach ($itemk as $itk) { ?>
                                                                    <option value="<?= $itk['id_kategori_item'] ?>"><?= $itk['nama_kategori_item'] ?></option>
                                                                <?php } ?>
                                                            </select>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="desk">Description</label>
                                                            <input type="text" class="form-control" id="desk" name="deskripsi_item">
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="Hargaitem">Price(<?= $currency['app_currency'] ?>)</label>
                                                            <input type="text"  class="form-control" id="Hargaitem" name="harga_item" required>
                                                        </div>
                                                        <div class="row">
                                                            <div class="col-lg-2">
                                                                <div class="form-group">
                                                                    <label>Is Promo</label>
                                                                    <select id="getname" onchange="admSelectCheck(this);" class="form-control custom-select  mt-15" style="width:100%" name="status_promo">
                                                                        <option id="yes" value="1">Yes</option>
                                                                        <option id="no" value="0">No</option>
                                                                    </select>
                                                                </div>
                                                            </div>
                                                            <div class="col-lg-10">
                                                                <div id="yescheck" style="display:block;" class="form-group">
                                                                    <label for="yes">Promo Price(<?= $currency['app_currency'] ?>)</label>
                                                                    <input type="text"  class="form-control" id="yes" name="harga_promo">
                                                                </div>
                                                            </div>
                                                        </div>
            
            
                                                        <div class="form-group">
                                                            <label>Status Menus</label>
                                                            <select class="form-control custom-select  mt-15" style="width:100%" name="status_item">
                                                                <option value="1">Active</option>
                                                                <option value="0">NonActive</option>
                                                            </select>
                                                        </div>
                                                        <button type="submit" class="btn btn-success mr-2">Submit</button>
                                                        <?= form_close(); ?>
													</div>
<!------------------------------------------------------------------------------------------------ tab Identitas ---------------------------------------------------------------------------------------->
													<div class="tab-pane fade" id="identitas" role="tabpanel" aria-labelledby="identitas-tab">
													 <?= form_open_multipart('mitra/editmitrafile'); ?>
                                                        <input type="hidden" class="form-control" name="id_mitra" value="<?= $mitra['id_mitra'] ?>">
                                                        <div class="form-group">
                                                            <label for="ic">Type of Id Card</label>
                                                            <input type="text" class="form-control" id="ic" name="jenis_identitas_mitra" value="<?= $mitra['jenis_identitas_mitra'] ?>" required>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="nc">Number of Id Card</label>
                                                            <input type="text" class="form-control" id="nc" name="nomor_identitas_mitra" value="<?= $mitra['nomor_identitas_mitra'] ?>" required>
                                                            
                                                        </div>
                                                        <div class="form-group">
                                                         <label class=mt-2 for="name">Foto Identitas</label>
                                                            <input id="uploadktp" type="file" class="dropify" name="foto_ktp" onchange="PreviewKtp();" data-default-file="<?= base_url('images/fotoberkas/ktp/') . $mitra['foto_ktp'] ?>" data-max-file-size="3mb" required /><br>
                                                                 <img id="KtpPreview" src="<?= base_url('images/fotoberkas/ktp/') . $mitra['foto_ktp'] ?>" style="width: 300px; height: 200px;" />
                                                           <script type="text/javascript">
                                                            function PreviewKtp() {
                                                                var oFReader = new FileReader();
                                                                oFReader.readAsDataURL(document.getElementById("uploadKTP").files[0]);
                                                        
                                                                oFReader.onload = function (oFREvent) {
                                                                    document.getElementById("KtpPreview").src = oFREvent.target.result;
                                                                };
                                                            };
                                                            
                                                            </script>
                                                        </div>
                                                        <button type=" submit" class="btn btn-success mr-2">Update</button>
                                                        <?= form_close(); ?>
                                                    </div>
                                                    <div class="tab-pane" id="mitpass" role="tabpanel" aria-labelledby="mitpass">
                                                        <?= form_open_multipart('mitra/editmitrapass'); ?>
                                                        <input type="hidden" class="form-control" name="id_mitra" value="<?= $mitra['id_mitra'] ?>">
                                                        <div class="form-group">
                                                            <label for="ic">Type of Id Card</label>
                                                            <input type="password" class="form-control" id="ic" name="password" placeholder="Enter Your New Password" required>
                                                        </div>
                                                        <button type=" submit" class="btn btn-success mr-2">Update</button>
                                                        <?= form_close(); ?>
													</div>
<!----------------------------------------------------------------------------------------------- tab Histori ----------------------------------------------------------------------------------------->
													<div class="tab-pane fade" id="transactionhistory" role="tabpanel" aria-labelledby="transactionhistory-tab">
													<div class="table-responsive">
                                                    <table id="tabhistori" class="table table-hover w-100 display pb-30" data-info="false">
                                                        <thead>
                                                            <tr>
                                                                <th>No.</th>
                                                                <th>Nota</th>
                                                                <th>Tanggal</th>
                                                                <th>Customer</th>
                                                                <th>Item</th>
                                                                <th>Harga</th>
                                                                <th>Actions</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                            <?php $i = 1;
                                                            foreach ($transaksi as $tr) { ?>
                                                                <tr>
                                                                    <td><?= $i ?></td>
                                                                    <td>INV-<?= $tr['id_transaksi'] ?></td>
                                                                    <td><?= $tr['waktu_order'] ?></td>
                                                                    <td><?= $tr['fullnama'] ?></td>
                                                                    <td><?= $tr['jumlah_item'] ?></td>
                                                                    <td>
                                                                        <?= $currency['app_currency'] ?>
                                                                        <?= rupiah($tr['total_biaya']) ?>
                                                                    </td>
                                                                    <td>
                                                                        <a href="<?= base_url(); ?>dashboard/detail/<?= $tr['id_transaksi'] ?>" class="btn btn-outline-primary">View</a>
                                                                    </td>
                                                                <?php $i++;
                                                            } ?>
                                                                </tr>
        
                                                        </tbody>
                                                    </table>
                                                </div>
													</div>
<!------------------------------------------------------------------------------------------------- tab Wallet ----------------------------------------------------------------------------------------->
													<div class="tab-pane fade" id="wallet" role="tabpanel" aria-labelledby="wallet-tab">
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
                                                                                <?= $currency['app_currency'] ?>
                                                                                <?= rupiah($wl['jumlah']) ?>
                                                                            </td>
            
                                                                        <?php } else { ?>
                                                                            <td class="text-danger">
                                                                                <?= $currency['app_currency'] ?>
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
										<div class="col-lg-4">
											<div class="card card-profile-feed">
                                                <div class="card-header card-header-action">
													<div class="media align-items-center">
														<div class="media-img-wrap d-flex mr-10">
															<div class="avatar avatar-sm">
																<img src="<?= base_url('images/merchant/') . $mitra['foto_merchant'] ?>" alt="user" class="avatar-img rounded-circle">
															</div>
														</div>
														<div class="media-body">
															<div class="text-capitalize font-weight-500 text-dark"><?= $mitra['nama_mitra'] ?>
														</div>
															<div class="font-13">Customer</div>
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
															<span class="d-block display-6 text-dark mb-5">
    														<?= $mitra['fitur'] ?>
															</span>
															<span class="d-block text-capitalize font-14">Layanan</span>
														</div>
													</div>
													<div class="col-4 pl-0">
														<div class="pa-15">
															<span class="d-block display-6 text-dark mb-5">
													 <?php if ($mitra['status_mitra'] == 3) {
                                                                echo 'Diblokir';
                                                            } elseif ($mitra['status_mitra'] == 0) {
                                                                echo 'Baru';
                                                            } else {
                                                                if ($mitra['status_merchant'] == 1) {
                                                                    echo 'Aktif';
                                                                }
                                                                if ($mitra['status_merchant'] == 0) {
                                                                    echo 'Nonaktif';
                                                                }
                                                            } ?>
														</span>
															<span class="d-block text-capitalize font-14">Status</span>
														</div>
													</div>
												</div>
											
											 </div>

											 <a class="btn btn-primary btn-block mb-15" href="<?= base_url(); ?>mitra">Kembali</a>
										</div>
									</div>
								</div>
							</div>
						</div>	
					</div>
                </div>
                <!-- /Row -->
            </div>
  
<script>
    $('#us3').locationpicker({
        location: {
            latitude: <?= $mitra["latitude_merchant"] ?>,
            longitude: <?= $mitra["longitude_merchant"] ?>
        },
        radius: 300,
        inputBinding: {
            latitudeInput: $('#us3-lat'),
            longitudeInput: $('#us3-lon'),
            radiusInput: $('#us3-radius'),
            locationNameInput: $('#us3-address')
        },
        enableAutocomplete: true,
        onchanged: function(currentLocation, radius, isMarkerDropped) {}
    });
</script>

<script type="text/javascript">
    $(function() {
        var code = "<?= $mitra['country_code_merchant'] ?>"; // Assigning value from model.
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

<script type="text/javascript">
    $(function() {
        var code = "<?= $mitra['country_code_mitra'] ?>"; // Assigning value from model.
        $('#txtPhone1').val(code);
        $('#txtPhone1').intlTelInput({
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


<script>
    function admSelectCheck(nameSelect) {
        if (nameSelect) {
            yesValue = document.getElementById("yes").value;
            noValue = document.getElementById("no").value;
            if (yesValue == nameSelect.value) {

                document.getElementById("yescheck").required = true;
                document.getElementById("yescheck").style.display = "block";
            } else if (noValue == nameSelect.value) {

                document.getElementById("yescheck").required = false;
                document.getElementById("yescheck").style.display = "none";
            }
        } else {
            document.getElementById("yescheck").style.display = "block";
            document.getElementById("yescheck").required = true;
        }
    }

    function addform() {
        return `<?= form_open_multipart('mitra/tambahcategoryitem'); ?>
                <input type="hidden" id="valmit" class="form-control" name="id_merchant" value="<?= $mitra['id_mitra'] ?>">
                <input type="hidden" id="valmer" class="form-control" name="id_mitra" value="<?= $mitra['id_merchant'] ?>">
                <h4 class="card-title">Add Item Category</h4>
                <div class="form-group">
                    <label for="nama">Category Name</label>
                    <input type="text" class="form-control" id="nama" name="nama_kategori_item" placeholder="enter item category" required>
                </div>
                <div class="row">
                        <button id="kirimid" type="submit" class="btn btn-success mr-2">Submit</button>
                    <?= form_close(); ?>
                        <button id="andhe" class="btn btn-secondary mr-2">cancel</button>
                </div>`
    }
    const tomboltambah = document.getElementById('tomboltambah');
    tomboltambah.addEventListener('click', function() {
        const getformadd = document.getElementById('tambahcategory');
        getformadd.innerHTML = addform();
        const tombolback = document.getElementById('andhe');
        tombolback.addEventListener('click', function() {
            getformadd.innerHTML = backform();
        })
    })

    function backform() {
        return ``
    }

    const jumlah = document.getElementById("jumlah").innerHTML

    for (let i = 0; i < 20; i++) {

        function tombedit(i) {

            const namkat = document.getElementById(`namkat${i}`).innerHTML
            const idkat = document.getElementById(`idkat${i}`).innerHTML
            document.getElementById('editcategory').style = "display:block;";
            document.getElementById('fornamkat').value = namkat;
            document.getElementById('foridkat').value = idkat;
        }
    }

    function kembalikan() {
        document.getElementById('editcategory').style = "display:none;";
    }
</script>