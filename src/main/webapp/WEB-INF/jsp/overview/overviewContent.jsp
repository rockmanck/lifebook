<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="i18n" type="java.util.ResourceBundle"--%>
<%--@elvariable id="plan" type="ua.lifebook.plan.Plan"--%>
<%--@elvariable id="dayPlans" type="ua.lifebook.plan.ItemsByDay"--%>
<%--@elvariable id="plansOverview" type="ua.lifebook.plan.OverviewPlans"--%>
<%--@elvariable id="row" type="java.util.List"--%>
<c:if test="${plansOverview != null}">
    <table class="table table-bordered" style="font-family: 'Lato', sans-serif;">
        <c:forEach var="week" begin="0" end="${plansOverview.weeksCount - 1}">
            <c:set var="row" value="${plansOverview.data.get(week)}"/>
            <tr>
                <c:forEach var="dayNumber" begin="0" end="6">
                    <c:set var="dayPlans" value="${row.get(dayNumber)}"/>
                    <td class="overview" width="14.28%">
                        <span class="overview-item-day">${dayPlans.day} ${dayPlans.dayOfMonth}</span><br>
                        <ul class="list-unstyled" style="font-size: 10pt;">
                        <c:forEach var="plan" items="${dayPlans.plans}">
                            <li style="border-top: #9d9d9d solid 1px;">
                                <c:set var="isDone" value="${plan.status.code eq 'DN'}"/>
                                <c:set var="isCanceled" value="${plan.status.code eq 'CNCL'}"/>
                                <c:set var="isOutdated" value="${plan.outdated}"/>
                                <span title="${plan.comments}">${plan.title}</span>
                            </li>
                        </c:forEach>
                        </ul>
                    </td>
                </c:forEach>
            </tr>
        </c:forEach>
    </table>
</c:if>
