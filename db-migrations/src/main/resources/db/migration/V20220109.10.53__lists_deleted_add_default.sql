ALTER TABLE lists
ALTER COLUMN deleted SET DEFAULT false;

UPDATE lists SET deleted = false WHERE deleted IS NULL;

ALTER TABLE lists
ALTER COLUMN deleted SET NOT NULL;
