<?php
include "connect.php";
$password = $_POST['password'];
$id    = $_POST['id'];

// check data
$query = 'SELECT * FROM `user` WHERE `password`= "' . $password . '" AND `id`= "' . $id . '" ';
$data = mysqli_query($conn, $query);
$numrow = mysqli_num_rows($data);

// kiểm tra tên sản phẩm đã tồn tại hay chưa
if ($numrow > 0) {
	$arr = [
		'success' => false,
		'message' => "Bạn đã nhập mật khẩu củ"
	];
} else {
	$query = 'UPDATE `user` SET `password`="' . $password . '" WHERE `id` =' . $id;

	$data = mysqli_query($conn, $query);

	if ($data == true) {
		$arr = [
			'success' => true,
			'message'  => "Thay đổi mật khẩu thành công",
		];
	} else {
		$arr = [
			'success' => false,
			'message'  => "Thay đổi mật khẩu thất bại",
		];
	}
}

print_r(json_encode($arr));