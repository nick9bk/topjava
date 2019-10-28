<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%--
  Created by IntelliJ IDEA.
  User: nick
  Date: 28.10.2019
  Time: 13:02
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <title>${not empty meal ? 'Update' : 'Add'}" meal</title>
</head>
<body>
<c:set var="add" scope="request" value="${not empty meal ? true : false}"/>
${add}
<form action="meals" method="post">
    <input type="hidden" name = "id" value="${meal.id}">
    Date <input type = "datetime" name = "date" value="${meal.date}">
    <br />
    Calories <input type = "number" name = "calories" value="${meal.calories}">
    <br />
    Description <input type = "text" name = "description" value="${meal.description}">
    <br />
    <input type = "submit" value = "${not empty meal ? 'Обновить' : 'Создать'}" />
</form>
</body>
</html>
