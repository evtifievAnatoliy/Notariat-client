SET NAMES 'UTF8';

CREATE TABLE CATEGORY_FISHES (
    id int(11) NOT NULL AUTO_INCREMENT,
    cod_vdovkin int(3) NOT NULL,
    name varchar(150) NOT NULL,
    PRIMARY KEY (id)
    );

DROP TABLE CATEGORY_FISHES;

INSERT INTO CATEGORY_FISHES (cod_vdovkin,name) VALUES (1,'Договоры');
SELECT * FROM CATEGORY_FISHES;
DELETE FROM CATEGORY_FISHES;

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