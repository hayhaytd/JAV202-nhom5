<%@ page contentType="text/html;charset=UTF-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

        <style>
            .title {
                font-size: 24px;
                font-weight: bold;
                margin-bottom: 20px;
                color: #2c3e50;
            }

            .card {
                background: #fff;
                padding: 20px;
                border-radius: 12px;
                box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
                margin-bottom: 20px;
            }

            input,
            select {
                padding: 6px 10px;
                margin: 5px;
                border-radius: 6px;
                border: 1px solid #ccc;
            }

            button {
                padding: 6px 12px;
                border-radius: 6px;
                border: none;
                cursor: pointer;
                transition: 0.3s;
            }

            .btn-primary {
                background: #3498db;
                color: white;
            }

            .btn-primary:hover {
                background: #2980b9;
            }

            .btn-success {
                background: #2ecc71;
                color: white;
            }

            .btn-success:hover {
                background: #27ae60;
            }

            .btn-danger {
                background: #e74c3c;
                color: white;
            }

            .btn-danger:hover {
                background: #c0392b;
            }

            table {
                width: 100%;
                border-collapse: collapse;
            }

            th {
                background: #2c3e50;
                color: white;
                padding: 10px;
            }

            td {
                padding: 10px;
                border-bottom: 1px solid #ddd;
                text-align: center;
            }

            tr:hover {
                background: #f9f9f9;
            }

            .status.active {
                color: green;
                font-weight: bold;
            }

            .status.inactive {
                color: red;
                font-weight: bold;
            }

            .pagination {
                text-align: center;
                margin-top: 20px;
            }

            .pagination a,
            .pagination strong {
                margin: 0 5px;
                padding: 6px 10px;
                border-radius: 6px;
                text-decoration: none;
            }

            .pagination a {
                background: #ecf0f1;
                color: #2c3e50;
            }

            .pagination strong {
                background: #3498db;
                color: white;
            }
        </style>

        <div class="title">👨‍💼 Quản lý nhân viên</div>

        <!-- ===== SEARCH ===== -->
        <div class="card">
            <form method="get" action="employee">
                <input type="text" name="fullname" placeholder="Tên nhân viên..." value="${fullname}">
                <input type="text" name="email" placeholder="Email..." value="${email}">

                <select name="active">
                    <option value="">-- Trạng thái --</option>
                    <option value="true" ${active==true ? 'selected' : '' }>Hoạt động</option>
                    <option value="false" ${active==false ? 'selected' : '' }>Ngưng</option>
                </select>

                <button class="btn-primary">🔍 Tìm</button>
            </form>
        </div>

        <!-- ===== CREATE ===== -->
        <c:if test="${sessionScope.user.role == 0}">
            <div class="card">
                <h3>➕ Thêm nhân viên</h3>
                <form method="post" action="employee">
                    <input type="hidden" name="action" value="create">

                    <input type="text" name="fullname" placeholder="Tên" required>
                    <input type="email" name="email" placeholder="Email" required>
                    <input type="text" name="password" placeholder="Mật khẩu" required>

                    <select name="active">
                        <option value="true">Hoạt động</option>
                        <option value="false">Ngưng</option>
                    </select>

                    <button class="btn-success">Thêm</button>
                </form>
            </div>
        </c:if>

        <!-- ===== TABLE ===== -->
        <div class="card">
            <c:choose>

                <c:when test="${not empty employees}">
                    <table>
                        <tr>
                            <th>ID</th>
                            <th>Tên</th>
                            <th>Email</th>
                            <th>Trạng thái</th>
                            <c:if test="${sessionScope.user.role == 0}">
                                <th>Hành động</th>
                            </c:if>
                        </tr>

                        <c:forEach var="u" items="${employees}">
                            <tr>

                                <td>${u.id}</td>

                                <c:if test="${sessionScope.user.role == 0}">
                                    <form method="post" action="employee">
                                        <input type="hidden" name="action" value="update">
                                        <input type="hidden" name="id" value="${u.id}">

                                        <td><input type="text" name="fullname" value="${u.fullname}"></td>
                                        <td><input type="email" name="email" value="${u.email}"></td>

                                        <td>
                                            <select name="active">
                                                <option value="true" ${u.active ? 'selected' : '' }>Hoạt động</option>
                                                <option value="false" ${!u.active ? 'selected' : '' }>Ngưng</option>
                                            </select>
                                        </td>

                                        <td>
                                            <button class="btn-primary">Sửa</button>
                                    </form>

                                    <form method="post" action="employee" style="display:inline;">
                                        <input type="hidden" name="action" value="delete">
                                        <input type="hidden" name="id" value="${u.id}">
                                        <button class="btn-danger"
                                            onclick="return confirm('Xóa nhân viên?')">Xóa</button>
                                    </form>
                                    </td>
                                </c:if>

                                <c:if test="${sessionScope.user.role != 0}">
                                    <td>${u.fullname}</td>
                                    <td>${u.email}</td>
                                    <td>
                                        <span class="status ${u.active ? 'active' : 'inactive'}">
                                            ${u.active ? 'Hoạt động' : 'Ngưng'}
                                        </span>
                                    </td>
                                </c:if>

                            </tr>
                        </c:forEach>

                    </table>
                </c:when>

                <c:otherwise>
                    <p>Không có dữ liệu nhân viên</p>
                </c:otherwise>

            </c:choose>
        </div>

        <!-- ===== PAGINATION ===== -->
        <div class="pagination">

            <c:if test="${currentPage > 1}">
                <a href="employee?page=${currentPage - 1}&fullname=${fullname}&email=${email}&active=${active}">⬅</a>
            </c:if>

            <c:forEach begin="1" end="${totalPages}" var="i">
                <c:choose>
                    <c:when test="${i == currentPage}">
                        <strong>${i}</strong>
                    </c:when>
                    <c:otherwise>
                        <a href="employee?page=${i}&fullname=${fullname}&email=${email}&active=${active}">${i}</a>
                    </c:otherwise>
                </c:choose>
            </c:forEach>

            <c:if test="${currentPage < totalPages}">
                <a href="employee?page=${currentPage + 1}&fullname=${fullname}&email=${email}&active=${active}">➡</a>
            </c:if>

        </div>