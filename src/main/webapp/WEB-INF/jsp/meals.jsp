<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<script type="text/javascript" src="resources/js/topjava.common.js" defer></script>
<script type="text/javascript" src="resources/js/topjava.meals.js" defer></script>
<jsp:include page="fragments/bodyHeader.jsp"/>

<section>
    <h3><spring:message code="meal.title"/></h3>

    <form method="get" class="border border-light p-3" action="meals/filter">
        <dl>
            <dt><spring:message code="meal.startDate"/>:</dt>
            <dd><input type="date" name="startDate" id="startDate" value="${param.startDate}"></dd>
        </dl>
        <dl>
            <dt><spring:message code="meal.endDate"/>:</dt>
            <dd><input type="date" name="endDate" id="endDate" value="${param.endDate}"></dd>
        </dl>
        <dl>
            <dt><spring:message code="meal.startTime"/>:</dt>
            <dd><input type="time" name="startTime" id="startTime" value="${param.startTime}"></dd>
        </dl>
        <dl>
            <dt><spring:message code="meal.endTime"/>:</dt>
            <dd><input type="time" name="endTime" id="endTime" value="${param.endTime}"></dd>
        </dl>
        <button type="button" id="filter-but" class="btn badge-info"><spring:message code="meal.filter"/></button>
        <button type="button" id="filterReset-but" class="btn badge-warning"><spring:message code="meal.filterReset"/></button>
    </form>
    <hr>
    <a href="meals/create"><spring:message code="meal.add"/></a>
    <hr>
    <table class="table table-striped" id="datatable">
        <thead>
        <tr>
            <th><spring:message code="meal.dateTime"/></th>
            <th><spring:message code="meal.description"/></th>
            <th><spring:message code="meal.calories"/></th>
            <th></th>
            <th></th>
            <th></th>
        </tr>
        </thead>
<%--        <c:forEach items="${meals}" var="meal">--%>
<%--            <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.to.MealTo"/>--%>
<%--            <tr data-mealExcess="${meal.excess}">--%>
<%--                <td>--%>
<%--                        &lt;%&ndash;${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}&ndash;%&gt;--%>
<%--                        &lt;%&ndash;<%=TimeUtil.toString(meal.getDateTime())%>&ndash;%&gt;--%>
<%--                        &lt;%&ndash;${fn:replace(meal.dateTime, 'T', ' ')}&ndash;%&gt;--%>
<%--                        ${fn:formatDateTime(meal.dateTime)}--%>
<%--                </td>--%>
<%--                <td>${meal.description}</td>--%>
<%--                <td>${meal.calories}</td>--%>
<%--                <td><a href="meals/update?id=${meal.id}"><spring:message code="common.update"/></a></td>--%>
<%--                <td><a href="meals/delete?id=${meal.id}"><spring:message code="common.delete"/></a></td>--%>
<%--            </tr>--%>
<%--        </c:forEach>--%>
    </table>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>