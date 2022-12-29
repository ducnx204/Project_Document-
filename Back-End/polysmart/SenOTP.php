<?php
include "connect.php";
$mobile    = $_POST['mobile'];
$query = 'SELECT * FROM `user` WHERE `mobile` = "' . $mobile . '"';
$data = mysqli_query($conn, $query);
$result = array();

while ($row = mysqli_fetch_assoc($data)) {
	$result[] = ($row);
	// code...
};

if (!empty($result)) {

	$arr = [
		'success' => true,
		'message'  => " Gửi mã xác nhận thành công",
		'result'    => $result

	];
} else {
	$arr = [
		'success' => false,
		'message'  => " Gửi mã xác nhận thất bại",
		'result'    => $result

	];
}
print_r(json_encode($arr));