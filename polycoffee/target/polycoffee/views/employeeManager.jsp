<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h2>QUẢN LÝ NHÂN VIÊN</h2>

<!-- ===== SEARCH ===== -->
<form method="get" action="employee">
    Tên:
    <input type="text" name="fullname" value="${fullname}">

    Email:
    <input type="text" name="email" value="${email}">

    Trạng thái:
    <select name="active">
        <option value="">-- Tất cả --</option>
        <option value="true" ${active == true ? 'selected' : ''}>Hoạt động</option>
        <option value="false" ${active == false ? 'selected' : ''}>Ngưng</option>
    </select>

    <button type="submit">Tìm</button>
</form>

<hr>

<!-- ===== ADMIN: THÊM ===== -->
<c:if test="${sessionScope.user.role == 0}">
    <h3>Thêm nhân viên</h3>
    <form method="post" action="employee">
        <input type="hidden" name="action" value="create">

        Tên: <input type="text" name="fullname" required>
        Email: <input type="email" name="email" required>
        Mật khẩu: <input type="text" name="password" required>

        Trạng thái:
        <select name="active">
            <option value="true">Hoạt động</option>
            <option value="false">Ngưng</option>
        </select>

        <button type="submit">Thêm</button>
    </form>
    <hr>
</c:if>

<!-- ===== TABLE ===== -->
<c:choose>

    <c:when test="${not empty employees}">
        <table border="1" width="100%" cellpadding="10">
            <tr>
                <th>ID</th>
                <th>Tên</th>
                <th>Email</th>
                <th>Trạng thái</th>

                <c:if test="${sessionScope.user.role == 0}">
                    <th>Hành động</th>
                </c:if>
            </tr>

            <c:forEach var="u" items="${employees}">
                <tr>
                    <td>${u.id}</td>

                    <!-- ===== ADMIN ===== -->
                    <c:if test="${sessionScope.user.role == 0}">
                        <form method="post" action="employee">
                            <input type="hidden" name="action" value="update">
                            <input type="hidden" name="id" value="${u.id}">

                            <td><input type="text" name="fullname" value="${u.fullname}"></td>
                            <td><input type="email" name="email" value="${u.email}"></td>

                            <td>
                                <select name="active">
                                    <option value="true" ${u.active ? 'selected' : ''}>Hoạt động</option>
                                    <option value="false" ${!u.active ? 'selected' : ''}>Ngưng</option>
                                </select>
                            </td>

                            <td>
                                <button type="submit">Sửa</button>
                        </form>

                        <!-- DELETE -->
                        <form method="post" action="employee" style="display:inline;">
                            <input type="hidden" name="action" value="delete">
                            <input type="hidden" name="id" value="${u.id}">
                            <button onclick="return confirm('Xóa nhân viên?')">Xóa</button>
                        </form>
                            </td>
                    </c:if>

                    <!-- ===== USER THƯỜNG ===== -->
                    <c:if test="${sessionScope.user.role != 0}">
                        <td>${u.fullname}</td>
                        <td>${u.email}</td>
                        <td>
                            <c:choose>
                                <c:when test="${u.active}">Hoạt động</c:when>
                                <c:otherwise>Ngưng</c:otherwise>
                            </c:choose>
                        </td>
                    </c:if>

                </tr>
            </c:forEach>
        </table>
    </c:when>

    <c:otherwise>
        <p>Không có dữ liệu nhân viên</p>
    </c:otherwise>

</c:choose>

<br>

<!-- ===== PAGINATION ===== -->
<div>

    <c:if test="${currentPage > 1}">
        <a href="employee?page=${currentPage - 1}&fullname=${fullname}&email=${email}&active=${active}">
            ⬅ Prev
        </a>
    </c:if>

    <c:forEach begin="1" end="${totalPages}" var="i">
        <a href="employee?page=${i}&fullname=${fullname}&email=${email}&active=${active}">
            [${i}]
        </a>
    </c:forEach>

    <c:if test="${currentPage < totalPages}">
        <a href="employee?page=${currentPage + 1}&fullname=${fullname}&email=${email}&active=${active}">
            Next ➡
        </a>
    </c:if>

</div>