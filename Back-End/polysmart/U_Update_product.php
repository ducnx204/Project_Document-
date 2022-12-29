<?php
include "connect.php";
$name_product = $_POST['name_product'];
$price_product    = $_POST['price_product'];
$imageview_product = $_POST['imageview_product'];
$describe_product   = $_POST['describe_product'];
$id_category   = $_POST['id_category'];
$id   = $_POST['id'];


// // check data
$query = 'UPDATE `product` SET 
    `name_product`="' . $name_product . '",
    `price_product`="' . $price_product . '",
    `imageview_product`="' . $imageview_product . '",
    `describe_product`="' . $describe_product . '",
    `id_category`=' . $id_category . ' 
    WHERE id=' . $id;


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


print_r(json_encode($arr));