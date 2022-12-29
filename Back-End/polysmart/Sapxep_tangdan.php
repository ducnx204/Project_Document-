<?php
include "connect.php";
// truy vấn từ nhỏ đến lớn
$query = "SELECT * FROM `product` ORDER BY `product`.`price_product` ASC";
$data = mysqli_query($conn, $query);
$result = array();

while ($row = mysqli_fetch_assoc($data)) {
    $result[] = ($row);
    // code...
};

if (!empty($result)) {

    $arr = [
        'success' => true,
        'message'  => "Sắp xếp thành công",
        'result'    => $result
    ];
} else {
    $arr = [
        'success' => false,
        'message'  => "Sắp xếp thất bại",
        'result'    => $result
    ];
}
print_r(json_encode($arr));