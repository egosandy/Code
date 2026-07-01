<?php 
require_once 'koneksi.php'; 
$id_transaksi = $_POST['id_transaksi'];
$query = "SELECT * FROM history_transaksi WHERE id_transaksi ='$id_transaksi'";

$result = mysqli_query($konek,$query);

$array = array();

while ($row  = mysqli_fetch_assoc($result))
{
	$array[] = $row; 
}


echo ($result) ? 
json_encode(array("kode" => 1, "result"=>$array)) :
json_encode(array("kode" => 0, "pesan"=>"data tidak ditemukan"));


?>