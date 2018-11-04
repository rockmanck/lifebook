CREATE TABLE user_settings
(
  view_options character varying(100),
  user_id integer NOT NULL,
  default_tab character varying(25) NOT NULL DEFAULT 'DAILY'::character varying,
  CONSTRAINT pk_user_settings PRIMARY KEY (user_id)
)
WITH (
OIDS=FALSE
);
ALTER TABLE user_settings
  OWNER TO postgres;