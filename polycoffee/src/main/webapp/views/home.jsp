<%@ page contentType="text/html;charset=UTF-8" %>

<style>
body {
    margin: 0;
    font-family: 'Segoe UI', sans-serif;
    background: linear-gradient(135deg, #eef2f7, #dfe9f3);
}

/* CONTAINER */
.container {
    text-align: center;
    padding: 80px 20px;
}

/* TITLE */
.title {
    font-size: 42px;
    font-weight: 800;
    color: #2c3e50;
}

.subtitle {
    margin-top: 10px;
    color: #6c757d;
    margin-bottom: 50px;
    font-size: 16px;
}

/* CARD GRID */
.actions {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(260px, 1fr));
    gap: 30px;
    max-width: 1000px;
    margin: auto;
}

/* CARD */
.card {
    padding: 30px 25px;
    border-radius: 18px;
    background: rgba(255, 255, 255, 0.8);
    backdrop-filter: blur(10px);
    box-shadow: 0 10px 30px rgba(0,0,0,0.08);
    transition: all 0.35s ease;
    position: relative;
    overflow: hidden;
}

.card:hover {
    transform: translateY(-8px) scale(1.03);
    box-shadow: 0 20px 40px rgba(0,0,0,0.12);
}

/* ICON */
.icon {
    font-size: 50px;
}

/* TEXT */
.card h3 {
    margin: 18px 0 10px;
    font-size: 20px;
}

.card p {
    color: #7f8c8d;
    font-size: 14px;
}

/* BUTTON */
.btn {
    display: inline-block;
    margin-top: 18px;
    padding: 10px 18px;
    border-radius: 10px;
    text-decoration: none;
    color: white;
    font-weight: 600;
    transition: 0.3s;
}

/* BUTTON COLORS */
.btn.menu {
    background: linear-gradient(135deg, #27ae60, #2ecc71);
}

.btn.about {
    background: linear-gradient(135deg, #3498db, #6dd5fa);
}

.btn.login {
    background: linear-gradient(135deg, #7f8c8d, #bdc3c7);
}

/* HOVER BTN */
.btn:hover {
    opacity: 0.9;
    transform: scale(1.05);
}
</style>

<div class="container">

    <div class="title">☕ PolyCoffee</div>
    <div class="subtitle">
        Trải nghiệm cà phê chất lượng & không gian hiện đại
    </div>

    <div class="actions">

        <!-- MENU -->
        <div class="card">
            <div class="icon">🍹</div>
            <h3>Xem đồ uống</h3>
            <p>Khám phá menu đa dạng và hấp dẫn</p>
            <a href="drink" class="btn menu">Xem menu</a>
        </div>

        <!-- Order -->
        <div class="card">
            <div class="icon">🛒</div>
            <h3>Đặt hàng</h3>
            <p>Đặt món nhanh</p>
            <a href="sale?action=create" class="btn about">Đặt hàng</a>
        </div>
        

        <!-- LOGIN -->
        <div class="card">
            <div class="icon">🔐</div>
            <h3>Đăng nhập</h3>
            <p>Dành cho nhân viên hệ thống</p>
            <a href="login" class="btn login">Đăng nhập</a>
        </div>

    </div>

</div>