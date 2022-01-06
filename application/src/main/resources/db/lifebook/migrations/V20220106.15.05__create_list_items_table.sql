CREATE TABLE list_items(
    id serial,
    list_id int,
    name varchar(100),
    comment varchar(500),
    completed boolean,
    PRIMARY KEY(id),
    FOREIGN KEY(list_id) REFERENCES lists(id)
);