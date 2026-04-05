<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h2>QUẢN LÝ ĐỒ UỐNG</h2>

<!-- ===== SEARCH ===== -->
<form method="get" action="drink">
    Tên:
    <input type="text" name="name" value="${name}">

    Loại:
    <select name="categoryId">
        <option value="">-- Tất cả --</option>
        <c:forEach var="c" items="${categories}">
            <option value="${c.id}" ${c.id == categoryId ? 'selected' : ''}>
                ${c.name}
            </option>
        </c:forEach>
    </select>

    Trạng thái:
    <select name="active">
        <option value="">-- Tất cả --</option>
        <option value="true" ${active == true ? 'selected' : ''}>Hoạt động</option>
        <option value="false" ${active == false ? 'selected' : ''}>Ngưng</option>
    </select>

    <button type="submit">Tìm</button>
</form>

<hr>

<!-- ===== ADMIN (role = 0) THÊM ===== -->
<c:if test="${sessionScope.user.role == 0}">
    <h3>Thêm đồ uống</h3>
    <form method="post" action="drink">
        <input type="hidden" name="action" value="create">

        Tên: <input type="text" name="name" required>
        Giá: <input type="number" step="0.01" name="price" required>

        Loại:
        <select name="categoryId">
            <c:forEach var="c" items="${categories}">
                <option value="${c.id}">${c.name}</option>
            </c:forEach>
        </select>

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

    <c:when test="${not empty drinks}">
        <table border="1" width="100%" cellpadding="10">
            <tr>
                <th>ID</th>
                <th>Tên</th>
                <th>Giá</th>
                <th>Hình ảnh</th>
                <th>Danh mục</th>
                <th>Trạng thái</th>
                <c:if test="${sessionScope.user.role == 0}">
                    <th>Hành động</th>
                </c:if>
            </tr>

            <c:forEach var="d" items="${drinks}">
                <tr>
                    <td>${d.id}</td>

                    <!-- ===== UPDATE (ADMIN) ===== -->
                    <c:if test="${sessionScope.user.role == 0}">
                        <form method="post" action="drink">
                            <input type="hidden" name="action" value="update">
                            <input type="hidden" name="id" value="${d.id}">

                            <td><input type="text" name="name" value="${d.name}"></td>
                            <td><input type="number" step="0.01" name="price" value="${d.price}"></td>

                            <td>
                                <img src="${pageContext.request.contextPath}/img/imgdrink/${d.image}" width="60">
                            </td>

                            <td>
                                <select name="categoryId">
                                    <c:forEach var="c" items="${categories}">
                                        <option value="${c.id}" ${c.id == d.category.id ? 'selected' : ''}>
                                            ${c.name}
                                        </option>
                                    </c:forEach>
                                </select>
                            </td>

                            <td>
                                <select name="active">
                                    <option value="true" ${d.active ? 'selected' : ''}>Hoạt động</option>
                                    <option value="false" ${!d.active ? 'selected' : ''}>Ngưng</option>
                                </select>
                            </td>

                            <td>
                                <button type="submit">Sửa</button>
                        </form>

                        <!-- DELETE -->
                        <form method="post" action="drink" style="display:inline;">
                            <input type="hidden" name="action" value="delete">
                            <input type="hidden" name="id" value="${d.id}">
                            <button onclick="return confirm('Xóa?')">Xóa</button>
                        </form>
                            </td>
                    </c:if>

                    <!-- ===== USER (KHÔNG PHẢI ADMIN) ===== -->
                    <c:if test="${sessionScope.user.role != 0}">
                        <td>${d.name}</td>
                        <td>${d.price}</td>
                        <td>
                            <img src="${pageContext.request.contextPath}/img/imgdrink/${d.image}" width="60">
                        </td>
                        <td>${d.category.name}</td>
                        <td>
                            <c:choose>
                                <c:when test="${d.active}">Hoạt động</c:when>
                                <c:otherwise>Ngưng</c:otherwise>
                            </c:choose>
                        </td>
                    </c:if>

                </tr>
            </c:forEach>
        </table>
    </c:when>

    <c:otherwise>
        <p>Không có dữ liệu đồ uống</p>
    </c:otherwise>

</c:choose>

<br>

<!-- ===== PAGINATION (FIX CHUẨN PAGE = 1) ===== -->
<div>

    <c:if test="${currentPage > 1}">
        <a href="drink?page=${currentPage - 1}&name=${name}&categoryId=${categoryId}&active=${active}">
            ⬅ Prev
        </a>
    </c:if>

    <c:forEach begin="1" end="${totalPages}" var="i">
        <a href="drink?page=${i}&name=${name}&categoryId=${categoryId}&active=${active}">
            [${i}]
        </a>
    </c:forEach>

    <c:if test="${currentPage < totalPages}">
        <a href="drink?page=${currentPage + 1}&name=${name}&categoryId=${categoryId}&active=${active}">
            Next ➡
        </a>
    </c:if>

</div>