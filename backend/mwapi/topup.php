<?php 
 require_once 'koneksi.php';

 if($_SERVER['REQUEST_METHOD'] == 'POST')
 {
 	$id = $_POST['id'];
 	$nama = $_POST['nama'];
 	$bank = $_POST['bank'];
 	$rek = $_POST['rek'];
 	$tipe = $_POST['tipe'];
 	$jumlah = $_POST['jumlah'];

 	$query = "INSERT INTO midtrans (id, nama, bank,rek,tipe,jumlah) VALUES ('$id','$nama','$bank','$rek','$tipe',$jumlah)";

 	$exeQuery = mysqli_query($konek, $query); 

 	echo ($exeQuery) ? json_encode(array('kode' =>1, 'pesan' => 'berhasil menambahkan data')) :  json_encode(array('kode' =>2, 'pesan' => 'data gagal ditambahkan'));
 }
 else
 {
 	 echo json_encode(array('kode' =>101, 'pesan' => 'request tidak valid'));
 }

 ?>