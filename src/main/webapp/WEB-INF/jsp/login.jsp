<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">

    <title>LifeBook Login</title>

    <!-- Latest compiled and minified Bootsrap CSS -->
    <link rel="stylesheet" href="<c:url value="/css/bootstrap.css"/>">

    <!-- Custom styles for this template -->
    <link href="<c:url value="/css/signin.css"/>" rel="stylesheet" media="screen">
</head>

<body>

<div class="container">

    <form class="form-signin" action="./login.html" method="POST">
        <h2 class="form-signin-heading">Please sign in</h2>
        <label for="login" class="sr-only">Login</label>
        <input type="text" id="login" class="form-control" placeholder="Login" required="" autofocus="" name="login">
        <label for="inputPassword" class="sr-only">Password</label>
        <input type="password" id="inputPassword" class="form-control" placeholder="Password" required="" name="password">
        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
    </form>

</div>


</body>
</html>