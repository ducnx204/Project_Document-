<?php

include "connect.php";

$target_dir = "images/";


// name
$query = "SELECT max(id) as id from product";
$data = mysqli_query($conn, $query);
$result = array();

while ($row = mysqli_fetch_assoc($data)) {
    $result[] = ($row);
    // code...
}
if ($result[0]['id'] == null) {
    $name = 1;
} else {
    $name = ++$result[0]['id'];
}
$name = $name . ".jpg";
$target_file_name = $target_dir . $name;

// Check if image file is an actual image or fake image  
if (isset($_FILES["file"])) {
    if (move_uploaded_file($_FILES["file"]["tmp_name"], $target_file_name)) {
        $arr = [
            'success' => true,
            'message'  => "success",
            "name"  => $name
        ];
    } else {
        $arr = [
            'success' => false,
            'message'  => "False",
        ];
    }
} else {
    $arr = [
        'success' => false,
        'message'  => "Error",
    ];
}

echo json_encode($arr);