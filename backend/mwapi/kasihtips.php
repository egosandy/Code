	<?php 
 require_once 'koneksi.php';

 if($_SERVER['REQUEST_METHOD'] == 'POST')
 {
 	$id_user = $_POST['id_user'];
 	$saldo = $_POST['saldo'];

 	$query = "UPDATE saldo SET saldo = '$saldo' WHERE id_user = '$id_user'";

 	$exeQuery = mysqli_query($konek, $query); 

 	echo ($exeQuery) ? json_encode(array('kode' =>1, 'pesan' => 'Tips Berhasil Diberikan.')) :  json_encode(array('kode' =>2, 'pesan' => 'Tips Gagal Diberikan.'));
 }
 else
 {
 	 echo json_encode(array('kode' =>101, 'pesan' => 'request tidak valid'));
 }

 ?>