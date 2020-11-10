<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: lance
  Date: 12/13/19
  Time: 12:07 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Ideas</title>
    <link rel="stylesheet" type="text/css" href="style.css"/>
</head>
<body>

<div class="container">
    <h1>${idea.content}</h1>
    <h3>Created By: <c:out value="${idea.creator.name}"/></h3>
    <br>
    <h2>Users who liked this idea:</h2>
    <table>
        <c:forEach var="like" items="${idea.userLikes}">
            <tr>
                <td>${like.name}</td>
            </tr>
        </c:forEach>
    </table>
    <c:if test="${idea.creator == user}">
        <button><a href="/ideas/${idea.id}/edit">Edit</a></button> |
        <form action="/ideas/${idea.id}/delete" method="post">
            <input type="hidden" name="_method" value="delete">
            <input type="submit" value="Delete">
        </form>

    </c:if>
    <br>
    <a href="/ideas">Home</a>



</div>
</body>
</html>
