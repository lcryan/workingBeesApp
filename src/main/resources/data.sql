INSERT INTO teams (id, team_name, working_space, team_size, extra_service)
VALUES (1, 'Creative Team', 'Tokyo Lounge', 12,
        'lunch catering'),
       (2, 'Marketing Team', 'Tokyo Conference', 45, 'catering breakfast');


INSERT INTO companies (id, company_name, team_name, company_details, payment_details)
VALUES (12, 'Pixar Inc.', 'Creative Design Team', 'Johnson Street 56, 896629 Calabasas, California',
        'American Bank, 873027 89830 9878');


UPDATE teams
SET company_id = 12
WHERE id = 1;