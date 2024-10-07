
CREATE TABLE CATEGORY(
  ID INTEGER GENERATED BY DEFAULT AS IDENTITY (START WITH 1) PRIMARY KEY,
  NAME VARCHAR(20) UNIQUE NOT NULL
);


CREATE TABLE PRODUCT(
  ID INTEGER GENERATED BY DEFAULT AS IDENTITY (START WITH 1) PRIMARY KEY,
 CATEGORY_ID INT NOT NULL,
NAME VARCHAR(20) NOT NULL,
PRICE INT,
FOREIGN KEY (CATEGORY_ID) REFERENCES CATEGORY(ID)

);