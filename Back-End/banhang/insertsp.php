<?php
include "connect.php";
$tensanpham = $_POST['tensanpham'];
$giasanpham    = $_POST['giasanpham'];
$hinhanhsanpham = $_POST['hinhanhsanpham'];
$motasanpham   = $_POST['motasanpham'];
$idsanpham   = $_POST['idsanpham'];

// // check data
$query = 'INSERT INTO `sanpham`(`tensanpham`, `giasanpham`, `hinhanhsanpham`, `motasanpham`, `idsanpham`) VALUES 
("'.$tensanpham.'",'.$giasanpham.',"'.$hinhanhsanpham.'","'.$motasanpham.'",'.$idsanpham.')';

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