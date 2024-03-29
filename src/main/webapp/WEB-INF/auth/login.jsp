<!-- Load taglibs -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="https://www.thymeleaf.org"
      xmlns:sec="https://www.thymeleaf.org/thymeleaf-extras-springsecurity3">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">

    <title>Login Page</title>

</head>
<body>

<div id="login">
    <br>
    <div class="container">
        <div id="login-row" class="row justify-content-center">
            <div id="login-column" class="col-md-6">
                <div id="login-box">
                    <form:form action="/login" id="login-form" class="form" method="POST">
                        <h2 class="text-center text-dark">Login</h2>
                        <div class="form-group">
                            <label  for="username" class="text-dark">Username: </label><br>
                            <input type="text"  name="username" id="username" class="form-control">
                        </div>
                        <div class="form-group">
                            <label for="password"  class="text-dark">Password: </label>
                            <br>
                            <input type="password"  name="password" id="password" class="form-control">
                        </div>

                        <div class="form-group">

                            <input type="submit" id="submit" name="submit" class="btn btn-primary btn-md" value="Login" disabled>
                        </div>
                        <div id="register-link" class="text-right">
                            <a href="/register"  class="text-dark">Register</a>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI" crossorigin="anonymous"></script>
</body>

<script>
    function checkIfRight( username, password) {
        // code to be executed
        if(username&&password){
            $("#submit").attr("disabled", false);
        }
    }
    $(function() {
        var username = false;
        var password = false;

        $("#username").keyup(function() {
            if ($(this).val().length < 1) {
                $("#submit").attr("disabled", true);
                username = false;
            } else {
                username = true;
                checkIfRight(username,password);
            }
        });

        $("#password").keyup(function() {
            if ($(this).val().length < 1) {
                $('#submit').attr("disabled", true);
                password = false;
            } else {
                password = true;
                checkIfRight(username,password);
            }
        });
    });
</script>
<footer class="footer fixed-bottom">
    <div class="footer-copyright text-center py-3">
        Copyright &copy; 2020: Room Reservation Systems, All rights reserved.
    </div>
</footer>

</html>
