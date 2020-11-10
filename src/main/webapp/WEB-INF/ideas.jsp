<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: lance
  Date: 12/13/19
  Time: 11:07 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Ideas</title>
    <link rel="stylesheet" type="text/css" href="style.css"/>
</head>
<body>
<h1>Hi <c:out value="${user.name}"/>!</h1>

<h3>Ideas</h3>
<table>
    <tr>
        <th class="columnWide">Name</th>
        <th>Created by</th>
        <th>Likes</th>
        <th>Action</th>
    </tr>
    <c:forEach var="idea" items="${ideas}">
            <tr>
                <td>
                    <a href="/ideas/${idea.id}"/> ${idea.content}
                </td>
                <td>${idea.creator.name}</td>
                <td>${idea.userLikes.size()}</td>
                <c:set var="liked" value="${false}"/>
                <c:forEach items="${idea.userLikes}" var="like">
                    <c:if test="${like == user}">
                        <c:set var="liked" value="${true}"/>
                    </c:if>
                </c:forEach>
                <c:choose>
                    <c:when test="${liked == false}">
                        <td><a href="/ideas/${idea.id}/join">Like</a></td>
                    </c:when>
                    <c:otherwise>
                        <td><a href="/ideas/${idea.id}/cancel">Unlike</a></td>
                    </c:otherwise>
                </c:choose>
            </tr>
    </c:forEach>
</table>

<br><br><br>
<button><a href="/ideas/new">Create an Idea</a></button>
<br><br><br>
<a href="/logout" style="text-align: right">Logout</a>
</body>
</html>
