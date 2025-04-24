-- V2__init_users.sql
-- Flyway migration: criação de registros para usuários

INSERT INTO users (id, cpf, name, username, password, role)
VALUES
-- admin | topsecret#Pth
('c7c845ec-f75f-40b3-9980-cd82be98f72c',
 'i9SK2NutQbVv0WH6diRQG5ETM0XIYeYWPj3uaVDqFmUFi7hqslo/',
 'j/Iv3ytWsRd+Wrq5uuCw9snhZ3d7cGVhOw7914rIDacodq7U6W6vHQ==',
 'admin',
 '$2b$12$NDYvHNNVUB3gjFq/W4g49edKLYDM701W1ud8FrdjVa4fsqdpkh0mK',
 'VENDOR'),

-- carlosc | olrac
('c4411217-2d30-4a3d-ba37-99558990c098',
 'AxE8R+t0SUJCScM7O60+lYMchyW72Wp4nxtwx9TnM5UYuCZjtUcZ',
 'MwOuHL++g2zNmhpS2yMzP1vSLGDSqFVDJpCrq2sOkEPx4jj2',
 'carlosc',
 '$2b$12$2.iCycR1UB6e9aNDw685f.wF9NWRPVX5UoAqYKvacshy62j684zUi',
 'CLIENT'),

-- robertos S | bob123
('d7555c27-342a-4905-a9f0-654444fcc3bc',
 'e01ETgtTZb7b9IoLXlSVIEzGz8UBDyoBWbgWUS1KDMwIgVERCRKG',
 'X/KrBMbGhx1sg+4xttdI1ThDRhCLg64t7shfI0iYYlrnN970pA==',
 'robertos',
 '$2b$12$j0aBbsxt792e7bWcjlruteHpOW9cOBaT1zAkOWqwT4BhMXwMl4fhy',
 'CLIENT'),

-- fcarla | alrac
('5de81bf9-05ff-43e8-8e89-153f7ef6f17f',
 'O4n3zcxZeKw06F+SlFkVgzQHirbWQPcHbuOtkf1Wv0IUM0ucSpmb',
 'xK1ZmDD2sFgHC/POtgDpIaULOchesvn9Ud92+RndYiOkgt0=',
 'fcarla',
 '$2b$12$4Gf8.V9nrQLtAEGYCdXPDuKHB1MaIqBJ7w0hhrZYiBKgIMCJmew.2',
 'VENDOR');
