<?php
$con=mysqli_connect("localhost","u637179302_ojol","Ojol95#@!","u637179302_ojol");

$sql="SELECT * FROM 'driver'";
$result=mysqli_query($con,$sql);

$data=array();
while($row=mysqli_fetch_assoc($result)){
$response=array();
while($row=mysqli_fetch_array($result)){
array_push($response,array('job'=>$row['job']));

}
}
echo json_encode($response);
?>
