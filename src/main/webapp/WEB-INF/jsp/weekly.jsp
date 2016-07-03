<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="i18n" type="java.util.ResourceBundle"--%>
<%--@elvariable id="plan" type="ua.lifebook.plans.Plan"--%>
<div class="row">
    <div id="weeklyList" class="col-sm-8">
        <jsp:include page="plans/weeklyList.jsp"/>
    </div>
    <div class="col-sm-4">
        <div id="datepicker-weekly"></div>
        <div style="margin-top: 8px; text-align: center;">
            <a class="btn icon-btn btn-info" href="#" data-toggle="modal" onclick="Plan.new('datepicker-weekly', ViewType.WEEKLY);">
                <span class="glyphicon btn-glyphicon glyphicon-plus img-circle text-success"></span>
                Add Plan
            </a>
            <a class="btn icon-btn btn-success" href="#">
                <span class="glyphicon btn-glyphicon glyphicon-plus img-circle text-success"></span>
                Add Moment
            </a>
        </div>

        <jsp:include page="./viewOptions.jsp"/>
    </div>
</div>