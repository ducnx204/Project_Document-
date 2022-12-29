<?php
include "connect.php";
$id    = $_POST['id'];
// truy vấn
$query = 'SELECT `id`, `image_user` FROM `user` WHERE  `id` =' . $id;
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