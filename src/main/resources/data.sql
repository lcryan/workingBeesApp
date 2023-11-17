INSERT INTO teams (id, team_name, company_Name, team_size)
VALUES (11, 'Monsters Inc. Design Team', 'Pixar Inc.', 10),
       (12, 'Elemental Marketing Team', 'Pixar Inc.', 25),
       (13, 'Toystory Animation Team', 'Pixar Inc.', 15),

       (76, 'Kafka On The Shore Publicist Team', 'Penguin Books', 25),
       (77, 'Harry Potter TDH Accountant Team', 'Penguin Books', 50),
       (78, 'Grown-Ups Advertising Team', 'Penguin Books', 10),

       (89, 'Avengers Production Team', 'Marvel Comics', 100),
       (90, 'Spiderman Accountant Team', 'Marvel Comics', 33),
       (91, 'Thor Love & Thunder Directors Team', 'Marvel Comics', 8),

       (79, 'Stranger Things Creative Team', 'Netflix', 35),
       (80, 'Robbie Williams Docu Team', 'Netflix', 43),
       (81, 'Derry Girls Planning Team', 'Netflix', 10),

       (93, 'Music Production Sia', 'Geffen Records', 20),
       (94, 'Live Concert NBT Planning Team', 'Geffen Records', 30);

INSERT
INTO working_spaces(id, space_name, company_name, space_type, space_capacity, price_per_room, duration,
                    start_date, end_date)
VALUES (22, 'Tokyo Valley', 'Pixar Inc.', 'open office and media', 15, 2000.00, '10 days',
        '2024-01-01', '2024-01-11'),
       (23, 'Helsinki Sea View', 'Pixar Inc.', 'conference room', 30, 2800.00, '14 days', '2024-03-04',
        '2024-03-18'),
       (24, 'Copenhagen Streets', 'Pixar Inc.', 'meeting room medium', 25, 1050.00, '7 days', '2024-05-09',
        '2024-05-16'),

       (25, 'Barcelona Sky', 'Penguin Books.', 'open office medium', 30, 750.00, '5 days', '2024-05-09', '2024-05-14'),
       (26, 'Lima Miraflores', 'Penguin Books', 'media presentation room', 65, 2500.00, '1 day', '2024-07-03',
        '2023-07-03'),
       (27, 'Berlin Beats', 'Penguin Books', 'open office small', 20, 4500.00, '30 days', '2023-11-20', '2023-12-20'),

       (15, 'Toronto Teal', 'Netflix', 'conference room', 40, 1800.00, '3 days', , '2024-06-29', '2024-07-02'),
       (16, 'New York Big Apple', 'Netflix', 'open office large', 40, 1200.00, '10 days', '2024- 10-10', '2024-10-20'),
       (17, 'Dublin Docks', 'Netflix', 'open office small', 15, 350.00, '2 days', '2024-05-02', '2024-04'),

       (18, 'Sydney Opera', 'Marvel Comics', 'conference hall large', 120, 2300.00, '1 day', '2024-08-08', '2024-08-08');

    INSERT
INTO file_uploads(id, file_name, content_type, url)
VALUES (11, 'Stockholm_PresentationRoom_CEOLevel .jpg', 'image/jpeg', 'http://localhost:8080/download/org.springframework.web.multipart.support.StandardMultipartHttpServletRequest$StandardMultipartFile@79e61b33');

INSERT INTO subscriptions (id, subscription_name, total_amount, company_name)
VALUES (45, 'Pixar Rental Agreement', 290.00, 'Pixar Inc.'),
       (55, 'Carbonyde Rental Agreement', 5600.00, 'Carbonyde GmbH');

INSERT INTO extra_services (id, extra_service_name, company_name, service_type, service_price, service_duration)
VALUES (88, 'Pixar Team Up!Lunch Buffet', 'Pixar Inc.', 'catering buffet', 3150.00, '7 days'),
       (123, 'Postal Service Pixar Marketing', 'Pixar Inc.', 'postal service', 150.00, '7 days'),
       (34, 'Carbonyde Corporate Dinner', 'Carbonyde', 'sit-down dinner', 800.00, '1 day');

INSERT INTO companies (id, company_name, company_details, payment_details)
VALUES (12, 'Pixar Inc.', 'Johnson Street 56, 896629 Calabasas, California', 'American Bank, 873027 89830 9878'),
       (15, 'Carbonyde GmbH', 'Am Münchener Dom 324, 67859 München', 'Deutsche Bank, 891520 987678');

UPDATE teams
SET team_working_space = 67
WHERE id = 2;

UPDATE teams
SET team_working_space = 99
WHERE id = 5;

UPDATE teams
SET company_id = 12
WHERE id = 5;

UPDATE teams
SET company_id = 12
WHERE id = 2;

UPDATE teams
SET company_id = 15
WHERE id = 2;

UPDATE companies
SET your_subscriptions = 45
WHERE id = 12;

UPDATE companies
SET your_subscriptions = 55
WHERE id = 15;

UPDATE extra_services
SET team_id = 2
WHERE id = 88;

UPDATE extra_services
SET team_id = 5
WHERE id = 123;

UPDATE working_spaces
SET subscription_id = 45
WHERE id = 67;

UPDATE working_spaces
SET subscription_id = 55
WHERE id = 99;

UPDATE working_spaces
SET file_id = 11
WHERE id = 99;

INSERT INTO roles (role_name)
VALUES ('ROLE_USER'),
       ('ROLE_ADMIN');