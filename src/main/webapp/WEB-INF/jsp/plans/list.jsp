<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--@elvariable id="i18n" type="java.util.ResourceBundle"--%>
<%--@elvariable id="plan" type="ua.lifebook.plan.Plan"--%>
<c:set var="newlineWindows" value="<%= \"\r\n\" %>" />
<c:set var="newline" value="<%= \"\n\" %>" />
<c:forEach var="planByDay" items="${plans}">
    <div>
        <c:set var="day" value="${planByDay.getDay()}"/>
        <div class="plans-day-strip expanded" data-forDay="${day}">${day}</div>
        <div class="plans-day-list">
            <ul class="event-list">
            <c:forEach var="plan" items="${planByDay.plans}">
                <c:set var="isDone" value="${plan.status.code eq 'DN'}"/>
                <c:set var="isCanceled" value="${plan.status.code eq 'CNCL'}"/>
                <c:set var="isOutdated" value="${plan.outdated}"/>
                <li id="plan-${plan.id}" class="<c:if test="${isDone}">done</c:if> <c:if test="${isCanceled}">canceled</c:if>">
                    <time datetime="${plan.dueDate}">
                        <span class="day">${plan.dueDate.dayOfMonth}</span>
                        <span class="month">${plan.dueDate.month}</span>
                        <span class="time">${plan.dueDate.toLocalTime()}</span>
                    </time>
                    <div class="info<c:if test='${isOutdated}'> outdated</c:if>">
                        <h2 class="title">${plan.title}</h2>
                        <p class="desc">${fn:replace(fn:replace(plan.comments, newline, "<br/>"), newlineWindows, "<br/>")}</p>
                    </div>
                    <div class="social">
                        <ul>
                            <li class="plan-action" onclick="Plan.edit(${plan.id}, ${viewType});"><span class="edit-button" title="Edit Plan"></span></li>
                            <c:if test="${!isDone && !isCanceled}">
                                <li class="plan-action" onclick="Plan.done(${plan.id}, ${viewType}, '${day}');"><span class="done-button" title="Mark as Done"></span></li>
                                <li class="plan-action" onclick="Plan.cancel(${plan.id}, ${viewType}, '${day}');"><span class="cancel-button" title="Cancel Plan"></span></li>
                            </c:if>
                        </ul>
                    </div>
                </li>
            </c:forEach>
            </ul>
        </div>
    </div>
</c:forEach>
