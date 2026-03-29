<<<<<<< HEAD
﻿CREATE DATABASE PolyCoffee

GO

USE PolyCoffee;

=======
﻿USE PolyCoffee;
>>>>>>> c6173474d57762732be7cecad51445b533133755
GO

-- =====================
-- RESET DATABASE
-- =====================
DROP TABLE IF EXISTS Bill_Details;
DROP TABLE IF EXISTS Bill;
DROP TABLE IF EXISTS Drink;
DROP TABLE IF EXISTS Category;
DROP TABLE IF EXISTS [User];

-- =====================
-- CREATE TABLE
-- =====================

-- USER
CREATE TABLE [User]
(
  id INT IDENTITY(1,1) PRIMARY KEY,
  fullname NVARCHAR(100) NOT NULL,
  email VARCHAR(100) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  phone VARCHAR(15) NOT NULL UNIQUE,
  role INT NOT NULL,
  active BIT NOT NULL
);

-- CATEGORY
CREATE TABLE Category
(
  id INT IDENTITY(1,1) PRIMARY KEY,
  name NVARCHAR(255) NOT NULL,
  active BIT NOT NULL
);

-- DRINK
CREATE TABLE Drink
(
  id INT IDENTITY(1,1) PRIMARY KEY,
  name NVARCHAR(250) NOT NULL,
  price DECIMAL(10,2) NOT NULL,
  image VARCHAR(250),
  description NVARCHAR(MAX),
  active BIT NOT NULL,
  category_id INT NOT NULL,
  FOREIGN KEY (category_id) REFERENCES Category(id)
);

-- BILL
CREATE TABLE Bill
(
  id INT IDENTITY(1,1) PRIMARY KEY,
  code VARCHAR(20) NOT NULL UNIQUE,
  created_at DATETIME NOT NULL,
  total DECIMAL(12,2) NOT NULL,
  status INT NOT NULL,
  user_id INT NOT NULL,
  FOREIGN KEY (user_id) REFERENCES [User](id)
);

-- BILL DETAILS
CREATE TABLE Bill_Details
(
  id INT IDENTITY(1,1) PRIMARY KEY,
  quantity INT NOT NULL,
  price DECIMAL(10,2) NOT NULL,
  bill_id INT NOT NULL,
  drink_id INT NOT NULL,
  FOREIGN KEY (bill_id) REFERENCES Bill(id),
  FOREIGN KEY (drink_id) REFERENCES Drink(id)
);

-- =====================
-- INSERT USER (>=3)
-- =====================
INSERT INTO [User] (fullname, email, password, phone, role, active) VALUES
(N'Admin', 'admin@gmail.com', '123', '0900000001', 0, 1),
(N'Employee', 'emp@gmail.com', '123', '0900000002', 1, 1),
(N'Guest', 'guest@gmail.com', '123', '0900000003', 2, 1);

-- =====================
-- INSERT CATEGORY (>=6)
-- =====================
INSERT INTO Category (name, active) VALUES
(N'Cà phê', 1),
<<<<<<< HEAD
(N'Trà s?a', 1),
(N'Nu?c ép', 1),
(N'Sinh t?', 1),
(N'Trà', 1),
(N'Ðá xay', 1);
=======
(N'Trà sữa', 1),
(N'Nước ép', 1),
(N'Sinh tố', 1),
(N'Trà', 1),
(N'Đá xay', 1);
>>>>>>> c6173474d57762732be7cecad51445b533133755

-- =====================
-- INSERT DRINK (50 MÓN)
-- =====================
INSERT INTO Drink (name, price, image, description, active, category_id) VALUES
<<<<<<< HEAD
(N'Cà phê den',20000,'h1.jpg',N'Cà phê',1,1),
(N'Cà phê s?a',25000,'h2.jpg',N'Cà phê',1,1),
(N'B?c x?u',27000,'h3.jpg',N'Cà phê',1,1),
(N'Latte',30000,'h4.jpg',N'Cà phê',1,1),
(N'Cappuccino',32000,'h5.jpg',N'Cà phê',1,1),

(N'Trà s?a trân châu',30000,'h6.jpg',N'Trà s?a',1,2),
(N'Trà s?a matcha',35000,'h7.jpg',N'Trà s?a',1,2),
(N'Trà s?a socola',34000,'h8.jpg',N'Trà s?a',1,2),
(N'Trà s?a dâu',33000,'h9.jpg',N'Trà s?a',1,2),
(N'Trà s?a khoai môn',36000,'h10.jpg',N'Trà s?a',1,2),

(N'Nu?c ép cam',28000,'h11.jpg',N'Nu?c ép',1,3),
(N'Nu?c ép táo',28000,'h12.jpg',N'Nu?c ép',1,3),
(N'Nu?c ép dua h?u',28000,'h13.jpg',N'Nu?c ép',1,3),
(N'Nu?c ép cà r?t',28000,'h14.jpg',N'Nu?c ép',1,3),
(N'Nu?c ép thom',28000,'h15.jpg',N'Nu?c ép',1,3),

(N'Sinh t? bo',30000,'h16.jpg',N'Sinh t?',1,4),
(N'Sinh t? xoài',30000,'h17.jpg',N'Sinh t?',1,4),
(N'Sinh t? dâu',30000,'h18.jpg',N'Sinh t?',1,4),
(N'Sinh t? chu?i',30000,'h19.jpg',N'Sinh t?',1,4),
(N'Sinh t? vi?t qu?t',30000,'h20.jpg',N'Sinh t?',1,4),

