-- valid data to initialize database

-- address type
INSERT INTO address_type(type)
    VALUES ('home');
INSERT INTO address_type(type)
    VALUES ('work');

-- address
INSERT INTO address(address_type_id, city, country, post_code, street, street_number)
    VALUES (1, 'Gdansk', 'Poland', '80-180', 'Zielona', '12a');
INSERT INTO address(address_type_id, city, country, post_code, street, street_number)
    VALUES (1, 'Gdynia', 'Poland', '10-526', 'Czerwona', '2');
INSERT INTO address(address_type_id, city, country, post_code, street, street_number)
    VALUES (2, 'Warsaw', 'Poland', '80-123', 'Gorzysta', '5b/11');
INSERT INTO address(address_type_id, city, country, post_code, street, street_number)
    VALUES (1, 'Sopot', 'Poland', '96-662', 'Zachodnia', '22');

-- client
INSERT INTO client(birth_date, first_name, gender, last_name, address_id)
    VALUES ('1999-10-16', 'Pawel', 'MALE', 'Nowak', 1);
INSERT INTO client(birth_date, first_name, gender, last_name, address_id)
    VALUES ('1956-06-26', 'Asia', 'FEMALE', 'Kowalska', 2);
INSERT INTO client(birth_date, first_name, gender, last_name, address_id)
    VALUES ('1986-01-06', 'Ania', 'FEMALE', 'Graniasta', 3);
INSERT INTO client(birth_date, first_name, gender, last_name, address_id)
    VALUES ('1975-12-01', 'Mariusz', 'MALE', 'Hanecki', 4);

-- policy
INSERT INTO policy(conclusion_date, ins_begin_date, ins_end_date, total_premium, client_id)
    VALUES ('2022-12-18', '2023-01-01', '2023-12-31', 3200, 1);
INSERT INTO policy(conclusion_date, ins_begin_date, ins_end_date, total_premium, client_id)
    VALUES ('2022-12-18', '2023-01-01', '2023-12-31', 8141.25, 2);
INSERT INTO policy(conclusion_date, ins_begin_date, ins_end_date, total_premium, client_id)
    VALUES ('2020-06-12', '2021-01-01', '2023-12-31', 8141.25, 3);
INSERT INTO policy(conclusion_date, ins_begin_date, ins_end_date, total_premium, client_id)
    VALUES ('2021-01-01', '2021-02-01', '2021-12-31', 8141.25, 4);

-- car
INSERT INTO car(insured_value, make, production_year, registration_number, type, policy_id)
    VALUES (1000, 'audi', '2016', 'GD001', 'sedan', 1);
INSERT INTO car(insured_value, make, production_year, registration_number, type, policy_id)
    VALUES (1275, 'bmw', '1996', 'GD002', 'sedan', 2);
INSERT INTO car(insured_value, make, production_year, registration_number, type, policy_id)
    VALUES (1800, 'porsche', '2020', 'GD003', 'suv', 3);
INSERT INTO car(insured_value, make, production_year, registration_number, type, policy_id)
    VALUES (600, 'hyundai', '2005', 'GD004', 'coupe', 4);

-- cover
INSERT INTO cover(description, premium, type, car_id)
    VALUES ('OC insurance is a basic third-party liability insurance. It is mandatory for all vehicle owners.',
        1050, 'OC', 1);
INSERT INTO cover(description, premium, type, car_id)
    VALUES ('OC insurance is a basic third-party liability insurance. It is mandatory for all vehicle owners.',
        1338.75, 'OC', 2);
INSERT INTO cover(description, premium, type, car_id)
    VALUES ('OC insurance is a basic third-party liability insurance. It is mandatory for all vehicle owners.',
        1890, 'OC', 3);
INSERT INTO cover(description, premium, type, car_id)
    VALUES ('OC insurance is a basic third-party liability insurance. It is mandatory for all vehicle owners.',
        630, 'OC', 4);
INSERT INTO cover(description, premium, type, car_id)
    VALUES ('AC insurance in the economic version will protect you against the financial consequences of: collision, theft and vandalism.',
        950, 'AC', 1);
INSERT INTO cover(description, premium, type, car_id)
    VALUES ('NNW is a personal accident insurance that protects health and life of the driver and passengers.',
        1402.5, 'NWW', 2);
INSERT INTO cover(description, premium, type, car_id)
    VALUES ('Provide yourself with support in case of unexpected situations, including dead car battery, breakdown or fuel failure when en route.',
        2160, 'ASSISTANCE', 3);
INSERT INTO cover(description, premium, type, car_id)
    VALUES ('Provide yourself with support in case of unexpected situations, including dead car battery, breakdown or fuel failure when en route.',
        720, 'ASSISTANCE', 4);
INSERT INTO cover(description, premium, type, car_id)
    VALUES ('Provide yourself with support in case of unexpected situations, including dead car battery, breakdown or fuel failure when en route.',
        1200, 'ASSISTANCE', 1);
