<%@ page contentType="text/html;charset=UTF-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

        <c:set var="user" value="${sessionScope.user}" />
        <c:set var="isAdmin" value="${user.role == 0}" />
        <c:set var="isEmp" value="${user.role == 1}" />

        <style>
            body {
                background: #f4f6f9;
                font-family: 'Segoe UI', sans-serif;
            }

            .dashboard {
                padding: 40px;
            }

            /* TITLE */
            .title {
                font-size: 28px;
                font-weight: 700;
                margin-bottom: 10px;
            }

            .welcome {
                color: #6c757d;
                margin-bottom: 30px;
            }

            /* CARD GRID */
            .cards {
                display: grid;
                grid-template-columns: repeat(auto-fit, minmax(230px, 1fr));
                gap: 25px;
            }

            /* CARD */
            .card {
                padding: 25px;
                border-radius: 16px;
                color: white;
                font-weight: 600;
                position: relative;
                overflow: hidden;
                transition: all 0.3s ease;
                box-shadow: 0 10px 25px rgba(0, 0, 0, 0.08);
            }

            .card:hover {
                transform: translateY(-5px) scale(1.02);
            }

            /* CARD COLORS */
            .blue {
                background: linear-gradient(135deg, #3498db, #6dd5fa);
            }

            .green {
                background: linear-gradient(135deg, #27ae60, #2ecc71);
            }

            .orange {
                background: linear-gradient(135deg, #f39c12, #f1c40f);
            }

            .red {
                background: linear-gradient(135deg, #e74c3c, #ff6a6a);
            }

            .card .value {
                font-size: 26px;
                margin-top: 10px;
            }

            /* ACTIONS */
            .actions {
                margin-top: 40px;
                display: flex;
                gap: 20px;
                flex-wrap: wrap;
            }

            .action-btn {
                padding: 14px 22px;
                border-radius: 12px;
                text-decoration: none;
                color: white;
                font-weight: 600;
                transition: 0.3s;
                box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
            }

            .action-btn:hover {
                transform: translateY(-3px);
                opacity: 0.9;
            }

            /* BUTTON COLORS */
            .btn-sale {
                background: linear-gradient(135deg, #27ae60, #2ecc71);
            }

            .btn-drink {
                background: linear-gradient(135deg, #8e44ad, #c39bd3);
            }

            .btn-revenue {
                background: linear-gradient(135deg, #2980b9, #6dd5fa);
            }

            .btn-employee {
                background: linear-gradient(135deg, #e67e22, #f5b041);
            }
        </style>

        <div class="dashboard">

            <!-- TITLE -->
            <div class="title">📊 Dashboard quản lý</div>
            <div class="welcome">
                Xin chào 👋 <b>${user.fullname}</b>
            </div>

            <!-- STATS -->
            <div class="cards">

                <div class="card blue">
                    💰 Doanh thu hôm nay
                    <div class="value">
                        <c:out value="${todayRevenue}" default="0" />
                    </div>
                </div>

                <div class="card green">
                    🧾 Số đơn hôm nay
                    <div class="value">
                        <c:out value="${todayBills}" default="0" />
                    </div>
                </div>

                <div class="card orange">
                    ⏳ Đơn chưa thanh toán
                    <div class="value">
                        <c:out value="${unpaidBills}" default="0" />
                    </div>
                </div>

                <div class="card red">
                    ❌ Đơn bị huỷ
                    <div class="value">
                        <c:out value="${cancelBills}" default="0" />
                    </div>
                </div>

            </div>

            <!-- ACTIONS -->
            <div class="actions">

                <c:if test="${isAdmin || isEmp}">
                    <a href="sale?action=create" class="action-btn btn-sale">🛒 Bán hàng</a>
                </c:if>

                <c:if test="${isAdmin}">
                    <a href="drink" class="action-btn btn-drink">🍹 Quản lý đồ uống</a>
                    <a href="revenue" class="action-btn btn-revenue">📈 Thống kê</a>
                    <a href="employee" class="action-btn btn-employee">👨‍💼 Nhân viên</a>
                </c:if>

            </div>

        </div>