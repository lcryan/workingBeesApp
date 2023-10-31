INSERT INTO teams (id, team_name, team_size, extra_service)
VALUES (1, 'Pixar Inc. Creative Team', 12,
        'lunch catering'),
       (2, 'Pixar Inc. Marketing Team', 45, 'catering breakfast');

INSERT INTO subscriptions (id, price, duration, start_date, end_date, working_space_type)
VALUES (45, 290.00, '3 months', '12/1/2024', '12/4/2024', 'Conference room');


INSERT INTO companies (id, company_name, company_details, payment_details)
VALUES (12, 'Pixar Inc.', 'Johnson Street 56, 896629 Calabasas, California',
        'American Bank, 873027 89830 9878');

INSERT INTO working_spaces(id, space_name, space_type, space_capacity)
VALUES (67, 'Tokyo Valley', 'conference room', 100);

UPDATE teams
SET team_working_space = 67
WHERE id = 2;


UPDATE teams
SET company_id = 12
WHERE id = 1;

UPDATE teams
SET company_id = 12
WHERE id = 2;

UPDATE companies
SET your_subscriptions = 45
WHERE id = 12;

