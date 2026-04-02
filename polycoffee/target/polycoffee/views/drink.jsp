<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

        <c:choose>

            <c:when test="${not empty drinks}">
                <table border="1" width="100%" cellpadding="10">
                    <tr>
                        <th>ID</th>
                        <th>Tên</th>
                        <th>Giá</th>
                        <th>Hình ảnh</th>
                        <th>Mô tả</th>
                        <th>Danh mục</th>
                    </tr>
Q
                    <c:forEach var="d" items="${drinks}">
                        <tr>
                            <td>${d.id}</td>
                            <td>${d.name}</td>
                            <td>${d.price}</td>
                            <td>
                                <img src="${pageContext.request.contextPath}/img/imgdrink/${d.image}" width="60">
                            </td>
                            <td>${d.description}</td>
                            <td>${d.category.name}</td>
                        </tr>
                    </c:forEach>
                </table>
            </c:when>

            <c:otherwise>
                <p>Không có dữ liệu đồ uống</p>
            </c:otherwise>

        </c:choose>