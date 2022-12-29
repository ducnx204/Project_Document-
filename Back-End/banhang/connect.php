<?php
$host = "localhost";
$user = "root";
$pass = "";
$database = "thietbi";

$conn = mysqli_connect($host, $user, $pass, $database);
mysqli_set_charset($conn, "utf8");

	// check kiểm tra 
	// if ($conn) {
	// 	// code...
	// 	echo "ket noi sever mysql thanh cong";
	// }else{

	// 	echo "ket noi sever mysql  khong thanh cong";
	// }