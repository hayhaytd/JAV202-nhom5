<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html>

    <head>
        <meta charset="UTF-8">
        <title>Quên mật khẩu</title>
    </head>

    <body>

        <h2>Quên mật khẩu</h2>

        <form method="post" action="forgot-password">
            Email: <input type="text" name="email" required>
            <button type="submit">Gửi</button>
        </form>

        <p style="color:red">${message}</p>
    </body>

    </html>