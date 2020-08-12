SET NAMES 'UTF8';

//быстрая отчистка таблиц.!!! Не забудь, про наличие записей в Категориях и Департаментах (отделах) 
DELETE FROM department_fish_category_subcategories;
DELETE FROM fish_category_subcategories;
DELETE FROM fish_subcategories;
DELETE FROM fish_subcategories_fishes;
DELETE FROM fishes;

CREATE TABLE fish_categories (
    category_id int(11) NOT NULL AUTO_INCREMENT,
    cod_vdovkin int(3) NOT NULL,
    name varchar(150) CHARACTER SET utf8 NOT NULL,
    PRIMARY KEY (category_id)
    );

ALTER TABLE fish_categories DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
ALTER TABLE fish_categories CHANGE name name VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_general_ci;
DROP TABLE fish_categories;
INSERT INTO fish_categories (cod_vdovkin,name) VALUES (1,'Договора');
INSERT INTO fish_categories (cod_vdovkin,name) VALUES (2,'Доверенности');
INSERT INTO fish_categories (cod_vdovkin,name) VALUES (3,'Завещания');
INSERT INTO fish_categories (cod_vdovkin,name) VALUES (4,'Заявления');
INSERT INTO fish_categories (cod_vdovkin,name) VALUES (5,'Свидетельства');
INSERT INTO fish_categories (cod_vdovkin,name) VALUES (6,'Штампы');
INSERT INTO fish_categories (cod_vdovkin,name) VALUES (7,'Прочие');
INSERT INTO fish_categories (cod_vdovkin,name) VALUES (10,'Согласия');
INSERT INTO fish_categories (cod_vdovkin,name) VALUES (11,'Обязательства');
INSERT INTO fish_categories (cod_vdovkin,name) VALUES (12,'Отказы');
INSERT INTO fish_categories (cod_vdovkin,name) VALUES (13,'Наследство');
SELECT * FROM fish_categories;
DELETE FROM fish_categories;

CREATE TABLE fish_category_subcategories (
    id int(11) NOT NULL AUTO_INCREMENT,
    category_id int(11) NOT NULL,
    subcategory_id int(11) NOT NULL,
    PRIMARY KEY (id)
    );
DROP TABLE fish_category_subcategories;
SELECT * FROM fish_category_subcategories;
DELETE FROM fish_category_subcategories;
INSERT INTO fish_category_subcategories (category_id, subcategory_id) VALUES (1, 1);
SELECT MAX(id) FROM fish_category_subcategories;

CREATE TABLE fish_subcategories (
    subcategory_id int(11) NOT NULL AUTO_INCREMENT,
    cod_vdovkin int(3) NOT NULL,
    name varchar(150) CHARACTER SET utf8 NOT NULL,
    PRIMARY KEY (subcategory_id)
    );
DROP TABLE fish_subcategories;
INSERT INTO fish_subcategories (cod_vdovkin, name) VALUES ();
SELECT * FROM fish_subcategories;
SELECT MAX(subcategory_id) FROM fish_subcategories;
DELETE FROM fish_subcategories;
SELECT * FROM fish_subcategories subCategory
    inner join fish_category_subcategories subCategoryOfCategory on subCategoryOfCategory.subcategory_id = subCategory.subcategory_id
    inner join fish_categories category on category.category_id = subCategoryOfCategory.category_id
    inner join department_fish_category_subcategories dfcs on dfcs.category_subcategories_id = subCategoryOfCategory.id
    inner join departments department on department.id = dfcs.department_id
    WHERE category.category_id = 13 AND department.name = 'НК2';

CREATE TABLE department_fish_category_subcategories (
    id int(11) NOT NULL AUTO_INCREMENT,
    category_subcategories_id int(11) NOT NULL,
    department_id int(11) NOT NULL,
    PRIMARY KEY (id)
    );
INSERT INTO department_fish_category_subcategories (category_subcategories_id, department_id) VALUES (?, ?);
SELECT * FROM department_fish_category_subcategories;
DROP TABLE department_fish_category_subcategories;

CREATE TABLE departments(
    id int(11) NOT NULL AUTO_INCREMENT,
    name varchar(150) CHARACTER SET utf8 NOT NULL,
    PRIMARY KEY (id)
    );
INSERT INTO departments (name) VALUES ('НК2');
SELECT * FROM departments dep WHERE dep.name = 'НК2';
DROP TABLE departments;

CREATE TABLE fish_subcategories_fishes (
    id int(11) NOT NULL AUTO_INCREMENT,
    subcategory_id int(11) NOT NULL,
    fish_id int(11) NOT NULL,
    PRIMARY KEY (id)
    );
INSERT INTO fish_subcategories_fishes (subcategory_id, fish_id) VALUES (1, 1);
SELECT * FROM fish_subcategories_fishes;
DROP TABLE fish_subcategories_fishes;

CREATE TABLE fishes(
    fish_id int(11) NOT NULL AUTO_INCREMENT,
    name varchar(150) CHARACTER SET utf8 NOT NULL,
    code int(6),
    code_atribute int(2),
    body MEDIUMTEXT CHARACTER SET utf8 NOT NULL,
    PRIMARY KEY (fish_id)
    );
INSERT INTO fishes (name, body) VALUES ('','');
DROP TABLE fishes;
SELECT * FROM fishes fishes
    inner join fish_subcategories_fishes fsf on fishes.fish_id = fsf.fish_id
    inner join fish_subcategories fs on fs.subcategory_id = fsf.subcategory_id
    WHERE fs.subcategory_id = ;


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