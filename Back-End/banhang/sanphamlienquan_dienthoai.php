<?php
include "connect.php";
// $idsanpham = $_POST ['idsanpham'];

$query = 'SELECT * FROM `sanpham` WHERE `idsanpham`';
	// $query = 'SELECT * FROM `sanpham` WHERE `idsanpham` =' .$idsanpham.' ORDER BY id DESC' ;

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