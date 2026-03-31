<%@ page contentType="text/html;charset=UTF-8" %>

<h2>Form Category</h2>

<form method="post" action="category">

    <input type="hidden" name="id" value="${c.id}" />

    Tên:
    <input name="name" value="${c.name}" required />

    Active:
    <input type="checkbox" name="active"
        <c:if test="${c.active}">checked</c:if> />

    <br><br>
    <button>Lưu</button>

</form>