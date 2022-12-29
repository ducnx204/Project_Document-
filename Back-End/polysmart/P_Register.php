<?php
include "connect.php";
$ho = $_POST['ho'];
$ten = $_POST['ten'];
$email    = $_POST['email'];
$password = $_POST['password'];
$mobile = $_POST['mobile'];
$diachi = $_POST['diachi'];

// $passMaHoa = password_hash($password, PASSWORD_DEFAULT); // mã hóa password
// check email
$queryEmail = 'SELECT * FROM `user` WHERE `email`= "' . $email . '"';
$dataEmail = mysqli_query($conn, $queryEmail);
$numberEmail = mysqli_num_rows($dataEmail);
// check số điện thoại
$queryMobile = 'SELECT * FROM `user` WHERE `mobile`= "' . $mobile . '"';
$dataMobile = mysqli_query($conn, $queryMobile);
$numberMobile = mysqli_num_rows($dataMobile);
// kiểm tra emaill đã tồn tại hay chưa nếu email đã tồn tại thì sẻ đăng ký thất bại
if ($numberEmail > 0) {
	$arr = [
		'success' => false,
		'message' => " Email đã tồn tại. "
	];
} else if ($numberMobile > 0) {
	$arr = [
		'success' => false,
		'message' => " Số điện thoại đã tồn tại."
	];
} else {
	// check status
	$query = 'SELECT * FROM `user`';
	$data = mysqli_query($conn, $query);
	$numrow = mysqli_num_rows($data);
	// kiểm tra User
	if ($numrow > 0) {
		$query = 'INSERT INTO `user`(`ho`, `ten`, `email`, `password`,`mobile`,`diachi`,`status`,`VIP`) 
		VALUES ("' . $ho . '","' . $ten . '","' . $email . '","' . $password . '","' . $mobile . '","' . $diachi . '",1,1)';
		$data = mysqli_query($conn, $query);
		if ($data == true) {
			$arr = [
				'success' => true,
				'message' => " Đăng ký thành công."
			];
		} else {
			$arr = [
				'success' => false,
				'message'  => " Đăng ký thất bại."
			];
		}
	} else {
		// // 	//  insert Admin 
		$query = 'INSERT INTO `user`(`ho`, `ten`, `email`, `password`,`mobile`.`diachi`) 
		VALUES ("' . $ho . '","' . $ten . '","' . $email . '","' . $password . '","' . $mobile . '","' . $diachi . '",)';
		$data = mysqli_query($conn, $query);

		if ($data == true) {
			$arr = [
				'success' => true,
				'message' => " Đăng ký thành công."
			];
		} else {
			$arr = [
				'success' => false,
				'message'  => " Đăng ký thất bại."
			];
		}
	}
}

print_r(json_encode($arr));