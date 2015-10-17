CREATE TABLE public."Users"
(
   "Id" serial,
   "Login" character varying(100),
   "FirstName" character varying(100),
   "LastName" character varying(100),
   "Email" character varying(150),
   "Password" character varying(64),
   CONSTRAINT "User_pk" PRIMARY KEY ("Id", "Login")
)
WITH (
  OIDS = FALSE
)
;