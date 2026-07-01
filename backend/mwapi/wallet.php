<?php 
 require_once 'koneksi.php';

 if($_SERVER['REQUEST_METHOD'] == 'POST')
 {
    $id_user = $_POST['id_user'];
 	$jumlah = $_POST['jumlah'];
 	$bank = $_POST['bank'];
 	$nama_pemilik = $_POST['nama_pemilik'];
 	$rekening = $_POST['rekening'];
 	$tujuan = $_POST['tujuan'];
 	$type = $_POST['type'];
 	$status = $_POST['status'];

 	$query = "INSERT INTO wallet (id_user, jumlah, bank,nama_pemilik,rekening,tujuan,type,status) VALUES ('$id_user','$jumlah','$bank','$nama_pemilik','$rekening','$tujuan','$type','$status')";

 	$exeQuery = mysqli_query($konek, $query); 

 	echo ($exeQuery) ? json_encode(array('kode' =>1, 'pesan' => 'berhasil menambahkan data')) :  json_encode(array('kode' =>2, 'pesan' => 'data gagal ditambahkan'));
 }
 else
 {
 	 echo json_encode(array('kode' =>101, 'pesan' => 'request tidak valid'));
 }

 ?>