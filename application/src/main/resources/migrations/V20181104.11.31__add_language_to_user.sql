ALTER TABLE "Users" ADD COLUMN "Language" character varying(10);
COMMENT ON COLUMN "Users"."Language" IS 'two symbol language code';

UPDATE "Users" SET "Language" = 'en';