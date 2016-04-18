<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="i18n" type="java.util.ResourceBundle"--%>
<%--@elvariable id="plan" type="ua.lifebook.plans.Plan"--%>
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
                    <li class="plan-edit" onclick="Plan.edit(${plan.id});"><span class="plan-edit-button"></span></li>
                    <li class="twitter" style="width:34%;"><a href="#twitter"><span class="fa fa-twitter"></span></a></li>
                    <li class="google-plus" style="width:33%;"><a href="#google-plus"><span>-</span></a></li>
                </ul>
            </div>
        </li>
    </c:forEach>
</ul>