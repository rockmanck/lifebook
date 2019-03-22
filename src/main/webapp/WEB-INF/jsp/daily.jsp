<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="i18n" type="java.util.ResourceBundle"--%>
<%--@elvariable id="plan" type="ua.lifebook.plan.Plan"--%>
<div class="row">
    <div id="dailyList" class="col-sm-8">
        <c:set var="viewType" value="ViewType.DAILY" scope="request"/>
        <jsp:include page="plans/list.jsp"/>
    </div>
    <div class="col-sm-4">
        <div id="datepicker-daily"></div>
        <div style="margin-top: 8px; text-align: center;">
            <a class="btn icon-btn btn-info" href="#" data-toggle="modal" onclick="Plan.new('datepicker-daily', ${viewType});">
                <span class="glyphicon btn-glyphicon glyphicon-plus img-circle text-success"></span>
                Add Plan
            </a>
            <a class="btn icon-btn btn-success" href="#">
                <span class="glyphicon btn-glyphicon glyphicon-plus img-circle text-success"></span>
                Add Moment
            </a>
        </div>

        <div class="panel panel-default" style="margin-top: 15px;">
            <div class="panel-heading">View options</div>
            <div class="panel-body" id="view-options-daily">
                <jsp:include page="./viewOptions.jsp"/>
            </div>
        </div>
    </div>
</div>