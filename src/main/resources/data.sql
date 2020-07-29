INSERT INTO book (id, title) VALUES
    (1, 'Clean Code: A Handbook of Agile Software Craftsmanship'),
    (2, 'Patterns of Enterprise Application Architecture'),
    (3, 'Refactoring: Improving the Design of Existing Code');

INSERT INTO author(id, name) VALUES
    (1, 'Martin Fowler'),
    (2, 'Uncle Bob');

INSERT INTO book_author(id_book, id_author) VALUES
    (1, 2),
    (2, 1),
    (3, 1);

INSERT INTO book_rented(id, id_book, rented_date) VALUES
    (1, 1, '2020-07-28 21:13:58.365');