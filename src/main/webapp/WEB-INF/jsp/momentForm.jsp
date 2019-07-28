<%--@elvariable id="i18n" type="java.util.ResourceBundle"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">x</span><span class="sr-only">Close</span></button>
                <h3 class="modal-title" id="lineModalLabel">
                    <c:choose>
                        <c:when test="${empty moment.id}">
                            ${i18n.getString("newMomentTitle")}
                        </c:when>
                        <c:otherwise>
                            ${i18n.getString("editMomentTitle")}
                        </c:otherwise>
                    </c:choose>
                </h3>
            </div>
            <div class="modal-body">
                <form:form action="/moment/save.html" method="post">
                    <input type="hidden" value="${moment.id}" name="id">
                    <input type="hidden" value="${moment.rawDate}" id="momentDateRaw">
                    <input type="hidden" value="" id="viewType">
                    <div class="form-group">
                        <label for="momentDate">${i18n.getString("momentDateLabel")}</label>
                        <div class='input-group date' id='momentDate'>
                            <input type='text' class="form-control" name="date" value="${moment.date}"/>
                            <span class="input-group-addon">
                                <span class="glyphicon glyphicon-time"></span>
                            </span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="momentDescription">${i18n.getString("momentDescriptionLabel")}</label>
                        <textarea name="description" id="momentDescription" rows="20" cols="78" style="resize: none;">${moment.description}</textarea>
                    </div>
                </form:form>
            </div>
            <div class="modal-footer">
                <div class="btn-group btn-group-justified" role="group" aria-label="group button">
                    <div class="btn-group" role="group">
                        <button type="button" class="btn btn-default" data-dismiss="modal" role="button">Close</button>
                    </div>
                    <div class="btn-group btn-delete hidden" role="group">
                        <button type="button" id="delImage" class="btn btn-default btn-hover-red" data-dismiss="modal" role="button">Delete</button>
                    </div>
                    <div class="btn-group" role="group">
                        <button type="button" class="btn btn-default btn-hover-green" data-action="save" role="button" onclick="Moment.save();">Save</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
