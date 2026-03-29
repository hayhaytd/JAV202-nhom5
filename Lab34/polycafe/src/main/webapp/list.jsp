<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h2>Danh sách Category</h2>

<a href="category?action=edit">➕ Thêm mới</a>

<table border="1">
<tr>
    <th>ID</th>
    <th>Tên</th>
    <th>Active</th>
    <th>Action</th>
</tr>

<c:forEach var="c" items="${list}">
<tr>
    <td>${c.id}</td>
    <td>${c.name}</td>
    <td>${c.active}</td>
    <td>
        <a href="category?action=edit&id=${c.id}">Sửa</a>
        <a href="category?action=delete&id=${c.id}">Xóa</a>
    </td>
</tr>
</c:forEach>

</table>