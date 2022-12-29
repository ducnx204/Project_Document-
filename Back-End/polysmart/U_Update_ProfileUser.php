<?php
include "connect.php";
$id    = $_POST['id'];
$ho	 = $_POST['ho'];
$ten = $_POST['ten'];
$diachi = $_POST['diachi'];
$mobile = $_POST['mobile'];
$image_user = $_POST['image_user'];
// check số điện thoại
$queryMobile = 'SELECT * FROM `user` WHERE `mobile`= "' . $mobile . '"';
$dataMobile = mysqli_query($conn, $queryMobile);
$numberMobile = mysqli_num_rows($dataMobile);

if ($numberMobile > 0) {
	$arr = [
		'success' => false,
		'message' => " Số điện thoại đã tồn tại. "
	];
} else {
	// // check data
	$query = 'UPDATE `user` SET `ho`="' . $ho . '",`ten`="' . $ten . '",`diachi`="' . $diachi . '",`mobile`="' . $mobile . '",`image_user`="' . $image_user . '" WHERE `id` =' . $id;
	$data = mysqli_query($conn, $query);
	if ($data == true) {
		$arr = [
			'success' => true,
			'message'  => "Thay đổi thông tin thành công",
		];
	} else {

		$arr = [
			'success' => false,
			'message'  => "Thay đổi thông tin thất bại",
		];
	}
}

print_r(json_encode($arr));