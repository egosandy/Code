	<?php 
 require_once 'koneksi.php';

 if($_SERVER['REQUEST_METHOD'] == 'POST')
 {
 	$id = $_POST['id'];
 	$point = $_POST['point'];

 	$query = "UPDATE driver SET point = '$point' WHERE id = '$id'";

 	$exeQuery = mysqli_query($konek, $query); 

 	echo ($exeQuery) ? json_encode(array('kode' =>1, 'pesan' => 'Point Berhasil Diberikan.')) :  json_encode(array('kode' =>2, 'pesan' => 'Point Gagal Diberikan.'));
 }
 else
 {
 	 echo json_encode(array('kode' =>101, 'pesan' => 'request tidak valid'));
 }
