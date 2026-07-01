<!-- partial -->
<div class="container-fluid mt-xl-50 mt-sm-30 mt-15 px-xxl-65 px-xl-20">

    <div class="col-md-12 grid-margin stretch-card">
        <div class="card">
            <div class="card-body">
                <?php if ($this->session->flashdata('tambah') or $this->session->flashdata('ubah')) : ?>
                    <div class="alert alert-success" role="alert">
                        <?php echo $this->session->flashdata('tambah'); ?>
                        <?php echo $this->session->flashdata('ubah'); ?>
                    </div>
                <?php endif; ?>
                <?php if ($this->session->flashdata('demo') or $this->session->flashdata('hapus')) : ?>
                    <div class="alert alert-danger" role="alert">
                        <?php echo $this->session->flashdata('demo'); ?>
                        <?php echo $this->session->flashdata('hapus'); ?>
                    </div>
                <?php endif; ?>

              
                <div class="tab-minimal tab-minimal-success">
                    <ul class="nav nav-tabs" role="tablist">
                        <li class="nav-item">
                            <a class="nav-link active" id="tab-2-1" data-toggle="tab" href="#alldrivers-2-1" role="tab" aria-controls="alldrivers-2-1" aria-selected="true">
                                <i class="mdi mdi-motorbike"></i>Semua</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" id="tab-2-2" data-toggle="tab" href="#active-2-2" role="tab" aria-controls="active-2-2" aria-selected="false">
                                <i class="mdi mdi-account-settings"></i>Aktif</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" id="tab-2-4" data-toggle="tab" href="#suspended-2-4" role="tab" aria-controls="suspended-2-4" aria-selected="false">
                                <i class="mdi mdi-account-off"></i>Di Berhentikan</a>
                        </li>
                    </ul>
                    <div class="tab-content">
                        <!-- all driver -->
                        <div class="tab-pane fade show active" id="alldrivers-2-1" role="tabpanel" aria-labelledby="tab-2-1">
                            <div class="card">
                                <div class="card-body">
                                    <div class="row">
                                        <div class="col-12">
                                            <div class="table-responsive">
                                                <table id="mwtable" class="table table-striped table-hover dt-responsive display nowrap" data-info="false" cellspacing="0" width="100%">
                                                    <thead>
                                                        <tr>
                                                            <th>Detail</th>
                                                            <th>Id Mitra</th>
                                                            <th>Pemilik</th>
                                                            <th>Kontak</th>
                                                            <th>Nama Mitra</th>
                                                            <th>Profil</th>
                                                            <th>Layanan</th>
                                                            <th>Kategori</th>
                                                            <th>Status</th>
                                                            <th>Actions</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <?php $i = 1;
                                                        foreach ($mitra as $mtr) {
                                                            if ($mtr['status_mitra'] != 0) { ?>
                                                                <tr>
                                                                    <td>
                                                                        
                                                                    </td>
                                                                    <td><?= $mtr['id_mitra'] ?></td>
                                                                    <td><?= $mtr['nama_mitra'] ?></td>
                                                                    <td><?= $mtr['telepon_mitra'] ?></td>
                                                                    <td><?= $mtr['nama_merchant'] ?></td>
                                                                    <td>
                                                                        <img class="avatar-img rounded-circle" src="<?= base_url('images/merchant/') . $mtr['foto_merchant']; ?>" width="80" height="80">
                                                                    </td>
                                                                    <td><?= $mtr['fitur'] ?></td>
                                                                    <td><?= $mtr['nama_kategori'] ?></td>
                                                                    <td>
                                                                        <?php if ($mtr['status_mitra'] == 3) { ?>
                                                                            <label class="badge badge-dark">Diblokir</label>
                                                                        <?php } else { ?>
                                                                            <label class="badge badge-primary">Aktif</label>
                                                                        <?php } ?>
                                                                    </td>
                                                                    <td>
                                                                        <a href="<?= base_url(); ?>mitra/detail/<?= $mtr['id_mitra'] ?>">
                                                                            <button class="btn btn-outline-primary mr-2">Lihat</button>
                                                                        </a>
                                                                        <?php
                                                                        if ($mtr['status_mitra'] == 1) { ?>
                                                                            <a href="<?= base_url(); ?>mitra/block/<?= $mtr['id_mitra'] ?>"><button class="btn btn-outline-dark text-red mr-2">Block</button></a>
                                                                        <?php } else { ?>
                                                                            <a href="<?= base_url(); ?>mitra/unblock/<?= $mtr['id_mitra'] ?>"><button class="btn btn-outline-success text-red mr-2">Unblock</button></a>
                                                                        <?php } ?>
                                                                        <a href="<?= base_url(); ?>mitra/hapus/<?= $mtr['id_mitra'] ?>">
                                                                            <button onclick="return confirm ('Are you sure want to delete this Partner?')" class="btn btn-outline-danger text-red mr-2">Delete</button>
                                                                        </a>

                                                                    </td>
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
                            </div>
                        </div>
                        <!-- end of all driver -->

                        <!-- active driver -->
                        <div class="tab-pane fade" id="active-2-2" role="tabpanel" aria-labelledby="tab-2-2">
                            <div class="card">
                                <div class="card-body">
                                  
                                    <div class="row">
                                        <div class="col-12">
                                            <div class="table-responsive">
                                                <table id="tabwallet" class="table table-striped table-hover dt-responsive display nowrap" data-info="false" cellspacing="0" width="100%">
                                                    <thead>
                                                        <tr>
                                                            <th>No</th>
                                                            <th>Id Mitra</th>
                                                            <th>Pemilik</th>
                                                            <th>Kontak</th>
                                                            <th>Nama Mitra</th>
                                                            <th>Profil</th>
                                                            <th>Layanan</th>
                                                            <th>Kategori</th>
                                                            <th>Status</th>
                                                            <th>Actions</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <?php $i = 1;
                                                        foreach ($mitra as $mtr) {
                                                            if ($mtr['status_mitra'] == 1) { ?>
                                                                <tr>
                                                                    <td>
                                                                        <?= $i ?>
                                                                    </td>
                                                                    <td><?= $mtr['id_mitra'] ?></td>
                                                                    <td><?= $mtr['nama_mitra'] ?></td>
                                                                    <td><?= $mtr['telepon_mitra'] ?></td>
                                                                    <td><?= $mtr['nama_merchant'] ?></td>
                                                                    <td>
                                                                        <img class="avatar-img rounded-circle" src="<?= base_url('images/merchant/') . $mtr['foto_merchant']; ?>" width="80" height="80">
                                                                    </td>
                                                                    <td><?= $mtr['fitur'] ?></td>
                                                                    <td><?= $mtr['nama_kategori'] ?></td>
                                                                    <td>
                                                                        <?php if ($mtr['status_mitra'] == 3) { ?>
                                                                            <label class="badge badge-dark">Banned</label>
                                                                        <?php } else { ?>
                                                                            <label class="badge badge-primary">Active</label>
                                                                        <?php } ?>
                                                                    </td>
                                                                    <td>
                                                                        <a href="<?= base_url(); ?>mitra/detail/<?= $mtr['id_mitra'] ?>">
                                                                            <button class="btn btn-outline-primary mr-2">View</button>
                                                                        </a>
                                                                        <?php
                                                                        if ($mtr['status_mitra'] == 1) { ?>
                                                                            <a href="<?= base_url(); ?>mitra/block/<?= $mtr['id_mitra'] ?>"><button class="btn btn-outline-dark text-red mr-2">Block</button></a>
                                                                        <?php } else { ?>
                                                                            <a href="<?= base_url(); ?>mitra/unblock/<?= $mtr['id_mitra'] ?>"><button class="btn btn-outline-success text-red mr-2">Unblock</button></a>
                                                                        <?php } ?>
                                                                        <a href="<?= base_url(); ?>mitra/hapus/<?= $mtr['id_mitra'] ?>">
                                                                            <button onclick="return confirm ('Are you sure want to delete this Partner?')" class="btn btn-outline-danger text-red mr-2">Delete</button>
                                                                        </a>

                                                                    </td>
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
                            </div>
                        </div>
                        <!-- end of active driver -->

                        <!-- non active driver -->

                        <!-- end of non active driver -->

                        <!-- suspended drivers -->
                        <div class="tab-pane fade" id="suspended-2-4" role="tabpanel" aria-labelledby="tab-2-4">
                            <div class="card">
                                <div class="card-body">
                                  
                                    <div class="row">
                                        <div class="col-12">
                                            <div class="table-responsive">
                                                <table id="tabwallet2" class="table table-striped table-hover dt-responsive display nowrap" data-info="false" cellspacing="0" width="100%">
                                                    <thead>
                                                        <tr>
                                                            <th>No</th>
                                                            <th>Id Mitra</th>
                                                            <th>Pemilik</th>
                                                            <th>Kontak</th>
                                                            <th>Nama Mitra</th>
                                                            <th>Profil</th>
                                                            <th>Layanan</th>
                                                            <th>Kategori</th>
                                                            <th>Status</th>
                                                            <th>Actions</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <?php $i = 1;
                                                        foreach ($mitra as $mtr) {
                                                            if ($mtr['status_mitra'] == 3) { ?>
                                                                <tr>
                                                                    <td>
                                                                        <?= $i ?>
                                                                    </td>
                                                                    <td><?= $mtr['id_mitra'] ?></td>
                                                                    <td><?= $mtr['nama_mitra'] ?></td>
                                                                    <td><?= $mtr['telepon_mitra'] ?></td>
                                                                    <td><?= $mtr['nama_merchant'] ?></td>
                                                                    <td>
                                                                        <img src="<?= base_url('images/merchant/') . $mtr['foto_merchant']; ?>">
                                                                    </td>
                                                                    <td><?= $mtr['fitur'] ?></td>
                                                                    <td><?= $mtr['nama_kategori'] ?></td>
                                                                    <td>
                                                                        <?php if ($mtr['status_mitra'] == 3) { ?>
                                                                            <label class="badge badge-dark">Banned</label>
                                                                        <?php } else { ?>
                                                                            <label class="badge badge-primary">Active</label>
                                                                        <?php } ?>
                                                                    </td>
                                                                    <td>
                                                                        <a href="<?= base_url(); ?>mitra/detail/<?= $mtr['id_mitra'] ?>">
                                                                            <button class="btn btn-outline-primary mr-2">View</button>
                                                                        </a>
                                                                        <?php
                                                                        if ($mtr['status_mitra'] == 1) { ?>
                                                                            <a href="<?= base_url(); ?>mitra/block/<?= $mtr['id_mitra'] ?>"><button class="btn btn-outline-dark text-red mr-2">Block</button></a>
                                                                        <?php } else { ?>
                                                                            <a href="<?= base_url(); ?>mitra/unblock/<?= $mtr['id_mitra'] ?>"><button class="btn btn-outline-success text-red mr-2">Unblock</button></a>
                                                                        <?php } ?>
                                                                        <a href="<?= base_url(); ?>mitra/hapus/<?= $mtr['id_mitra'] ?>">
                                                                            <button onclick="return confirm ('Are you sure want to delete this Partner?')" class="btn btn-outline-danger text-red mr-2">Delete</button>
                                                                        </a>

                                                                    </td>
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
                            </div>
                        </div>
                        <!-- end of suspended driver -->

                    </div>
                </div>

            </div>
        </div>
    </div>

</div>
<!-- content-wrapper ends -->