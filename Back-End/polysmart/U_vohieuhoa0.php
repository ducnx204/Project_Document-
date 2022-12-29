<?php
include "connect.php";
$id    = $_POST['id'];


// // check data
$query = 'UPDATE `user` SET `vohieuhoa` = 0 WHERE `id` =' . $id;

$data = mysqli_query($conn, $query);

if ($data == true) {
	$arr = [
		'success' => true,
		'message'  => "Hủy bỏ vô hiệu hóa",
	];
} else {

	$arr = [
		'success' => false,
		'message'  => " Vo hieu hoa khong thanh cong",
	];
}


print_r(json_encode($arr));