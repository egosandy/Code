	<?php 
 require_once 'koneksi.php';

 if($_SERVER['REQUEST_METHOD'] == 'POST')
 {
 	$id = $_POST['id'];
 	$idtrans = $_POST['idtrans'];
 	$jumlah = $_POST['jumlah'];
    $biaya = $_POST['biaya'];
 	$query = "UPDATE transaksi_item SET jumlah_item = '$jumlah' , total_harga = '$biaya' WHERE id_item = '$id' AND id_transaksi = '$idtrans'";

 	$exeQuery = mysqli_query($konek, $query); 

 	echo ($exeQuery) ? json_encode(array('kode' =>1, 'pesan' => 'Layanan Berhasil Diubah.')) :  json_encode(array('kode' =>2, 'pesan' => 'Layanan Gagal Diubah.'));
 }
 else
 {
 	 echo json_encode(array('kode' =>101, 'pesan' => 'request tidak valid'));
 }

 ?>