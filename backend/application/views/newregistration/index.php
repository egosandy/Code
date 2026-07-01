<div class="container-fluid mt-xl-50 mt-sm-30 mt-15 px-xxl-65 px-xl-20">
    <div class="card">
        <div class="card-body">
            <div>
                <a class="btn btn-info" href="<?= base_url(); ?>driver/tambah">
                    <i class="mdi mdi-plus-circle-outline"></i>Tambah Driver</a>
            </div>
            <br>
            <h4 class="card-title">Registrasi Driver</h4>
            <div class="row">
                <div class="col-12">
                    <div class="table-responsive">
                        <table id="mwtable1" class="table table-striped table-hover dt-responsive display nowrap" data-info="false" cellspacing="0" width="100%">
                            <thead>
                                <tr>
                                    <th>+/-</th>
                                    <th>Id Driver</th>
                                    <th>Profil</th>
                                    <th>Nama</th>
                                    <th>Kontak</th>
                                    <th>Rating</th>
                                    <th>Job</th>
                                    <th>Status</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <?php $i = 1;
                                foreach ($driver as $drv) {
                                    if ($drv['status'] == 0 || $drv['status'] == 5) { ?>
                                        <tr>
                                            <td>
                                                
                                            </td>
                                            <td><?= $drv['id'] ?></td>
                                            <td>
                                                <img src="<?= base_url('images/fotodriver/') . $drv['foto']; ?>" width="80" height="80">
                                            </td>
                                            <td><?= $drv['nama_driver'] ?></td>
                                            <td><?= $drv['no_telepon'] ?></td>
                                            <td><?= rupiah($drv['rating'], 1) ?></td>
                                            <td><?= $drv['driver_job'] ?></td>
                                            <td>
                                                <?php if ($drv['status'] == 3) { ?>
                                                    <label class="badge badge-dark">Diblokir</label>
                                                <?php } elseif ($drv['status'] == 0) { ?>
                                                    <label class="badge badge-secondary text-dark">Registrasi</label>
                                                <?php } elseif ($drv['status'] == 5) { ?>
                                                    <label class="badge badge-warning text-white">Menunggu</label>
                                                    <?php } else {
                                           if ($drv['status_job'] == 1) { ?>
                                                        <label class="badge badge-primary">Aktif</label>
                                                    <?php }
                                                    if ($drv['status_job'] == 2) { ?>
                                                        <label class="badge badge-info">Memproses</label>
                                                    <?php }
                                                    if ($drv['status_job'] == 3) { ?>
                                                        <label class="badge badge-success">Pengantaran</label>
                                                    <?php }
                                                    if ($drv['status_job'] == 4) { ?>
                                                        <label class="badge badge-danger">Nonaktif</label>
                                                <?php }
                                                } ?>
                                            </td>
                                            <td>
                                                <a href="<?= base_url(); ?>driver/detail/<?= $drv['id'] ?>">
                                                    <button class="btn btn-outline-info mr-2">Lihat</button>
                                                </a>
                                                <a href="<?= base_url(); ?>newregistration/confirm/<?= $drv['id'] ?>">
                                                    <button class="btn btn-outline-primary mr-2">Konfirmasi</button>
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