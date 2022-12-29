<?php
include "connect.php";
$token = $_POST['token'];
$iddonhang = $_POST['id'];


// // check data
$query = 'UPDATE `donhang` SET `momo`="'.$token.'" WHERE `id` ='.$iddonhang;

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