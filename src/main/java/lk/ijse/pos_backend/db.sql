DROP DATABASE IF EXISTS pos_system;

create database if not  exists pos_system ;


USE pos_system;

CREATE TABLE customer (
                          id VARCHAR(255) PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          address VARCHAR(255) NOT NULL,
                          salary DOUBLE NOT NULL
);


CREATE TABLE item (
                      ItemId VARCHAR(255) PRIMARY KEY,
                      ItemName VARCHAR(255) NOT NULL,
                      ItemQty INT,
                      ItemPrice DECIMAL(10, 2)
);
