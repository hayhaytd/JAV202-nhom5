<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

        <h2>Thống kê Top 5 đồ uống bán chạy</h2>

        <form id="dateForm" method="get" action="top5drink">
            From Date:
            <input type="date" name="fromDate" id="fromDate" required>

            To Date:
            <input type="date" name="toDate" id="toDate" required>

            <button type="submit">Lọc</button>
        </form>
        <!-- <script>window.addEventListener("load", function () {
                const fromInput = document.getElementById("fromDate");
                const toInput = document.getElementById("toDate");

                let today = new Date();
                today.setHours(0, 0, 0, 0);

                let past = new Date();
                past.setDate(today.getDate() - 7);

                function formatDate(date) {
                    let y = date.getFullYear();
                    let m = String(date.getMonth() + 1).padStart(2, '0');
                    let d = String(date.getDate()).padStart(2, '0');
                    return `${y}-${m}-${d}`;
                }

                if (!fromInput.value || !toInput.value) {
                    fromInput.value = formatDate(past);
                    toInput.value = formatDate(today);
                }
            });
        </script> -->
        <br>

        <!-- Hiển thị lỗi -->
        <c:if test="${not empty error}">
            <p style="color:red;">${error}</p>
        </c:if>

        <!-- Bảng dữ liệu -->
        <table border="1" cellpadding="5">
            <tr>
                <th>STT</th>
                <th>Tên đồ uống</th>
                <th>Số lượng bán</th>
            </tr>

            <c:if test="${empty top5List}">
                <tr>
                    <td colspan="3">Không có dữ liệu</td>
                </tr>
            </c:if>

            <c:forEach var="row" items="${top5List}" varStatus="i">
                <tr>
                    <td>${i.index + 1}</td>
                    <td>${row[0]}</td>
                    <td>${row[1]}</td>
                </tr>
            </c:forEach>
        </table>