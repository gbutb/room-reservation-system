<!-- Load taglibs -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

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
                    <form:form id="register-form" class="form" action="/register" method="POST">
                        <h2 class="text-center text-dark">Register</h2>
                        <div class="form-group">
                            <label  for="username" class="text-dark">Username: </label><br>
                            <input type="text"  name="username" id="username" class="form-control">
                        </div>

                        <div class="form-group">
                            <label  for="mail" class="text-dark">Mail: </label><br>
                            <input type="text" name="email" id="mail" class="form-control">
                        </div>
                        <div id="password-group">
                            <div class="form-group">
                                <label for="password"  class="text-dark">Password: </label>
                                <br>
                                <input type="password"  name="password" id="password" class="form-control">
                                <span id = 'strongMessage'></span>
                            </div>

                            <div class="form-group">
                                <label for="repeatPassword"  class="text-dark">Repeat Password: </label>
                                <br>
                                <input type="password" id="repeatPassword" class="form-control">
                                <span id = 'message'></span>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="number"  class="text-dark">Phone Number: </label>
                            <br>
                            <input type="text"  name="number" id="number" class="form-control" required>
                            <span id = 'numMessage'></span>
                        </div>

                        <div class="form-group">
                            <input type="submit" name="submit" id="submit" class="btn btn-primary btn-md" value="register" disabled>
                        </div>
                        <div id="login-link" class="text-right">
                            <a href="/login"  class="text-secondary">Login to existing account</a>
                        </div>

                    </form:form>
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

    function checkIfRight(validUsername,validMail,validNumber,samePasswords){
        if(validMail&&validUsername&&validNumber&&samePasswords){
            $('#submit').attr("disabled",false);
        }
    }

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
                checkIfRight(validUsername,validMail,validNumber,samePasswords);
            }
        });

        $('#mail').keyup(function () {
            if (!($(this).val().endsWith('@freeuni.edu.ge')||$(this).val().endsWith('@agruni.edu.ge'))) {
                $('#submit').attr("disabled",true);
                validMail=false
            } else {
                validMail=true;
                checkIfRight(validUsername,validMail,validNumber,samePasswords);
            }
        });

        $('#password').keyup(function(){
            if($('#password').val() == ""){
                $('#strongMessage').html("This must be filled").css('color', 'grey');
            }else if(passwordIsStrong($('#password').val())){
                $('#strongMessage').html("Password is strong").css('color', 'green');
            }else{
                $('#strongMessage').html(`Password must contain: 1 lowercase letter, 1 uppercase letter, be minimum 8 characters`).css('color', 'red');
            }
        });

        $('#password-group').keyup(function () {
            if($('#repeatPassword').val() == ""){
                $('#submit').attr("disabled",true);
                samePasswords=false;
            }else if (!($('#password').val()===$('#repeatPassword').val())) {
                $('#submit').attr("disabled",true);
                samePasswords=false;
                $('#message').html("Your passwords should match").css('color', 'red');
            } else {
                samePasswords=true;
                $('#message').html("");
                checkIfRight(validUsername,validMail,validNumber,samePasswords);
            }
        });


        $('#number').keyup(function () {
            if ($(this).val() == "") {
                $('#submit').attr("disabled",true);
                $('#numMessage').html("Please enter your phone number").css('color', 'red');
                validNumber=false;
            } else if (!checkNumber($(this).val()) && $(this).val() != "") {
                $('#submit').attr("disabled",true);
                $('#numMessage').html("Please enter a valid phone number").css('color', 'red');
                validNumber=false;
            } else {
                validNumber=true;
                $('#numMessage').html("");
                checkIfRight(validUsername,validMail,validNumber,samePasswords);
            }
        });
    });

    function passwordIsStrong(pass){
        var strongRegex = new RegExp("^(?=.*[a-z])(?=.*[A-Z])(?=.{8,})");
        return pass.match(strongRegex);
    }
    function checkNumber(num){
        var reg = /^\d+$/;

        return reg.test(num) && num.length==9;
    }
</script>

</body>
</html>