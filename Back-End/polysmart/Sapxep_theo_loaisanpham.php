<?php
include "connect.php";
// sắp xếp theo loại sản phẩm
$query = "SELECT * FROM `product` ORDER BY `product`.`id_category`";
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