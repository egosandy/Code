 <div class="container-fluid mt-xl-50 mt-sm-30 mt-15 px-xxl-65 px-xl-20">
    <div class="row grid-margin">
        <div class="col-12">
            <div class="card">
                <div class="card-body">
                    <?php if ($this->session->flashdata('tambah')) : ?>
                        <div class="alert alert-success" role="alert">
                            <?php echo $this->session->flashdata('tambah'); ?>
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
                   
                    <div class="table-responsive">
                        <table id="mwtable" class="table table-striped table-hover dt-responsive display nowrap" data-info="false" cellspacing="0" width="100%">
                            <thead>
                                <tr>
                                    <th>No</th>
                                    <th>Invoice</th>
                                    <th>Produk</th>
                                    <th>ID User</th>
                                    <th>Nomor</th>
                                    <th>Harga</th>
                                    <th>Status</th>
                                   
                                </tr>
                            </thead>
                            <tbody>
                                <?php $i = 1;
                                foreach ($transaksi as $s) { ?>
                                    <tr>
                                        <td><?= $i; ?></td>
                                        <td><?= $s['invoice']; ?></td>
                                        <td><?= $s['produk_nama']; ?></td>
                                        <td><?= $s['id_user']; ?></td>
                                        <td><?= $s['nomor_tagihan']; ?></td>
                                        <td><?= $s['harga']; ?></td>
                                        <td>
                                            <?php if ($s['status'] == 0) { ?>
                                                <label class="badge badge-warning">Proses</label>
                                            <?php }else if ($s['status'] == 1) { ?>
                                                 <label class="badge badge-success">Berhasil</label>
                                            <?php } else { ?>
                                                <label class="badge badge-danger">Gagal</label>
                                            <?php } ?>
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
