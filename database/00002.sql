CREATE TABLE "Plans"
(
  "Id" serial NOT NULL,
  "Title" character varying(255),
  "DueDate" date,
  "DueTime" time without time zone,
  "Repeated" character varying(10), -- code of RepeatType
  "Comments" character varying(2000),
  "Status" character varying(10),
  "Category" character varying(10),
  "User" integer,
  CONSTRAINT "Plans_pk" PRIMARY KEY ("Id"),
  CONSTRAINT "Plan_Status_fk" FOREIGN KEY ("Status")
  REFERENCES "PlanStatus" ("Code") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT "User_fk" FOREIGN KEY ("User")
  REFERENCES "Users" ("Id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE CASCADE
)
WITH (
OIDS=FALSE
);
ALTER TABLE "Plans"
OWNER TO postgres;
COMMENT ON COLUMN "Plans"."Repeated" IS 'code of RepeatType';

CREATE INDEX "Plans_Date_idx"
ON "Plans"
USING btree
("DueDate");

CREATE INDEX "fki_Plan_Status_fk"
ON "Plans"
USING btree
("Status" COLLATE pg_catalog."default");

CREATE INDEX "fki_User_fk"
ON "Plans"
USING btree
("User");


CREATE TABLE "Moments"
(
  "Id" serial NOT NULL,
  "Date" date,
  "Description" character varying(8000),
  CONSTRAINT "Moments_pk" PRIMARY KEY ("Id")
)
WITH (
OIDS=FALSE
);


CREATE TABLE "Category"
(
  "Code" character varying(10) NOT NULL,
  "Name" character varying(100),
  CONSTRAINT "Category_pk" PRIMARY KEY ("Code")
)
WITH (
OIDS=FALSE
);

CREATE TABLE "Reminders"
(
  "Id" serial NOT NULL,
  "PlanId" integer,
  "RemindAt" timestamp without time zone,
  CONSTRAINT "Reminder_pk" PRIMARY KEY ("Id"),
  CONSTRAINT "Reminder_Plan_fk" FOREIGN KEY ("PlanId")
  REFERENCES "Plans" ("Id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE CASCADE
)
WITH (
OIDS=FALSE
);

CREATE TABLE "RepeatType"
(
  "Code" character varying(10) NOT NULL,
  "Name" character varying(100),
  CONSTRAINT "RepeatType_pk" PRIMARY KEY ("Code")
)
WITH (
OIDS=FALSE
);

CREATE TABLE "PlanStatus"
(
  "Code" character varying(10) NOT NULL,
  "Name" character varying(100),
  CONSTRAINT "PlanStatus_pk" PRIMARY KEY ("Code")
)
WITH (
OIDS=FALSE
);