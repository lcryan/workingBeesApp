INSERT INTO companies (id, company_name, team_name, company_details, payment_details, subscription);
VALUES (1002, 'Momoyama Inc.', 'Marketing team', 'Amsterdam 128, 5654HS Velveteen', 'Bankbook NL, 1927304790',  );

INSERT INTO subscriptions (id, price, duration, start_date, end_date, working_space_type)
VALUES (2230, 300.00, '30 days', )


UPDATE companies
SET subscription_overview = 2230;