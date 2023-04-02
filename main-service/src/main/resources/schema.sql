DROP TABLE IF EXISTS locations, users, categories, events, requests, compilations, places CASCADE;
DROP FUNCTION IF EXISTS distance;

CREATE OR REPLACE FUNCTION distance(lat_place float, lon_place float, lat_event float, lon_event float)
    RETURNS float
AS
'
    declare
        dist float = 0;
        rad_lat1 float;
        rad_lat2 float;
        theta float;
        rad_theta float;
    BEGIN
        IF lat_place = lat_event AND lon_place = lon_event
        THEN
            RETURN dist;
        ELSE
            -- переводим градусы широты в радианы
            rad_lat1 = pi() * lat_place / 180;
            -- переводим градусы долготы в радианы
            rad_lat2 = pi() * lat_event / 180;
            -- находим разность долгот
            theta = lon_place - lon_event;
            -- переводим градусы в радианы
            rad_theta = pi() * theta / 180;
            -- находим длину ортодромии
            dist = sin(rad_lat1) * sin(rad_lat2) + cos(rad_lat1) * cos(rad_lat2) * cos(rad_theta);

            IF dist > 1
            THEN dist = 1;
            END IF;

            dist = acos(dist);
            -- переводим радианы в градусы
            dist = dist * 180 / pi();
            -- переводим градусы в километры
            dist = dist * 60 * 1.8524;

            RETURN dist;
        END IF;
    END;
'
    LANGUAGE PLPGSQL;

CREATE TABLE IF NOT EXISTS locations (
    lat FLOAT NOT NULL,
    lon FLOAT NOT NULL,
    PRIMARY KEY (lat, lon));

CREATE TABLE IF NOT EXISTS users (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY NOT NULL,
    email VARCHAR UNIQUE,
    name VARCHAR);

CREATE TABLE IF NOT EXISTS categories (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY NOT NULL,
    name VARCHAR UNIQUE NOT NULL);

CREATE TABLE IF NOT EXISTS events (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY NOT NULL,
    annotation VARCHAR NOT NULL,
    category_id BIGINT,
    confirmed_requests BIGINT,
    created_on TIMESTAMP WITHOUT TIME ZONE,
    description VARCHAR,
    event_date TIMESTAMP WITHOUT TIME ZONE,
    initiator_id BIGINT,
    lat FLOAT,
    lon FLOAT,
    paid BOOLEAN,
    participant_limit BIGINT,
    published_on TIMESTAMP WITHOUT TIME ZONE,
    request_moderation BOOLEAN,
    state VARCHAR,
    title VARCHAR,
    views BIGINT,
    compilation_id BIGINT,
    CONSTRAINT fk_events_to_categories FOREIGN KEY(category_id) REFERENCES categories(id),
    CONSTRAINT fk_events_to_users FOREIGN KEY(initiator_id) REFERENCES users(id),
    CONSTRAINT fk_events_to_locations FOREIGN KEY(lat, lon) REFERENCES locations(lat, lon));

CREATE TABLE IF NOT EXISTS requests (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY NOT NULL,
    created TIMESTAMP WITHOUT TIME ZONE,
    event_id BIGINT,
    requester_id BIGINT,
    status VARCHAR,
    CONSTRAINT event_id_requester_id UNIQUE(event_id, requester_id),
    CONSTRAINT fk_requests_to_events FOREIGN KEY(event_id) REFERENCES events(id),
    CONSTRAINT fk_requests_to_users FOREIGN KEY(requester_id) REFERENCES users(id));

CREATE TABLE IF NOT EXISTS compilations (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY NOT NULL,
    pinned BOOLEAN,
    title VARCHAR);

CREATE TABLE IF NOT EXISTS places (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY NOT NULL,
    lat FLOAT NOT NULL,
    lon FLOAT NOT NULL,
    radius FLOAT NOT NULL);