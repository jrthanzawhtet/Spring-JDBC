DROP TABLE IF EXISTS MEMBER;

CREATE TABLE MEMBER (
    loginId VARCHAR(8) PRIMARY KEY,
    password VARCHAR(8) NOT NULL,
    name VARCHAR(40) NOT NULL,
    phone VARCHAR(12),
    email VARCHAR(40)
);
