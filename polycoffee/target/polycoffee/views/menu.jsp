<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

        <nav>
            <ul style="
        list-style: none;
        margin: 0;
        padding: 0;
        display: flex;
        align-items: center;
    ">
                <li style="margin-right: 20px;">
                    <a href="home" style="color: #fff; text-decoration: none;">trang chủ</a>

                <li style="margin-right: 20px;">
                    <a href="drink" style="color: #fff; text-decoration: none;">Đồ uống</a>
                </li>

                <c:if test="${sessionScope.uesr != null && sessionScope.user.getRole() == 0}">
                    <li style="margin-right: 20px;">
                        <a href="employee" style="color: #fff; text-decoration: none;">
                            Quản lý nhân viên
                        </a>
                    </li>
                </c:if>
                <li style="margin-left: 20px;">
                    <a href="login" style="color: #fff; text-decoration: none;">Dăng nhập</a>
                </li>
            </ul>
        </nav>