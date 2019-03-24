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
    <script src="<c:url value="/js/user-settings.js"/>"></script>
    <script src="<c:url value="/js/loader.js"/>"></script>
    <script src="<c:url value="/js/plansListCollapse.js"/>"></script>
    <link rel="shortcut icon" type="image/png" href="<c:url value="/img/lb-favicon.png"/>"/>
    <link rel="stylesheet" href="<c:url value="/css/bootstrap.css"/>">
    <link rel="stylesheet" href="<c:url value="/css/bootstrap-datetimepicker.css"/>">
    <link rel="stylesheet" href="<c:url value="/css/eventlist.css"/>">
    <link rel="stylesheet" href="<c:url value="/css/index.css"/>">
    <%--some new line of code--%>
</head>
<body>
<div class="container">
    <div class="alert alert-warning" id="implement-me" style="display: none;">
        <a href="#" class="close" data-dismiss="alert">&times;</a>
        <strong>Hi Dev!</strong> It is not implemented yet. Let's do it!
    </div>

    <div id="loading" style="display: none;"></div>
    <div class="jumbotron">
        <h2>${i18n.getString("welcome")}</h2>
        <p>${i18n.getString("descriptionMain")}</p>

        <div class="dropdown" id="user-menu">
            <button class="btn btn-info dropdown-toggle" type="button" data-toggle="dropdown">
                ${user.firstName} ${user.lastName}
                <span class="caret"></span></button>
            <ul class="dropdown-menu">
                <li><a href="#" onclick="animation.notImplemented();">Admin</a></li>
                <li><a href="#" onclick="animation.notImplemented();">Change Password</a></li>
                <li class="divider"></li>
                <li><a href="/logout.html">Logout</a></li>
            </ul>
        </div>
    </div>

    <ul class="nav nav-tabs">
        <li <c:if test='${defaultTab eq "DAILY"}'>class="active"</c:if>>
            <a data-toggle="tab" href="#daily" data-type="DAILY">${i18n.getString("daily")}</a>
        </li>
        <li <c:if test='${defaultTab eq "WEEKLY"}'>class="active"</c:if>>
            <a data-toggle="tab" href="#weekly" data-type="WEEKLY">${i18n.getString("weekly")}</a>
        </li>
        <li <c:if test='${defaultTab eq "SEARCH"}'>class="active"</c:if>>
            <a data-toggle="tab" href="#search" data-type="SEARCH">${i18n.getString("search")}</a>
        </li>
        <li <c:if test='${defaultTab eq "OVERVIEW"}'>class="active"</c:if>>
            <a data-toggle="tab" href="#overview" data-type="OVERVIEW">${i18n.getString("overview")}</a>
        </li>
    </ul>

    <c:set var="viewOptions" value="${userViewOptions}" scope="request"/>
    <div class="tab-content">
        <div id="daily" class="tab-pane fade <c:if test='${defaultTab eq "DAILY"}'>in active</c:if>">
            <jsp:include page="daily.jsp"/>
        </div>
        <div id="weekly" class="tab-pane fade <c:if test='${defaultTab eq "WEEKLY"}'>in active</c:if>">
            <jsp:include page="weekly.jsp"/>
        </div>
        <div id="search" class="tab-pane fade <c:if test='${defaultTab eq "SEARCH"}'>in active</c:if>">

        </div>
        <div id="overview" class="tab-pane fade <c:if test='${defaultTab eq "OVERVIEW"}'>in active</c:if>">
            <jsp:include page="overview/overview.jsp"/>
        </div>
    </div>
</div>

<div class="modal fade" id="planModal" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
    <jsp:include page="planForm.jsp"/>
</div>

</body>
</html>
