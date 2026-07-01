<!DOCTYPE html>
<!-- 
Template Name: dashgrin - Responsive Bootstrap 4 Admin Dashboard Template
Author: Hencework

License: You must have a valid license purchased only from themeforest to legally use the template for your project.
-->
<html lang="en">

<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <title><?=$this->config->item("APPNAME");?></title>
    <meta name="description" content="Website Admin" />

    <!-- Favicon -->
    <link rel="shortcut icon" href="favicon.ico">
    <link rel="icon" href="favicon.ico" type="image/x-icon">

    <!-- Custom CSS -->
    <link href="<?= base_url(); ?>dist/css/style.css" rel="stylesheet" type="text/css">
</head>

<body>
   
   
	<!-- HK Wrapper -->
	<div class="hk-wrapper">

        <!-- Main Content -->
        <div class="hk-pg-wrapper bg-white hk-auth-wrapper">
            <header class="d-flex justify-content-between align-items-center">
                <a class="d-flex auth-brand" href="#">
                    <img class="brand-img" src="<?= base_url(); ?>dist/img/logo-dark.png" alt="brand" />
                </a>
              
            </header>
            
                    <div class="col-xl-7 pa-0">
                        <div class="auth-form-wrap py-xl-0 py-50">
                            <div class="auth-form w-xxl-65 w-xl-75 w-sm-90 w-100 card pa-25 shadow-lg">
							<?php if ($this->session->flashdata()) : ?>
                                    <div class="alert alert-danger" role="alert">
                                        <?php echo $this->session->flashdata('error'); ?>
                                    </div>
                                <?php endif; ?>
                                <?= form_open_multipart('login/aksi_login'); ?>
                                    <h1 class="display-4 mb-10">Pusata Kontrol</h1>
                                    <p class="mb-30">Masuk Sebagai Admin.</p>
                                    <div class="form-group">
                                        <input class="form-control" id="exampleInputEmail1" placeholder="Username" name="user_name" type="text" required>
                                    </div>
                                    <div class="form-group">
                                        <div class="input-group">
                                            <input class="form-control" id="exampleInputPassword1" placeholder="Password" type="password" name="password" required>
                                            <div class="input-group-append">
                                                <span class="input-group-text"><span class="feather-icon"><i data-feather="eye-off"></i></span></span>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="custom-control custom-checkbox mb-25">
                                        <input class="custom-control-input" id="same-address" type="checkbox" checked>
                                        <label class="custom-control-label font-14" for="same-address">Tetap Masuk</label>
                                    </div>
                                    <button class="btn btn-primary btn-block" type="submit">Login</button>
                                <?= form_close(); ?>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- /Main Content -->

    </div>
	<!-- /HK Wrapper -->

    <!-- jQuery -->
    <script src="<?= base_url(); ?>vendors/jquery/dist/jquery.min.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="<?= base_url(); ?>vendors/popper.js/dist/umd/popper.min.js"></script>
    <script src="<?= base_url(); ?>vendors/bootstrap/dist/js/bootstrap.min.js"></script>

    <!-- Slimscroll JavaScript -->
    <script src="<?= base_url(); ?>dist/js/jquery.slimscroll.js"></script>

    <!-- Fancy Dropdown JS -->
    <script src="<?= base_url(); ?>dist/js/dropdown-bootstrap-extended.js"></script>

    <!-- Owl JavaScript -->
    <script src="<?= base_url(); ?>vendors/owl.carousel/dist/owl.carousel.min.js"></script>

    <!-- FeatherIcons JavaScript -->
    <script src="<?= base_url(); ?>dist/js/feather.min.js"></script>

    <!-- Init JavaScript -->
    <script src="<?= base_url(); ?>dist/js/init.js"></script>
    <script src="<?= base_url(); ?>dist/js/login-data.js"></script>
</body>

</html>