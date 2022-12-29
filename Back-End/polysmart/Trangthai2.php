<?php
include "connect.php";
$iduser = $_POST['iduser'];


if ($iduser == 0) {
    //get all don hang theo user admin  
    $query = 'SELECT * FROM `thanhtoan` WHERE trangthai = 2 ORDER BY `thanhtoan`.`id` DESC';
} else {
    $query = 'SELECT * FROM `thanhtoan` WHERE  `iduser` = ' . $iduser . ' AND trangthai = 2  ORDER BY id DESC';
}

$data = mysqli_query($conn, $query);
$result = array();

while ($row = mysqli_fetch_assoc($data)) {
    $truyvan = 'SELECT * FROM `chitietthanhtoan`INNER JOIN product ON chitietthanhtoan.idsp = product.id WHERE chitietthanhtoan.iddonhang =' . $row['id'];
    $data1 = mysqli_query($conn, $truyvan);
    $item = array();
    while ($row1 = mysqli_fetch_assoc($data1)) {
        $item[] = $row1;
    }
    $row['item'] = $item;
    $result[] = ($row);
    // code...
};

if (!empty($result)) {

    $arr = [
        'success' => true,
        'message'  => "Thành Công",
        'result'    => $result
    ];
} else {
    $arr = [
        'success' => false,
        'message'  => " Không Thành Công",
        'result'    => $result
    ];
}
print_r(json_encode($arr));