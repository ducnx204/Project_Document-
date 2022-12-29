<?php
include "connect.php";
$page = $_POST['page'];
$id_category = $_POST['id_category'];
$total = 5; // can lay 5 san pham tren 1 trang
$pos = ($page - 1) * $total;   // thời gian 1s


$query = 'SELECT * FROM `product` WHERE `id_category`= ' . $id_category . ' LIMIT ' . $pos . ',' . $total . '';

$data = mysqli_query($conn, $query);
$result = array();

while ($row = mysqli_fetch_assoc($data)) {
	$result[] = ($row);
	// code...
};

if (!empty($result)) {

	$arr = [
		'success' => true,
		'message'  => "Thành Công",
		'result'    => $result
	];
} else {
	$arr = [
		'success' => false,
		'message'  => " Không Thành Công",
		'result'    => $result
	];
}
print_r(json_encode($arr));