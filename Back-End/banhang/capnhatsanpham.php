<?php
include "connect.php";
$tensanpham = $_POST['tensanpham'];
$giasanpham    = $_POST['giasanpham'];
$hinhanhsanpham = $_POST['hinhanhsanpham'];
$motasanpham   = $_POST['motasanpham'];
$idsanpham   = $_POST['idsanpham'];
$id   = $_POST['id'];


// // check data
$query = 'UPDATE `sanpham` SET `tensanpham`="'.$tensanpham.'",`giasanpham`="'.$giasanpham.'",`hinhanhsanpham`="'.$hinhanhsanpham.'",`motasanpham`="'.$motasanpham.'",`idsanpham`='.$idsanpham.' WHERE id='.$id;

	$data = mysqli_query($conn, $query);

		if ($data == true) {
			$arr = [
				'success' => true,
				'message'  => "thanh cong",
			];

			
		}else{

			$arr = [
				'success' => false,
				'message'  => " khong thanh cong",
			];

		}

	
print_r(json_encode($arr));

?>