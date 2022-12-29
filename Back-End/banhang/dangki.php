<?php
include "connect.php";
$username = $_POST['username'];
$email    = $_POST['email'];
$password = $_POST['password'];
$mobile   = $_POST['mobile'];
$uid   = $_POST['uid'];




// check data
$query = 'SELECT * FROM `user` WHERE `email`= "' . $email . '"';
$data = mysqli_query($conn, $query);
$numrow = mysqli_num_rows($data);


if ($numrow > 0) {
	$arr = [
		'success' => false,
		'message'  => " Email đã tồn tại",
	];
} else {
	// // 	//  insert
	$query = 'INSERT INTO `user`(`username`, `email`, `password`, `mobile`,`uid`) 
	VALUES ("' . $username . '","' . $email . '","' . $password . '","' . $mobile . '","' . $uid . '")';
	$data = mysqli_query($conn, $query);

	if ($data == true) {
		$arr = [
			'success' => true,
			'message'  => "Thành Công",
		];
	} else {

		$arr = [
			'success' => false,
			'message'  => " Không Thành Công",
		];
	}
}


print_r(json_encode($arr));