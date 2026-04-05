<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h2>QUẢN LÝ NHÂN VIÊN</h2>

<!-- FORM -->
<form method="get" action="user">
    Name: <input type="text" name="fullname" value="${fullname}"/>

    Email: <input type="text" name="email" value="${email}"/>

    Status:
    <select name="active">
        <option value="">All</option>
        <option value="true" <c:if test="${active == 'true'}">selected</c:if>>Active</option>
        <option value="false" <c:if test="${active == 'false'}">selected</c:if>>Inactive</option>
    </select>

    <button type="submit">Search</button>
</form>

<hr>

<!-- TABLE -->
<c:choose>
    <c:when test="${not empty list}">
        <table border="1" width="100%">
            <tr>
                <th>ID</th>
                <th>Fullname</th>
                <th>Email</th>
                <th>Status</th>
            </tr>

            <c:forEach var="u" items="${list}">
                <tr>
                    <td>${u.id}</td>
                    <td>${u.fullname}</td>
                    <td>${u.email}</td>
                    <td>
                        <c:choose>
                            <c:when test="${u.active}">Active</c:when>
                            <c:otherwise>Inactive</c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:when>

    <c:otherwise>
        <p>Không có dữ liệu</p>
    </c:otherwise>
</c:choose>

<br>

<!-- PAGINATION -->
<div>
    <c:if test="${currentPage > 0}">
        <a href="user?page=${currentPage - 1}&fullname=${fullname}&email=${email}&active=${active}">
            ⬅ Prev
        </a>
    </c:if>

    <c:forEach begin="0" end="${totalPages - 1}" var="i">
        <a href="user?page=${i}&fullname=${fullname}&email=${email}&active=${active}">
            <c:choose>
                <c:when test="${i == currentPage}">
                    <b>[${i + 1}]</b>
                </c:when>
                <c:otherwise>
                    ${i + 1}
                </c:otherwise>
            </c:choose>
        </a>
    </c:forEach>

    <c:if test="${currentPage < totalPages - 1}">
        <a href="user?page=${currentPage + 1}&fullname=${fullname}&email=${email}&active=${active}">
            Next ➡
        </a>
    </c:if>
</div>