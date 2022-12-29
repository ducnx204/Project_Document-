<?php
include "connect.php";
$mobile =  $_POST['mobile'];
$email    = $_POST['email'];

$query = 'SELECT * FROM `user` WHERE  `mobile` = "' . $mobile . '" AND  `email` = "' . $email . '"';
$data = mysqli_query($conn, $query);
$result = array();

while ($row = mysqli_fetch_assoc($data)) {
    $result[] = ($row);
    // code...
};

if (!empty($result)) {

    $arr = [
        'success' => true,
        'message'  => " Truy vấn thành công ",
        'result'    => $result

    ];
} else {
    $arr = [
        'success' => false,
        'message'  => " Tài khoản không tồn tại",
        'result'    => $result

    ];
}
print_r(json_encode($arr));