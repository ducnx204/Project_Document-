<?php
include "connect.php";
$page = $_POST['page'];
$total = 5; // can lay 5 san pham tren 1 trang
$pos = ($page-1)*$total;   // 0,5   5,5 
$idsanpham = $_POST['idsanpham'];

$query = 'SELECT * FROM `sanpham` WHERE `idsanpham`= '.$idsanpham.' LIMIT '.$pos.','.$total.'';

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

	
}else{
	$arr = [
		'success' => false,
		'message'  => " Không Thành Công",
		'result'    => $result	
	];

}
print_r(json_encode($arr));

?>  