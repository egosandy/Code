	<?php 
 require_once 'koneksi.php';

 if($_SERVER['REQUEST_METHOD'] == 'POST')
 {
 	$id = $_POST['id'];
 	$biaya = $_POST['biaya'];

 	$query = "UPDATE transaksi SET biaya_akhir = '$biaya' WHERE id = '$id'";

 	$exeQuery = mysqli_query($konek, $query); 

 	echo ($exeQuery) ? json_encode(array('kode' =>1, 'pesan' => 'Biaya Berhasil Diubah.')) :  json_encode(array('kode' =>2, 'pesan' => 'Biaya Gagal Diubah.'));
 }
 else
 {
 	 echo json_encode(array('kode' =>101, 'pesan' => 'request tidak valid'));
 }

 ?>