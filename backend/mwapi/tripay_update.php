	<?php 
 require_once 'koneksi.php';

 if($_SERVER['REQUEST_METHOD'] == 'POST')
 {
 	$id = $_POST['id'];
 	$istopup = $_POST['istopup'];
    $noreff = $_POST['noreff'];
 
 	$query = "UPDATE pelanggan SET istopup = '$istopup' AND noreff = '$noreff'  WHERE id = '$id'";

 	$exeQuery = mysqli_query($konek, $query); 

 	echo ($exeQuery) ? json_encode(array('kode' =>1, 'pesan' => 'Tripau Berhasil Di Perbarui.')) :  json_encode(array('kode' =>2, 'pesan' => 'Lokasi Gagal Di Perbarui.'));
 }
 else
 {
 	 echo json_encode(array('kode' =>101, 'pesan' => 'request tidak valid'));
 }

 ?>