<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: lance
  Date: 12/13/19
  Time: 12:49 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page isErrorPage="true"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Ideas</title>
    <link rel="stylesheet" type="text/css" href="style.css"/>
</head>
<body>
<h2>Edit your Event</h2>
<form:errors path="idea.*"/>
<form:form action="/ideas/${idea.id}/edit" method="POST" modelAttribute="idea">
    <input type="hidden" name="_method" value="put">
    <p>
        <form:label path="content">Content: </form:label>
        <form:input path="content"/>
    </p>

    <p>
        <form:hidden path="creator" value="${user.id}"/>
    </p>
    <input type="submit" value="Submit"/>
</form:form>
</body>
</html>
