CREATE TABLE lists(
    id serial,
    user_id int,
    name varchar(100),
    deleted boolean,
    PRIMARY KEY(id)
);