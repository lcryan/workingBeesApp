INSERT INTO teams (id, team_name, company_Name, team_size)
/*teams Pixar Inc.*/
VALUES (11, 'Monsters Inc. Design Team', 'Pixar Inc.', 10),
       (12, 'Elemental Marketing Team', 'Pixar Inc.', 25),
       (13, 'Toystory Animation Team', 'Pixar Inc.', 15),
/*teams Penguin Books*/
       (76, 'Kafka On The Shore Publicist Team', 'Penguin Books', 25),
       (77, 'Harry Potter TDH Accountant Team', 'Penguin Books', 50),
       (78, 'Grown-Ups Advertising Team', 'Penguin Books', 10),
/*teams Marvel Comics*/
       (89, 'Avengers Production Team', 'Marvel Comics', 100),
       (90, 'Spiderman Accountant Team', 'Marvel Comics', 33),
       (91, 'Thor Love & Thunder Directors Team', 'Marvel Comics', 8),
/*teams Netflix*/
       (79, 'Stranger Things Creative Team', 'Netflix', 35),
       (80, 'Robbie Williams Docu Team', 'Netflix', 43),
       (81, 'Derry Girls Planning Team', 'Netflix', 10),
/*teams Geffen Records*/
       (93, 'Music Production Sia', 'Geffen Records', 20),
       (94, 'Live Concert NBT Planning Team', 'Geffen Records', 30);

INSERT
INTO working_spaces(id, space_name, company_name, space_type, space_capacity, price_per_room, duration, is_available,
                    start_date, end_date)

/*working spaces for Pixar Inc.*/
VALUES (22, 'Tokyo Valley', 'Pixar Inc.', 'open office and media', 15, 2000.00, '10 days', true, '2024-01-01',
        '2024-01-11'),
       (23, 'Helsinki Sea View', 'Pixar Inc.', 'conference room', 30, 2800.00, '14 days', true, '2024-03-04',
        '2024-03-18'),
       (24, 'Copenhagen Streets', 'Pixar Inc.', 'meeting room medium', 25, 1050.00, '7 days', true, '2024-05-09',
        '2024-05-16'),
/*working spaces for Penguin Books*/
       (25, 'Barcelona Sky', 'Penguin Books.', 'open office medium', 30, 750.00, '5 days', true, '2024-05-09',
        '2024-05-14'),
       (26, 'Lima Miraflores', 'Penguin Books', 'media presentation room', 65, 2500.00, '1 day', true, '2024-07-03',
        '2023-07-03'),
       (27, 'Berlin Beats', 'Penguin Books', 'open office small', 20, 4500.00, '30 days', true, '2023-11-20',
        '2023-12-20'),
/*working spaces for Netflix*/
       (15, 'Toronto Teal', 'Netflix', 'conference room', 40, 1800.00, '3 days', true, '2024-06-29', '2024-07-02'),
       (16, 'New York Big Apple', 'Netflix', 'open office large', 40, 1200.00, '10 days', true, '2024-10-10',
        '2024-10-20'),
       (17, 'Dublin Docks', 'Netflix', 'open office small', 15, 350.00, '2 days', true, '2024-05-02', '2024-05-4'),
/*working spaces for Marvel Comics*/
       (18, 'Sydney Opera', 'Marvel Comics', 'conference hall large', 120, 2300.00, '1 day', true, '2024-08-08',
        '2024-08-08'),
       (19, 'Madrid Milagros', 'Marvel Comics', 'open office medium', 35, 1500.00, '10 days', true, '2024-02-10',
        '2024-02-20'),
       (20, 'Chengdu Station', 'Marvel Comics', 'office small', 10, 1050.00, '7 days', true, '2024-01-18',
        '2024-01-25'),
/*working spaces for Geffen Records*/
       (21, 'San Fransisco City', 'Geffen Records', 'open office medium', 30, 2100.00, '14 days', true, '2024-03-10',
        '2023-03-24'),
       (33, 'California Sea', 'Geffen Records', 'open office large', 40, 1400.00, '7 days', true, '2024-03-10',
        '2023-03-17');

