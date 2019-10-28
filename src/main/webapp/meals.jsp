<%--
  Created by IntelliJ IDEA.
  User: nick
  Date: 24.10.2019
  Time: 16:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<table border=1>
    <thead>
    <tr>
        <th>Date</th>
        <th>Calories</th>
        <th>Description</th>
        <th colspan=2>Action</th>
    </tr>
    </thead>
    <c:forEach items="${allMeals}" var="meal">
        <div style="color: ${meal.excess == 'true' ? 'green' : 'red'}; ">
            <tr>
                <td><fmt:parseDate value="${meal.dateTime}" pattern="yyyy-MM-dd" type="date"/></td>
                <td>${meal.calories}</td>
                <td>${meal.description}</td>
                <td><a href="meals?action=edit&mealId=<c:out value="${meal.id}"/>">Update</a></td>
                <td><a href="meals?action=delete&mealId=<c:out value="${meal.id}"/>">Delete</a></td>
            </tr>
        </div>
    </c:forEach>
</table>
<p><a href="meals?action=add">Add User</a></p>
</body>
</html>
