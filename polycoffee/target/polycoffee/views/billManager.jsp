<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<h2>QUẢN LÝ HOÁ ĐƠN</h2>

<!-- ===== TABLE ===== -->
<c:choose>
    <c:when test="${not empty bills}">
        <table border="1" width="100%" cellpadding="10">
            <tr>
                <th>STT</th>
                <th>Mã đơn</th>
                <th>Nhân viên tạo</th>
                <th>Ngày tạo</th>
                <th>Tổng tiền</th>
                <th>Trạng thái</th>
                <th>Hành động</th>
            </tr>

            <c:forEach var="b" items="${bills}" varStatus="st">
                <tr>
                    <td>${(currentPage - 1) * 10 + st.index + 1}</td>
                    <td>${b.code}</td>
                    <td>${b.user.fullname}</td>
                    <td>
                        <fmt:formatDate value="${b.created_at}" pattern="dd/MM/yyyy HH:mm"/>
                    </td>
                    <td>
                        <fmt:formatNumber value="${b.total}" type="number" groupingUsed="true"/> đ
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${b.status == 0}">⏳ Chờ xử lý</c:when>
                            <c:when test="${b.status == 1}">✅ Hoàn thành</c:when>
                            <c:when test="${b.status == 2}">❌ Đã huỷ</c:when>
                            <c:otherwise>${b.status}</c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <a href="bill?action=detail&id=${b.id}">🔍 Chi tiết</a>

                        <c:if test="${b.status != 2}">
                            &nbsp;
                            <form method="post" action="bill" style="display:inline;">
                                <input type="hidden" name="action" value="cancel">
                                <input type="hidden" name="id" value="${b.id}">
                                <button type="submit"
                                        onclick="return confirm('Huỷ đơn hàng #${b.id}?')">
                                    ❌ Huỷ
                                </button>
                            </form>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:when>

    <c:otherwise>
        <p>Không có đơn hàng nào.</p>
    </c:otherwise>
</c:choose>

<br>

<!-- ===== PAGINATION ===== -->
<div>
    <c:if test="${currentPage > 1}">
        <a href="bill?page=${currentPage - 1}">⬅ Prev</a>
    </c:if>

    <c:forEach begin="1" end="${totalPages}" var="i">
        <c:choose>
            <c:when test="${i == currentPage}">
                <strong>[${i}]</strong>
            </c:when>
            <c:otherwise>
                <a href="bill?page=${i}">[${i}]</a>
            </c:otherwise>
        </c:choose>
    </c:forEach>

    <c:if test="${currentPage < totalPages}">
        <a href="bill?page=${currentPage + 1}">Next ➡</a>
    </c:if>
</div>
