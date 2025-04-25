-- V2__init_values.sql
-- Flyway migration: criação de registros para as tabelas sales, vehicles e cart

set search_path = "public";

-- VEÍCULOS
INSERT INTO vehicles (id, year, base_price, color, model, available)
VALUES ('2c0e9a6e-79d3-42e2-bfe1-d146132b5150', 2020, 40000.00, 'WHITE', 'Gol G6', true),
       ('fe0876e2-0c45-4e55-8fd3-8b62f57a8081', 2022, 55000.00, 'BLACK', 'Onix LTZ', false),
       ('cd4f2df4-dba9-49df-a03e-7d89a5b83c4d', 2021, 48000.00, 'SILVER', 'HB20S', false);

-- CARRINHO ATIVO PARA CLIENTE carlosc
INSERT INTO cart (id, vehicle_id, client_id, created_at)
VALUES ('b8b6d38d-9135-4e4e-b72c-9187ff119a41', '2c0e9a6e-79d3-42e2-bfe1-d146132b5150',
        'c4411217-2d30-4a3d-ba37-99558990c098', now());

-- VENDA ANTERIOR: cliente robertos, vendedor fcarla
INSERT INTO sales (id, type, client_id, seller_id, vehicle_id, date, final_price)
VALUES ('a56a1227-8df5-426b-9301-3a8802a2b11f', 'FISICA', 'd7555c27-342a-4905-a9f0-654444fcc3bc',
        '5de81bf9-05ff-43e8-8e89-153f7ef6f17f', 'fe0876e2-0c45-4e55-8fd3-8b62f57a8081', now() - interval '3 days',
        53900.00);

-- VENDA ONLINE FINALIZADA
INSERT INTO sales (id, type, client_id, seller_id, vehicle_id, date, final_price)
VALUES ('25db49e5-bf31-4635-a066-6805b6302289', 'ONLINE', 'c4411217-2d30-4a3d-ba37-99558990c098', null,
        'cd4f2df4-dba9-49df-a03e-7d89a5b83c4d', now() - interval '1 day', 55440.00);

