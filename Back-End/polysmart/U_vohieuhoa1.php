<?php
include "connect.php";
$id    = $_POST['id'];

// // check data
$query = 'UPDATE `user` SET `vohieuhoa` = 1 WHERE `id` =' . $id;


$data = mysqli_query($conn, $query);

if ($data == true) {
	$arr = [
		'success' => true,
		'message'  => "Đã vô hiệu hóa tài khoản",
	];
} else {

	$arr = [
		'success' => false,
		'message'  => "Vô hiệu hóa tài khoản không thành công",
	];
}


print_r(json_encode($arr));