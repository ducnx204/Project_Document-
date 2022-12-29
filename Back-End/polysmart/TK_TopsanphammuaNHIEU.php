<?php
include "connect.php";
$query = "SELECT `idsp`,product.name_product,SUM(`soluong`) AS tong FROM `chitietthanhtoan` INNER JOIN product ON product.id = chitietthanhtoan.idsp GROUP BY `idsp` ASC ORDER BY `tong` DESC LIMIT 4";
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