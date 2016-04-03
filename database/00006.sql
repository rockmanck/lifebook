-- recreate all tables with postgres code convention
-- Table: "Category"

DROP TABLE "Category";

CREATE TABLE category
(
  id serial NOT NULL,
  name character varying(100),
  CONSTRAINT category_pk PRIMARY KEY (id)
)
WITH (
OIDS=FALSE
);
ALTER TABLE category
OWNER TO postgres;

-- Table: "Moments"

DROP TABLE "Moments";

CREATE TABLE moments
(
  id serial NOT NULL,
  date date,
  description character varying(8000),
  CONSTRAINT moments_pk PRIMARY KEY (id)
)
WITH (
OIDS=FALSE
);
ALTER TABLE moments
OWNER TO postgres;

-- Table: "PlanStatus"

DROP TABLE "PlanStatus" CASCADE;

CREATE TABLE plan_status
(
  code character varying(10) NOT NULL,
  name character varying(100),
  CONSTRAINT plan_status_pk PRIMARY KEY (code)
)
WITH (
OIDS=FALSE
);
ALTER TABLE plan_status
OWNER TO postgres;

-- Table: "Reminders"

DROP TABLE "Reminders";

CREATE TABLE reminders
(
  id serial NOT NULL,
  plan_id integer,
  remind_at timestamp without time zone,
  CONSTRAINT reminder_pk PRIMARY KEY (id),
  CONSTRAINT reminder_plan_fk FOREIGN KEY (plan_id)
  REFERENCES "Plans" ("Id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE CASCADE
)
WITH (
OIDS=FALSE
);
ALTER TABLE reminders
OWNER TO postgres;

-- Table: "Plans"

DROP TABLE "Plans" CASCADE;

CREATE TABLE plans
(
  id serial NOT NULL,
  title character varying(255),
  repeated character varying(10), -- code of RepeatType
  comments character varying(2000),
  status character varying(10),
  user_id integer,
  category integer,
  due_time timestamp without time zone,
  CONSTRAINT plans_pk PRIMARY KEY (id),
  CONSTRAINT user_fk FOREIGN KEY (user_id)
  REFERENCES "Users" ("Id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE CASCADE
)
WITH (
OIDS=FALSE
);
ALTER TABLE plans
OWNER TO postgres;
COMMENT ON COLUMN plans.repeated IS 'code of RepeatType';


-- Index: "fki_Plan_Status_fk"

--DROP INDEX "fki_Plan_Status_fk";

CREATE INDEX fki_plan_status_fk
ON plans
USING btree
(status COLLATE pg_catalog."default");

-- Index: "fki_User_fk"

--DROP INDEX "fki_User_fk";

CREATE INDEX fki_user_fk
ON plans
USING btree
(user_id);


-- Table: "RepeatType"

DROP TABLE "RepeatType";

CREATE TABLE repeat_type
(
  code character varying(10) NOT NULL,
  name character varying(100),
  CONSTRAINT repeat_type_pk PRIMARY KEY (code)
)
WITH (
OIDS=FALSE
);
ALTER TABLE repeat_type
OWNER TO postgres;


-- Table: "Users"

DROP TABLE "Users" CASCADE;

CREATE TABLE users
(
  id serial NOT NULL,
  login character varying(100) NOT NULL,
  first_name character varying(100),
  last_name character varying(100),
  email character varying(150),
  password character varying(64),
  is_admin boolean,
  language character varying(10), -- two symbol language code
  CONSTRAINT user_pk PRIMARY KEY (id, login),
  CONSTRAINT users_id_uk UNIQUE (id)
)
WITH (
OIDS=FALSE
);
ALTER TABLE users
OWNER TO postgres;
COMMENT ON COLUMN users.language IS 'two symbol language code';

ALTER TABLE reminders
ADD CONSTRAINT reminder_plan_fk FOREIGN KEY (plan_id)
REFERENCES plans (id) MATCH SIMPLE
ON UPDATE NO ACTION ON DELETE CASCADE;

ALTER TABLE plans
ADD CONSTRAINT user_fk FOREIGN KEY (user_id)
REFERENCES users (id) MATCH SIMPLE
ON UPDATE NO ACTION ON DELETE CASCADE;

ALTER TABLE plans
ADD CONSTRAINT category_fk FOREIGN KEY (category)
REFERENCES category (id) MATCH SIMPLE
ON UPDATE NO ACTION ON DELETE SET NULL;