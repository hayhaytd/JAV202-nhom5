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
                background: white;
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

            img {
                border-radius: 6px;
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

        <div class="title">🥤 Quản lý đồ uống</div>

        <!-- ===== SEARCH ===== -->
        <div class="card">
            <form method="get" action="drink">
                <input type="text" name="name" placeholder="Tên đồ uống..." value="${name}">

                <select name="categoryId">
                    <option value="">-- Danh mục --</option>
                    <c:forEach var="c" items="${categories}">
                        <option value="${c.id}" ${c.id==categoryId ? 'selected' : '' }>
                            ${c.name}
                        </option>
                    </c:forEach>
                </select>

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
                <h3>➕ Thêm đồ uống</h3>
                <form method="post" action="drink">
                    <input type="hidden" name="action" value="create">

                    <input type="text" name="name" placeholder="Tên" required>
                    <input type="number" step="0.01" name="price" placeholder="Giá" required>

                    <select name="categoryId">
                        <c:forEach var="c" items="${categories}">
                            <option value="${c.id}">${c.name}</option>
                        </c:forEach>
                    </select>

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

                <c:when test="${not empty drinks}">
                    <table>
                        <tr>
                            <th>ID</th>
                            <th>Tên</th>
                            <th>Giá</th>
                            <th>Hình</th>
                            <th>Danh mục</th>
                            <th>Trạng thái</th>
                            <c:if test="${sessionScope.user.role == 0}">
                                <th>Hành động</th>
                            </c:if>
                        </tr>

                        <c:forEach var="d" items="${drinks}">
                            <tr>

                                <td>${d.id}</td>

                                <c:if test="${sessionScope.user.role == 0}">
                                    <form method="post" action="drink">
                                        <input type="hidden" name="action" value="update">
                                        <input type="hidden" name="id" value="${d.id}">

                                        <td><input type="text" name="name" value="${d.name}"></td>
                                        <td><input type="number" step="0.01" name="price" value="${d.price}"></td>

                                        <td>
                                            <img src="${pageContext.request.contextPath}/img/imgdrink/${d.image}"
                                                width="60">
                                        </td>

                                        <td>
                                            <select name="categoryId">
                                                <c:forEach var="c" items="${categories}">
                                                    <option value="${c.id}" ${c.id==d.category.id ? 'selected' : '' }>
                                                        ${c.name}
                                                    </option>
                                                </c:forEach>
                                            </select>
                                        </td>

                                        <td>
                                            <select name="active">
                                                <option value="true" ${d.active ? 'selected' : '' }>Hoạt động</option>
                                                <option value="false" ${!d.active ? 'selected' : '' }>Ngưng</option>
                                            </select>
                                        </td>

                                        <td>
                                            <button class="btn-primary">Sửa</button>
                                    </form>

                                    <form method="post" action="drink" style="display:inline;">
                                        <input type="hidden" name="action" value="delete">
                                        <input type="hidden" name="id" value="${d.id}">
                                        <button class="btn-danger" onclick="return confirm('Xóa?')">Xóa</button>
                                    </form>
                                    </td>
                                </c:if>

                                <c:if test="${sessionScope.user.role != 0}">
                                    <td>${d.name}</td>
                                    <td>${d.price}</td>
                                    <td>
                                        <img src="${pageContext.request.contextPath}/img/imgdrink/${d.image}"
                                            width="60">
                                    </td>
                                    <td>${d.category.name}</td>
                                    <td>
                                        <span class="status ${d.active ? 'active' : 'inactive'}">
                                            ${d.active ? 'Hoạt động' : 'Ngưng'}
                                        </span>
                                    </td>
                                </c:if>

                            </tr>
                        </c:forEach>

                    </table>
                </c:when>

                <c:otherwise>
                    <p>Không có dữ liệu</p>
                </c:otherwise>

            </c:choose>
        </div>

        <!-- ===== PAGINATION ===== -->
        <div class="pagination">

            <c:if test="${currentPage > 1}">
                <a href="drink?page=${currentPage - 1}&name=${name}&categoryId=${categoryId}&active=${active}">⬅</a>
            </c:if>

            <c:forEach begin="1" end="${totalPages}" var="i">
                <c:choose>
                    <c:when test="${i == currentPage}">
                        <strong>${i}</strong>
                    </c:when>
                    <c:otherwise>
                        <a href="drink?page=${i}&name=${name}&categoryId=${categoryId}&active=${active}">${i}</a>
                    </c:otherwise>
                </c:choose>
            </c:forEach>

            <c:if test="${currentPage < totalPages}">
                <a href="drink?page=${currentPage + 1}&name=${name}&categoryId=${categoryId}&active=${active}">➡</a>
            </c:if>

        </div>