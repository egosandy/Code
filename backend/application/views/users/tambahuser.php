<!-- partial -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
 <div class="container-fluid mt-xl-50 mt-sm-30 mt-15 px-xxl-65 px-xl-20">
    <div class="row ">
        <div class="col-md-8 offset-md-2 grid-margin stretch-card">
            <div class="card">
                <div class="card-body">
                    <h4 class="card-title">Add Users</h4>
                    <?php if ($this->session->flashdata() or validation_errors()) : ?>
                        <div class="alert alert-danger" role="alert">
                            <?= validation_errors() ?>
                            <?php echo $this->session->flashdata('invalid'); ?>
                            <?php echo $this->session->flashdata('demo'); ?>
                        </div>
                    <?php endif; ?>
                    <?= form_open_multipart('users/tambah'); ?>

                    <div class="form-group">
                        <label>Foto Profile</label>
                        <input id="uploadProfile" type="file" class="dropify" name="fotopelanggan" onchange="PreviewProfile();" data-max-file-size="3mb" />
                                                         <img id="ProfilePreview"  style="width: 300px; height: 200px;" />
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
                    <div class="form-group">
                        <label for="fullnama">Name</label>
                        <input type="text" class="form-control" id="fullnama" name="fullnama" placeholder="enter name" <?php if ($_POST != NULL) { ?> value="<?= $_POST['fullnama']; ?>" <?php } ?> required>
                    </div>

                    <div class="form-group">
                        <label for="tgl_lahir">Birth Date</label>
                        <input type="date" class="form-control" id="tgl_lahir" name="tgl_lahir" placeholder="enter birth date " <?php if ($_POST != NULL) { ?> value="<?= $_POST['tgl_lahir']; ?>" <?php } ?> required>
                    </div>

                    <div class="form-group">
                        <label for="email">Email</label>
                        <input type="email" class="form-control" id="email" name="email" placeholder="enter email " <?php if ($_POST != NULL) { ?> value="<?= $_POST['email']; ?>" <?php } ?> required>
                    </div>

                    <label class="text-small">Phone Number</label>
                    <div class="row">

                        <div class="form-group col-2">
                            <input type="tel" id="txtPhone" class="form-control" name="countrycode" placeholder="+1 " <?php if ($_POST != NULL) { ?> value="<?= $_POST['countrycode']; ?>" <?php } ?> required>
                        </div>
                        <div class=" form-group col-10">
                            <input type="text" class="form-control" id="phone" name="phone" placeholder="enter phone number" <?php if ($_POST != NULL) { ?> value="<?= $_POST['phone']; ?>" <?php } ?>>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="password">Password</label>
                        <input type="password" class="form-control" id="password" name="password" placeholder="enter password" required>
                    </div>
                    <button type="submit" class="btn btn-success mr-2">Submit</button>
                    <button class="btn btn-light">Cancel</button>
                    <?= form_close(); ?>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(function() {
        var code = "+62"; // Assigning value from model.
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
<!-- end of content wrapper -->