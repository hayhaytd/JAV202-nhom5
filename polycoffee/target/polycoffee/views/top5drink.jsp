<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

            <h2>Top sale</h2>

            <script>
                window.addEventListener("load", function () {
                    const fromInput = document.getElementById("fromDate");
                    const toInput = document.getElementById("toDate");

                    let today = new Date();
                    today.setHours(0, 0, 0, 0);

                    let past = new Date(today);
                    past.setDate(today.getDate() - 7);

                    function formatDate(date) {
                        return date.toISOString().split('T')[0];
                    }

                    if (!fromInput.value || !toInput.value) {
                        fromInput.value = formatDate(past);
                        toInput.value = formatDate(today);
                    }
                });
            </script>
            <style>
                .top5-container {
                    display: flex;
                    gap: 20px;
                    justify-content: center;
                    flex-wrap: wrap;
                }

                .card {
                    width: 180px;
                    border: 1px solid #ddd;
                    border-radius: 12px;
                    padding: 10px;
                    text-align: center;
                    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
                    transition: 0.3s;
                    background: #fff;
                }

                .card:hover {
                    transform: translateY(-5px);
                }

                .card img {
                    width: 100%;
                    height: 120px;
                    object-fit: cover;
                    border-radius: 10px;
                }

                .card h4 {
                    margin: 10px 0 5px;
                }

                .price {
                    color: green;
                    font-weight: bold;
                }

                .sold {
                    color: #555;
                    font-size: 14px;
                }
            </style>

            <form id="dateForm" method="get" action="home">
                From Date:
                <input type="date" name="fromDate" id="fromDate" required>

                To Date:
                <input type="date" name="toDate" id="toDate" required>

                <button type="submit">Lọc</button>
            </form>
            <br>

            <!-- Hiển thị lỗi -->
            <div class="top5-container">

                <!-- Lỗi -->
                <c:if test="${not empty error}">
                    <p style="color:red;">${error}</p>
                </c:if>

                <!-- Không có data -->
                <c:if test="${empty top5List}">
                    <p>Không có dữ liệu</p>
                </c:if>

                <!-- Có data -->
                <c:forEach var="row" items="${top5List}">
                    <div class="card">

                        <!-- Ảnh (fallback nếu lỗi) -->
                        <img src="${pageContext.request.contextPath}/img/imgdrink/${row[2]}" alt="Drink Image">

                        <!-- Tên -->
                        <h4>${row[0]}</h4>

                        <!-- Giá -->
                        <div class="price">
                            <fmt:formatNumber value="${row[3]}" type="number" /> VNĐ
                        </div>

                        <!-- Số lượng -->
                        <div class="sold">
                            Đã bán: ${row[1]}
                        </div>

                    </div>
                </c:forEach>

            </div>