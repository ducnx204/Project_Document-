<?php
include "connect.php";
if ($_GET['key'] && $_GET['reset']) {
    $email = $_GET['key'];
    $password = $_GET['reset'];
    // $pass = password_hash($password, PASSWORD_DEFAULT);
    $query = "select email,password from user where email ='$email' and password ='$password'";
    $data = mysqli_query($conn, $query);
    if ($data == true) {
?>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đặt lại mật khẩu</title>
    <link rel="icon" type="image/x-icon" href="https://www.iconpacks.net/icons/2/free-reddit-logo-icon-2436-thumb.png">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css"
        integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>

<body>
    <center style="margin-top: 50px;">
        <img style="margin-top: 40px ;width:100px ; height: 100px;border-radius: 50px;"
            src="https://www.iconpacks.net/icons/2/free-reddit-logo-icon-2436-thumb.png" />
        <main>
            <section class="signup-section">
                <div class="container">
                    <div class="" style="border: none">
                        <article class="card-body mx-auto" style="max-width: 400px;">
                            <form method="post" action="submit_new.php" id="form-register">
                                <!-- GET Email// -->
                                <input type="hidden" name="email" value="<?php echo $email; ?>" />
                                <!-- form-group// -->
                                <div class="form-group">
                                    <input style="width: 300px; height: 40px;padding-left: 10px;" name="password"
                                        class="form-control password" placeholder="Mật khẩu" type="password">
                                    <div style="margin-bottom: 5px;margin-top: 5px;">
                                        <span style="font-size: 15px; color: red;width: 100%;"
                                            class="form-message"></span>
                                    </div>
                                </div>
                                <div class="form-group ">
                                    <input style="width: 300px; height: 40px;padding-left: 10px;" name=""
                                        class="form-control password_confirmation" placeholder="Xác nhận Mật khẩu"
                                        type="password">

                                    <div style="margin-bottom: 5px;margin-top: 5px;">
                                        <span style="font-size: 15px;color: red;width: 100%;"
                                            class="form-message"></span>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <button style="width: 250px;border-radius: 4px;" type="submit"
                                        name="submit_password" class="btn btn-primary"> Đặt lại mật khẩu
                                    </button>
                                </div> <!-- form-group// -->

                            </form>
                        </article>
                    </div> <!-- card.// -->

                </div>
                <!--container end.//-->
        </main>
    </center>
</body>

</html>
<?php
    }
}
?>

<script>
function Validator(options) {
    var formElement = document.querySelector(options.form);
    var selectorRules = {}
    // hàm thực hiện validate
    var selectorRules = {}

    function validate(inputElement, rule) {
        var input = inputElement.parentElement.querySelector('.form-control')
        var errorElement = inputElement.parentElement.querySelector('.form-message');
        var errorMessage
        //
        var rules = selectorRules[rule.selector]

        for (var i = 0; i < rules.length; ++i) {
            errorMessage = rules[i](inputElement.value)
            if (errorMessage) break;
        }
        if (errorMessage) {
            errorElement.innerText = errorMessage;
            input.style.borderColor = '#ff0000'
        } else {
            errorElement.innerText = ''
            input.style.borderColor = ''
        }
        return !errorMessage;
    }

    // lấy element của form
    if (formElement) {
        formElement.onsubmit = function(e) {


            var isFormValid = true

            options.rules.forEach(function(rule) {

                var inputElement = formElement.querySelector(rule.selector)
                var isValid = validate(inputElement, rule)
                if (!isValid) {
                    isFormValid = false
                }
            });


            if (isFormValid) {
                formElement.submit()
            } else {
                e.preventDefault();
            }
        }
        // lặp qua mỗi rule và xửa lý sự kiện
        options.rules.forEach(function(rule) {
            //lu lai cac rules cho moi input

            if (Array.isArray(selectorRules[rule.selector])) {
                selectorRules[rule.selector].push(rule.test)
            } else {
                selectorRules[rule.selector] = [rule.test]
            }

            var inputElement = formElement.querySelector(rule.selector)
            var errorElement = inputElement.parentElement.querySelector(options.errorSelector);
            var input = inputElement.parentElement.querySelector('.form-control')

            if (inputElement) {
                inputElement.onblur = function() {
                    validate(inputElement, rule)
                }

                inputElement.oninput = function() {
                    errorElement.innerHTML = ''
                    input.style.borderColor = ''
                }
            }
        })
    }
}

Validator.isRequired = function(selector) {
    return {
        selector,
        test(value) {
            return value.trim() ? undefined : 'Vui lòng nhập tên'
        }
    }
}
Validator.isPassword = function(selector) {
    return {
        selector,
        test(value) {
            return value ? undefined : 'Vui lòng nhập mật khẩu'
        }
    }
}

Validator.minLength = function(selector, min) {
    return {
        selector,
        test(value) {
            return value.length >= min ? undefined : `Vui lòng nhập tối đa ${min} ký tự`
        }
    }
}


Validator.setMess = function(selector) {
    return {
        selector,
        test(value) {
            return value ? undefined : 'Vui lòng xác nhận mật khẩu'
        }
    }
}

Validator.isComfirmed = function(selector, getComfirmed, message) {
    return {
        selector,
        test(value) {
            return value == getComfirmed() ? undefined : message || 'Giá trị sai'
        }
    }
}

Validator.isAddress = function(selector, message) {
    return {
        selector,
        test(value) {
            return value ? undefined : message || 'Vui lòng nhập'
        }
    }
}

Validator.isPhone = function(selector, min, message) {
    return {
        selector,
        test(value) {
            return value.length >= min ? undefined : `Vui lòng nhập tối thiểu ${min} số `
        }
    }
}


Validator({
    form: '#form-register',
    errorSelector: '.form-message',
    rules: [
        Validator.isPassword('.password'),
        Validator.minLength('.password', 6),
        Validator.setMess('.password_confirmation'),
        Validator.isComfirmed('.password_confirmation', function() {
            return document.querySelector('#form-register .password').value
        }, 'Xác nhận Mật khẩu không chính xác'),
    ],
})
</script>