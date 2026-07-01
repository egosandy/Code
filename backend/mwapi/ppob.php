<?php 
 require_once 'koneksi.php';

 if($_SERVER['REQUEST_METHOD'] == 'POST')
 {
    $id_user = $_POST['id_user'];
 	$reff = $_POST['reff'];
 	$code = $_POST['code'];
 	$tipe = $_POST['tipe'];
 	$operator = $_POST['operator'];
 	$price = $_POST['price'];
    $hp = $_POST['hp'];
    $sukses = $_POST['sukses'];
    $onstatus = $_POST['onstatus'];
 	$query = "INSERT INTO ppob (id_user,reff,code,tipe, operator, price,hp,sukses,onstatus) VALUES ('$id_user','$reff','$code','$tipe','$operator','$price','$hp','$sukses','$onstatus')";

 	$exeQuery = mysqli_query($konek, $query); 

 	echo ($exeQuery) ? json_encode(array('kode' =>1, 'pesan' => 'berhasil menambahkan data')) :  json_encode(array('kode' =>2, 'pesan' => 'data gagal ditambahkan'));
 }
 else
 {
 	 echo json_encode(array('kode' =>101, 'pesan' => 'request tidak valid'));
 }

 ?>