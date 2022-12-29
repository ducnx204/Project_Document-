<?php
include "connect.php";
$id_category = $_POST['id_category'];
$query = 'SELECT * FROM `product` WHERE `id_category` =' . $id_category . ' ORDER BY id DESC';
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