<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="i18n" type="java.util.ResourceBundle"--%>
<html>
<head>
    <title>Your LifeBook</title>
    <script src="<c:url value="/js/jquery-2.1.4.js"/>"></script>
    <script src="<c:url value="/js/moment-with-locales.js"/>"></script>
    <script src="<c:url value="/js/bootstrap.js"/>"></script>
    <script src="<c:url value="/js/bootstrap-datetimepicker.js"/>"></script>
    <script src="<c:url value="/js/index.js"/>"></script>
    <link rel="stylesheet" href="<c:url value="/css/bootstrap.css"/>">
    <link rel="stylesheet" href="<c:url value="/css/bootstrap-datetimepicker.css"/>">
</head>
<body>
<div class="container">
    <div class="jumbotron">
        <h2>${i18n.getString("welcome")}</h2>
        <p>${i18n.getString("descriptionMain")}</p>
    </div>

    <ul class="nav nav-tabs">
        <li class="active"><a data-toggle="tab" href="#daily">Daily</a></li>
        <li><a data-toggle="tab" href="#weekly">Weekly</a></li>
        <li><a data-toggle="tab" href="#search">Search</a></li>
    </ul>

    <div class="tab-content">
        <div id="daily" class="tab-pane fade in active">
            <jsp:include page="daily.jsp"/>
        </div>
        <div id="weekly" class="tab-pane fade">

        </div>
        <div id="search" class="tab-pane fade">

        </div>
    </div>
</div>
</body>
</html>
