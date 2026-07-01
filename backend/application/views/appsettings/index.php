 <div class="container-fluid mt-xl-50 mt-sm-30 mt-15 px-xxl-65 px-xl-20">
    <div class="row">
        <div class="col-md-8 offset-md-2 grid-margin stretch-card">
            <div class="card">
                <div class="card-body">
                    <h4 class="card-title">App Settings</h4>
                    <?php if ($this->session->flashdata('send') or $this->session->flashdata('ubah')) : ?>
                        <div class="alert alert-success" role="alert">
                            <?php echo $this->session->flashdata('send'); ?>
                            <?php echo $this->session->flashdata('ubah'); ?>
                        </div>
                    <?php endif; ?>
                    <?php if ($this->session->flashdata('demo')) : ?>
                        <div class="alert alert-danger" role="alert">
                            <?php echo $this->session->flashdata('demo'); ?>
                        </div>
                    <?php endif; ?>
                    <div class="tab-minimal tab-minimal-success">
                        <ul class="nav nav-tabs" role="tablist">
                            <li class="nav-item">
                                <a class="nav-link active" id="tab-2-1" data-toggle="tab" href="#app-2-1" role="tab" aria-controls="app-2-1" aria-selected="true">
                                    <i class="mdi mdi-cellphone-android"></i>App</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" id="tab-2-2" data-toggle="tab" href="#email-2-2" role="tab" aria-controls="email-2-2" aria-selected="false">
                                    <i class="mdi mdi-cellphone-android"></i>Email</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" id="tab-2-3" data-toggle="tab" href="#smtp-2-3" role="tab" aria-controls="smtp-2-3" aria-selected="false">
                                    <i class="mdi mdi-cellphone-android"></i>SMTP</a>
                            </li>
                          
                            <li class="nav-item">
                                <a class="nav-link" id="tab-2-4" data-toggle="tab" href="#banktransfer-2-4" role="tab" aria-controls="banktransfer-2-4" aria-selected="false">
                                    <i class="mdi mdi-cellphone-android"></i>Bank Transfer</a>
                            </li>
                            
                            <li class="nav-item">
                                <a class="nav-link" id="tab-2-5" data-toggle="tab" href="#midtrans-2-5" role="tab" aria-controls="midtrans-2-5" aria-selected="false">
                                    <i class="mdi mdi-cellphone-android"></i>Midtrans</a>
                            </li>
                            
                            <li class="nav-item">
                                <a class="nav-link" id="tab-2-6" data-toggle="tab" href="#mobilepulsa-2-6" role="tab" aria-controls="mobilepulsa-2-6" aria-selected="false">
                                    <i class="mdi mdi-cellphone-android"></i>MobilePulsa</a>
                            </li>
                            
                            <li class="nav-item">
                                <a class="nav-link" id="tab-2-6" data-toggle="tab" href="#admob-2-6" role="tab" aria-controls="admob-2-6" aria-selected="false">
                                    <i class="mdi mdi-cellphone-android"></i>Admob Banner</a>
                            </li>
                            
                            <li class="nav-item">
                                <a class="nav-link" id="tab-2-7" data-toggle="tab" href="#digi-2-7" role="tab" aria-controls="digi-2-7" aria-selected="false">
                                    <i class="mdi mdi-cellphone-android"></i>Digiflazz</a>
                            </li>
                            
                        </ul>
                        <div class="tab-content col-12 justify-content-center">
                            <div class="tab-pane fade show active" id="app-2-1" role="tabpanel" aria-labelledby="tab-2-1">
                                <div class="col-12 grid-margin">
                                    <div class="card">
                                        <div class="card-body">
                                            <h4 class="card-title">App Settings</h4>
                                            <br>
                                            <?= form_open_multipart('appsettings/ubahapp'); ?>
                                            <div class="form-group">
                                                <label for="appemail">App Email</label>
                                                <input type="email" class="form-control" id="appemail" name="app_email" value="<?= $appsettings['app_email']; ?>" required></div>
                                            <div class="form-group">
                                                <label for="appname">App Name</label>
                                                <input type="text" class="form-control" id="appname" name="app_name" value="<?= $appsettings['app_name']; ?>" required></div>
                                            <div class="form-group">
                                                <label for="appcontact">App Contact *Contoh : 628991585xxx</label>
                                                <input type="text" class="form-control" id="appcontact" name="app_contact" value="<?= $appsettings['app_contact']; ?>" required></div>
                                            <div class="form-group">
                                                <label for="appwebsite">App Website</label>
                                                <input type="text" class="form-control" id="appwebsite" name="app_website" value="<?= $appsettings['app_website']; ?>" required></div>
                                            <div class="form-group">
                                                <label for="privacypolicy">Privacy Policy</label>
                                                <textarea type="text" class="form-control" id="summernoteExample1" name="app_privacy_policy" required><?= $appsettings['app_privacy_policy']; ?></textarea>
                                            </div>
                                            <div class="form-group">
                                                <label for="aboutus">About Us</label>
                                                <textarea type="text" class="form-control" id="summernoteExample2" name="app_aboutus" required><?= $appsettings['app_aboutus']; ?></textarea>
                                            </div>
                                            <div class="form-group">
                                                <label for="appaddress">App Address</label>
                                                <textarea type="text" class="form-control" id="summernoteExample3" name="app_address" required><?= $appsettings['app_address']; ?></textarea></div>
                                            <div class="form-group">
                                                <label for="googlelink">Google Link</label>
                                                <input type="text" class="form-control" id="googlelink" name="app_linkgoogle" value="<?= $appsettings['app_linkgoogle']; ?>" required></div>
                                            <div class="form-group">
                                                <label for="appcurrency">Mata Uang</label>
                                                <input type="text" class="form-control" id="appcurrency" name="app_currency" value="<?= $appsettings['app_currency']; ?>" required></div>
                                            <div class="form-group">
                                                <label for="appcurrency">Google Api Key</label>
                                                <input type="password" class="form-control" id="mapkey" name="map_key" value="<?= $appsettings['map_key']; ?>" required></div>
                                             <div class="form-group">
                                             <div class="row">
                                                 <div class="col-md-6">
                                        
                                                        <label for="midtrans_aktif">Sistem OTP</label>
                                                        <select name="isotp" id="isotp" class="form-control custom-select  mt-15" style="width:100%">
                                                            <option value="1" <?php if ($appsettings['isotp'] == '1') { ?>selected<?php } ?>>Active</option>
                                                            <option value="0" <?php if ($appsettings['isotp'] == '0') { ?>selected<?php } ?>>NonActive</option>
                                                        </select>
                                                   
                                                 </div>
                                                 <div class="col-md-6">
                                                      <label for="midtrans_aktif">Sender Whatsapp</label>
                                                         <input type="text" class="form-control mt-15" id="sender_wasap" name="sender_wasap" value="<?= $appsettings['sender_wasap']; ?>" required>
                                                 </div>
                                             </div>
                                              </div>
                                            <div class="form-group">
                                                <label for="midtrans_aktif">Api Key Whatsapp</label>
                                                         <input type="text" class="form-control mt-15" id="key_api_wasap" name="key_api_wasap" value="<?= $appsettings['key_api_wasap']; ?>" required>
                                            </div>
                                            <div class="form-group">
                                                <label for="midtrans_aktif">App Maintenance</label>
                                                <select name="maintenance" id="maintenance" class="form-control custom-select  mt-15" style="width:100%">
                                                    <option value="1" <?php if ($appsettings['maintenance'] == '1') { ?>selected<?php } ?>>Active</option>
                                                    <option value="0" <?php if ($appsettings['maintenance'] == '0') { ?>selected<?php } ?>>NonActive</option>
                                                </select>
                                            </div>
                                            <hr class="mt-5 mb-3"/>
                                            <div class="form-group">
                                                <label for="midtrans_aktif">App Version Customer</label>
                                                <div class="row">
                                                    <div class="col-md-4">
                                                        <label for="midtrans_aktif" style="font-size:12px;">Force Update</label>
                                                        <select name="force_user" id="maintenance" class="form-control custom-select" style="width:100%">
                                                            <option value="1" <?php if ($appsettings['force_update_user'] == '1') { ?>selected<?php } ?>>Yes</option>
                                                            <option value="0" <?php if ($appsettings['force_update_user'] == '0') { ?>selected<?php } ?>>No</option>
                                                        </select>
                                                    </div>
                                                    <div class="col-md-8">
                                                        <label for="midtrans_aktif" style="font-size:12px;">Version Code</label>
                                                         <input type="text" class="form-control" id="versics" name="versi_cs" value="<?= $appsettings['versi_cs']; ?>" required>
                                                    </div>
                                                </div>
                                               
                                            </div>
                                            <hr class="mt-5 mb-3"/>
                                            <div class="form-group">
                                                 <label for="midtrans_aktif">App Version Driver</label>
                                                <div class="row">
                                                    <div class="col-md-4">
                                                        <label for="midtrans_aktif" style="font-size:12px;">Force Update</label>
                                                        <select name="force_driver" id="maintenance" class="form-control custom-select" style="width:100%">
                                                            <option value="1" <?php if ($appsettings['force_update_driver'] == '1') { ?>selected<?php } ?>>Yes</option>
                                                            <option value="0" <?php if ($appsettings['force_update_driver'] == '0') { ?>selected<?php } ?>>No</option>
                                                        </select>
                                                    </div>
                                                    <div class="col-md-8">
                                                        <label for="midtrans_aktif" style="font-size:12px;">Version Code</label>
                                                         <input type="text" class="form-control" id="versics" name="versi_driver" value="<?= $appsettings['versi_driver']; ?>" required>
                                                    </div>
                                                </div>
                                               
                                            </div>
                                            <hr class="mt-5 mb-3"/>
                                            <div class="form-group">
                                                <label for="midtrans_aktif">App Version Mitra</label>
                                                <div class="row">
                                                    <div class="col-md-4">
                                                        <label for="midtrans_aktif" style="font-size:12px;">Force Update</label>
                                                        <select name="force_mitra" id="maintenance" class="form-control custom-select" style="width:100%">
                                                            <option value="1" <?php if ($appsettings['force_update_mitra'] == '1') { ?>selected<?php } ?>>Yes</option>
                                                            <option value="0" <?php if ($appsettings['force_update_mitra'] == '0') { ?>selected<?php } ?>>No</option>
                                                        </select>
                                                    </div>
                                                    <div class="col-md-8">
                                                        <label for="midtrans_aktif" style="font-size:12px;">Version Code</label>
                                                         <input type="text" class="form-control" id="versics" name="versi_mitra" value="<?= $appsettings['versi_mitra']; ?>" required>
                                                    </div>
                                                </div>
                                               
                                            </div>
                                            
                                            
                                            <label for="newscategory">Biaya-biaya tambahan</label>
                                            <div class = "row">
                                                
                                                 <div class="col-md-6">
                                                    <div class="form-group">
                                                        <label for="newscategory">Status Tambahan Jam Tertentu</label>
                                                        <select class="form-control custom-select  mt-10" name="fee_time_status" style="width:100%">
                                                            <option value="0" <?php if ($appsettings['fee_time_status'] == 0) { ?>selected<?php } ?>>Nonactive</option>
                                                            <option value="1" <?php if ($appsettings['fee_time_status'] == 1) { ?>selected<?php } ?>>Active</option>
                                                        </select>
                                                    </div>
                                                    <div class="form-group">
                                                        <label for="newstitle">Tambahan Biaya</label>
                                                        <input type="number"  class="form-control" id="newstitle" name="fee_add_time" value="<?= rupiah($appsettings['fee_add_time']) ?>" required>
                                                    </div>
                                                    
                                                    <div class="row">
                                                        <div class="col-6">
                                                            <div class="form-group">
                                                                <label for="newstitle">Waktu Mulai</label>
                                                                <input type="time"  class="form-control" id="newstitle" name="fee_time_on" value="<?= $appsettings['fee_time_on'] ?>" required>
                                                            </div>
                                                        </div>
                                                        
                                                        <div class="col-6">
                                                            <div class="form-group">
                                                                <label for="newstitle">Waktu Berakhir</label>
                                                                <input type="time"  class="form-control" id="newstitle" name="fee_time_off" value="<?= $appsettings['fee_time_off'] ?>" required>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    
                                                   
                                                </div>
                                                
                                                <div class="col-md-6">
                                                    <div class="form-group">
                                                        <label for="newscategory">Status Rain</label>
                                                        <select class="form-control custom-select  mt-10" name="fee_rain_status" style="width:100%">
                                                            <option value="0" <?php if ($appsettings['fee_rain_status'] == 0) { ?>selected<?php } ?>>OFF</option>
                                                            <option value="1" <?php if ($appsettings['fee_rain_status'] == 1) { ?>selected<?php } ?>>ON</option>
                                                        </select>
                                                    </div>
                                                    <div class="form-group">
                                                        <label for="newstitle">Tambahan Biaya</label>
                                                        <input type="number"  class="form-control" id="newstitle" name="fee_rain" value="<?= rupiah($appsettings['fee_rain']) ?>" required>
                                                    </div>
                                                    
                                                  
                                                </div>
                                                
                                               
                                                
                                            </div>
                                            
                                              <div class="form-group">
                                                <label for="appcurrency">Minimal Transfer</label>
                                                <input type="text" class="form-control" id="appcurrency" name="minimum_transfer" value="<?= $appsettings['minimum_transfer']; ?>" required></div>
                                                
                                                  <div class="form-group">
                                                <label for="appcurrency">Mata Uang</label>
                                                <input type="text" class="form-control" id="appcurrency" name="minimum_wallet" value="<?= $appsettings['minimum_wallet']; ?>" required></div>
                                            
                                            <button type="submit" class="btn btn-success mr-2">Submit</button>

                                            <?= form_close(); ?>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="tab-pane fade" id="email-2-2" role="tabpanel" aria-labelledby="tab-2-2">
                                <div class="col-12 grid-margin">
                                    <div class="card">
                                        <div class="card-body">
                                            <h4 class="card-title">Email Template For Forgot Password</h4>
                                            <br>
                                            <?= form_open_multipart('appsettings/ubahemail'); ?>
                                            <div class="form-group">
                                                <label for="emailsubject">Email Subject</label>
                                                <textarea type="email" class="form-control" id="emailsubject" name="email_subject" required><?= $appsettings['email_subject']; ?></textarea>
                                            </div>
                                            <div class="form-group">
                                                <label for="emailtext1">Email Text 1</label>
                                                <textarea type="email" class="form-control" id="summernoteExample4" name="email_text1" required><?= $appsettings['email_text1']; ?></textarea>
                                            </div>
                                            <div class="form-group">
                                                <label for="emailtext2">Email Text 2</label>
                                                <textarea type="email" class="form-control" id="summernoteExample5" name="email_text2" required><?= $appsettings['email_text2']; ?></textarea>
                                            </div>

                                            <h4 class="card-title">Email Template For Confirm Driver</h4>

                                            <div class="form-group">
                                                <label for="emailsubject">Email Subject</label>
                                                <textarea type="email" class="form-control" id="emailsubject" name="email_subject_confirm" required><?= $appsettings['email_subject_confirm']; ?></textarea>
                                            </div>

                                            <div class="form-group">
                                                <label for="emailtext1">Email Text 1</label>
                                                <textarea type="email" class="form-control" id="summernoteExample6" name="email_text3" required><?= $appsettings['email_text3']; ?></textarea>
                                            </div>
                                            <div class="form-group">
                                                <label for="emailtext2">Email Text 2</label>
                                                <textarea type="email" class="form-control" id="summernoteExample7" name="email_text4" required><?= $appsettings['email_text4']; ?></textarea>
                                            </div>


                                            <button type="submit" class="btn btn-success mr-2">Submit</button>

                                            <?= form_close(); ?>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="tab-pane fade" id="smtp-2-3" role="tabpanel" aria-labelledby="tab-2-3">
                                <div class="col-12 grid-margin">
                                    <div class="card">
                                        <div class="card-body">
                                            <h4 class="card-title">SMTP Settings</h4>
                                            <br>

                                            <?php if (demo == TRUE) { ?>
                                                <?= form_open_multipart('appsettings/ubahsmtp'); ?>
                                                <div class="form-group">
                                                    <label for="smtphost">SMTP Host</label>
                                                    <input type="text" class="form-control" id="smtphost" name="smtp_host" required>
                                                </div>
                                                <div class="form-group">
                                                    <label for="smtpport">SMTP Port</label>
                                                    <input type="text" class="form-control" id="smtpport" name="smtp_port" required>
                                                </div>
                                                <div class="form-group">
                                                    <label for="smtpusername">SMTP User Name</label>
                                                    <input type="text" class="form-control" id="smtpusername" name="smtp_username" required>
                                                </div>
                                                <div class="form-group">
                                                    <label for="smtppassword">SMTP Password</label>
                                                    <input type="password" class="form-control" id="smtppassword" name="smtp_password" required>
                                                </div>
                                                <div class="form-group">
                                                    <label for="smtpform">SMTP Form</label>
                                                    <input type="text" class="form-control" id="smtpfrom" name="smtp_from" required>
                                                </div>
                                                <div class="form-group">
                                                    <label for="smtp_secure">SMTP Secure</label>
                                                    <select class="form-control border-primary" name="smtp_secure" id="smtp_secure">
                                                        <option value="tls" <?php if ($appsettings['smtp_secure'] == 'tls') { ?>selected<?php } ?>>TLS</option>
                                                        <option value="ssl" <?php if ($appsettings['smtp_secure'] == 'ssl') { ?>selected<?php } ?>>SSL</option>
                                                    </select>
                                                </div>
                                                <button type="submit" class="btn btn-success mr-2">Submit</button>

                                                <?= form_close(); ?>
                                            <?php } else { ?>

                                                <?= form_open_multipart('appsettings/ubahsmtp'); ?>
                                                <div class="form-group">
                                                    <label for="smtphost">SMTP Host</label>
                                                    <input type="text" value="<?= $appsettings['smtp_host']; ?>" class="form-control" id="smtphost" name="smtp_host" required>
                                                </div>
                                                <div class="form-group">
                                                    <label for="smtpport">SMTP Port</label>
                                                    <input type="text" class="form-control" id="smtpport" name="smtp_port" value="<?= $appsettings['smtp_port']; ?>" required>
                                                </div>
                                                <div class="form-group">
                                                    <label for="smtpusername">SMTP User Name</label>
                                                    <input type="text" class="form-control" id="smtpusername" name="smtp_username" value="<?= $appsettings['smtp_username']; ?>" required>
                                                </div>
                                                <div class="form-group">
                                                    <label for="smtppassword">SMTP Password</label>
                                                    <input type="password" class="form-control" id="smtppassword" name="smtp_password" value="<?= $appsettings['smtp_password']; ?>" required>
                                                </div>
                                                <div class="form-group">
                                                    <label for="smtpform">SMTP Form</label>
                                                    <input type="text" class="form-control" id="smtpfrom" name="smtp_from" value="<?= $appsettings['smtp_from']; ?>" required>
                                                </div>
                                                <div class="form-group">
                                                    <label for="smtp_secure">SMTP Secure</label>
                                                    <select class="form-control border-primary" name="smtp_secure" id="smtp_secure">
                                                        <option value="tls" <?php if ($appsettings['smtp_secure'] == 'tls') { ?>selected<?php } ?>>TLS</option>
                                                        <option value="ssl" <?php if ($appsettings['smtp_secure'] == 'ssl') { ?>selected<?php } ?>>SSL</option>
                                                    </select>
                                                </div>
                                                <button type="submit" class="btn btn-success mr-2">Submit</button>

                                                <?= form_close(); ?>

                                            <?php } ?>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="tab-pane fade" id="banktransfer-2-4" role="tabpanel" aria-labelledby="tab-2-4">
                                <div class="col-12 grid-margin">
                                    <div class="card">
                                        <div class="card-body">
                                            <h4 class="card-title">Bank Transfer Settings</h4>
                                            <div>
                                                <a class="btn btn-info" href="<?= base_url(); ?>appsettings/addbank"><i class="mdi mdi-plus-circle-outline"></i>Add Bank</a>
                                            </div>

                                            <br>
                                            <div class="row">
                                                <div class="col-12">
                                                    <div class="table-responsive">
                                                        <table id="order-listing7" class="table">
                                                            <thead>
                                                                <tr>
                                                                    <th>No</th>
                                                                    <th>Image</th>
                                                                    <th>Bank Name</th>
                                                                    <th>Account Number</th>
                                                                    <th>Status</th>
                                                                    <th>Actions</th>
                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                                <?php $i = 1;
                                                                foreach ($transfer as $trf) { ?>
                                                                    <tr>

                                                                        <td><?= $i ?></td>
                                                                        <td><img src="<?= base_url('images/bank/') . $trf['image_bank']; ?>"></td>
                                                                        <td><?= $trf['nama_bank'] ?></td>
                                                                        <td><?= $trf['rekening_bank'] ?></td>
                                                                        <td><?php if ($trf['status_bank'] == 1) { ?>
                                                                                <label class="badge badge-primary">Active</label>
                                                                            <?php } else if ($trf['status_bank'] == 0) { ?>
                                                                                <label class="badge badge-danger">Non Active</label>
                                                                            <?php } ?>
                                                                        </td>
                                                                        <td>
                                                                            <a href="<?= base_url(); ?>appsettings/editbank/<?= $trf['id_bank'] ?>">
                                                                                <button class="btn btn-outline-primary">Edit</button>
                                                                            </a>
                                                                            <a href="<?= base_url(); ?>appsettings/hapusbank/<?= $trf['id_bank'] ?>" onclick="return confirm ('are you sure?')">
                                                                                <button class="btn btn-outline-danger">Delete</button>
                                                                            </a>
                                                                        </td>
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
                             <div class="tab-pane fade" id="midtrans-2-5" role="tabpanel" aria-labelledby="tab-2-5">
                                <div class="col-12 grid-margin">
                                    <div class="card">
                                        <div class="card-body">
                                            <h4 class="card-title">Midtrans Settings</h4>
                                            <br>
                                            <?= form_open_multipart('appsettings/ubahmidtrans'); ?>
                                            <div class="form-group">
                                                <label for="midtransurl">Midtrans Merchant Url</label>
                                                <input type="text" class="form-control" id="midtransurl" name="midtrans_url" value="<?= $appsettings['midtrans_url'] ?>" required>
                                            </div>
                                             <div class="form-group">
                                                <label for="midtranskey">Midtrans Client Key</label>
                                                <input type="password" class="form-control" id="midtranskey" name="midtrans_key" value="<?= $appsettings['midtrans_key'] ?>" required>
                                            </div>


                                            <div class="form-group">
                                                <label for="midtrans_aktif">Midtrans Status</label>
                                                <select name="midtrans_aktif" id="midtrans_aktif" class="form-control custom-select  mt-15" style="width:100%">
                                                    <option value="1" <?php if ($appsettings['midtrans_aktif'] == '1') { ?>selected<?php } ?>>Active</option>
                                                    <option value="0" <?php if ($appsettings['midtrans_aktif'] == '0') { ?>selected<?php } ?>>NonActive</option>
                                                </select>
                                            </div>
                                            <button type="submit" class="btn btn-success mr-2">Submit</button>

                                            <?= form_close(); ?>
                                        </div>
                                    </div>
                                </div>
                            </div>
                             <div class="tab-pane fade" id="mobilepulsa-2-6" role="tabpanel" aria-labelledby="tab-2-6">
                                <div class="col-12 grid-margin">
                                    <div class="card">
                                        <div class="card-body">
                                            <h4 class="card-title">MobilePulsa Settings</h4>
                                            <br>
                                            <?= form_open_multipart('appsettings/ubahmobilepulsa'); ?>
                                            <div class="form-group">
                                                <label for="midtransurl">MobilePulsa Url</label>
                                                <input type="text" class="form-control" id="mobilepulsaurl" name="mobilepulsa_url" value="<?= $appsettings['mobilepulsa_url'] ?>" required>
                                            </div>
                                             <div class="form-group">
                                                <label for="midtranskey">MobilePulsa Username</label>
                                                <input type="text" class="form-control" id="mobilepulsauser" name="mobilepulsa_user" value="<?= $appsettings['mobilepulsa_user'] ?>" required>
                                            </div>

                                            <div class="form-group">
                                                <label for="midtranskey">MobilePulsa Password</label>
                                                <input type="password" class="form-control" id="mobilepulsapass" name="mobilepulsa_pass" value="<?= $appsettings['mobilepulsa_pass'] ?>" required>
                                            </div>
                                            <div class="form-group">
                                                <label for="mobilepulsa_aktif">MobilePulsa Status</label>
                                                <select name="mobilepulsa_aktif" id="mobilepulsa_aktif" class="form-control custom-select  mt-15" style="width:100%">
                                                    <option value="1" <?php if ($appsettings['mobilepulsa_aktif'] == '1') { ?>selected<?php } ?>>Active</option>
                                                    <option value="0" <?php if ($appsettings['mobilepulsa_aktif'] == '0') { ?>selected<?php } ?>>NonActive</option>
                                                </select>
                                            </div>
                                            <button type="submit" class="btn btn-success mr-2">Submit</button>

                                            <?= form_close(); ?>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            
                             <div class="tab-pane fade" id="admob-2-6" role="tabpanel" aria-labelledby="tab-2-6">
                                <div class="col-12 grid-margin">
                                    <div class="card">
                                        <div class="card-body">
                                            <h4 class="card-title">Admob Banner</h4>
                                            <br>
                                            <?= form_open_multipart('appsettings/ubahbanner'); ?>
                                            <div class="form-group">
                                                <label for="midtransurl">Application Id</label>
                                                <input type="text" class="form-control" id="bannerid" name="bannerid" value="<?= $appsettings['bannerid'] ?>" required>
                                            </div>
                                             <div class="form-group">
                                                <label for="midtranskey">Banner Unit ID</label>
                                                <input type="text" class="form-control" id="bannerunit" name="bannerunit" value="<?= $appsettings['bannerunit'] ?>" required>
                                            </div>


                                            <div class="form-group">
                                                <label for="banneraktif">Banner Status</label>
                                                <select name="banneraktif" id="midtrans_aktif" class="form-control custom-select  mt-15" style="width:100%">
                                                    <option value="1" <?php if ($appsettings['banneraktif'] == '1') { ?>selected<?php } ?>>Active</option>
                                                    <option value="0" <?php if ($appsettings['banneraktif'] == '0') { ?>selected<?php } ?>>NonActive</option>
                                                </select>
                                            </div>
                                            <button type="submit" class="btn btn-success mr-2">Submit</button>

                                            <?= form_close(); ?>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            
                            <div class="tab-pane fade" id="digi-2-7" role="tabpanel" aria-labelledby="tab-2-7">
                                <div class="col-12 grid-margin">
                                    <div class="card">
                                        <div class="card-body">
                                            <h4 class="card-title">Digiflazz</h4>
                                            <br>
                                            <?= form_open_multipart('appsettings/ubahdigiflazz'); ?>
                                            <div class="form-group">
                                                <input type="hidden" name="id_digi" value="<?= $digi->id ?>">
                                                <label for="midtransurl">Username</label>
                                                <input type="text" class="form-control" id="username" name="username" value="<?= $digi->username ?>" required>
                                            </div>
                                            <div class="form-group">
                                                <label for="midtranskey">Key Development</label>
                                                <input type="text" class="form-control" id="key_development" name="key_development" value="<?= $digi->key_development ?>" required>
                                            </div>
                                            
                                            <div class="form-group">
                                                <label for="midtranskey">Key Production</label>
                                                <input type="text" class="form-control" id="key_production" name="key_production" value="<?= $digi->key_production ?>" required>
                                            </div>

                                            <div class="form-group">
                                                <label for="banneraktif">Is Demo</label>
                                                <select name="is_demo" id="is_demo" class="form-control custom-select  mt-15" style="width:100%">
                                                    <option value="1" <?php if ($digi->is_demo == '1') { ?>selected<?php } ?>>Ya</option>
                                                    <option value="0" <?php if ($digi->is_demo == '0') { ?>selected<?php } ?>>Tidak</option>
                                                </select>
                                            </div>
                                            <button type="submit" class="btn btn-success mr-2">Submit</button>

                                            <?= form_close(); ?>
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