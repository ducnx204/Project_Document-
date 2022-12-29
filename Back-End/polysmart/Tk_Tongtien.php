<?php
include "connect.php";
$query = "SELECT `idsp`,name_product,SUM(`gia`) AS tong FROM `chitietthanhtoan` INNER JOIN product ON product.price_product = chitietthanhtoan.gia GROUP BY `gia` ORDER BY `tong` DESC LIMIT 7";
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