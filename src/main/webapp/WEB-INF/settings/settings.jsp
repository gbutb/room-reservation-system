<%--
  Created by IntelliJ IDEA.
  User: dadak
  Date: 31.07.2020
  Time: 16:50
  To change this template use File | Settings | File Templates.
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
          integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">

    <title>Account Settings</title>
</head>

<body>
<%-- Container for entire screen --%>
<div class="d-flex flex-column" style="width: 100%; height: 100vh; min-width: 1280px; min-height: 720px;">

    <%-- Container for top navigation bar --%>
    <div class="d-flex flex-row justify-content-between align-items-center sticky-top bg-dark"
         style="width: 100%; height: 10%;">

        <%-- Container for navigation bar branding --%>
        <div class="d-flex justify-content-start align-items-center p-4" style="width: 100px; height: 100%;">
            <a class="text-light" style="font-size: 1.2rem;" href="/homepage-gv?floor=1">RRS</a>
        </div>

        <%-- Container for user dropdown toggler --%>
        <div class="d-flex justify-content-end align-items-center p-4" style="width: 100px; height: 100%;">
            <div class="dropdown">
                <a class="dropdown-toggle text-muted" href="#" style="font-size: 1.2rem" id="userMenu"
                   data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    User
                </a>
                <div class="dropdown-menu dropdown-menu-right" aria-labelledby="userMenu">
                    <a class="dropdown-item" href="#">Settings</a>
                    <div class="dropdown-divider"></div>
                    <a class="dropdown-item" href="#">Log Out</a>
                </div>
            </div>
        </div>
    </div>

    <%-- Container for bottom space after navigation bar --%>
    <div class="d-flex flex-column justify-content-center align-items-center bg-light" style="width: 100%; height: 90%;">

        <%-- Container for title --%>
        <div class="d-flex justify-content-between align-items-center" style="width: 50%;">
            <h2>Settings</h2>
        </div>

        <%-- Container for settings view --%>
        <span class="d-flex justify-content-center align-items-start border rounded  p-4 align-items-center"
              style="border-width: 1.5px !important; width: 50%; height: 550px;">

            <%-- TODO: User code here --%>

                <div class="container">
                    <div class="column justify-content-center col-md-4 mx-auto">
                        <form:form id="afterSave" class="form" action="settings" method="POST">
                            <div class="form-group">
                                <label for="number" class="text-dark">New Phone Number: </label><br>
                                <input type="text"  name="number" id="number" class="form-control">
                                 <span id = 'numMessage'></span>
                            </div>
                            <div class="form-group">
                                <label for="password"  class="text-dark">New Password: </label>
                                <br>
                                <input type="password"  name="password" id="password" class="form-control">
                                <span id = 'strongMessage'></span>
                            </div>
                            <div class="form-group">
                                    <label for="repeatPassword"  class="text-dark">Repeat Password: </label>
                                    <br>
                                    <input type="password" id="repeatPassword" class="form-control">
                                    <span id = 'repeatMessage'></span>
                            </div>
                            <div class="form-group">
                                <input type="submit" id="change" name="change" class="btn btn-primary btn-md" value="submit changes" disabled>
                            </div>
                        </form:form>
                    </div>
                </div>

        </span>
    </div>
</div>


<script src="https://cdn.rawgit.com/PascaleBeier/bootstrap-validate/v2.2.0/dist/bootstrap-validate.js" ></script>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI" crossorigin="anonymous"></script>

<script>
    $(function () {
        var validNumber=false;
        var samePasswords=false;

        $('#password').keyup(function(){
            if($('#password').val() == ""){
                $('#strongMessage').html("");
                if($('#number').val() == ""){
                    $('#change').attr("disabled",true);
                }
            }else if(passwordIsStrong($('#password').val())){
                $('#strongMessage').html("Password is strong").css('color', 'green');
            }else{
                $('#strongMessage').html(`Password must contain: 1 lowercase letter, 1 uppercase letter, be minimum 8 characters`).css('color', 'red');
            }
        });

        $('#repeatPassword').keyup(function () {
            if($('#repeatPassword').val() == ""){
                $('#change').attr("disabled",true);
                samePasswords=false;
            }else if (!($('#password').val()===$('#repeatPassword').val())) {
                $('#change').attr("disabled",true);
                samePasswords=false;
                $('#repeatMessage').html("Your passwords should match").css('color', 'red');
            } else {
                samePasswords=true;
                $('#repeatMessage').html("");
                $('#change').attr("disabled", false);
            }
        });


        $('#number').keyup(function () {
            if($(this).val() == "" && $('#password').val() == ""){
                $('#change').attr("disabled",true);
                validNumber=false;
                samePasswords=false;
            }else if (!checkNumber($(this).val()) && $(this).val() != "") {
                $('#change').attr("disabled",true);
                $('#numMessage').html("Please enter a valid phone number").css('color', 'red');
                validNumber=false;
            } else {
                validNumber=true;
                $('#numMessage').html("");
                $('#change').attr("disabled", false);
            }
        });
    });

    function passwordIsStrong(pass){
        var strongRegex = new RegExp("^(?=.*[a-z])(?=.*[A-Z])(?=.{8,})");
        return pass.match(strongRegex) && pass!="";
    }
    function checkNumber(num){
        var reg = /^\d+$/;

        return reg.test(num) && num.length==9;
    }
</script>

<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
        integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"
        integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI"
        crossorigin="anonymous"></script>

</body>
</html>
