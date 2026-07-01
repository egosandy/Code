	<?php 
 require_once 'koneksi.php';

 if($_SERVER['REQUEST_METHOD'] == 'POST')
 {
 	$id = $_POST['id'];
 	$biaya = $_POST['biaya'];

 	$query = "UPDATE transaksi_detail_merchant SET total_biaya = '$biaya' WHERE id_transaksi = '$id'";

 	$exeQuery = mysqli_query($konek, $query); 

 	echo ($exeQuery) ? json_encode(array('kode' =>1, 'pesan' => 'Layanan Berhasil Diubah.')) :  json_encode(array('kode' =>2, 'pesan' => 'Layanan Gagal Diubah.'));
 }
 else
 {
 	 echo json_encode(array('kode' =>101, 'pesan' => 'request tidak valid'));
 }

 ?>