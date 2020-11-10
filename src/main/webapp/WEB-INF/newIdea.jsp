<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: lance
  Date: 12/13/19
  Time: 11:43 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Ideas</title>
</head>
<body>
<h2>Create an Idea</h2>
<form:errors path="idea.*"/>
<form:form action="/ideas/new" method="post" modelAttribute="idea">
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
