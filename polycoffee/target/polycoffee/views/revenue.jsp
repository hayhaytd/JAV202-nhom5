<%@ page contentType="text/html;charset=UTF-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

        <html>

        <head>
            <title>Revenue Dashboard</title>
            <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

            <style>
                body {
                    font-family: Arial;
                }

                .container {
                    display: grid;
                    grid-template-columns: repeat(3, 1fr);
                    gap: 20px;
                }

                .card {
                    padding: 20px;
                    border-radius: 12px;
                    box-shadow: 0 0 10px #ccc;
                    background: #fff;
                }

                canvas {
                    max-height: 250px;
                }

                .summary p {
                    font-size: 18px;
                }
            </style>
        </head>

        <body>

            <h2>📊 Revenue Dashboard</h2>

            <form method="post" action="revenue">
                From:
                <input type="date" name="from" value="${from}">
                To:
                <input type="date" name="to" value="${to}">
                <button type="submit">Lọc</button>
            </form>

            <div class="container">

                <!-- SUMMARY -->
                <div class="card summary">
                    <h3>💰 Tổng quan</h3>
                    <c:if test="${not empty summary}">
                        <p>Hóa đơn: ${summary[0]}</p>
                        <p>Doanh thu: ${summary[1]}</p>
                        <p>Trung bình: ${summary[2]}</p>
                    </c:if>
                </div>

                <!-- DATE -->
                <div class="card">
                    <h3>📈 Theo ngày</h3>
                    <canvas id="dateChart"></canvas>
                </div>

                <!-- TOP -->
                <div class="card">
                    <h3>🏆 Top đồ uống</h3>
                    <canvas id="topChart"></canvas>
                </div>

                <!-- STAFF -->
                <div class="card">
                    <h3>👨‍💼 Nhân viên</h3>
                    <canvas id="staffChart"></canvas>
                </div>

                <!-- CATEGORY -->
                <div class="card">
                    <h3>🍹 Theo loại</h3>
                    <canvas id="categoryChart"></canvas>
                </div>

                <!-- HOUR -->
                <div class="card">
                    <h3>⏰ Theo giờ</h3>
                    <canvas id="hourChart"></canvas>
                </div>

            </div>

            <script>
                // ================= DATE =================
                const dateLabels = [];
                const dateValues = [];

                <c:forEach var="row" items="${dateData}">
                    dateLabels.push("${row[0]}");
                    dateValues.push(${row[1]});
                </c:forEach>

                new Chart(document.getElementById("dateChart"), {
                    type: "line",
                    data: {
                        labels: dateLabels,
                        datasets: [{ data: dateValues }]
                    }
                });

                // ================= TOP =================
                const topLabels = [];
                const topValues = [];

                <c:forEach var="row" items="${topData}">
                    topLabels.push("${row[0]}");
                    topValues.push(${row[1]});
                </c:forEach>

                new Chart(document.getElementById("topChart"), {
                    type: "bar",
                    data: {
                        labels: topLabels,
                        datasets: [{ data: topValues }]
                    }
                });

                // ================= STAFF =================
                const staffLabels = [];
                const staffValues = [];

                <c:forEach var="row" items="${staffData}">
                    staffLabels.push("${row[0]}");
                    staffValues.push(${row[1]});
                </c:forEach>

                new Chart(document.getElementById("staffChart"), {
                    type: "bar",
                    data: {
                        labels: staffLabels,
                        datasets: [{ data: staffValues }]
                    }
                });

                // ================= CATEGORY =================
                const cateLabels = [];
                const cateValues = [];

                <c:forEach var="row" items="${categoryData}">
                    cateLabels.push("${row[0]}");
                    cateValues.push(${row[1]});
                </c:forEach>

                new Chart(document.getElementById("categoryChart"), {
                    type: "pie",
                    data: {
                        labels: cateLabels,
                        datasets: [{ data: cateValues }]
                    }
                });

                // ================= HOUR =================
                const hourLabels = [];
                const hourValues = [];

                <c:forEach var="row" items="${hourData}">
                    hourLabels.push("${row[0]}h");
                    hourValues.push(${row[1]});
                </c:forEach>

                new Chart(document.getElementById("hourChart"), {
                    type: "bar",
                    data: {
                        labels: hourLabels,
                        datasets: [{ data: hourValues }]
                    }
                });

            </script>

        </body>

        </html>