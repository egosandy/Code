	<?php 
 require_once 'koneksi.php';

 if($_SERVER['REQUEST_METHOD'] == 'POST')
 {
 	$id_driver = $_POST['id_driver'];
    $status = $_POST['status'];
 	$query = "UPDATE config_driver SET status = '$status' WHERE id_driver = '$id_driver'";

 	$exeQuery = mysqli_query($konek, $query); 

 	echo ($exeQuery) ? json_encode(array('kode' =>1, 'pesan' => 'Status Berhasil Di Perbarui.')) :  json_encode(array('kode' =>2, 'pesan' => 'Status Gagal Di Perbarui.'));
 }
 else
 {
 	 echo json_encode(array('kode' =>101, 'pesan' => 'request tidak valid'));
 }

 ?>