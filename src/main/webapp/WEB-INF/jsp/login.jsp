<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
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
    <%--@elvariable id="i18n" type="java.util.ResourceBundle"--%>
    <form class="form-signin" action="./login.html" method="POST">
        <h2 class="form-signin-heading">${i18n.getString("signInPrompt")}</h2>
        <label for="login" class="sr-only">${i18n.getString("login")}</label>
        <input type="text" id="login" class="form-control" placeholder="${i18n.getString("login")}" required="" autofocus="" name="login">
        <label for="inputPassword" class="sr-only">${i18n.getString("password")}</label>
        <input type="password" id="inputPassword" class="form-control" placeholder="${i18n.getString("password")}" required="" name="password">
        <button class="btn btn-lg btn-primary btn-block" type="submit">${i18n.getString("signIn")}</button>
    </form>

</div>


</body>
</html>