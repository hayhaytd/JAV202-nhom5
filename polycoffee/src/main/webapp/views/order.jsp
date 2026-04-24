<%@ page contentType="text/html;charset=UTF-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

            <style>
                .container {
                    display: flex;
                    gap: 20px;
                }

                .box {
                    width: 50%;
                    background: white;
                    padding: 15px;
                    border-radius: 10px;
                    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
                }

                table {
                    width: 100%;
                    border-collapse: collapse;
                }

                th,
                td {
                    padding: 8px;
                    border-bottom: 1px solid #ddd;
                    text-align: center;
                }

                th {
                    background: #2c3e50;
                    color: white;
                }

                button {
                    padding: 4px 8px;
                    cursor: pointer;
                }

                .drink-grid {
                    display: grid;
                    grid-template-columns: repeat(3, 1fr);
                    gap: 10px;
                }

                .drink-card {
                    border: 1px solid #ddd;
                    border-radius: 8px;
                    padding: 10px;
                    text-align: center;
                }

                .drink-card img {
                    width: 80px;
                    height: 80px;
                    object-fit: cover;
                }
            </style>

            <h2>🛒 BÁN HÀNG</h2>

            <c:if test="${bill != null}">

                <p><strong>Mã HĐ:</strong> ${bill.code}</p>

                <div class="container">

                    <!-- ================= GIỎ HÀNG ================= -->
                    <div class="box">
                        <h3>🧾 Giỏ hàng</h3>

                        <table>
                            <tr>
                                <th>Tên</th>
                                <th>SL</th>
                                <th>Giá</th>
                                <th>TT</th>
                                <th></th>
                            </tr>

                            <c:forEach var="d" items="${details}">
                                <tr>
                                    <td>${d.drink.name}</td>

                                    <!-- QUANTITY -->
                                    <td>
                                        <form method="post" action="sale" style="display:inline;">
                                            <input type="hidden" name="action" value="update">
                                            <input type="hidden" name="id" value="${d.id}">
                                            <input type="hidden" name="billId" value="${bill.id}">
                                            <input type="hidden" name="quantity" value="${d.quantity - 1}">
                                            <button>-</button>
                                        </form>

                                        ${d.quantity}

                                        <form method="post" action="sale" style="display:inline;">
                                            <input type="hidden" name="action" value="update">
                                            <input type="hidden" name="id" value="${d.id}">
                                            <input type="hidden" name="billId" value="${bill.id}">
                                            <input type="hidden" name="quantity" value="${d.quantity + 1}">
                                            <button>+</button>
                                        </form>
                                    </td>

                                    <td>
                                        <fmt:formatNumber value="${d.price}" />
                                    </td>

                                    <td>
                                        <fmt:formatNumber value="${d.price * d.quantity}" />
                                    </td>

                                    <!-- DELETE -->
                                    <td>
                                        <form method="post" action="sale">
                                            <input type="hidden" name="action" value="remove">
                                            <input type="hidden" name="id" value="${d.id}">
                                            <input type="hidden" name="billId" value="${bill.id}">
                                            <button>❌</button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>

                        <h3>💰 Tổng:
                            <fmt:formatNumber value="${bill.total}" /> đ
                        </h3>

                        <!-- PAY -->
                        <form method="post" action="sale">
                            <input type="hidden" name="action" value="pay">
                            <input type="hidden" name="billId" value="${bill.id}">
                            <button style="background:green;color:white;">💳 Thanh toán</button>
                        </form>

                    </div>

                    <!-- ================= MENU ================= -->
                    <div class="box">
                        <h3>☕ Menu</h3>

                        <div class="drink-grid">
                            <c:forEach var="d" items="${drinks}">
                                <div class="drink-card">

                                    <img src="${pageContext.request.contextPath}/img/imgdrink/${d.image}" alt="Drink Image">

                                    <p><b>${d.name}</b></p>
                                    <p>
                                        <fmt:formatNumber value="${d.price}" /> đ
                                    </p>

                                    <form method="post" action="sale">
                                        <input type="hidden" name="action" value="add">
                                        <input type="hidden" name="billId" value="${bill.id}">
                                        <input type="hidden" name="drinkId" value="${d.id}">
                                        <button>➕ Thêm</button>
                                    </form>

                                </div>
                            </c:forEach>
                        </div>
                    </div>

                </div>

            </c:if>

            <c:if test="${bill == null}">
                <p>⚠️ Vui lòng chọn hóa đơn!</p>
            </c:if>