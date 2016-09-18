<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="i18n" type="java.util.ResourceBundle"--%>
<%--@elvariable id="plan" type="ua.lifebook.plans.Plan"--%>
<ul class="event-list">
    <c:forEach var="planByDay" items="${plans}">
        <div>
            ${planByDay.getDay()}
        </div>
        <c:forEach var="plan" items="${planByDay.plans}">
            <c:set var="isDone" value="${plan.status.code eq 'DN'}"/>
            <c:set var="isCanceled" value="${plan.status.code eq 'CNCL'}"/>
            <c:set var="isOutdated" value="${plan.outdated}"/>
            <li id="plan${plan.id}" class="<c:if test="${isDone}">done</c:if> <c:if test="${isCanceled}">canceled</c:if>">
                <time datetime="${plan.dueDate}">
                    <span class="day">${plan.dueDate.dayOfMonth}</span>
                    <span class="month">${plan.dueDate.month}</span>
                    <span class="time">${plan.dueDate.toLocalTime()}</span>
                </time>
                <div class="info<c:if test='${isOutdated}'> outdated</c:if>">
                    <h2 class="title">${plan.title}</h2>
                    <p class="desc">${plan.comments}</p>
                </div>
                <div class="social">
                    <ul>
                        <li class="plan-action" onclick="Plan.edit(${plan.id}, ViewType.WEEKLY);"><span class="edit-button" title="Edit Plan"></span></li>
                        <c:if test="${!isDone && !isCanceled}">
                            <li class="plan-action" onclick="Plan.done(${plan.id});"><span class="done-button" title="Mark as Done"></span></li>
                            <li class="plan-action" onclick="Plan.cancel(${plan.id});"><span class="cancel-button" title="Cancel Plan"></span></li>
                        </c:if>
                    </ul>
                </div>
            </li>
        </c:forEach>
    </c:forEach>
</ul>