INSERT
INTO file_uploads(id, file_name, content_type, url)
VALUES (110, 'Copenhagen_Streets .jpg', 'image/jpeg',
        'http://localhost:8080/download/org.springframework.web.multipart.support.StandardMultipartHttpServletRequest$StandardMultipartFile@79e61b33'),
       (111, 'Helsinki_Sea_View.jpg', 'image/jpg', 'http://localhost:8080/download/Helsinki_Sea_View.jpg'),
       (112, 'Tokyo_Valley.jpg', 'image/jpg', 'http://localhost:8080/download/Tokyo_Valley.jpg'),
       (113, 'Berlin_Beats.jpg', 'image/jpg', 'http://localhost:8080/download/Berlin_Beats.jpg'),
       (114, 'Lima_Miraflores.jpeg', 'image/jpg', 'http://localhost:8080/download/Lima_Miraflores.jpeg'),
       (115, 'Barcelona_Sky_jpg', 'image/jpg', 'http://localhost:8080/download/Barcelona_Sky_jpg'),
       (116, 'Toronto_Teal.jpg', 'image/jpg', 'http://localhost:8080/download/Toronto_Teal.jpg'),
       (117, 'New_York_Big_Apple.jpg', 'image/jpg', 'http://localhost:8080/download/New_York_Big_Apple.jpg'),
       (118, 'Dublin_Docks.jpg', 'image/jpg', 'http://localhost:8080/download/Dublin_Docks.jpg'),
       (119, 'Sydney_Opera.jpg', 'image/jpg', 'http://localhost:8080/download/Sydney_Opera.jpg'),
       (120, 'Madrid_Milagros.jpg', 'image/jpg', 'http://localhost:8080/download/Madrid_Milagros.jpg'),
       (121, 'Chengdu_Station.jpg', 'image/jpg', 'http://localhost:8080/download/Chengdu_Station.jpg'),
       (122, 'San_Fransisco_City.jpg', 'image/jpg', 'http://localhost:8080/download/San_Fransisco_City.jpg'),
       (123, 'California_Sea.jpg', 'image/jpg', 'http://localhost:8080/download/California_Sea.jpg');


INSERT
INTO subscriptions (id, subscription_name, total_amount, company_name)
VALUES (41, 'Pixar Rental Agreement', 5850.00, 'Pixar Inc.'),
       (42, 'Penguin Books Rental Agreement', 7750.00, 'Penguin Books'),
       (43, 'Netflix Rental Agreement', 3350.00, 'Netflix'),
       (44, 'Marvel Comics Rental Agreement', 4850.00, 'Marvel Comics'),
       (45, 'Geffen Records Rental Agreement', 3500.00,
        'Geffen Records'); /*still have to add up total amount extra services here!!!*/

INSERT INTO extra_services (id, extra_service_name, company_name, service_type, service_price, service_duration)
/*Pixar extra services*/
VALUES (31, 'Pixar Monsters Team Breakfast', 'Pixar Inc.', 'breakfast buffet', 1000.00, '10 days'),
       (32, 'Pixar Elemental Team Lunch', 'Pixar Inc.', 'sit-down lunch', 550.00, '2 days'),
       (33, 'Pixar Toy Story Team Postal Service', 'Pixar Inc', 'postal delivery', 250.00, '7 days'),
/*Penguin Books extra services*/
       (34, 'Kafka Team Dinner', 'Penguin Books', 'sit-down dinner', 1850.00, '1 day'),
       (35, 'Harry Potter Team Coffee & Tea', 'Penguin Books', 'coffee/tea service', 250.00, '1 day'),
       (36, 'Grown-Ups Team Coffee & Tea', 'Penguin Books', 'coffee/tea daily service', 1500.00, '30 days'),
