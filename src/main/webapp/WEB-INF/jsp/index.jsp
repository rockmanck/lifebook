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
    <script src="<c:url value="/js/plan.js"/>"></script>
    <script src="<c:url value="/js/animation.js"/>"></script>
    <link rel="shortcut icon" type="image/png" href="<c:url value="/img/lb-favicon.png"/>"/>
    <link rel="stylesheet" href="<c:url value="/css/bootstrap.css"/>">
    <link rel="stylesheet" href="<c:url value="/css/bootstrap-datetimepicker.css"/>">
    <link rel="stylesheet" href="<c:url value="/css/eventlist.css"/>">
    <style>
        .btn-glyphicon { padding:8px; background:#ffffff; margin-right:4px; }
        .icon-btn { padding: 1px 15px 3px 2px; border-radius:50px;}
    </style>
</head>
<body>
<div class="container">
    <div class="jumbotron">
        <h2>${i18n.getString("welcome")}</h2>
        <p>${i18n.getString("descriptionMain")}</p>
    </div>

    <ul class="nav nav-tabs">
        <li class="active"><a data-toggle="tab" href="#daily">${i18n.getString("daily")}</a></li>
        <li><a data-toggle="tab" href="#weekly">${i18n.getString("weekly")}</a></li>
        <li><a data-toggle="tab" href="#search">${i18n.getString("search")}</a></li>
    </ul>

    <div class="tab-content">
        <div id="daily" class="tab-pane fade in active">
            <c:set var="plans" value="${todayPlans}" scope="request"/>
            <jsp:include page="daily.jsp"/>
        </div>
        <div id="weekly" class="tab-pane fade">
            <c:set var="wplans" value="${weekPlans}" scope="request"/>
            <jsp:include page="weekly.jsp"/>
        </div>
        <div id="search" class="tab-pane fade">

        </div>
    </div>
</div>

<div class="modal fade" id="planModal" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
    <jsp:include page="planForm.jsp"/>
</div>

</body>
</html>
