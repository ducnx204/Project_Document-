<?php
include "connect.php";
$password = $_POST['password'];
$id    = $_POST['id'];

$query = 'UPDATE `user` SET `password`="'.$password.'" WHERE `id` ='.$id;

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