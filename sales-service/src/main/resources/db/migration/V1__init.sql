-- V1__init.sql
-- Flyway migration: criação inicial do schema criação das tabelas vehicles, cart e sales

CREATE
EXTENSION IF NOT EXISTS unaccent;

CREATE TABLE IF NOT EXISTS vehicles
(
    id         UUID PRIMARY KEY,
    year       INT            NOT NULL,
    base_price NUMERIC(10, 2) NOT NULL,
    color      VARCHAR(10)    NOT NULL CHECK (color IN ('WHITE', 'SILVER', 'BLACK')),
    model      VARCHAR(255)   NOT NULL,
    available  BOOLEAN        NOT NULL DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS cart
(
    id         UUID PRIMARY KEY,
    vehicle_id UUID      NOT NULL,
    client_id  UUID      NOT NULL,
    created_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_cart_vehicle FOREIGN KEY (vehicle_id) REFERENCES vehicles (id)
);

CREATE TABLE IF NOT EXISTS sales
(
    id          UUID PRIMARY KEY,
    type        VARCHAR(10)    NOT NULL CHECK (type IN ('ONLINE', 'FISICA')),
    client_id   UUID           NOT NULL,
    seller_id   UUID,
    vehicle_id  UUID           NOT NULL,
    date        TIMESTAMP      NOT NULL,
    final_price NUMERIC(10, 2) NOT NULL,
    CONSTRAINT fk_sales_vehicle FOREIGN KEY (vehicle_id) REFERENCES vehicles (id)
);