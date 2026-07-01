<!-- partial -->
<div class="content-wrapper">
    
    <div class="row">
        <div class="col-md-12 grid-margin stretch-card">
            <div class="card">
                <div class="card-body">
                    <?php if ($this->session->flashdata()) : ?>
                        <div class="alert alert-success" role="alert">
                            <?php echo $this->session->flashdata('ubah'); ?>
                        </div>
                    <?php endif; ?>
                   
                    <h4 class="card-title">Transaksi Topup online</h4>
                     <br>
                    <div class="table-responsive">
                        <table id="order-listing" class="table">
                            <thead>
                                <tr>
                                    <th>No.</th>
                                    <th>Invoice</th>
                                    <th>Date</th>
                                    <th>ID User</th>
                                    <th>Tipe</th>
                                    <th>Channel</th>
                                    <th>Nominal</th>
                                    <th>Biaya</th>
                                    <th>Total</th>
                                    <th>Status</th>
                                    <th>Demo</th>
                                </tr>
                            </thead>
                            <tbody>
                                <?php $i = 1;
                                foreach ($transaksi as $ol) { ?>
                                <tr>
                                    <td><?= $i ?></td>
                                    <td><?= $ol['invoice'] ?></td>
                                    <td><?= $ol['regtime'] ?></td>
                                    <td><?= $ol['id_user'] ?></td>
                                    <td><?= $ol['jenis_nama'] ?></td>
                                    <td><?= $ol['metode'] ?></td>
                                    <td><?= number_format($ol['nominal']) ?></td>
                                    <td><?= number_format($ol['biaya']) ?></td>
                                    <td><?= number_format($ol['total']) ?></td>
                                    <td>
                                        <?php if($ol['status'] == 0) { ?>
                                         <label class="badge badge-default">Created</label>
                                         <?php }else if($ol['status'] == 1) { ?>
                                         <label class="badge badge-warning">Pending</label>
                                         <?php }else if($ol['status'] == 2) { ?>
                                         <label class="badge badge-success">Success</label>
                                         <?php }else { ?>
                                         <label class="badge badge-danger">Gagal</label>
                                         <?php } ?>
                                    </td>
                                    <td>
                                        <?php 
                                            if ($ol['is_demo'] == '0') { ?>
                                            <label class="badge badge-info">Tidak</label>
                                        <?php }else{ ?>
                                            <label class="badge badge-info">Ya</label>
                                        <?php } ?>
                                    </td>
                                
                                
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