-- Banks
INSERT INTO cleverbank.bank (name, bank_code)
VALUES ('Clever Bank', 'CLV');

INSERT INTO cleverbank.bank (name, bank_code)
VALUES ('Sber Bank', 'SBR');

INSERT INTO cleverbank.bank (name, bank_code)
VALUES ('Alfa-Bank', 'ALF');

INSERT INTO cleverbank.bank (name, bank_code)
VALUES ('VTB', 'VTB');

INSERT INTO cleverbank.bank (name, bank_code)
VALUES ('Tinkoff', 'TCS');

-- Users
INSERT INTO cleverbank.user (first_name, last_name, phone_number, email, password)
VALUES
    ('Anna', 'Smirnova', '+71234567890', 'anna.smirnova@email.com', 'password123'),
    ('Ivan', 'Ivanov', '+71234567891', 'ivan.ivanov@email.com', 'password124'),
    ('Sergey', 'Petrov', '+71234567892', 'sergey.petrov@email.com', 'password125'),
    ('Maria', 'Popova', '+71234567893', 'maria.popova@email.com', 'password126'),
    ('Alexey', 'Vasilyev', '+71234567894', 'alexey.vasilyev@email.com', 'password127'),
    ('Tatyana', 'Mikhailova', '+71234567895', 'tatyana.mikhailova@email.com', 'password128'),
    ('Dmitriy', 'Novikov', '+71234567896', 'dmitry.novikov@email.com', 'password129'),
    ('Elena', 'Morozova', '+71234567897', 'elena.morozova@email.com', 'password130'),
    ('Artem', 'Volkov', '+71234567898', 'artem.volkov@email.com', 'password131'),
    ('Olga', 'Pavlova', '+71234567899', 'olga.pavlova@email.com', 'password132'),
    ('Nikita', 'Romanov', '+71234567900', 'nikita.romanov@email.com', 'password133'),
    ('Svetlana', 'Osipova', '+71234567901', 'svetlana.osipova@email.com', 'password134'),
    ('Andrey', 'Belyaev', '+71234567902', 'andrey.belyaev@email.com', 'password135'),
    ('Yulia', 'Zakharova', '+71234567903', 'yulia.zakharova@email.com', 'password136'),
    ('Denis', 'Fedorov', '+71234567904', 'denis.fedorov@email.com', 'password137'),
    ('Polina', 'Bogdanova', '+71234567905', 'polina.bogdanova@email.com', 'password138'),
    ('Kirill', 'Sergeev', '+71234567906', 'kirill.sergeev@email.com', 'password139'),
    ('Marina', 'Melnikova', '+71234567907', 'marina.melnikova@email.com', 'password140'),
    ('Stepan', 'Egorov', '+71234567908', 'stepan.egorov@email.com', 'password141'),
    ('Natalya', 'Shubina', '+71234567909', 'natalya.shubina@email.com', 'password142');

-- Accounts
INSERT INTO cleverbank.account (number, balance, bank_id, user_id)
VALUES
    ('1000', 5000.00, 50, 80),
    ('1001', 3000.00, 50, 81),
    ('1002', 7000.00, 50, 82),
    ('1003', 1500.00, 51, 80),
    ('1004', 9000.00, 52, 80),
    ('1005', 4200.00, 53, 83),
    ('1006', 800.00, 54, 84),
    ('1007', 6500.00, 51, 85),
    ('1008', 2700.00, 50, 89),
    ('1009', 3200.00, 54, 90),
    ('1010', 1200.00, 51, 86),
    ('1011', 5600.00, 52, 87),
    ('1012', 9300.00, 53, 88),
    ('1013', 4100.00, 54, 91),
    ('1014', 7500.00, 50, 92),
    ('1015', 6200.00, 51, 93),
    ('1016', 1800.00, 52, 94),
    ('1017', 4900.00, 53, 95),
    ('1018', 8800.00, 54, 96),
    ('1019', 3700.00, 50, 97),
    ('1020', 4200.00, 51, 98),
    ('1021', 3200.00, 52, 99),
    ('1022', 5600.00, 53, 86),
    ('1023', 7500.00, 54, 88),
    ('1024', 9300.00, 50, 95),
    ('1025', 1500.00, 51, 87),
    ('1026', 6500.00, 52, 80),
    ('1027', 1200.00, 53, 81),
    ('1028', 800.00, 54, 96),
    ('1029', 2700.00, 50, 97),
    ('1030', 6200.00, 51, 99),
    ('1031', 1800.00, 52, 98),
    ('1032', 4900.00, 53, 99),
    ('1033', 8800.00, 54, 81),
    ('1034', 3700.00, 50, 81),
    ('1035', 4200.00, 51, 82),
    ('1036', 3200.00, 52, 86),
    ('1037', 5600.00, 53, 83),
    ('1038', 7500.00, 54, 84),
    ('1039', 9300.00, 50, 85);
