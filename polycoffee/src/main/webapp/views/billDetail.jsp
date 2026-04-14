<%@ page contentType="text/html;charset=UTF-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

            <style>
                .title {
                    font-size: 24px;
                    font-weight: bold;
                    margin-bottom: 15px;
                    color: #2c3e50;
                }

                .back-btn {
                    display: inline-block;
                    margin-bottom: 20px;
                    text-decoration: none;
                    color: white;
                    background: #3498db;
                    padding: 8px 14px;
                    border-radius: 6px;
                    transition: 0.3s;
                }

                .back-btn:hover {
                    background: #2980b9;
                }

                .card {
                    background: white;
                    padding: 20px;
                    border-radius: 12px;
                    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
                    margin-bottom: 20px;
                }

                table {
                    width: 100%;
                    border-collapse: collapse;
                    margin-top: 10px;
                }

                th {
                    background: #2c3e50;
                    color: white;
                    padding: 10px;
                }

                td {
                    padding: 10px;
                    border-bottom: 1px solid #ddd;
                }

                tr:hover {
                    background: #f9f9f9;
                }

                .status {
                    font-weight: bold;
                }

                .status.wait {
                    color: orange;
                }

                .status.done {
                    color: green;
                }

                .status.cancel {
                    color: red;
                }

                .total {
                    text-align: right;
                    font-weight: bold;
                    font-size: 16px;
                }

                .btn-danger {
                    background: #e74c3c;
                    color: white;
                    padding: 10px 16px;
                    border: none;
                    border-radius: 6px;
                    cursor: pointer;
                    transition: 0.3s;
                }

                .btn-danger:hover {
                    background: #c0392b;
                }
            </style>

            <div class="title">🧾 Chi tiết hoá đơn</div>

            <a href="bill" class="back-btn">⬅ Quay lại</a>

            <!-- ===== THÔNG TIN ===== -->
            <div class="card">
                <table>
                    <tr>
                        <td><strong>Mã đơn hàng</strong></td>
                        <td>${bill.code}</td>
                    </tr>
                    <tr>
                        <td><strong>Nhân viên</strong></td>
                        <td>${bill.user.fullname}</td>
                    </tr>
                    <tr>
                        <td><strong>Ngày tạo</strong></td>
                        <td>
                            <fmt:formatDate value="${bill.created_at}" pattern="dd/MM/yyyy HH:mm:ss" />
                        </td>
                    </tr>
                    <tr>
                        <td><strong>Tổng tiền</strong></td>
                        <td>
                            <fmt:formatNumber value="${bill.total}" type="number" groupingUsed="true" /> đ
                        </td>
                    </tr>
                    <tr>
                        <td><strong>Trạng thái</strong></td>
                        <td>
                            <c:choose>
                                <c:when test="${bill.status == 0}">
                                    <span class="status wait">⏳ Chờ xử lý</span>
                                </c:when>
                                <c:when test="${bill.status == 1}">
                                    <span class="status done">✅ Hoàn thành</span>
                                </c:when>
                                <c:when test="${bill.status == 2}">
                                    <span class="status cancel">❌ Đã huỷ</span>
                                </c:when>
                                <c:otherwise>${bill.status}</c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </table>
            </div>

            <!-- ===== SẢN PHẨM ===== -->
            <div class="card">
                <h3>📦 Danh sách sản phẩm</h3>

                <c:choose>
                    <c:when test="${not empty details}">
                        <table>
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
                                    <td>
                                        <fmt:formatNumber value="${d.price}" type="number" groupingUsed="true" /> đ
                                    </td>
                                    <td>${d.quantity}</td>
                                    <td>
                                        <fmt:formatNumber value="${d.price * d.quantity}" type="number"
                                            groupingUsed="true" /> đ
                                    </td>
                                </tr>
                            </c:forEach>

                            <tr>
                                <td colspan="4" class="total">Tổng tiền:</td>
                                <td class="total">
                                    <fmt:formatNumber value="${bill.total}" type="number" groupingUsed="true" /> đ
                                </td>
                            </tr>
                        </table>
                    </c:when>

                    <c:otherwise>
                        <p>Không có sản phẩm trong đơn hàng này.</p>
                    </c:otherwise>
                </c:choose>
            </div>

            <!-- ===== HUỶ ===== -->
            <c:if test="${bill.status != 2}">
                <form method="post" action="bill">
                    <input type="hidden" name="action" value="cancel">
                    <input type="hidden" name="id" value="${bill.id}">
                    <button class="btn-danger" onclick="return confirm('Bạn chắc chắn muốn huỷ đơn hàng này?')">
                        ❌ Huỷ đơn hàng
                    </button>
                </form>
            </c:if>