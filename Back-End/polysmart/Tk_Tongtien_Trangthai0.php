<?php
include "connect.php";
$query = "SELECT SUM(tongtien) AS tong FROM `thanhtoan` WHERE trangthai = 0";
$data = mysqli_query($conn, $query);
$result = array();

while ($row = mysqli_fetch_assoc($data)) {
    $result[] = ($row);
    // code...
};

if (!empty($result)) {

    $arr = [
        'success' => true,
        'message'  => "thanh Cong",
        'result'    => $result
    ];
} else {
    $arr = [
        'success' => false,
        'message'  => " Khong Thanh Cong",
        'result'    => $result
    ];
}
print_r(json_encode($arr));