<%--@elvariable id="i18n" type="java.util.ResourceBundle"--%>
<%--@elvariable id="status" type="ua.lifebook.plans.PlanStatus"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="modal fade" id="planModal" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">x</span><span class="sr-only">Close</span></button>
                <h3 class="modal-title" id="lineModalLabel">${i18n.getString("newPlanTitle")}</h3>
            </div>
            <div class="modal-body">

                <!-- content goes here -->
                <form:form action="/plan/save.html" method="post">
                    <input type="hidden" value="${plan.id}" name="id">
                    <div class="form-group">
                        <label for="title">${i18n.getString("planTitleLabel")}</label>
                        <input type="text" class="form-control" id="title" name="title">
                    </div>
                    <div class="form-group">
                        <label for="planTime">${i18n.getString("planTimeLabel")}</label>
                        <div class='input-group date' id='planTime'>
                            <input type='text' class="form-control" name="dueDate"/>
                            <span class="input-group-addon">
                                <span class="glyphicon glyphicon-time"></span>
                            </span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="status">${i18n.getString("planStatusLabel")}</label>
                        <select id="status" name="status" class="form-control">
                            <c:forEach items="${planStatuses}" var="status">
                                <option value="${status.name()}" <c:if test="${status eq plan.status}">selected="selected"</c:if>>
                                    <c:set var="n" value="planStatus.${status.name()}"/>
                                    ${i18n.getString(n)}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="planDescription">${i18n.getString("planCommentsLabel")}</label>
                        <textarea name="comments" id="planDescription" rows="20" cols="78" style="resize: none;"></textarea>
                    </div>
                    <div class="checkbox">
                        <label>
                            <input type="checkbox"> Check me out
                        </label>
                    </div>
                    <button type="button" class="btn btn-default" id="planEditSubmit">Submit</button>
                </form:form>

            </div>
            <div class="modal-footer">
                <div class="btn-group btn-group-justified" role="group" aria-label="group button">
                    <div class="btn-group" role="group">
                        <button type="button" class="btn btn-default" data-dismiss="modal"  role="button">Close</button>
                    </div>
                    <div class="btn-group btn-delete hidden" role="group">
                        <button type="button" id="delImage" class="btn btn-default btn-hover-red" data-dismiss="modal" role="button">Delete</button>
                    </div>
                    <div class="btn-group" role="group">
                        <button type="button" id="saveImage" class="btn btn-default btn-hover-green" data-action="save" role="button">Save</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>