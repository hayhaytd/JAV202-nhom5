<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <h1>Trang Home Manager</h1>
    <p>user ${sessionScope.user.email} <br>
        role ${sessionScope.user.getRole()} <br>
        fullname ${sessionScope.user.fullname}
    </p>
    <c:choose>
        <c:when test="${sessionScope.user.role == '0'}">
            admin
        </c:when>
        <c:otherwise>
            employee
        </c:otherwise>
    </c:choose>