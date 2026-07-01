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
    <meta name="description" content="Reset Pasword" />

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
        <div class="hk-pg-wrapper hk-auth-wrapper">
            <header class="d-flex justify-content-between align-items-center">
                <a class="d-flex auth-brand" href="#">
                    <img class="brand-img" src="<?= base_url(); ?>dist/img/logo-dark.png" alt="brand" />
                </a>
              
            </header>
            
                                <div class="bg-overlay bg-trans-dark-50"></div>
               
                    <div class="col-xl-7 pa-0">
                        <div class="auth-form-wrap py-xl-0 py-50">
                            <div class="auth-form w-xxl-65 w-xl-75 w-sm-90 w-100 card pa-25 shadow-lg">
							<?php if ($this->session->flashdata()) : ?>
                                    <div class="alert alert-danger" role="alert">
                                        <?php echo $this->session->flashdata('error'); ?>
                                    </div>
                                <?php endif; ?>
                                    <form class="login100-form validate-form flex-sb flex-w" method="post">
                                        <h1 class="display-4 mb-10"><?=$this->config->item("APPNAME");?></h1>
                                    <p class="mb-30">Atur Ulang Password</p>
                					<input type="hidden" name="token" value="<?= $user['token']; ?>" />
                					<input type="hidden" name="type" value="<?= $user['idkey']; ?>" />
                					<input type="hidden" name="id" value="<?= $user['userid']; ?>" />
                					<span class="login100-form-title p-b-51">
                						Reset Password
                					</span>
                                    
                					<div class="form-group" data-validate="Password tidak boleh kosong!">
                						<input class="form-control" type="password" name="password" id="password" placeholder="Password Baru">
                						<span class="focus-input100"></span>
                					</div>
                
                					<div class="form-group container-login100-form-btn m-t-17">
                						 <button class="btn btn-success btn-block" type="submit">Reset Password</button>
                					</div>
                
                				</form>
                                   
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