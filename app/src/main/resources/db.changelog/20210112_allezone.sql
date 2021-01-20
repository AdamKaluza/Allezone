CREATE TABLE section (
    id INT NOT NULL PRIMARY KEY,
    name varchar NOT NULL
);
CREATE TABLE category(
    id INT NOT NULL PRIMARY KEY,
    name varchar NOT NULL,
    section_id int NOT NULL,
    CONSTRAINT section_fk FOREIGN KEY (section_id) REFERENCES section (id)
);
CREATE TABLE auction(
    id INT NOT NULL PRIMARY KEY,
    price DECIMAL NOT NULL,
    title VARCHAR NOT NULL,
    description TEXT NOT NULL,
    version INT NOT NULL,
    creator_id INT NOT NULL,
    category_id INT NOT NULL,
    CONSTRAINT category_fk FOREIGN KEY (category_id) REFERENCES category (id),
    CONSTRAINT user_fk FOREIGN KEY (creator_id) REFERENCES users (id)
);
CREATE TABLE auction_photos(
    id INT NOT NULL PRIMARY KEY,
    auction_id INT NOT NULL,
    name VARCHAR NOT NULL,
    position INT NOT NULL,
    CONSTRAINT auction_photos_fk FOREIGN KEY (auction_id) REFERENCES auction (id)
);
CREATE TABLE parameter(
    id INT NOT NULL PRIMARY KEY,
    key VARCHAR NOT NULL
);
CREATE TABLE auction_parameter(
    auction_id INT NOT NULL,
    parameter_id INT NOT NULL,
    value varchar NOT NULL,
    CONSTRAINT parameter_fk FOREIGN KEY (parameter_id) REFERENCES parameter (id),
    CONSTRAINT auction_fk FOREIGN KEY (auction_id) REFERENCES auction (id)
);
--    id INT NOT NULL PRIMARY KEY,


