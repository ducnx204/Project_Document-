<?php
include "connect.php";
// truy vấn get user mới nhất
$query = 'SELECT * FROM `user` WHERE `status` = 1 ORDER BY `user`.`id` DESC';
$data = mysqli_query($conn, $query);
$result = array();

while ($row = mysqli_fetch_assoc($data)) {
	$result[] = ($row);
	// code...
};

if (!empty($result)) {

	$arr = [
		'success' => true,
		'message'  => "thanh Cong",
		'result'    => $result
	];
} else {
	$arr = [
		'success' => false,
		'message'  => " Khong Thanh Cong",
		'result'    => $result
	];
}
print_r(json_encode($arr));