(N'Trà dào',25000,'h21.jpg',N'Trà',1,5),
(N'Trà chanh',20000,'h22.jpg',N'Trà',1,5),
(N'Trà v?i',27000,'h23.jpg',N'Trà',1,5),
(N'Trà dâu',27000,'h24.jpg',N'Trà',1,5),
(N'Trà t?c',22000,'h25.jpg',N'Trà',1,5),

(N'Ðá xay socola',35000,'h26.jpg',N'Ðá xay',1,6),
(N'Ðá xay matcha',35000,'h27.jpg',N'Ðá xay',1,6),
(N'Ðá xay dâu',35000,'h28.jpg',N'Ðá xay',1,6),
(N'Ðá xay oreo',36000,'h29.jpg',N'Ðá xay',1,6),
(N'Ðá xay cà phê',36000,'h30.jpg',N'Ðá xay',1,6),
=======
(N'Cà phê đen',20000,'h1.jpg',N'Cà phê',1,1),
(N'Cà phê sữa',25000,'h2.jpg',N'Cà phê',1,1),
(N'Bạc xỉu',27000,'h3.jpg',N'Cà phê',1,1),
(N'Latte',30000,'h4.jpg',N'Cà phê',1,1),
(N'Cappuccino',32000,'h5.jpg',N'Cà phê',1,1),

(N'Trà sữa trân châu',30000,'h6.jpg',N'Trà sữa',1,2),
(N'Trà sữa matcha',35000,'h7.jpg',N'Trà sữa',1,2),
(N'Trà sữa socola',34000,'h8.jpg',N'Trà sữa',1,2),
(N'Trà sữa dâu',33000,'h9.jpg',N'Trà sữa',1,2),
(N'Trà sữa khoai môn',36000,'h10.jpg',N'Trà sữa',1,2),

(N'Nước ép cam',28000,'h11.jpg',N'Nước ép',1,3),
(N'Nước ép táo',28000,'h12.jpg',N'Nước ép',1,3),
(N'Nước ép dưa hấu',28000,'h13.jpg',N'Nước ép',1,3),
(N'Nước ép cà rốt',28000,'h14.jpg',N'Nước ép',1,3),
(N'Nước ép thơm',28000,'h15.jpg',N'Nước ép',1,3),

(N'Sinh tố bơ',30000,'h16.jpg',N'Sinh tố',1,4),
(N'Sinh tố xoài',30000,'h17.jpg',N'Sinh tố',1,4),
(N'Sinh tố dâu',30000,'h18.jpg',N'Sinh tố',1,4),
(N'Sinh tố chuối',30000,'h19.jpg',N'Sinh tố',1,4),
(N'Sinh tố việt quất',30000,'h20.jpg',N'Sinh tố',1,4),

(N'Trà đào',25000,'h21.jpg',N'Trà',1,5),
(N'Trà chanh',20000,'h22.jpg',N'Trà',1,5),
(N'Trà vải',27000,'h23.jpg',N'Trà',1,5),
(N'Trà dâu',27000,'h24.jpg',N'Trà',1,5),
(N'Trà tắc',22000,'h25.jpg',N'Trà',1,5),

(N'Đá xay socola',35000,'h26.jpg',N'Đá xay',1,6),
(N'Đá xay matcha',35000,'h27.jpg',N'Đá xay',1,6),
(N'Đá xay dâu',35000,'h28.jpg',N'Đá xay',1,6),
(N'Đá xay oreo',36000,'h29.jpg',N'Đá xay',1,6),
(N'Đá xay cà phê',36000,'h30.jpg',N'Đá xay',1,6),
>>>>>>> c6173474d57762732be7cecad51445b533133755

(N'Món 31',30000,'h31.jpg',N'Khác',1,1),
(N'Món 32',30000,'h32.jpg',N'Khác',1,2),
(N'Món 33',30000,'h33.jpg',N'Khác',1,3),
(N'Món 34',30000,'h34.jpg',N'Khác',1,4),
(N'Món 35',30000,'h35.jpg',N'Khác',1,5),

(N'Món 36',30000,'h36.jpg',N'Khác',1,6),
(N'Món 37',30000,'h37.jpg',N'Khác',1,1),
(N'Món 38',30000,'h38.jpg',N'Khác',1,2),
(N'Món 39',30000,'h39.jpg',N'Khác',1,3),
(N'Món 40',30000,'h40.jpg',N'Khác',1,4),

(N'Món 41',30000,'h41.jpg',N'Khác',1,5),
(N'Món 42',30000,'h42.jpg',N'Khác',1,6),
(N'Món 43',30000,'h43.jpg',N'Khác',1,1),
(N'Món 44',30000,'h44.jpg',N'Khác',1,2),
(N'Món 45',30000,'h45.jpg',N'Khác',1,3),

(N'Món 46',30000,'h46.jpg',N'Khác',1,4),
(N'Món 47',30000,'h47.jpg',N'Khác',1,5),
(N'Món 48',30000,'h48.jpg',N'Khác',1,6),
(N'Món 49',30000,'h49.jpg',N'Khác',1,1),
(N'Món 50',30000,'h50.jpg',N'Khác',1,2);

-- =====================
-- BILL
-- =====================
INSERT INTO Bill (code, created_at, total, status, user_id) VALUES
('HD001', GETDATE(), 100000, 1, 1),
('HD002', GETDATE(), 80000, 1, 2);

-- =====================
-- BILL DETAILS
-- =====================
INSERT INTO Bill_Details (quantity, price, bill_id, drink_id) VALUES
(2, 20000, 1, 1),
(1, 30000, 1, 6),
<<<<<<< HEAD
(1, 25000, 2, 2);
=======
(1, 25000, 2, 2);
>>>>>>> c6173474d57762732be7cecad51445b533133755
