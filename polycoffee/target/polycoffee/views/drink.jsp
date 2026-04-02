<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h2>QUẢN LÝ ĐỒ UỐNG</h2>

<!-- FORM TÌM KIẾM -->
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

<!-- BẢNG DỮ LIỆU -->
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
            </tr>

            <c:forEach var="d" items="${drinks}">
                <tr>
                    <td>${d.id}</td>
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
                </tr>
            </c:forEach>
        </table>
    </c:when>

    <c:otherwise>
        <p>Không có dữ liệu đồ uống</p>
    </c:otherwise>

</c:choose>

<br>

<!-- PHÂN TRANG -->
<div>
    <c:if test="${currentPage > 0}">
        <a href="drink?page=${currentPage - 1}&name=${name}&categoryId=${categoryId}&active=${active}">
            ⬅ Prev
        </a>
    </c:if>

    <c:forEach begin="0" end="${totalPages - 1}" var="i">
        <a href="drink?page=${i}&name=${name}&categoryId=${categoryId}&active=${active}">
            [${i + 1}]
        </a>
    </c:forEach>

    <c:if test="${currentPage < totalPages - 1}">
        <a href="drink?page=${currentPage + 1}&name=${name}&categoryId=${categoryId}&active=${active}">
            Next ➡
        </a>
    </c:if>
</div>