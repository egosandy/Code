	<?php 
 require_once 'koneksi.php';

 if($_SERVER['REQUEST_METHOD'] == 'POST')
 {
 	$id_user = $_POST['id_user'];
 	$reff = $_POST['reff'];
 	$sukses = $_POST['sukses'];
 	$onstatus = $_POST['onstatus'];

 	$query = "UPDATE ppob SET sukses = '$sukses' , onstatus = '$onstatus' WHERE id_user = '$id_user' AND reff = '$reff'";
 	

 	$exeQuery = mysqli_query($konek, $query); 

 	echo ($exeQuery) ? json_encode(array('kode' =>1, 'pesan' => 'Biaya Berhasil Diubah.')) :  json_encode(array('kode' =>2, 'pesan' => 'Biaya Gagal Diubah.'));
 }
 else
 {
 	 echo json_encode(array('kode' =>101, 'pesan' => 'request tidak valid'));
 }

 ?>