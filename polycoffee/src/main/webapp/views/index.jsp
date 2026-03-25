<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>PolyCoffee</title>
    <style>
        body {
            margin: 0;
            font-family: Arial;
        }

        .navbar {
            background: #333;
            padding: 12px;
        }

        .navbar a {
            color: white;
            margin-right: 20px;
            text-decoration: none;
            font-weight: bold;
        }

        .navbar a:hover {
            color: yellow;
        }

        .active {
            color: yellow;
        }

        .content {
            padding: 20px;
        }
    </style>
</head>
<body>

<!-- NAVBAR -->
<div class="navbar">

    <a href="menu?action=home"
       class="${param.action=='home' || empty param.action ? 'active' : ''}">
        Home
    </a>

    <a href="menu?action=drink"
       class="${param.action=='drink' ? 'active' : ''}">
        Drink
    </a>

    <a href="menu?action=login"
       class="${param.action=='login' ? 'active' : ''}">
        Login
    </a>

</div>

<!-- CONTENT -->
<div class="content">

    <c:choose>
        <c:when test="${not empty view}">
            <jsp:include page="${view}" />
        </c:when>
        <c:otherwise>
            <jsp:include page="/views/homeGuest.jsp" />
        </c:otherwise>
    </c:choose>

</div>

</body>
</html>