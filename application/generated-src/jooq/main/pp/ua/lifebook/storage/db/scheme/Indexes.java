/*
 * This file is generated by jOOQ.
 */
package pp.ua.lifebook.storage.db.scheme;


import org.jooq.Index;
import org.jooq.OrderField;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;
import pp.ua.lifebook.storage.db.scheme.tables.Plans;


/**
 * A class modelling indexes of tables in public.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Indexes {

    // -------------------------------------------------------------------------
    // INDEX definitions
    // -------------------------------------------------------------------------

    public static final Index FKI_PLAN_STATUS_FK = Internal.createIndex(DSL.name("fki_plan_status_fk"), Plans.PLANS, new OrderField[] { Plans.PLANS.STATUS }, false);
    public static final Index FKI_USER_FK = Internal.createIndex(DSL.name("fki_user_fk"), Plans.PLANS, new OrderField[] { Plans.PLANS.USER_ID }, false);
}