<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<h2>CHI TIẾT HOÁ ĐƠN</h2>

<a href="bill">⬅ Quay lại danh sách</a>
<br><br>

<!-- ===== THÔNG TIN ĐƠN HÀNG ===== -->
<table border="1" cellpadding="10">
    <tr>
        <td><strong>Mã đơn hàng</strong></td>
        <td>${bill.code}</td>
    </tr>
    <tr>
        <td><strong>Nhân viên tạo đơn</strong></td>
        <td>${bill.user.fullname}</td>
    </tr>
    <tr>
        <td><strong>Ngày tạo đơn</strong></td>
        <td><fmt:formatDate value="${bill.created_at}" pattern="dd/MM/yyyy HH:mm:ss"/></td>
    </tr>
    <tr>
        <td><strong>Tổng tiền</strong></td>
        <td><fmt:formatNumber value="${bill.total}" type="number" groupingUsed="true"/> đ</td>
    </tr>
    <tr>
        <td><strong>Trạng thái</strong></td>
        <td>
            <c:choose>
                <c:when test="${bill.status == 0}">⏳ Chờ xử lý</c:when>
                <c:when test="${bill.status == 1}">✅ Hoàn thành</c:when>
                <c:when test="${bill.status == 2}">❌ Đã huỷ</c:when>
                <c:otherwise>${bill.status}</c:otherwise>
            </c:choose>
        </td>
    </tr>
</table>

<br>

<!-- ===== DANH SÁCH SẢN PHẨM ===== -->
<h3>Danh sách sản phẩm</h3>

<c:choose>
    <c:when test="${not empty details}">
        <table border="1" width="80%" cellpadding="10">
            <tr>
                <th>STT</th>
                <th>Tên thức uống</th>
                <th>Đơn giá</th>
                <th>Số lượng</th>
                <th>Thành tiền</th>
            </tr>

            <c:forEach var="d" items="${details}" varStatus="st">
                <tr>
                    <td>${st.index + 1}</td>
                    <td>${d.drink.name}</td>
                    <td><fmt:formatNumber value="${d.price}" type="number" groupingUsed="true"/> đ</td>
                    <td>${d.quantity}</td>
                    <td>
                        <fmt:formatNumber value="${d.price * d.quantity}"
                                          type="number" groupingUsed="true"/> đ
                    </td>
                </tr>
            </c:forEach>

            <tr>
                <td colspan="4" align="right"><strong>Tổng tiền:</strong></td>
                <td>
                    <strong>
                        <fmt:formatNumber value="${bill.total}" type="number" groupingUsed="true"/> đ
                    </strong>
                </td>
            </tr>
        </table>
    </c:when>

    <c:otherwise>
        <p>Không có sản phẩm trong đơn hàng này.</p>
    </c:otherwise>
</c:choose>

<br>

<!-- ===== NÚT HUỶ ===== -->
<c:if test="${bill.status != 2}">
    <form method="post" action="bill">
        <input type="hidden" name="action" value="cancel">
        <input type="hidden" name="id" value="${bill.id}">
        <button type="submit"
                onclick="return confirm('Bạn chắc chắn muốn huỷ đơn hàng này?')">
            ❌ Huỷ đơn hàng
        </button>
    </form>
</c:if>
