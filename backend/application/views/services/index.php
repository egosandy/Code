 <div class="container-fluid mt-xl-50 mt-sm-30 mt-15 px-xxl-65 px-xl-20">
    <div class="row grid-margin">
        <div class="col-12">
            <div class="card">
                <div class="card-body">
                    <?php if ($this->session->flashdata('ubah')) : ?>
                        <div class="alert alert-success" role="alert">
                            <?php echo $this->session->flashdata('ubah'); ?>
                        </div>
                    <?php endif; ?>
                    <?php if ($this->session->flashdata('demo')) : ?>
                        <div class="alert alert-danger" role="alert">
                            <?php echo $this->session->flashdata('demo'); ?>
                        </div>
                    <?php endif; ?>
                    <?php if ($this->session->flashdata('hapus')) : ?>
                        <div class="alert alert-danger" role="alert">
                            <?php echo $this->session->flashdata('hapus'); ?>
                        </div>
                    <?php endif; ?>
                    <div>
        <a class="btn btn-info" href="<?= base_url(); ?>services/addservice"><i class="mdi mdi-plus-circle-outline"></i>Tambah Layanan</a>
      </div>
      <br>
                    <div class="table-responsive">
                        <table id="mwtable" class="table table-striped table-hover dt-responsive display nowrap" data-info="false" cellspacing="0" width="100%">
                            <thead>
                                <tr>
                                    <th> </th>
                                    <th>Layanan</th>
                                    <th>Ikon</th>
                                    <th>Harga</th>
                                    <th>Diskon</th>
                                    <th>Jarak</th>
                                    <th>Komisi</th>
                                    <th>Min Harga</th>
                                    <th>Jarak Driver</th>
                                    <th>Max Jarak Pesanan</th>
                                    <th>Min Saldo</th>
                                    <th>Job</th>
                                    <th>Status</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <?php $i = 1;
                                foreach ($service as $s) { ?>
                                    <tr>
                                        <td></td>
                                        <td><?= $s['fitur']; ?></td>
                                        <td>
                                            <div class="badge badge-primary">
                                                <img width="80" height="80" class="avatar-img rounded-circle" src="<?= base_url('images/fitur/') . $s['icon']; ?>">
                                            </div>
                                        </td>
                                        <td>
                                            <?= $duit ?>
                                            <?= rupiah($s['biaya']) ?>
                                        </td>
                                        <td><?= $s['nilai']; ?>%</td>
                                        <td>/<?= $s['keterangan_biaya']; ?></td>
                                        <td><?php if($s['komisi_tipe'] == 1){ echo $s['komisi'] ; ?> %
                                        <?php }else{ ?>
                                            <?= $duit ?>
                                            <?= rupiah($s['komisi']); ?>
                                        <?php } ?>
                                        </td>
                                        <td><?= $duit ?>
                                            <?= rupiah($s['biaya_minimum']) ?></td>
                                        <td><?= $s['jarak_minimum']; ?>km</td>
                                        <td><?= $s['maks_distance']; ?>km</td>
                                        <td><?= $duit ?>
                                            <?= rupiah($s['wallet_minimum']) ?></td>
                                        <?php foreach ($driverjob as $dj) {
                                            if ($s['driver_job'] == $dj['id']) { ?>
                                                <td><?= $dj['driver_job']; ?></td>
                                        <?php }
                                        } ?>


                                        <td>
                                            <?php if ($s['active'] == 1) { ?>
                                                <label class="badge badge-success">Active</label>
                                            <?php } else { ?>
                                                <label class="badge badge-danger">Non Active</label>
                                            <?php } ?>
                                        </td>
                                        <td>
                                            <a href="<?= base_url(); ?>services/ubah/<?= $s['id_fitur']; ?>">
                                                <button class="btn btn-outline-primary">Edit</button>
                                            </a>
                                            <a href="<?= base_url(); ?>services/hapusservice/<?= $s['id_fitur']; ?>" onclick="return confirm ('are you sure?')">
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
    </div>
</div>