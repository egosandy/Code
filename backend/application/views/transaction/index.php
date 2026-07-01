<div class="container-fluid mt-xl-50 mt-sm-30 mt-15 px-xxl-65 px-xl-20">
    <div class="card">
        <div class="card-body">
            <div class="row">
                <div class="col-12">
                    <?php if ($this->session->flashdata()) : ?>
                        <div class="alert alert-danger" role="alert">
                            <?php echo $this->session->flashdata('demo'); ?>
                            <?php echo $this->session->flashdata('cancel'); ?>
                            <?php echo $this->session->flashdata('hapus'); ?>
                        </div>
                    <?php endif; ?>
                    <div class="tab-minimal tab-minimal-success">
                        <ul class="nav nav-tabs" role="tablist">
                            <li class="nav-item">
                                <a class="nav-link active" id="tab-2-1" data-toggle="tab" href="#alltrans-2-1" role="tab" aria-controls="alltrans-2-1" aria-selected="true">
                                    <i class="mdi mdi-rotate-3d"></i>Semua</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" id="tab-2-2" data-toggle="tab" href="#pending-2-2" role="tab" aria-controls="pending-2-2" aria-selected="false">
                                    <i class="mdi mdi-import"></i>Pending</a>
                            </li>
                            
                        </ul>
                    </div>
                    <div class="tab-content">
                        <div class="tab-pane fade show active" id="alltrans-2-1" role="tabpanel" aria-labelledby="tab-2-1">
                            <div class="card">
                                <div class="card-body">
                                    <div class="row">
                                        <div class="col-12">
                                            <div class="table-responsive">
                                                <table id="mwtable1" class="table table-hover w-100 display pb-30" data-info="false">
                                                    <thead>
                                                        <tr>
                                                            <th>+/-</th>
                                                            <th>No</th>
                                                            <th>Customer</th>
                                                            <th>Driver</th>
                                                            <th>Layanan</th>
                                                            <th>Harga</th>
                                                            <th>Pembayaran</th>
                                                            <th>Status</th>
                                                            <th>Actions</th>
                        									<th>Ambil</th>
                                                            <th>Tujuan</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <?php $i = 1;
                                                        foreach ($transaksi as $tr) { ?>
                        
                                                            <tr>
                                                                <td></td>
                                                                <td><?= $i;?></td>
                                                                <td><?= $tr['fullnama'] ?></td>
                                                                <td><?= $tr['nama_driver'] ?></td>
                                                                <td><?= $tr['fitur'] ?></td>
                                                                <td><?= $currency['app_currency'] ?>
                                                                    <?= rupiah($tr['biaya_akhir']) ?></td>
                                                                <td>
                                                                    <?php if ($tr['pakai_wallet'] == '0') {
                                                                        echo 'CASH';
                                                                    } else {
                                                                        echo 'WALLET';
                                                                    } ?>
                                                                </td>
                                                                <td>
                                                                    <?php if ($tr['status'] == '2') { ?>
                                                                        <label class="badge badge-primary"><?= $tr['status_transaksi']; ?></label>
                                                                    <?php } ?>
                                                                    <?php if ($tr['status'] == '3') { ?>
                                                                        <label class="badge badge-success"><?= $tr['status_transaksi']; ?></label>
                                                                    <?php } ?>
                                                                    <?php if ($tr['status'] == '5') { ?>
                                                                        <label class="badge badge-danger"><?= $tr['status_transaksi']; ?></label>
                                                                    <?php } ?>
                                                                    <?php if ($tr['status'] == '4') { ?>
                                                                        <label class="badge badge-info"><?= $tr['status_transaksi']; ?></label>
                                                                    <?php } ?>
                                                                    <?php if ($tr['status'] == '9') { ?>
                                                                        <label class="badge badge-warning"><?= $tr['status_transaksi']; ?></label>
                                                                    <?php } ?>
                                                                </td>
                                                                <td>
                                                                    <a href="<?= base_url(); ?>dashboard/detail/<?= $tr['id_transaksi']; ?>" class="btn btn-outline-primary">View</a>
                                                                    <a onclick="return confirm ('Are You Sure?')" href="<?= base_url(); ?>dashboard/delete/<?= $tr['id_transaksi']; ?>" class="btn btn-outline-danger">Delete</a>
                                                                </td>
                        										 <td style="max-width:300px;"><?= $tr['alamat_asal'] ?></td>
                                                                <td style="max-width:300px;"><?= $tr['alamat_tujuan'] ?></td>
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
                        <div class="tab-pane fade" id="pending-2-2" role="tabpanel" aria-labelledby="tab-2-2">
                            <div class="card">
                                <div class="card-body">
                                    <div class="row">
                                        <div class="col-12">
                                            <div class="table-responsive">
                                                <table id="mwtable2" class="table table-striped table-hover dt-responsive display nowrap" data-info="false" cellspacing="0" width="100%">
                                                    <thead>
                                                        <tr>
                                                            <th>+/-</th>
                                                            <th>No</th>
                                                            <th>Customer</th>
                                                            <th>Driver</th>
                                                            <th>Layanan</th>
                                                            <th>Harga</th>
                                                            <th>Pembayaran</th>
                                                            <th>Status</th>
                                                            <th>Actions</th>
                        									<th>Ambil</th>
                                                            <th>Tujuan</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <?php $i = 1;
                                                        foreach ($transaksi as $tr)
                                                            if($tr['status'] == '9') { ?>
                                                            <tr>
                                                                <td></td>
                                                                <td><?= $i;?></td>
                                                                <td><?= $tr['fullnama'] ?></td>
                                                                <td><?= $tr['nama_driver'] ?></td>
                                                                <td><?= $tr['fitur'] ?></td>
                                                                <td><?= $currency['app_currency'] ?>
                                                                    <?= rupiah($tr['biaya_akhir']) ?></td>
                                                                <td>
                                                                    <?php if ($tr['pakai_wallet'] == '0') {
                                                                        echo 'CASH';
                                                                    } else {
                                                                        echo 'WALLET';
                                                                    } ?>
                                                                </td>
                                                                <td>
                                                                    <?php if ($tr['status'] == '2') { ?>
                                                                        <label class="badge badge-primary"><?= $tr['status_transaksi']; ?></label>
                                                                    <?php } ?>
                                                                    <?php if ($tr['status'] == '3') { ?>
                                                                        <label class="badge badge-success"><?= $tr['status_transaksi']; ?></label>
                                                                    <?php } ?>
                                                                    <?php if ($tr['status'] == '5') { ?>
                                                                        <label class="badge badge-danger"><?= $tr['status_transaksi']; ?></label>
                                                                    <?php } ?>
                                                                    <?php if ($tr['status'] == '4') { ?>
                                                                        <label class="badge badge-info"><?= $tr['status_transaksi']; ?></label>
                                                                    <?php } ?>
                                                                    <?php if ($tr['status'] == '9') { ?>
                                                                        <label class="badge badge-warning"><?= $tr['status_transaksi']; ?></label>
                                                                    <?php } ?>
                                                                </td>
                                                                <td>
                                                                    <a href="<?= base_url(); ?>dashboard/detail/<?= $tr['id_transaksi']; ?>" class="btn btn-outline-primary">View</a>
                                                                    <a onclick="return confirm ('Are You Sure?')" href="<?= base_url(); ?>dashboard/delete/<?= $tr['id_transaksi']; ?>" class="btn btn-outline-danger">Delete</a>
                                                                </td>
                        										 <td style="max-width:300px;"><?= $tr['alamat_asal'] ?></td>
                                                                <td style="max-width:300px;"><?= $tr['alamat_tujuan'] ?></td>
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