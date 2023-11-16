INSERT INTO teams (id, team_name, company_Name, team_size)
VALUES (5, 'Pixar Inc. Creative Team', 'Pixar Inc.', 12),
       (2, 'Carbonyde Marketing Team', 'Carbonyde', 45);

INSERT INTO working_spaces(id, space_name, company_name, space_type, space_capacity, price_per_room, duration,
                           start_date, end_date)
VALUES (67, 'Tokyo Valley', 'Pixar Inc.', 'conference room', 100, 1000.00, '3 months', '12/1/2024', '12/4/2024'),
       (99, 'Amsterdam Grachten', 'Carbonyde GmbH', 'meeting room', 30, 2000.00, '1 month', '1/3/2024', '1/4/2024');

INSERT INTO file_uploads(id, file_name, content_type, url)
VALUES (11, 'Stockholm_PresentationRoom_CEOLevel .jpg', 'image/jpeg',
        'http://localhost:8080/download/org.springframework.web.multipart.support.StandardMultipartHttpServletRequest$StandardMultipartFile@79e61b33');

INSERT INTO subscriptions (id, total_amount, company_name, working_space_type)
VALUES (45, 290.00, 'Pixar Inc.', 'Conference room'),
       (55, 5600.00, 'Carbonyde GmbH', 'Meeting room');

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