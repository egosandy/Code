
            <!-- Breadcrumb -->
            <nav class="hk-breadcrumb" aria-label="breadcrumb">
                <ol class="breadcrumb breadcrumb-light bg-transparent">
                    <li class="breadcrumb-item"><a href="#">Pages</a></li>
                    <li class="breadcrumb-item active" aria-current="page">Invoice</li>
                </ol>
            </nav>
            <!-- /Breadcrumb -->

            <!-- Container -->
            <div class="container-fluid px-xxl-65 px-xl-20">
                <!-- Title -->
                <div class="hk-pg-header mb-10">
					<div>
						<h4 class="hk-pg-title"><span class="pg-title-icon"><i class="ion ion-md-bookmarks"></i></span>Invoice</h4>
                    </div>
					<div class="d-flex">
                        <?php if ($transaksi['status'] != 5 and $transaksi['status'] != 4) { ?>
                                <a href="<?= base_url(); ?>dashboard/cancletransaction/<?= $transaksi['id'] ?>/<?= $transaksi['id_driver'] ?>" class="btn btn-danger float-right mt-4 ml-2">
                                    <i class="mdi mdi-cancel mr-1"></i>Cancel Transaction</a>
                            <?php } ?>
                            <a href="<?= base_url(); ?>" class="btn btn-primary float-right mt-4">
                                <i class="mdi mdi-keyboard-backspace mr-1"></i>Back</a>
                    </div>
                </div>
                <!-- /Title -->

                <!-- Row -->
                <div class="row">
                    <div class="col-xl-12">
                        <section class="hk-sec-wrapper hk-invoice-wrap pa-35">
                            <div class="invoice-from-wrap">
                                <div class="row">
                                    <div class="col-md-7 mb-20">
                                        <img class="img-fluid invoice-brand-img d-block mb-20" src="<?= base_url(); ?>dist/img/logo-light.png" alt="brand" />
                                        <!-- -------------------------------------------------------------------------------------------- -->
										<?php if ($transaksi['home'] == 3) { ?>
											<span class="d-block text-uppercase mb-5 font-13">Lokasi Ambil</span>
											<h6 class="mb-5">Alamat.</h6>
											<address>
												<span class="d-block"><?= $transaksi['alamat_asal'] ?></span>
											</address>
										<?php } else { ?>
										<span class="d-block text-uppercase mb-5 font-13">Lokasi Ambil</span>
											<h6 class="mb-5">Alamat.</h6>
											<address>
												<span class="d-block"><?= $transaksi['alamat_asal'] ?></span>
											</address>
										<?php } ?>
										<!-- -------------------------------------------------------------------------------------------- -->
										
                                    </div>
                                    <div class="col-md-5 mb-20">
                                        <h4 class="mb-35 font-weight-600">Invoice / Receipt</h4>
                                        <span class="d-block">Status:<span class="pl-10 text-dark">
										<?php if ($transaksi['status'] == '2') { ?>
											<p class="ml-2 badge badge-primary"><?= $transaksi['status_transaksi'] ?></p>
										<?php } ?>
										<?php if ($transaksi['status'] == '3') { ?>
											<p class="ml-2 badge badge-success"><?= $transaksi['status_transaksi'] ?></p>
										<?php } ?>
										<?php if ($transaksi['status'] == '4') { ?>
											<p class="ml-2 badge badge-info"><?= $transaksi['status_transaksi'] ?></p>
										<?php } ?>
										<?php if ($transaksi['status'] == '5') { ?>
											<p class="ml-2 badge badge-danger"><?= $transaksi['status_transaksi'] ?></p>
										<?php } ?>
										</span></span>
										<!-- --------------------------------------------------------------------------------------- -->
										<?php if ($transaksi['home'] == 4) { ?>
										<span class="d-block">Customer #<span class="pl-10 text-dark"><?= $transaksi['fullnama'] ?></span></span>
                                        <span class="d-block">Mitra #<span class="pl-10 text-dark"><?= $transaksi['nama_merchant'] ?></span></span>
										<span class="d-block">Driver #<span class="pl-10 text-dark"><?= $transaksi['nama_driver'] ?></span></span>

                                <?php } else { ?>
										<span class="d-block">Customer #<span class="pl-10 text-dark"><?= $transaksi['fullnama'] ?></span></span>
    									<span class="d-block">Driver #<span class="pl-10 text-dark"><?= $transaksi['nama_driver'] ?></span></span>
										<span class="d-block">Layanan #<span class="pl-10 text-dark"><?= $transaksi['fitur'] ?></span></span>
                                <?php } ?>
										<!-- --------------------------------------------------------------------------------------- -->
                                        
                                    </div>
                                </div>
                            </div>
                            <hr class="mt-0">
                            <div class="invoice-to-wrap pb-20">
                                <div class="row">
                                    <div class="col-md-7 mb-30">
									 <!-- -------------------------------------------------------------------------------------------- -->
										<?php if ($transaksi['home'] == 3) { ?>
											<span class="d-block text-uppercase mb-5 font-13">Lokasi Tujuan</span>
											<h6 class="mb-5">Alamat.</h6>
												<address>
													<span class="d-block"><?= $transaksi['alamat_tujuan'] ?></span>
												</address>
										<?php } else { ?>
											<span class="d-block text-uppercase mb-5 font-13">Lokasi Tujuan</span>
											<h6 class="mb-5">Alamat</h6>
												<address>
													<span class="d-block"><?= $transaksi['alamat_tujuan'] ?></span>
												</address>
											
										<?php } ?>
										<!-- -------------------------------------------------------------------------------------------- -->
                                        
                                    </div>
                                    <div class="col-md-5 mb-30">
										<?php if ($transaksi['home'] == 4) { ?>
									 <span class="d-block">Belanja : <?= $currency['app_currency'] ?>
									 <?= rupiah($transaksi['total_belanja']) ?></span>
											<span class="d-block">Ongkir :
									 <?= $currency['app_currency'] ?>
									 <?= rupiah($transaksi['harga']) ?></span>
											<span class="d-block">Sub Total :  <?php $subtotal = $transaksi['harga'] + $transaksi['total_belanja']; ?>
										<?= $currency['app_currency'] ?>
										<?= rupiah($subtotal) ?></span>
											<span class="d-block">Diskon <span class="text-dark"> (<?php if ($transaksi['pakai_wallet'] == '1') {
													echo $transaksi['nilai'];
												} else {
													echo 0;
												} ?>
											%) :
										<?= $currency['app_currency'] ?>
										 <?= rupiah($transaksi['kredit_promo']) ?></span></span>
											<span class="d-block text-uppercase mt-20 mb-5 font-13">Metode Pembayaran :
										<?php if ($transaksi['pakai_wallet'] == '0') { ?>
											<span class="badge badge-success"><?= 'CASH'; ?>
											<?php } else { ?>
												<span class="badge badge-primary"><?= 'wallet';
																				} ?>
												</span></span>
											<span class="d-block text-dark font-18 font-weight-600">Total : <?= $currency['app_currency'] ?>
										 <?= rupiah($transaksi['biaya_akhir']) ?></span>
										<?php } else { ?>
									   <!-- -------------------------------------------------------------------------------------- -->
												<span class="d-block">Sub Total :  <?php $subtotal = $transaksi['harga'] + $transaksi['total_belanja']; ?>
											<?= $currency['app_currency'] ?>
											<?= rupiah($subtotal) ?></span>
												<span class="d-block">Diskon <span class="text-dark"> (<?php if ($transaksi['pakai_wallet'] == '1') {
														echo $transaksi['nilai'];
													} else {
														echo 0;
													} ?>
												%) :
											<?= $currency['app_currency'] ?>
											 <?= rupiah($transaksi['kredit_promo']) ?></span></span>
												<span class="d-block text-uppercase mt-20 mb-5 font-13">Metode Pembayaran :
											<?php if ($transaksi['pakai_wallet'] == '0') { ?>
												<span class="badge badge-success"><?= 'CASH'; ?>
												<?php } else { ?>
													<span class="badge badge-primary"><?= 'wallet';
																					} ?>
													</span></span>
												<span class="d-block text-dark font-18 font-weight-600">Total : <?= $currency['app_currency'] ?>
											 <?= rupiah($transaksi['biaya_akhir']) ?></span>
												<?php } ?>
                                        
                                    </div>
                                </div>
                            </div>
                           
                          
							<!-- ------------------------------------------------------------------- -->
							<?php if ($transaksi['home'] == 4) { ?>
                            <div class="container-fluid mt-5 d-flex justify-content-center w-100">
                                <div class="table-responsive w-100">
                                    <table class="table">
                                        <thead>
                                            <tr>
                                                <th>Layanan</th>
                                                <th>Item</th>
                                                <th>Qty</th>
                                                <th class="text-right">Total</th>
                                                <!-- <th class="text-right">Total</th> -->
                                            </tr>
                                        </thead>

                                        <tbody>
                                            <tr>
                                                <td class="text-left"><?= $transaksi['fitur'] ?>
                                                    Service</td>

                                                <td>
                                                    <?php foreach ($transitem as $item) { ?>
                                                        <ul class="list-group list-group-flush">
                                                            <li class="list-group-item"><?= $item['nama_item'] ?></li>
                                                        </ul>
                                                    <?php } ?>
                                                </td>
                                                <td>
                                                    <?php foreach ($transitem as $item) { ?>
                                                        <ul class="list-group list-group-flush">
                                                            <li class="list-group-item"><?= $item['jumlah_item'] ?></li>
                                                        </ul>
                                                    <?php } ?>
                                                </td>
                                                <td class="text-right">
                                                    <?php foreach ($transitem as $item) { ?>
                                                        <ul class="list-group list-group-flush">
                                                            <li class="list-group-item">
                                                                <?= $currency['app_currency'] ?>
                                                                <?= rupiah($item['total_harga']) ?>
                                                            </li>
                                                        </ul>
                                                    <?php } ?>
                                                </td>
                                            </tr>

                                        </tbody>
                                    </table>
                                </div>
                            </div>

                        <?php } else { ?>
                            <div class="container-fluid mt-5 d-flex justify-content-center w-100">
                                <div class="table-responsive w-100">
                                    <table class="table">
                                        <thead>
                                            <tr>
                                                <th>Layanan</th>
                                                <?php if ($transaksi['home'] != '2') { ?>
                                                    <th class="text-right">Jarak</th>
                                                <?php } ?>
                                                <th class="text-right">Biaya</th>
                                                <th class="text-right">Total</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr class="text-right">
                                                <td class="text-left"><?= $transaksi['fitur'] ?>
                                                    Service</td>
                                                <?php if ($transaksi['home'] != '2') { ?>
                                                    <td>
                                                        <?php if ($transaksi['home'] == '3') {
                                                            if ($transaksi['jarak'] == '0') {
                                                                echo $transaksi['estimasi_time'];
                                                            }
                                                        } else {
                                                            $jarak = $transaksi['jarak'];
                                                            $jarakbulat = rupiah($jarak, 1);
                                                            echo $jarakbulat;
                                                            echo ' ';
                                                            echo $transaksi['keterangan_biaya'];
                                                        } ?>
                                                    </td>
                                                <?php } ?>
                                                <td>
                                                    <?= $currency['app_currency'] ?>
                                                    <?= rupiah($transaksi['biaya']) ?>
                                                  </td>
                                                <td>
                                                    <?= $currency['app_currency'] ?>
                                                    <?= rupiah($transaksi['harga']) ?>
                                                   </td>
                                            </tr>

                                        </tbody>

                                    </table>
                                </div>
                            </div>

                        <?php } ?>
                        

                    </div>
                </div>
                <!-- /Row -->
            </div>
            <!-- /Container -->
