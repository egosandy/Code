	<?php 
 require_once 'koneksi.php';

 if($_SERVER['REQUEST_METHOD'] == 'POST')
 {
 	$id_driver = $_POST['id_driver'];
 	$latitude = $_POST['latitude'];
    $longitude = $_POST['longitude'];
    $bearing = $_POST['bearing'];
 	$query = "UPDATE config_driver SET latitude = '$latitude' AND longitude = '$longitude' AND bearing = '$bearing' WHERE id_driver = '$id_driver'";

 	$exeQuery = mysqli_query($konek, $query); 

 	echo ($exeQuery) ? json_encode(array('kode' =>1, 'pesan' => 'Lokasi Berhasil Di Perbarui.' .$id_driver. ',' . $latitude . ',' .$longitude . ','.$bearing)) :  json_encode(array('kode' =>2, 'pesan' => 'Lokasi Gagal Di Perbarui.'));
 }
 else
 {
 	 echo json_encode(array('kode' =>101, 'pesan' => 'request tidak valid'));
 }

 ?>