-- address type
INSERT INTO address_type(id, type)
    VALUES (1, 'home');
INSERT INTO address_type(id, type)
    VALUES (2, 'work');

-- address
INSERT INTO address(address_type_id, city, country, post_code, street, street_number)
    VALUES (1, 'Gdansk', 'Poland', '80-180', 'Zielona', '12a');
INSERT INTO address(address_type_id, city, country, post_code, street, street_number)
    VALUES (1, 'Gdynia', 'Poland', '10-526', 'Czerwona', '2');
INSERT INTO address(address_type_id, city, country, post_code, street, street_number)
    VALUES (2, 'Sopot', 'Poland', '96-662', 'Zachodnia', '22');