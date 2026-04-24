<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<style>
    .title {
        font-size: 24px;
        font-weight: bold;
        margin-bottom: 20px;
        color: #2c3e50;
    }

    .card {
        background: white;
        padding: 20px;
        border-radius: 12px;
        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
    }

    table {
        width: 100%;
        border-collapse: collapse;
    }

    th {
        background: #2c3e50;
        color: white;
        padding: 10px;
        text-align: center;
    }

    td {
        padding: 10px;
        border-bottom: 1px solid #ddd;
        text-align: center;
    }

    tr:hover {
        background: #f9f9f9;
    }

    /* highlight bill chưa thanh toán */
    .waiting-row {
        background: #fff8e1;
    }

    .status {
        font-weight: bold;
        padding: 4px 8px;
        border-radius: 6px;
    }

    .wait {
        color: orange;
        background: #fff3cd;
    }

    .done {
        color: green;
        background: #d4edda;
    }

    .cancel {
        color: red;
        background: #f8d7da;
    }

    .btn {
        padding: 6px 10px;
        border-radius: 6px;
        text-decoration: none;
        color: white;
        font-size: 13px;
        transition: 0.3s;
        border: none;
        cursor: pointer;
    }

    .btn-detail { background: #3498db; }
    .btn-sale { background: #27ae60; }
    .btn-cancel { background: #e74c3c; }

    .btn:hover { opacity: 0.8; }

    .pagination {
        margin-top: 20px;
        text-align: center;
    }

    .pagination a,
    .pagination strong {
        margin: 0 5px;
        padding: 6px 10px;
        border-radius: 6px;
        text-decoration: none;
    }

    .pagination a {
        background: #ecf0f1;
        color: #2c3e50;
    }

    .pagination strong {
        background: #3498db;
        color: white;
    }
</style>

<div class="title">📋 Quản lý hoá đơn</div>

<div class="card">

    <c:choose>
        <c:when test="${not empty bills}">
            <table>
                <tr>
                    <th>STT</th>
                    <th>Mã đơn</th>
                    <th>Nhân viên</th>
                    <th>Ngày tạo</th>
                    <th>Tổng tiền</th>
                    <th>Trạng thái</th>
                    <th>Hành động</th>
                </tr>

                <c:forEach var="b" items="${bills}" varStatus="st">
                    <tr class="${b.status == 0 ? 'waiting-row' : ''}">
                        <td>${(currentPage - 1) * 10 + st.index + 1}</td>
                        <td>${b.code}</td>
                        <td>${b.user.fullname}</td>

                        
                        <td>
                            <fmt:formatDate value="${b.created_at}" pattern="dd/MM/yyyy HH:mm" />
                        </td>

                        <td>
                            <fmt:formatNumber value="${b.total}" type="number" groupingUsed="true" /> đ
                        </td>

                        <td>
                            <c:choose>
                                <c:when test="${b.status == 0}">
                                    <span class="status wait">⏳ Chờ</span>
                                </c:when>
                                <c:when test="${b.status == 1}">
                                    <span class="status done">✅ Xong</span>
                                </c:when>
                                <c:when test="${b.status == 2}">
                                    <span class="status cancel">❌ Huỷ</span>
                                </c:when>
                            </c:choose>
                        </td>

                        <td>
                            <!-- Xem chi tiết -->
                            <a href="bill?action=detail&id=${b.id}" class="btn btn-detail">🔍</a>

                            <!-- 👉 Bán hàng nhanh -->
                            <c:if test="${b.status == 0}">
                                <a href="sale?billId=${b.id}" class="btn btn-sale">🛒</a>
                            </c:if>

                            <!-- Huỷ -->
                            <c:if test="${b.status == 0}">
                                <form method="post" action="bill" style="display:inline;">
                                    <input type="hidden" name="action" value="cancel">
                                    <input type="hidden" name="id" value="${b.id}">
                                    <button class="btn btn-cancel"
                                        onclick="return confirm('Huỷ đơn #${b.code}?')">
                                        ❌
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

</div>

<!-- ===== PAGINATION ===== -->
<div class="pagination">

    <c:if test="${currentPage > 1}">
        <a href="bill?page=${currentPage - 1}">⬅</a>
    </c:if>

    <c:forEach begin="1" end="${totalPages}" var="i">
        <c:choose>
            <c:when test="${i == currentPage}">
                <strong>${i}</strong>
            </c:when>
            <c:otherwise>
                <a href="bill?page=${i}">${i}</a>
            </c:otherwise>
        </c:choose>
    </c:forEach>

    <c:if test="${currentPage < totalPages}">
        <a href="bill?page=${currentPage + 1}">➡</a>
    </c:if>

</div>