<?php
include "connect.php";

use PHPMailer\PHPMailer\PHPMailer;
use PHPMailer\PHPMailer\Exception;

require 'PHPMailer/src/Exception.php';
require 'PHPMailer/src/PHPMailer.php';
require 'PHPMailer/src/SMTP.php';
$linkid = "172.16.19.239";

$email = $_POST['email'];

$query = 'SELECT * FROM `user` WHERE `email`= "' . $email . '"';

$data = mysqli_query($conn, $query);
$result = array();

while ($row = mysqli_fetch_assoc($data)) {
    $result[] = ($row);
    // code...
}


if (empty($result)) {
    $arr = [
        'success'    => false,
        'message'  => "Mail không chính xác",
        'result'     => $result
    ];
} else {
    // send mail
    $email = ($result[0]["email"]);
    $password = ($result[0]["password"]);
    $link = "<a href='http://$linkid/polysmart/reset_pass.php?key=" . $email . "&reset=
      " . $password . "'>Đặt lại mật khẩu</a>";
    $mail = new PHPMailer();
    $mail->CharSet =  "utf-8";
    $mail->IsSMTP();
    // enable SMTP authentication
    $mail->SMTPAuth = true;

    // GMAIL username
    $mail->Username = "ducnxpd04647@fpt.edu.vn";
    // GMAIL password
    $mail->Password = "xuanduc123"; // mat khau cua gmail
    $mail->SMTPSecure = "ssl";
    // sets GMAIL as the SMTP server
    $mail->Host = "smtp.gmail.com";
    // set the SMTP port for the GMAIL server
    $mail->Port = "465";
    $mail->From =  "ducnxpd04647@fpt.edu.vn"; // mail nguoi nhan
    $mail->FromName = 'Polysmart'; // ten app
    $mail->AddAddress($email, 'reciever_name');
    $mail->Subject  =  'Đặt lại mật khẩu';
    $mail->IsHTML(true);
    $mail->Body    = $link;
    if ($mail->Send()) {
        $arr = [
            'success'    => true,
            'message'  =>  "Vui lòng kiểm tra lại Gmail của bạn",
            'result'     => $result
        ];
    } else {
        $arr = [
            'success'    => false,
            'message'   => "Gửi Gmail Không thành công",
            'result'     => $result
        ];
    }
}
print_r(json_encode($arr));