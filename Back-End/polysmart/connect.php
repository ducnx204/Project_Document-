<?php
$host = "localhost";
$user = "root";
$pass = "";
$database = "polysmart";

$conn = mysqli_connect($host, $user, $pass, $database);


	// mysqli_set_charset($conn,"SET NAMES 'utf8'");
	// mysqli_set_charset($conn);
	// // check kiểm tra 
	// if ($conn) {
	// 	// code...
	// 	echo "ket noi sever mysql thanh cong";
	// }else{

	// 	echo "ket noi sever mysql  khong thanh cong";
	// }