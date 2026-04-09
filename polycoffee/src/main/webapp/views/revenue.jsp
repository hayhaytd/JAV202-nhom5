<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Revenue Statistics</title>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>

<body>

<h2>📊 Thống kê doanh thu</h2>

<form method="post" action="revenue">

    <select name="type" onchange="toggleInput(this.value)">
        <option value="date">Theo ngày</option>
        <option value="month">Theo tháng</option>
        <option value="year">Theo năm</option>
    </select>

    <div id="dateBox">
        From: <input type="date" name="from">
        To: <input type="date" name="to">
    </div>

    <div id="monthBox" style="display:none">
        Year: <input type="number" name="year" value="2025">
    </div>

    <button type="submit">Thống kê</button>

</form>

<hr>

<canvas id="chart" width="800" height="400"></canvas>

<script>
    function toggleInput(type) {
        document.getElementById("dateBox").style.display = (type === "date") ? "block" : "none";
        document.getElementById("monthBox").style.display = (type === "month") ? "block" : "none";
    }
</script>

<c:if test="${not empty data}">
<script>
    const labels = [];
    const values = [];

    <c:forEach var="row" items="${data}">
        labels.push("${row[0]}");
        values.push(${row[1]});
    </c:forEach>

    const ctx = document.getElementById('chart');

    new Chart(ctx, {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [{
                label: 'Doanh thu',
                data: values
            }]
        }
    });
</script>
</c:if>

</body>
</html>