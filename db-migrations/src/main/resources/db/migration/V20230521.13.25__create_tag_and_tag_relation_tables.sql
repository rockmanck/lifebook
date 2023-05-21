CREATE TABLE tag(
    id serial,
    user_id int,
    name varchar(100),
    PRIMARY KEY (id)
);

CREATE UNIQUE INDEX idx_user_tag_lower ON tag (user_id, lower(name));

CREATE TABLE tag_plan_relation(
    tag_id int,
    plan_id int
);

CREATE TABLE tag_moment_relation(
    tag_id int,
    moment_id int
);