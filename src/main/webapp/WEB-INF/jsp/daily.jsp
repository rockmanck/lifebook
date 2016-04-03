<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="i18n" type="java.util.ResourceBundle"--%>
<%--@elvariable id="plan" type="ua.lifebook.plans.Plan"--%>
<div class="row">
    <div class="col-sm-8">
        <ul class="event-list">
            <c:forEach var="plan" items="${plans}">
                <li>
                    <time datetime="${plan.dueDate}">
                        <span class="day">${plan.dueDate.dayOfMonth}</span>
                        <span class="month">${plan.dueDate.month}</span>
                        <span class="time">${plan.dueDate.toLocalTime()}</span>
                    </time>
                    <div class="info">
                        <h2 class="title">${plan.title}</h2>
                        <p class="desc">${plan.comments}</p>
                    </div>
                    <div class="social">
                        <ul>
                            <li class="facebook" style="width:33%;"><a href="#facebook"><span class="fa fa-facebook"></span></a></li>
                            <li class="twitter" style="width:34%;"><a href="#twitter"><span class="fa fa-twitter"></span></a></li>
                            <li class="google-plus" style="width:33%;"><a href="#google-plus"><span class="fa fa-google-plus"></span></a></li>
                        </ul>
                    </div>
                </li>
            </c:forEach>
        </ul>
    </div>
    <div class="col-sm-4">
        <div id="datepicker-daily"></div>
        <div style="margin-top: 8px; text-align: center;">
            <a class="btn icon-btn btn-info" href="#" data-toggle="modal" data-target="#planModal">
                <span class="glyphicon btn-glyphicon glyphicon-plus img-circle text-success"></span>
                Add Plan
            </a>
            <a class="btn icon-btn btn-success" href="#">
                <span class="glyphicon btn-glyphicon glyphicon-plus img-circle text-success"></span>
                Add Moment
            </a>
        </div>
    </div>
</div>

<jsp:include page="planForm.jsp"/>