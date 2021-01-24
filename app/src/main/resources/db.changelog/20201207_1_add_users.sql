CREATE TABLE users(
    id BIGSERIAL NOT NULL PRIMARY KEY,
    username VARCHAR NOT NULL unique,
    password VARCHAR NOT NULL
);
CREATE TABLE authorities(
    id int NOT NULL,
    authority VARCHAR NOT NULL,
    CONSTRAINT users_fk FOREIGN KEY (id) REFERENCES users (id)
);
