DROP TABLE IF EXISTS book;
CREATE TABLE book (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL
);

DROP TABLE IF EXISTS author;
CREATE TABLE author (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

DROP TABLE IF EXISTS book_author;
CREATE TABLE book_author (
    id_book INT,
    id_author INT
);

DROP TABLE IF EXISTS book_rented;
CREATE TABLE book_rented (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_book INT NOT NULL,
    rented_date TIMESTAMP,
    returned_date TIMESTAMP
);

DROP TABLE if EXISTS user;
CREATE TABLE user (
    id_user INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    roles VARCHAR(255)
);
