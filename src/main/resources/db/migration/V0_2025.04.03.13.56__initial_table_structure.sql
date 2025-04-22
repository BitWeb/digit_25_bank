CREATE TABLE person
(
    id BIGSERIAL PRIMARY KEY NOT NULL,
    first_name character varying(255) NOT NULL,
    last_name character varying(255) NOT NULL,
    person_code character varying(255) NOT NULL,
    warrant_issued boolean NOT NULL,
    has_contract boolean NOT NULL,
    blacklisted boolean NOT NULL
);
CREATE UNIQUE INDEX ON person("person_code");

CREATE TABLE account
(
    id BIGSERIAL PRIMARY KEY NOT NULL,
    number character varying(255) NOT NULL,
    owner character varying(255) NOT NULL,
    balance decimal(10,2) NOT NULL,
    closed boolean NOT NULL
);
CREATE UNIQUE INDEX ON account("number");

CREATE TABLE device
(
    id BIGSERIAL PRIMARY KEY NOT NULL,
    mac character varying(255) NOT NULL,
    blacklisted boolean NOT NULL
);
CREATE UNIQUE INDEX ON device("mac");

CREATE TABLE detector
(
    id BIGSERIAL PRIMARY KEY NOT NULL,
    name character varying(255) NOT NULL,
    token character varying(255) NOT NULL
);

CREATE UNIQUE INDEX ON detector("token");


