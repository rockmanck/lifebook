<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="i18n" type="java.util.ResourceBundle"--%>
<%--@elvariable id="plan" type="ua.lifebook.plan.Plan"--%>
<%--@elvariable id="dayPlans" type="ua.lifebook.plan.PlansByDay"--%>
<%--@elvariable id="plansOverview" type="ua.lifebook.plan.OverviewPlans"--%>
<%--@elvariable id="row" type="java.util.List"--%>
TODO month choice control<br><br>
<table>
<c:forEach var="week" begin="0" end="${plansOverview.weeksCount - 1}">
    <c:set var="row" value="${plansOverview.data.get(week)}"/>
    <tr>
        <c:forEach var="dayNumber" begin="0" end="6">
            <c:set var="dayPlans" value="${row.get(dayNumber)}"/>
            <td class="overview" width="14.28%">
                <span class="overview-item-day">${dayPlans.day} ${dayPlans.dayOfMonth}</span><br>
                <c:forEach var="plan" items="${dayPlans.plans}">
                    <c:set var="isDone" value="${plan.status.code eq 'DN'}"/>
                    <c:set var="isCanceled" value="${plan.status.code eq 'CNCL'}"/>
                    <c:set var="isOutdated" value="${plan.outdated}"/>
                    <span title="${plan.comments}">${plan.title}</span>
                </c:forEach>
            </td>
        </c:forEach>
    </tr>
</c:forEach>
</table>