<!-- partial -->
<div class="container-fluid mt-xl-50 mt-sm-30 mt-15 px-xxl-65 px-xl-20">
    
    <div class="row">
        <div class="col-md-12 grid-margin stretch-card">
            <div class="card">
                <div class="card-body">
                    <?php if ($this->session->flashdata()) : ?>
                        <div class="alert alert-success" role="alert">
                            <?php echo $this->session->flashdata('ubah'); ?>
                        </div>
                    <?php endif; ?>
                    <div class="tab-minimal tab-minimal-success">
                        <ul class="nav nav-tabs" role="tablist">
                            <li class="nav-item">
                                <a class="nav-link active" id="tab-2-1" data-toggle="tab" href="#allwallet-2-1" role="tab" aria-controls="allwallet-2-1" aria-selected="true">
                                    <i class="mdi mdi-rotate-3d"></i>Semua</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" id="tab-2-2" data-toggle="tab" href="#topup-2-2" role="tab" aria-controls="topup-2-2" aria-selected="false">
                                    <i class="mdi mdi-import"></i>Topup</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" id="tab-2-3" data-toggle="tab" href="#withdraw-2-3" role="tab" aria-controls="withdraw-2-3" aria-selected="false">
                                    <i class="mdi mdi-export"></i>Penarikan</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" id="tab-2-4" data-toggle="tab" href="#transaction-2-4" role="tab" aria-controls="transaction-2-4" aria-selected="false">
                                    <i class="mdi mdi-motorbike"></i>Transaksi</a>
                            </li>
                        </ul>
                        </ul>
                        <div class="tab-content">

                            <!-- all wallet -->
                            <div class="tab-pane fade show active" id="allwallet-2-1" role="tabpanel" aria-labelledby="tab-2-1">
                                <div class="card">
                                    <div class="card-body">
                                     
                                        <div class="row">
                                            <div class="col-12">
                                                <div class="table-responsive">
                                                    <table id="tabwallet" class="table table-striped table-hover dt-responsive display nowrap" data-info="false" cellspacing="0" width="100%">
                                                        <thead>
                                                            <tr>
                                                                <th>No.</th>
                                                                <th>Tanggal</th>
                                                                <th>Driver/User</th>
                                                                <th>Nama</th>
                                                                <th>Jumlah</th>
                                                                <th>Tipe</th>
                                                                <th>Status</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                            <?php $i = 1;
                                                            foreach ($wallet as $wlt) { ?>
                                                                <tr>
                                                                    <td><?= $i ?></td>
                                                                    <td><?= $wlt['waktu'] ?></td>

                                                                    <?php $caracter = substr($wlt['id_user'], 0, 1);
                                                                    if ($caracter == 'P') { ?>
                                                                        <td class="text-primary">Customer</td>
                                                                    <?php } elseif ($caracter == 'M') { ?>
                                                                        <td class="text-success">Mitra</td>
                                                                    <?php } else { ?>
                                                                        <td class="text-warning">Driver</td>

                                                                    <?php } ?>

                                                                    <td><?= $wlt['nama_driver'] ?><?= $wlt['fullnama'] ?><?= $wlt['nama_mitra'] ?></td>
                                                                    <?php if ($wlt['type'] == 'topup' or $wlt['type'] == 'Order+') { ?>
                                                                        <td class="text-success">
                                                                            <?= $currency['duit'] ?>
                                                                            <?= rupiah($wlt['jumlah']) ?>
                                                                        </td>

                                                                    <?php } else { ?>
                                                                        <td class="text-danger">
                                                                            <?= $currency['duit'] ?>
                                                                            <?= rupiah($wlt['jumlah']) ?>
                                                                        </td>
                                                                    <?php } ?>



                                                                    <?php if ($wlt['type'] == 'topup' or $wlt['type'] == 'Order+') { ?>
                                                                        <td>
                                                                            <label class="badge badge-outline-success"><?= $wlt['type'] ?></label>
                                                                        </td>
                                                                    <?php } else { ?>
                                                                        <td>
                                                                            <label class="badge badge-outline-danger"><?= $wlt['type'] ?></label>
                                                                        </td>
                                                                    <?php } ?>

                                                                    <?php if ($wlt['status'] == '0') { ?>
                                                                        <td>
                                                                            <label class="badge badge-secondary text-dark">Ditunda</label>
                                                                        </td>
                                                                    <?php }
                                                                    if ($wlt['status'] == '1') { ?>
                                                                        <td>
                                                                            <label class="badge badge-success">Berhasil</label>
                                                                        </td>
                                                                    <?php }
                                                                    if ($wlt['status'] == '2') { ?>
                                                                        <td>
                                                                            <label class="badge badge-danger">Dibatalkan</label>
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
                            <!-- end of all wallet -->

                            <!-- top up -->
                            <div class="tab-pane fade" id="topup-2-2" role="tabpanel" aria-labelledby="tab-2-2">
                                <div class="card">
                                    <div class="card-body">
                                        <div>
                                            <a class="btn btn-info" href="<?= base_url(); ?>wallet/tambahtopup"><i class="mdi mdi-plus-circle-outline"></i>Tambah Top Up</a>
                                        </div>
                                        <br>
                                        <div class="row">
                                            <div class="col-12">
                                                <div class="table-responsive">
                                                    <table id="tabwallet2" class="table table-striped table-hover dt-responsive display nowrap" data-info="false" cellspacing="0" width="100%">
                                                        <thead>
                                                            <tr>
                                                                <th>No.</th>
                                                                <th>Nota</th>
                                                                <th>Tanggal</th>
                                                                <th>Driver/User</th>
                                                                <th>Nama</th>
                                                                <th>Jumlah</th>
                                                                <th>Bank</th>
                                                                <th>Atas Nama</th>
                                                                <th>Rekening</th>
                                                                <th>Status</th>
                                                                <th>Aksi</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                            <?php $i = 1;
                                                            foreach ($wallet as $wlt) {
                                                                if ($wlt['type'] == 'topup') { ?>
                                                                    <tr>
                                                                        <td><?= $i ?></td>
                                                                        <td><?= $wlt['id'] ?></td>
                                                                        <td><?= $wlt['waktu'] ?></td>

                                                                        <?php $caracter = substr($wlt['id_user'], 0, 1);
                                                                        if ($caracter == 'P') { ?>
                                                                            <td class="text-primary">Customer</td>
                                                                        <?php } elseif ($caracter == 'M') { ?>
                                                                            <td class="text-success">Mitra</td>
                                                                        <?php } else { ?>
                                                                            <td class="text-warning">Driver</td>

                                                                        <?php } ?>

                                                                        <td><?= $wlt['nama_driver'] ?><?= $wlt['fullnama'] ?><?= $wlt['nama_mitra'] ?></td>
                                                                        <td class="text-success"><?= $currency['duit'] ?>
                                                                            <?= rupiah($wlt['jumlah']) ?></td>
                                                                        <td><?= $wlt['bank'] ?></td>
                                                                        <td><?= $wlt['nama_pemilik'] ?></td>
                                                                        <?php if ($wlt['bank'] == 'QRIS') { ?>
                                                                            <td>"QR CODE"</td>
                                                                        <?php } else { ?>
                                                                            <td><?= $wlt['rekening'] ?></td>
                                                                        <?php } ?>
                                                                        <?php if ($wlt['status'] == '0') { ?>
                                                                            <td>
                                                                                <label class="badge badge-secondary text-dark">Ditunda</label>
                                                                            </td>
                                                                        <?php }
                                                                        if ($wlt['status'] == '1') { ?>
                                                                            <td>
                                                                                <label class="badge badge-success">Berhasil</label>
                                                                            </td>
                                                                        <?php }
                                                                        if ($wlt['status'] == '2') { ?>
                                                                            <td>
                                                                                <label class="badge badge-danger">Dibatalkan</label>
                                                                            </td>
                                                                        <?php } ?>
                                                                        <td>
                                                                            <?php if ($wlt['status'] == '0') { ?>
                                                                                <a href="<?= base_url(); ?>wallet/tconfirm/<?= $wlt['id'] ?>/<?= $wlt['id_user'] ?>/<?= $wlt['jumlah'] ?>">
                                                                                    <button class="btn btn-outline-primary">Konfirmasi</button></a>
                                                                                <a href="<?= base_url(); ?>wallet/tcancel/<?= $wlt['id'] ?>/<?= $wlt['id_user'] ?>">
                                                                                    <button onclick="return confirm ('Are You Sure?')" class="btn btn-outline-danger">Batal</button></a>
                                                                            <?php } else { ?>
                                                                                <span class="btn btn-outline-muted">Selesai</span>
                                                                            <?php } ?>

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
                            <!-- end of top up -->

                            <!-- withdraw -->
                            <div class="tab-pane fade" id="withdraw-2-3" role="tabpanel" aria-labelledby="tab-2-3">
                                <div class="card">
                                    <div class="card-body">
                                        <div>
                                            <a class="btn btn-info" href="<?= base_url(); ?>wallet/tambahwithdraw"><i class="mdi mdi-plus-circle-outline"></i>Tambah Penarikan</a>
                                        </div>
                                        <br>
                                        <h4 class="card-title">Semua Penarikan</h4>
                                        <div class="row">
                                            <div class="col-12">
                                                <div class="table-responsive">
                                                    <table id="tabwallet3" class="table table-striped table-hover dt-responsive display nowrap" data-info="false" cellspacing="0" width="100%">
                                                        <thead>
                                                            <tr>
                                                                <th>No.</th>
                                                                <th>No</th>
                                                                <th>Tanggal</th>
                                                                <th>Driver/User</th>
                                                                <th>Nama</th>
                                                                <th>Jumlah</th>
                                                                <th>Bank</th>
                                                                <th>Atas Nama</th>
                                                                <th>Rekening</th>
                                                                <th>Status</th>
                                                                <th>Aksi</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                            <?php $i = 1;
                                                            foreach ($wallet as $wlt) {
                                                                if ($wlt['type'] == 'withdraw') { ?>
                                                                    <tr>
                                                                        <td><?= $i ?></td>
                                                                        <td><?= $wlt['id'] ?></td>
                                                                        <td><?= $wlt['waktu'] ?></td>

                                                                        <?php $caracter = substr($wlt['id_user'], 0, 1);
                                                                        if ($caracter == 'P') { ?>
                                                                            <td class="text-primary">Customer</td>
                                                                        <?php } elseif ($caracter == 'M') { ?>
                                                                            <td class="text-success">Mitra</td>
                                                                        <?php } else { ?>
                                                                            <td class="text-warning">Driver</td>

                                                                        <?php } ?>

                                                                        <td><?= $wlt['nama_driver'] ?><?= $wlt['fullnama'] ?><?= $wlt['nama_mitra'] ?></td>

                                                                        <td class="text-danger"><?= $currency['duit'] ?>
                                                                            <?= rupiah($wlt['jumlah']) ?></td>
                                                                        <td><?= $wlt['bank'] ?></td>
                                                                        <td><?= $wlt['nama_pemilik'] ?></td>
                                                                        <?php if ($wlt['bank'] == 'QRIS') { ?>
                                                                            <td>"QR CODE"</td>
                                                                        <?php } else { ?>
                                                                            <td><?= $wlt['rekening'] ?></td>
                                                                        <?php } ?>
                                                                        <?php if ($wlt['status'] == '0') { ?>
                                                                            <td>
                                                                                <label class="badge badge-secondary text-dark">Ditunda</label>
                                                                            </td>
                                                                        <?php }
                                                                        if ($wlt['status'] == '1') { ?>
                                                                            <td>
                                                                                <label class="badge badge-success">Berhasil</label>
                                                                            </td>
                                                                        <?php }
                                                                        if ($wlt['status'] == '2') { ?>
                                                                            <td>
                                                                                <label class="badge badge-danger">Dibatalkan</label>
                                                                            </td>
                                                                        <?php } ?>



                                                                        <td>
                                                                            <?php if ($wlt['status'] == '0') { ?>
                                                                                <a href="<?= base_url(); ?>wallet/wconfirm/<?= $wlt['id'] ?>/<?= $wlt['id_user'] ?>/<?= $wlt['jumlah'] ?>">
                                                                                    <button class="btn btn-outline-primary">Konfirmasi</button></a>
                                                                                <a href="<?= base_url(); ?>wallet/wcancel/<?= $wlt['id'] ?>/<?= $wlt['id_user'] ?>">
                                                                                    <button onclick="return confirm ('Are You Sure?')" class="btn btn-outline-danger">Batal</button></a>
                                                                            <?php } else { ?>
                                                                                <span class="btn btn-outline-muted">Selesai</span>
                                                                            <?php } ?>

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
                            <!-- end of withdraw -->

                            <!-- transaction -->
                            <div class="tab-pane fade" id="transaction-2-4" role="tabpanel" aria-labelledby="tab-2-4">
                                <div class="card">
                                    <div class="card-body">
                                        <h4 class="card-title">Semua Transaksi</h4>
                                        <div class="row">
                                            <div class="col-12">
                                                <div class="table-responsive">
                                                   <table id="tabwallet4" class="table table-striped table-hover dt-responsive display nowrap" data-info="false" cellspacing="0" width="100%">
                                                        <thead>
                                                            <tr>
                                                                <th>No.</th>
                                                                <th>Nota</th>
                                                                <th>Layanan</th>
                                                                <th>Tanggal</th>
                                                                <th>Driver/User</th>
                                                                <th>Nama</th>
                                                                <th>Jumlah</th>
                                                                <th>Tipe</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                            <?php $i = 1;
                                                            foreach ($wallet as $wlt) {
                                                                if ($wlt['type'] == 'Order+' or $wlt['type'] == 'Order-') { ?>
                                                                    <tr>
                                                                        <td><?= $i ?></td>
                                                                        <td><?= $wlt['id'] ?></td>
                                                                        <td><?= $wlt['bank'] ?></td>
                                                                        <td><?= $wlt['waktu'] ?></td>

                                                                        <?php $caracter = substr($wlt['id_user'], 0, 1);
                                                                        if ($caracter == 'P') { ?>
                                                                            <td class="text-primary">Customer</td>
                                                                        <?php } elseif ($caracter == 'M') { ?>
                                                                            <td class="text-success">Mitra</td>
                                                                        <?php } else { ?>
                                                                            <td class="text-warning">Driver</td>

                                                                        <?php } ?>

                                                                        <td><?= $wlt['nama_driver'] ?><?= $wlt['fullnama'] ?><?= $wlt['nama_mitra'] ?></td>

                                                                        <?php if ($wlt['type'] == 'Order+') { ?>
                                                                            <td class="text-success"><?= $currency['duit'] ?>
                                                                                <?= rupiah($wlt['jumlah']) ?></td>
                                                                        <?php } else { ?>
                                                                            <td class="text-danger"><?= $currency['duit'] ?>
                                                                                <?= rupiah($wlt['jumlah']) ?></td>
                                                                        <?php } ?>

                                                                        <?php if ($wlt['type'] == 'Order+') { ?>
                                                                            <td>
                                                                                <label class="badge badge-primary">IN</label>
                                                                            </td>
                                                                        <?php } else { ?>
                                                                            <td>
                                                                                <label class="badge badge-danger">OUT</label>
                                                                            </td>
                                                                        <?php } ?>
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
                                    <!-- end of transaction -->

                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>