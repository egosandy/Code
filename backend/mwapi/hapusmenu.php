<?php 
 require_once 'koneksi.php';

 if($_SERVER['REQUEST_METHOD'] == 'POST')
 {
 	$id = $_POST['id'];
    $idtrans = $_POST['idtrans'];
 	$query = "DELETE FROM transaksi_item WHERE id_item ='$id' AND id_transaksi = '$idtrans'";

 	$exeQuery = mysqli_query($konek, $query); 

 	echo ($exeQuery) ? json_encode(array('kode' =>1, 'pesan' => 'berhasil Menghapus data')) :  json_encode(array('kode' =>2, 'pesan' => 'data gagal dihapus'));
 }
 else
 {
 	 echo json_encode(array('kode' =>101, 'pesan' => 'request tidak valid'));
 }

 ?>