<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<style>
.navbar {
    display: flex;
    justify-content: space-between;
    align-items: center;
    background: #2c3e50;
    padding: 12px 20px;
    color: white;
}

.nav-left, .nav-right {
    display: flex;
    align-items: center;
}

.logo {
    font-weight: bold;
    color: #f1c40f;
    margin-right: 20px;
}

.nav-link {
    margin-right: 15px;
    text-decoration: none;
    color: #ecf0f1;
    transition: 0.2s;
}

.nav-link:hover {
    color: #f1c40f;
}

.active {
    color: #f1c40f;
    border-bottom: 2px solid #f1c40f;
}

.user {
    margin-right: 15px;
    color: #bdc3c7;
}
</style>
<c:set var="user" value="${sessionScope.user}" />

<nav class="navbar">

    <!-- LEFT -->
    <div class="nav-left">

        <div class="logo">☕ PolyCoffee</div>

        <!-- TRANG CHỦ -->
        <a href="home" class="nav-link ${page=='home'?'active':''}">
            🏠 Trang chủ
        </a>

        <!-- ================= LOGIN USER ================= -->
        <c:if test="${not empty user}">


            <!-- HÓA ĐƠN (admin + employee) -->
            <c:if test="${user.role == 0 || user.role == 1}">
                <a href="bill" class="nav-link ${page=='bill'?'active':''}">
                    🧾 Hóa đơn
                </a>
            </c:if>

            <!-- ADMIN ONLY -->
            <c:if test="${user.role == 0}">
                <a href="drink" class="nav-link ${page=='drink'?'active':''}">
                    🍹 Đồ uống
                </a>

                <a href="revenue" class="nav-link ${page=='revenue'?'active':''}">
                    📊 Thống kê
                </a>

                <a href="employee" class="nav-link ${page=='employee'?'active':''}">
                    👨‍💼 Nhân viên
                </a>
            </c:if>

            <!-- GUEST (role 2) -->
            <c:if test="${user.role == 2}">
                <a href="home" class="nav-link">
                    👀 Xem sản phẩm
                </a>
            </c:if>

        </c:if>

    </div>

    <!-- RIGHT -->
    <div class="nav-right">

        <c:choose>

            <c:when test="${not empty user}">
                <span class="user">
                    👋 ${user.fullname}
                </span>
                <a href="logout" class="nav-link">Đăng xuất</a>
            </c:when>

            <c:otherwise>
                <a href="login" class="nav-link">Đăng nhập</a>
            </c:otherwise>

        </c:choose>

    </div>

</nav>