<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

        <style>
            .nav-container {
                display: flex;
                justify-content: space-between;
                align-items: center;
                background: linear-gradient(90deg, #2c3e50, #34495e);
                padding: 12px 25px;
                box-shadow: 0 2px 6px rgba(0, 0, 0, 0.2);
            }

            .nav-left,
            .nav-right {
                display: flex;
                align-items: center;
            }

            .nav-left a,
            .nav-right a {
                color: #ecf0f1;
                text-decoration: none;
                margin-right: 20px;
                font-weight: 500;
                transition: 0.3s;
                position: relative;
            }

            .nav-left a:hover,
            .nav-right a:hover {
                color: #f1c40f;
            }

            /* ACTIVE MENU */
            .active {
                color: #f1c40f !important;
            }

            .active::after {
                content: "";
                position: absolute;
                width: 100%;
                height: 2px;
                background: #f1c40f;
                bottom: -4px;
                left: 0;
            }

            /* LOGO */
            .logo {
                font-size: 18px;
                font-weight: bold;
                color: #f1c40f;
                margin-right: 30px;
            }
        </style>

        <nav class="nav-container">

            <!-- LEFT MENU -->
            <div class="nav-left">

                <div class="logo">☕ PolyCoffee</div>

                <a href="home" class="${page == 'home' ? 'active' : ''}">Trang chủ</a>

                <a href="drink" class="${page == 'drink' ? 'active' : ''}">Đồ uống</a>

                <a href="bill" class="${page == 'bill' ? 'active' : ''}">Bill</a>

                <a href="revenue" class="${page == 'revenue' ? 'active' : ''}">Thống kê</a>

                <a href="employee" class="${page == 'employee' ? 'active' : ''}">
                    Nhân viên
                </a>
            </div>

            <!-- RIGHT MENU -->
            <div class="nav-right">

                <c:choose>
                    <c:when test="${not empty sessionScope.user}">
                        <span style="color: #bdc3c7; margin-right: 15px;">
                            👋 ${sessionScope.user.fullname}
                        </span>
                        <a href="logout">Đăng xuất</a>
                    </c:when>

                    <c:otherwise>
                        <a href="login">Đăng nhập</a>
                    </c:otherwise>
                </c:choose>

            </div>

        </nav>