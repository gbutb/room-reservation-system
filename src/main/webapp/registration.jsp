<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- create Register page -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">

    <title>Register Page</title>

</head>
<body>

<div id="register">
    <br>
    <div class="container">
        <div id="register-row" class="row justify-content-center">
            <div id="register-column" class="col-md-6">

                <div  id="register-box" class="col-md-10">
                    <form id="register-form" class="form" action="login" method="post">
                        <h2 class="text-center text-dark">Register</h2>
                        <div class="form-group">
                            <label  for="username" class="text-dark">Username: </label><br>
                            <input type="text"  name="username" id="username" class="form-control">
                        </div>

                        <div class="form-group">
                            <label  for="mail" class="text-dark">Mail: </label><br>
                            <input type="text" name="mail" id="mail" class="form-control">
                        </div>
                        <div class="form-group">
                            <label for="password"  class="text-dark">Password: </label>
                            <br>
                            <input type="password"  name="password" id="password" class="form-control">
                        </div>
                        <div class="form-group">
                            <label for="password"  class="text-dark">Repeat Password: </label>
                            <br>
                            <input type="password"  name="password" id="repeatPassword" class="form-control">
                        </div>
                        <div class="form-group">
                            <label for="number"  class="text-dark">Phone Number: </label>
                            <br>
                            <input type="text"  name="number" id="number" class="form-control" required>
                        </div>

                        <div class="form-group">
                            <input type="submit" name="submit" id="submit" class="btn btn-primary btn-md" value="register" disabled>
                        </div>
                        <div id="login-link" class="text-right">
                            <a href="/login"  class="text-secondary">Login to existing account</a>
                        </div>

                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.rawgit.com/PascaleBeier/bootstrap-validate/v2.2.0/dist/bootstrap-validate.js" ></script>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI" crossorigin="anonymous"></script>

<script>
    bootstrapValidate('#username', 'min:5:Please enter minimum 5 symbols.')
    bootstrapValidate('#mail', 'email:Please enter a valid mail.')
    bootstrapValidate('#repeatPassword', 'matches:#password:Your passwords should match')

    $(function () {
        var validUsername=false;
        var validMail=false;
        var validNumber=false;
        var samePasswords=false;
        $('#username').keyup(function () {
            if ($(this).val().length <= 4) {
                $('#submit').attr("disabled",true);
                validUsername=false;
            } else {
                validUsername=true;
            }
        });

        $('#mail').keyup(function () {
            if (!($(this).val().endsWith("@freeuni.edu.ge")||$(this).val().endsWith("@agruni.edu.ge"))) {
                $('#submit').attr("disabled",true);
                validMail=false
            } else {
                validMail=true;

            }
        });
        $('#repeatPassword').keyup(function () {
            if (!($(this).val()===$('#password').val())) {
                $('#submit').attr("disabled",true);
                samePasswords=false;
            } else {
                samePasswords=true;
            }
        });
        $('#number').keyup(function () {
            if ($(this).val().length <= 8) {
                $('#submit').attr("disabled",true);
                validNumber=false;
            } else {
                validNumber=true;
                if(validMail&&validUsername&&validNumber&&samePasswords){
                    $('#submit').attr("disabled",false);
                }
            }
        });
    });
</script>

</body>
</html>