/*Netflix extra services*/
       (37, 'Stranger Things Team Coffee & Tea', 'Netflix', 'coffee/tea daily service', 525.00, '3 days'),
       (38, 'Stranger Things Team Lunch', 'Netflix', 'lunch buffet', 2.400, '3 days'),
       (39, 'Robbie Williams Docu Team Teatime', 'Netflix', 'high tea', 4000.00, '10 days'),
       (40, 'Derry Girls Team Lunch', 'Netflix', 'lunch buffet', 585.00, '2 days'),
/*Marvel Comics extra services*/
       (41, 'Avengers Team Breakfast', 'Marvel Comics', 'breakfast buffet', 1300.00, '1 day'),
       (42, 'Spiderman Team Coffe & Tea', 'Marvel Comics', 'coffee/tea daily service', 2640.50, '10 days'),
       (43, 'Thor Team Post Delivery', 'Marvel Comics', 'postal service incl. FedEx Express', 350.00, '7 days'),
/*Geffen Records extra services*/
       (44, 'Sia Team Dinner', 'Geffen Records', 'sit-down dinner', 1200.00, '3 days'),
       (45, 'NBT Team Breakfast', 'Geffen Records', 'breakfast buffet', 1680.00, '7 days');


INSERT
INTO companies (id, company_name, company_details, payment_details)
VALUES (11, 'Pixar Inc.', '123 Animation Lane Emeryville, CA 94608 United States',
        'Creative Bank Account Number: 123-456-7890'),
       (76, 'Penguin Books', '456 Literary Avenue Booktown, NY 10001 United States',
        'Literary National Bank Account Number: 987-654-3210'),
       (89, 'Marvel Comics', '321 Superhero Plaza New York, NY 10001 United States',
        ' Super Savings & Trust Account Number: 789-012-3456'),
       (79, 'Netflix', '567 Streamer Boulevard Los Gatos, CA 95032 United States',
        ' Streamline Financial Account Number: 234-567-8901'),
       (93, 'Geffen Records', '789 Melody Street Los Angeles, CA 90064 United States',
        'Harmony Bank Account Number: 456-789-0123');

UPDATE teams
SET team_working_space =
        CASE
            WHEN id = 11 THEN 22
            WHEN id = 12 THEN 23
            WHEN id = 13 THEN 24

            WHEN id = 76 THEN 25
            WHEN id = 77 THEN 26
            WHEN id = 78 THEN 27

            WHEN id = 89 THEN 18
            WHEN id = 90 THEN 19
            WHEN id = 91 THEN 20

            WHEN id = 79 THEN 15
            WHEN id = 80 THEN 16
            WHEN id = 81 THEN 17

            WHEN id = 93 THEN 21
            WHEN id = 94 THEN 33
            END;


UPDATE teams
SET company_id =
        CASE
            WHEN id = 11 THEN 11
            WHEN id = 12 THEN 11
            WHEN id = 13 THEN 11

            WHEN id = 76 THEN 76
            WHEN id = 77 THEN 76
            WHEN id = 78 THEN 76

            WHEN id = 89 THEN 89
            WHEN id = 90 THEN 89
            WHEN id = 91 THEN 89

            WHEN id = 79 THEN 79
            WHEN id = 80 THEN 79
            WHEN id = 81 THEN 79

            WHEN id = 93 THEN 93
            WHEN id = 94 THEN 93
            END;

UPDATE companies
SET your_subscription =
        CASE
            WHEN id = 11 THEN 41
            WHEN id = 76 THEN 42
            WHEN id = 89 THEN 43
            WHEN id = 79 THEN 44
            WHEN id = 93 THEN 45
            END;

UPDATE extra_services
SET team_id =
        CASE
            WHEN id = 31 THEN 11
            WHEN id = 32 THEN 12
            WHEN id = 33 THEN 13

            WHEN id = 34 THEN 76
            WHEN id = 35 THEN 77
            WHEN id = 36 THEN 78

            WHEN id = 41 THEN 89
            WHEN id = 42 THEN 90
            WHEN id = 43 THEN 91

            WHEN id = 37 THEN 79
            WHEN id = 38 THEN 79
            WHEN id = 39 THEN 80
            WHEN id = 40 THEN 81

            WHEN id = 44 THEN 93
            WHEN id = 45 THEN 94
            END;

