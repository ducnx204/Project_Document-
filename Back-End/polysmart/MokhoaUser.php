<?php
include "connect.php";
$mobile =  $_POST['mobile'];
$email    = $_POST['email'];
$id    = $_POST['id'];

$query = 'SELECT * FROM `user` WHERE  `mobile` = "' . $mobile . '" AND  `email` = "' . $email . '" AND  `id` = "' . $id . '"';
$data = mysqli_query($conn, $query);
$result = array();

while ($row = mysqli_fetch_assoc($data)) {
    $result[] = ($row);
    // code...
};

if (!empty($result)) {

    $arr = [
        'success' => true,
        'message'  => " Mở khóa tài khoản thành công",
        'result'    => $result

    ];
} else {
    $arr = [
        'success' => false,
        'message'  => "Mở khóa tài khoản thất bại",
        'result'    => $result

    ];
}

print_r(json_encode($arr));