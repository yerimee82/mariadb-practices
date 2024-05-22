/*Bookmall 테이블 DDL */

CREATE TABLE user (
    no INT AUTO_INCREMENT PRIMARY KEY, -- 번호
    name VARCHAR(50) NOT NULL, -- 이름
    email VARCHAR(200) NOT NULL, -- 이메일
    password VARCHAR(64) NOT NULL, -- 비밀번호
    phone CHAR(13) NOT NULL -- 전화번호
);

CREATE TABLE category (
    no INT AUTO_INCREMENT PRIMARY KEY, -- 번호
    name VARCHAR(50) NOT NULL -- 이름
);

CREATE TABLE book (
    no INT AUTO_INCREMENT PRIMARY KEY, -- 번호
    title VARCHAR(200) NOT NULL, -- 타이틀
    price INT NOT NULL, -- 가격
    category_no INT, -- 카테고리번호
    FOREIGN KEY (category_no) REFERENCES category(no)
);

CREATE TABLE cart (
    user_no INT, -- 사용자번호
    book_no INT, -- 책번호
    quantity INT NOT NULL, -- 수량
    FOREIGN KEY (user_no) REFERENCES user(no),
    FOREIGN KEY (book_no) REFERENCES book(no)
);

CREATE TABLE orders (
    no INT AUTO_INCREMENT PRIMARY KEY, -- 번호
    number VARCHAR(20) NOT NULL, -- 주문번호
    payment INT NOT NULL, -- 결제금액
    shipping VARCHAR(200) NOT NULL, -- 배송지
    status ENUM('입금확인중', '배송준비', '배송중', '배송완료') NOT NULL, -- 상태
    user_no INT, -- 사용자 번호
    FOREIGN KEY (user_no) REFERENCES user(no)
);

CREATE TABLE orders_book (
    order_no INT, -- 주문번호
    book_no INT, -- 서적번호
    quantity INT NOT NULL, -- 수량
    price INT NOT NULL, -- 가격
    FOREIGN KEY (order_no) REFERENCES orders(no),
    FOREIGN KEY (book_no) REFERENCES book(no)
);

