INSERT INTO companies (id, company_name, team_name, company_details, payment_details)
VALUES (1002, 'Momma Inc.', 'Marketing team', 'Amsterdam 128, 5654HS Velveteen', 'Bankbook NL, 1927304790');

INSERT INTO subscriptions (id, price, duration, start_date, end_date, working_space_type)
VALUES (2230, 300.00, '30 days', '2023/10/22', '2023/11/22', 'meeting room');

/*UPDATE subscriptions
SET company_id = 1002
WHERE id = 2230;*/

UPDATE companies
SET subscription_overview = 2230
WHERE id = 1002; /*functional - but no overview of the total subscription with all data etc. still in progress*/