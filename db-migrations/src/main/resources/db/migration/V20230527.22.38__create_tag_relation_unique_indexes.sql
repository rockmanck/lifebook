CREATE UNIQUE INDEX idx_tag_plan_unq ON tag_plan_relation (tag_id, plan_id);
CREATE UNIQUE INDEX idx_tag_moment_unq ON tag_moment_relation (tag_id, moment_id);