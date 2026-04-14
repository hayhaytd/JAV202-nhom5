<%@ page contentType="text/html;charset=UTF-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

        <!DOCTYPE html>
        <html>

        <head>
            <title>PolyCoffee</title>

            <style>
                * {
                    margin: 0;
                    padding: 0;
                    box-sizing: border-box;
                }

                body {
                    font-family: 'Segoe UI', Arial, sans-serif;
                    background: #f5f6fa;
                }

                /* NAVBAR */
                .navbar {
                    background: linear-gradient(90deg, #2c3e50, #34495e);
                    padding: 14px 30px;
                    display: flex;
                    align-items: center;
                    justify-content: space-between;
                    box-shadow: 0 2px 6px rgba(0, 0, 0, 0.2);
                }

                .navbar a {
                    color: #ecf0f1;
                    margin-right: 20px;
                    text-decoration: none;
                    font-weight: 500;
                    transition: 0.3s;
                }

                .navbar a:hover {
                    color: #f1c40f;
                }

                .active {
                    color: #f1c40f !important;
                    border-bottom: 2px solid #f1c40f;
                    padding-bottom: 4px;
                }

                /* CONTENT */
                .content {
                    padding: 25px;
                }

                .card {
                    background: white;
                    padding: 20px;
                    border-radius: 12px;
                    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
                    margin-bottom: 20px;
                }

                h2 {
                    margin-bottom: 15px;
                    color: #2c3e50;
                }
            </style>
        </head>

        <body>

            <!-- NAVBAR -->
            <div class="navbar">
                <jsp:include page="/views/menu.jsp" />
            </div>

            <!-- CONTENT -->
            <div class="content">

                <div class="card">
                    <c:choose>
                        <c:when test="${not empty view}">
                            <jsp:include page="${view}" />
                        </c:when>
                        <c:otherwise>
                            <jsp:include page="/views/home.jsp" />
                        </c:otherwise>
                    </c:choose>
                </div>

            </div>

        </body>

        </html>