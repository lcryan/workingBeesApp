INSERT INTO companies (id, company_name, team_name, company_details, payment_details)
VALUES (1002, 'Momma Inc.', 'Marketing team', 'Amsterdam 128, 5654HS Velveteen', 'Bankbook NL, 1927304790'),
       (1003, 'Pixar Inc.', 'Creative Design Team', 'Johnson Street 56, 896629 Calabasas, California',
        'American Bank, 873027 89830 9878'),
       (1004, 'Netflix Com.', 'Production Team', 'La Rue Chappelle 78, 78056 Paris, France', 'French Bank 9782309243');

INSERT INTO subscriptions (id, price, duration, start_date, end_date, working_space_type)
VALUES (2230, 300.00, '30 days', '2023/10/22', '2023/11/22', 'meeting room');

INSERT INTO teams(id, working_space, team_size, extra_service)
VALUES (4456, 'Budapest Lounge', 12, 'catering lunch'),
       (6678, 'Tokyo Conference', 45, 'catering breakfast'),
       (9908, 'Los Angeles Meeting Room CEO', 10, 'no services required');

UPDATE teams
SET company_id = 1002
WHERE id = 4456;
UPDATE teams
SET company_id = 1002
WHERE id = 6678;
UPDATE teams
SET company_id = 1002
WHERE id = 9908;


UPDATE companies
SET subscription_overview = 2230
WHERE id = 1002; /*functional - but no overview of the total subscription with all data etc. still in progress - connection in db works though*/