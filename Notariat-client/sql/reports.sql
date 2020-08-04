SET NAMES 'UTF8';
SHOW CREATE TABLE CATEGORY_FISHES;

CREATE TABLE FISH_CATEGORIES (
    category_id int(11) NOT NULL AUTO_INCREMENT,
    cod_vdovkin int(3) NOT NULL,
    name varchar(150) CHARACTER SET utf8 NOT NULL,
    PRIMARY KEY (category_id)
    );

ALTER TABLE FISH_CATEGORIES DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
ALTER TABLE FISH_CATEGORIES CHANGE name name VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_general_ci;
DROP TABLE FISH_CATEGORIES;
INSERT INTO FISH_CATEGORIES (cod_vdovkin,name) VALUES (1,'Договора');
INSERT INTO FISH_CATEGORIES (cod_vdovkin,name) VALUES (2,'Доверенности');
INSERT INTO FISH_CATEGORIES (cod_vdovkin,name) VALUES (3,'Завещания');
INSERT INTO FISH_CATEGORIES (cod_vdovkin,name) VALUES (4,'Заявления');
INSERT INTO FISH_CATEGORIES (cod_vdovkin,name) VALUES (5,'Свидетельства');
INSERT INTO FISH_CATEGORIES (cod_vdovkin,name) VALUES (6,'Штампы');
INSERT INTO FISH_CATEGORIES (cod_vdovkin,name) VALUES (7,'Прочие');
INSERT INTO FISH_CATEGORIES (cod_vdovkin,name) VALUES (10,'Согласия');
INSERT INTO FISH_CATEGORIES (cod_vdovkin,name) VALUES (11,'Обязательства');
INSERT INTO FISH_CATEGORIES (cod_vdovkin,name) VALUES (12,'Отказы');
INSERT INTO FISH_CATEGORIES (cod_vdovkin,name) VALUES (13,'Наследство');
SELECT * FROM FISH_CATEGORIES;
DELETE FROM FISH_CATEGORIES;

CREATE TABLE FISH_CATEGORY_SUBCATEGORIES (
    id int(11) NOT NULL AUTO_INCREMENT,
    category_id int(11) NOT NULL,
    subcategory_id int(11) NOT NULL,
    PRIMARY KEY (id)
    );
DROP TABLE FISH_CATEGORY_SUBCATEGORIES;
SELECT * FROM FISH_CATEGORY_SUBCATEGORIES;
DELETE FROM FISH_CATEGORY_SUBCATEGORIES;
INSERT INTO FISH_CATEGORY_SUBCATEGORIES (category_id, subcategory_id) VALUES ();
SELECT MAX(id) FROM FISH_CATEGORY_SUBCATEGORIES;

CREATE TABLE FISH_SUBCATEGORIES (
    subcategory_id int(11) NOT NULL AUTO_INCREMENT,
    cod_vdovkin int(3) NOT NULL,
    name varchar(150) CHARACTER SET utf8 NOT NULL,
    PRIMARY KEY (subcategory_id)
    );
DROP TABLE FISH_SUBCATEGORIES;
INSERT INTO FISH_SUBCATEGORIES (cod_vdovkin, name) VALUES ();
SELECT * FROM FISH_SUBCATEGORIES;
SELECT MAX(subcategory_id) FROM FISH_SUBCATEGORIES;
DELETE FROM FISH_SUBCATEGORIES;
SELECT * FROM FISH_SUBCATEGORIES subCategory
    inner join FISH_CATEGORY_SUBCATEGORIES subCategoryOfCategory on subCategoryOfCategory.subcategory_id = subCategory.subcategory_id
    inner join FISH_CATEGORIES category on category.category_id = subCategoryOfCategory.category_id
    inner join DEPARTMENT_FISH_CATEGORY_SUBCATEGORIES dfcs on dfcs.category_subcategories_id = subCategoryOfCategory.id
    inner join DEPARTMENTS department on department.id = dfcs.department_id
    WHERE category.category_id = 1 AND department.name = 'НК2';

CREATE TABLE DEPARTMENT_FISH_CATEGORY_SUBCATEGORIES (
    id int(11) NOT NULL AUTO_INCREMENT,
    category_subcategories_id int(11) NOT NULL,
    department_id int(11) NOT NULL,
    PRIMARY KEY (id)
    );