UPDATE working_spaces
SET subscription_id =
        CASE
            WHEN id = 22 THEN 41
            WHEN id = 23 THEN 41
            WHEN id = 24 THEN 41

            WHEN id = 25 THEN 42
            WHEN id = 26 THEN 42
            WHEN id = 27 THEN 42

            WHEN id = 15 THEN 43
            WHEN id = 16 THEN 43
            WHEN id = 17 THEN 43

            WHEN id = 18 THEN 44
            WHEN id = 19 THEN 44
            WHEN id = 20 THEN 44

            WHEN id = 21 THEN 45
            WHEN id = 33 THEN 45
            END;

UPDATE working_spaces
SET file_id =
        CASE
            WHEN id = 22 THEN 112
            WHEN id = 23 THEN 111
            WHEN id = 24 THEN 110

            WHEN id = 25 THEN 115
            WHEN id = 26 THEN 114
            WHEN id = 27 THEN 113

            WHEN id = 15 THEN 116
            WHEN id = 16 THEN 117
            WHEN id = 17 THEN 118

            WHEN id = 18 THEN 119
            WHEN id = 19 THEN 120
            WHEN id = 20 THEN 121

            WHEN id = 21 THEN 122
            WHEN id = 33 THEN 123
            END;

INSERT INTO users (username, password)
VALUES ('JimMo', '$2a$12$PIEjrWCwq05jOe6ddQO5T.m5mIuJc8UCEdBpytWH8pmVYHoM5txfG'),
       ('MarkusD', '$2a$12$61dCmUQ1wYB32VMVsp0j0u5XgLRlB/nIEonSoplejxj0641Hpp8Ba'),
       ('StanTheOne', '$2a$12$TmxPe2g330H3HoZ1qSJ1ueiPh8e5Mt3IRaCJn1aCxkQCywjVrNyuC'),
       ('Min-Young', '$2a$12$H0z0gWRvmSA2ZcESVJ0FteGp.4.d9xW9/YRL.67rBFL3vYeHgzvmG'),
       ('RachelLe', '$2a$12$NTjAFfUNYSJjoPCZw6o3DuPs9u6ZV7V9sEsDO3wERZibFbtSYK8d2'),
       ('LRyan', '$2a$12$/tHNCW4PO3LXooK8DCZ7WuO0pRnz3.w9oXadhVKerWnJ4axVp/0la');


INSERT INTO roles (role_name)
VALUES ('ROLE_USER'),
       ('ROLE_ADMIN');

INSERT INTO users_role_list (role_list_role_name, user_list_username)
VALUES ('ROLE_USER', 'JimMo'),
       ('ROLE_USER', 'MarkusD'),
       ('ROLE_USER', 'StanTheOne'),
       ('ROLE_USER', 'Min-Young'),
       ('ROLE_USER', 'RachelLe'),
       ('ROLE_ADMIN', 'LRyan');


INSERT INTO accounts(id, company_name, email, first_name, last_name, user_username)
VALUES (45, 'Pixar Inc.', 'jimmorris@pixar.com', 'Jim', 'Morris', 'JimMo'),
       (46, 'Penguin Books', 'markus.dohle@penguin-books.com', 'Markus', 'Dohle', 'MarkusD'),
       (47, 'Marvel Comics', 'stanlee@marvel-comics.com', 'Stan', 'Lee', 'StanTheOne'),
       (48, 'Netflix', 'min-young-kim@netflix.com', 'Min-Young', 'Kim', 'Min-Young'),
       (49, 'Geffen Records', 'rachelLevy@geffen-records.com', 'Rachel', 'Levy', 'RachelLe'),
       (50, 'WorkingBeesHub', 'luciaRyan@workingBeesHub.com', 'Lucia', 'Ryan', 'LRyan');






