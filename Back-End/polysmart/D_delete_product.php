<?php
include "connect.php";
$id = $_POST['id'];
$query = 'DELETE FROM `product` WHERE `id` =' . $id;

$data = mysqli_query($conn, $query);

if ($data == true) {
	$arr = [
		'success' => true,
		'message'  => "Delete success",
	];
} else {
	$arr = [
		'success' => false,
		'message'  => "Delete false",
	];
}

print_r(json_encode($arr));