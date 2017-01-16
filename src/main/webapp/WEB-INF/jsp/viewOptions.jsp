<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="checkbox">
    <label>
        <input type="checkbox" name="outdated" value="SHOW_OUTDATED" <c:if test='${viewOptions.contains("SHOW_OUTDATED")}'>checked="checked"</c:if>>
        Show outdated
    </label>
</div>
<div class="checkbox">
    <label>
        <input type="checkbox" name="done" value="SHOW_DONE" <c:if test='${viewOptions.contains("SHOW_DONE")}'>checked="checked"</c:if>>
        Show done
    </label>
</div>
<div class="checkbox">
    <label>
        <input type="checkbox" name="canceled" value="SHOW_CANCELED" <c:if test='${viewOptions.contains("SHOW_CANCELED")}'>checked="checked"</c:if>>
        Show canceled
    </label>
</div>