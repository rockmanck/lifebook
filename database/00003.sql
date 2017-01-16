DROP TABLE "Category";

CREATE TABLE "Category"
(
  "Id" SERIAL,
  "Name" character varying(100),
  CONSTRAINT "Category_pk" PRIMARY KEY ("Id")
)
WITH (
OIDS=FALSE
);

TRUNCATE TABLE "Plans";

ALTER TABLE "Plans"
ALTER COLUMN "Category" TYPE INT