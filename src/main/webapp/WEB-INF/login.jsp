<!doctype html>
<html lang="en">
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

                <div  "font-size:small;" id="login-box" class="col-md-10">
                <form id="login-form" class="form" action="POST" >
                    <h2 class="text-center text-info">Login</h2>
                    <div class="form-group">
                        <label  for="username" class="text-info">Username: </label><br>
                        <input type="text" placeholder ="type username" name="username" id="username" class="form-control">
                    </div>
                    <div class="form-group">
                        <label for="password"  class="text-info">Password: </label>
                        <br>
                        <input type="text" placeholder = "type password" name="password" id="password" class="form-control">
                    </div>

                    <div class="form-group">

                        <input type="submit" name="submit" class="btn btn-info btn-md" value="submit">
                    </div>
                    <div id="register-link" class="text-right">
                        <a href="/register"  class="text-info">Register</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</div>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI" crossorigin="anonymous"></script>
</body>

</html>

