<!-- partial -->
<div class="container-fluid mt-xl-50 mt-sm-30 mt-15 px-xxl-65 px-xl-20">
    <div class="col-md-12 grid-margin stretch-card">
        <div class="card">
            <div class="card-body">
                <div>
                    <a class="btn btn-info" href="<?= base_url(); ?>users/tambah">
                        <i class="mdi mdi-plus-circle-outline"></i>Tambah</a>
                </div>
                <br>
                <?php if ($this->session->flashdata('demo') or $this->session->flashdata('hapus')) : ?>
                    <div class="alert alert-danger" role="alert">
                        <?php echo $this->session->flashdata('demo'); ?>
                        <?php echo $this->session->flashdata('hapus'); ?>
                    </div>
                <?php endif; ?>
                <?php if ($this->session->flashdata('ubah') or $this->session->flashdata('tambah')) : ?>
                    <div class="alert alert-danger" role="alert">
                        <?php echo $this->session->flashdata('ubah'); ?>
                        <?php echo $this->session->flashdata('tambah'); ?>
                    </div>
                <?php endif; ?>
               
                <div class="tab-minimal tab-minimal-success">
                    <ul class="nav nav-tabs" role="tablist">
                        <li class="nav-item">
                            <a class="nav-link active" id="tab-2-1" data-toggle="tab" href="#allusers-2-1" role="tab" aria-controls="allusers-2-1" aria-selected="true">
                                <i class="mdi mdi-account"></i>Semua Customer</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" id="tab-2-2" data-toggle="tab" href="#blocked-2-2" role="tab" aria-controls="blocked-2-2" aria-selected="false">
                                <i class="mdi mdi-account-off"></i>Daftar Blokir</a>
                        </li>
                    </ul>
                    <div class="tab-content">

                        <!-- all users -->
                        <div class="tab-pane fade show active" id="allusers-2-1" role="tabpanel" aria-labelledby="tab-2-1">
                            <div class="card">
                                <div class="card-body">
                                    <div class="row">
                                        <div class="col-12">
                                            <div class="table-responsive">
                                                <table id="mwtable" class="table table-striped table-hover dt-responsive display nowrap" data-info="false" cellspacing="0" width="100%">
                                                    <thead>
                                                        <tr>
                                                            <th>Aksi</th>
                                                            <th>Customer Id</th>
                                                            <th>Profil</th>
                                                            <th>Nama</th>
                                                            <th>Email</th>
                                                            <th>Kontak</th>
                                                            <th>Status</th>
                                                            <th>Actions</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <?php $i = 1;
                                                        foreach ($user as $us) { ?>
                                                            <tr>
                                                                <td></td>
                                                                <td><?= $us['id'] ?></td>
                                                                <td>
                                                                    <img class="avatar-img rounded-circle" src="<?= base_url('images/pelanggan/') . $us['fotopelanggan']; ?>" width="80" height="80">
                                                                </td>
                                                                <td><?= $us['fullnama'] ?></td>
                                                                <td><?= $us['email'] ?></td>
                                                                <td><?= $us['no_telepon'] ?></td>
                                                                <td>
                                                                    <?php if ($us['status'] == 1) { ?>
                                                                        <label class="badge badge-success">Active</label>
                                                                    <?php } else { ?>
                                                                        <label class="badge badge-dark">Blocked</label>
                                                                    <?php } ?>
                                                                </td>
                                                                <td>
                                                                    <a href="<?= base_url(); ?>users/detail/<?= $us['id'] ?>">
                                                                        <button class="btn btn-outline-primary mr-2">View</button>
                                                                    </a>
                                                                    <?php if ($us['status'] == 0) { ?>
                                                                        <a href="<?= base_url(); ?>users/userunblock/<?= $us['id'] ?>">
                                                                            <button class="btn btn-outline-success text-red mr-2">Unblock</button>
                                                                        </a>
                                                                    <?php } else { ?>
                                                                        <a href="<?= base_url(); ?>users/userblock/<?= $us['id'] ?>">
                                                                            <button class="btn btn-outline-dark text-dark mr-2">Block</button>
                                                                        </a>
                                                                    <?php } ?>
                                                                    <a href="<?= base_url(); ?>users/hapususers/<?= $us['id'] ?>">
                                                                        <button onclick="return confirm ('Are You Sure?')" class="btn btn-outline-danger text-red mr-2">Delete</button>
                                                                    </a>
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
                            </div>
                        </div>
                        <!-- end of all users -->

                        <!-- blocked users -->
                        <div class="tab-pane fade" id="blocked-2-2" role="tabpanel" aria-labelledby="tab-2-2">
                            <div class="card">
                                <div class="card-body">
                         
                                    <div class="row">
                                        <div class="col-12">
                                            <div class="table-responsive">
                                                <table id="mwtable1" class="table table-striped table-hover dt-responsive display nowrap" data-info="false" cellspacing="0" width="100%">
                                                    <thead>
                                                        <tr>
                                                            <th>No</th>
                                                            <th>Customer Id</th>
                                                            <th>Profil</th>
                                                            <th>Nama</th>
                                                            <th>Email</th>
                                                            <th>Kontak</th>
                                                            <th>Status</th>
                                                            <th>Actions</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <?php $i = 1;
                                                        foreach ($user as $us) {
                                                            if ($us['status'] == 0) { ?>
                                                                <tr>
                                                                    <td><?= $i ?></td>
                                                                    <td><?= $us['id'] ?></td>
                                                                    <td>
                                                                        <img src="<?= base_url('images/pelanggan/') . $us['fotopelanggan']; ?>">
                                                                    </td>
                                                                    <td><?= $us['fullnama'] ?></td>
                                                                    <td><?= $us['email'] ?></td>
                                                                    <td><?= $us['no_telepon'] ?></td>
                                                                    <td>
                                                                        <?php if ($us['status'] == 1) { ?>
                                                                            <label class="badge badge-success">Active</label>
                                                                        <?php } else { ?>
                                                                            <label class="badge badge-dark">Blocked</label>
                                                                        <?php } ?>
                                                                    </td>
                                                                    <td>
                                                                        <a href="<?= base_url(); ?>users/detail/<?= $us['id'] ?>">
                                                                            <button class="btn btn-outline-primary mr-2">View</button>
                                                                        </a>
                                                                        <a href="<?= base_url(); ?>users/userunblock/<?= $us['id'] ?>">
                                                                            <button class="btn btn-outline-success text-red mr-2">Unblock</button>
                                                                        </a>

                                                                        <a href="<?= base_url(); ?>users/hapususers/<?= $us['id'] ?>">
                                                                            <button class="btn btn-outline-danger text-red mr-2">Delete</button>
                                                                        </a>
                                                                    </td>
                                                            <?php $i++;
                                                            }
                                                        } ?>
                                                                </tr>

                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- end of blocked -->

                    </div>
                </div>
            </div>
        </div>
    </div>

</div>
<!-- content-wrapper ends -->