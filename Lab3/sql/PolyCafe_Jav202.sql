use PolyCafe_Jav202;
GO

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

INSERT INTO [User] (fullname, email, password, phone, role, active) VALUES 
(N'Nguyễn Văn A', 'a@gmail.com', '123456', '0900000001', 1, 1),
(N'Trần Thị B', 'b@gmail.com', '123456', '0900000002', 0, 1),
(N'Lê Văn C', 'c@gmail.com', '123456', '0900000003', 0, 1);

INSERT INTO Category (name, active) VALUES
(N'Cà phê', 1),
(N'Trà sữa', 1),
(N'Nước ép', 1);

INSERT INTO Drink (name, price, image, description, active, category_id) VALUES
(N'Cà phê đen', 20000, 'cf_den.jpg', N'Cà phê truyền thống', 1, 1),
(N'Cà phê sữa', 25000, 'cf_sua.jpg', N'Cà phê sữa đá', 1, 1),
(N'Trà sữa trân châu', 30000, 'ts_tc.jpg', N'Trà sữa ngon', 1, 2),
(N'Trà sữa matcha', 35000, 'ts_matcha.jpg', N'Trà sữa vị matcha', 1, 2),
(N'Nước ép cam', 28000, 'cam.jpg', N'Nước ép cam tươi', 1, 3);

INSERT INTO Bill (code, created_at, total, status, user_id) VALUES
('HD001', GETDATE(), 75000, 1, 2),
('HD002', GETDATE(), 55000, 1, 3),
('HD003', GETDATE(), 45000, 0, 2);

INSERT INTO Bill_Details (quantity, price, bill_id, drink_id) VALUES
(2, 20000, 1, 1), -- 2 cà phê đen
(1, 35000, 1, 4), -- 1 trà sữa matcha

(1, 25000, 2, 2), -- 1 cà phê sữa
(1, 30000, 2, 3), -- 1 trà sữa trân châu

(1, 20000, 3, 1),
(1, 25000, 3, 2);