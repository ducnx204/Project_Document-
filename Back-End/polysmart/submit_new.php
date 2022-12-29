<?php
include "connect.php";
if (isset($_POST['submit_password']) && $_POST['email']) {
    $email = $_POST['email'];
    $password = $_POST['password'];
    // $passMaHoa = password_hash($password, PASSWORD_DEFAULT); // mã hóa password
    $query = "update user set password='$password' where email='$email'";
    $data = mysqli_query($conn, $query);
    if ($data == true) {
?>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thay đổi mật khẩu thành công</title>
    <link rel="icon" type="image/x-icon" href="https://www.iconpacks.net/icons/2/free-reddit-logo-icon-2436-thumb.png">
</head>

<body>
    <center>
        <div style="width: 400px;height: 300px">
            <img style="margin-top: 40px ;width:100px ; height: 100px;border-radius: 50px;"
                src="https://www.iconpacks.net/icons/2/free-reddit-logo-icon-2436-thumb.png" />
            <br />
            <h4>
                Chúc mừng bạn đã thay đổi mật khẩu thành công.
            </h4>
        </div>
    </center>
</body>

</html>


<?php
    }
}