	<?php 
 require_once 'koneksi.php';

 if($_SERVER['REQUEST_METHOD'] == 'POST')
 {
 	$id= $_POST['id'];
 	$proses = $_POST['proses'];
 	$query = "UPDATE history_transaksi SET proses = '$proses' WHERE id_transaksi = '$id'";

 	$exeQuery = mysqli_query($konek, $query); 

 	echo ($exeQuery) ? json_encode(array('kode' =>1, 'pesan' => 'Proses Berhasil Di Perbarui.')) :  json_encode(array('kode' =>2, 'pesan' => 'Proses Gagal Di Perbarui.'));
 }
 else
 {
 	 echo json_encode(array('kode' =>101, 'pesan' => 'request tidak valid'));
 }

 ?>