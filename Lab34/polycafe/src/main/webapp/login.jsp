<%@ page contentType="text/html;charset=UTF-8" %>

<div style="width:300px;margin:auto;padding:20px;background:white;box-shadow:0 0 10px gray;border-radius:8px;">
    
    <h2>Đăng nhập</h2>

    <!-- Hiển thị lỗi -->
    <p style="color:red;">
        ${message}
    </p>

    <!-- FORM LOGIN -->
    <form action="dang-nhap" method="post">

        <input type="text" name="email" placeholder="Email"
               required style="width:100%;margin:10px 0;padding:8px;">

        <input type="password" name="password" placeholder="Mật khẩu"
               required style="width:100%;margin:10px 0;padding:8px;">

        <button type="submit"
                style="width:100%;padding:10px;background:green;color:white;border:none;">
            Đăng nhập
        </button>

    </form>

</div>