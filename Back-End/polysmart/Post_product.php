<?php
include "connect.php";
$name_product = $_POST['name_product'];
$price_product    = $_POST['price_product'];
$imageview_product = $_POST['imageview_product'];
$describe_product   = $_POST['describe_product'];
$id_category   = $_POST['id_category'];

// check data
$query = 'SELECT * FROM `product` WHERE `name_product`= "' . $name_product . '"';
$data = mysqli_query($conn, $query);
$numrow = mysqli_num_rows($data);

// kiểm tra tên sản phẩm đã tồn tại hay chưa
if ($numrow > 0) {
	$arr = [
		'success' => false,
		'message' => " Tên sản phảm đã tồn tại "
	];
} else {
	// // truy vân
	$query = 'INSERT INTO `product`(`name_product`, `price_product`, `imageview_product`, `describe_product`, `id_category`) VALUES 
("' . $name_product . '",' . $price_product . ',"' . $imageview_product . '","' . $describe_product . '",' . $id_category . ')';

	$data = mysqli_query($conn, $query);
	if ($data == true) {
		$arr = [
			'success' => true,
			'message'  => "Thêm sản phẩm thành công",
		];
	} else {

		$arr = [
			'success' => false,
			'message'  => "Thêm sản phẩm thất bại",
		];
	}
}

print_r(json_encode($arr));