INSERT INTO DEPARTMENT_FISH_CATEGORY_SUBCATEGORIES (category_subcategories_id, department_id) VALUES (?, ?);
SELECT * FROM DEPARTMENT_FISH_CATEGORY_SUBCATEGORIES;
DROP TABLE DEPARTMENT_FISH_CATEGORY_SUBCATEGORIES;

CREATE TABLE DEPARTMENTS(
    id int(11) NOT NULL AUTO_INCREMENT,
    name varchar(150) CHARACTER SET utf8 NOT NULL,
    PRIMARY KEY (id)
    );
INSERT INTO DEPARTMENTS (name) VALUES ('НК2');
SELECT * FROM DEPARTMENTS dep WHERE dep.name = 'НК2';

//-------------------------------------------------------------------

CREATE TABLE ORDERS(
    order_number varchar(100) not null primary key,
    date_the_order_was_greated date not null,
    contact_person int not null references PERSONS (person_id),
    disconte int not null,
    status_of_order varchar(25) not null
);

CREATE TABLE ORDER_POSITIONS(
    order_id varchar(100) not null references ORDERS (order_number),
    item_id varchar(100) not null references ITEMS (article),
    number_of_items int not null,
    amount_OF_items int not null,
    disconte int not null
);

CREATE TABLE ADDRESSES(
    address_id int not null primary key,
    contry varchar(100) not null,
    region varchar(150) not null,
    street varchar(100),
    house varchar(20) not null,
    flat varchar(20)
);

CREATE TABLE PERSONS(
    person_id int not null primary key,
    person_name varchar(100) not null,
    phone_number varchar(100) not null,
    address_to_delivery int not null references ADDRESSES (address_id)
);

CREATE TABLE CUSTOMERS(
    person_name varchar(100) not null,
    phone_number varchar(100) not null,
    contry varchar(100) not null,
    region varchar(150) not null,
    street varchar(100),
    house varchar(20) not null,
    flat varchar(20)
);

insert INTO ITEMS (ARTICLE, NAME, COLOR, PRICE, STOCK_BALANCE) VALUES ('1', '1', '1', 1800, 1);
INSERT INTO CUSTOMERS (PERSON_NAME, PHONE_NUMBER, CONTRY, REGION, STREET, HOUSE, FLAT) VALUES (?, ?, ?, ?, ?, ?, ?);

SELECT * FROM ITEMS;  
SELECT * FROM PERSONS;  
SELECT * FROM ADDRESSES;  
SELECT * FROM ORDERS;  
SELECT * FROM ORDER_POSITIONS;
SELECT * FROM CUSTOMERS;

DELETE FROM ADDRESSES WHERE ADDRESS_ID = 2;
DELETE FROM ORDER_POSITIONS WHERE ORDER_ID = '202061017430';
DELETE FROM ORDERS WHERE ORDER_NUMBER = '202061017430';
DELETE FROM PERSONS WHERE PERSON_ID = 2;


UPDATE ITEMS SET NAME = 'Стол обеденный', COLOR = 'орех', PRICE = 15000, STOCK_BALANCE = 1  WHERE ARTICLE = '32516028337712';
UPDATE ORDERS SET DISCONTE = 10, STATUS_OF_ORDER = ? WHERE ORDER_NUMBER = ?;
UPDATE PERSONS SET PERSON_NAME = , PHONE_NUMBER =  WHERE PERSON_ID =;
UPDATE ADDRESSES SET CONTRY =, REGION = , STREET = , HOUSE = , FLAT =  WHERE ADDRESS_ID = ; 


ALTER TABLE PERSONS ADD COLUMN phone_number varchar(100);

SELECT * FROM ORDERS ord 
    inner join PERSONS per on ord.CONTACT_PERSON = per.PERSON_ID
    inner join ADDRESSES adr on per.ADDRESS_TO_DELIVERY = adr.ADDRESS_ID
    WHERE ord.ORDER_NUMBER = '202061017430';

SELECT * FROM ORDER_POSITIONS ordPos
    inner join ITEMS item on ordPos.ITEM_ID = item.ARTICLE
    where ordPos.ORDER_ID = '202061017430';