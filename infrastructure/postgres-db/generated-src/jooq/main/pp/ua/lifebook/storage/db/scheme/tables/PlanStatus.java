/*
 * This file is generated by jOOQ.
 */
package pp.ua.lifebook.storage.db.scheme.tables;


import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row2;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import pp.ua.lifebook.storage.db.scheme.Keys;
import pp.ua.lifebook.storage.db.scheme.Public;
import pp.ua.lifebook.storage.db.scheme.tables.records.PlanStatusRecord;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class PlanStatus extends TableImpl<PlanStatusRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.plan_status</code>
     */
    public static final PlanStatus PLAN_STATUS = new PlanStatus();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<PlanStatusRecord> getRecordType() {
        return PlanStatusRecord.class;
    }

    /**
     * The column <code>public.plan_status.code</code>.
     */
    public final TableField<PlanStatusRecord, String> CODE = createField(DSL.name("code"), SQLDataType.VARCHAR(10).nullable(false), this, "");

    /**
     * The column <code>public.plan_status.name</code>.
     */
    public final TableField<PlanStatusRecord, String> NAME = createField(DSL.name("name"), SQLDataType.VARCHAR(100), this, "");

    private PlanStatus(Name alias, Table<PlanStatusRecord> aliased) {
        this(alias, aliased, null);
    }

    private PlanStatus(Name alias, Table<PlanStatusRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.plan_status</code> table reference
     */
    public PlanStatus(String alias) {
        this(DSL.name(alias), PLAN_STATUS);
    }

    /**
     * Create an aliased <code>public.plan_status</code> table reference
     */
    public PlanStatus(Name alias) {
        this(alias, PLAN_STATUS);
    }

    /**
     * Create a <code>public.plan_status</code> table reference
     */
    public PlanStatus() {
        this(DSL.name("plan_status"), null);
    }

    public <O extends Record> PlanStatus(Table<O> child, ForeignKey<O, PlanStatusRecord> key) {
        super(child, key, PLAN_STATUS);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public UniqueKey<PlanStatusRecord> getPrimaryKey() {
        return Keys.PLAN_STATUS_PK;
    }

    @Override
    public PlanStatus as(String alias) {
        return new PlanStatus(DSL.name(alias), this);
    }

    @Override
    public PlanStatus as(Name alias) {
        return new PlanStatus(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public PlanStatus rename(String name) {
        return new PlanStatus(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public PlanStatus rename(Name name) {
        return new PlanStatus(name, null);
    }

    // -------------------------------------------------------------------------
    // Row2 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row2<String, String> fieldsRow() {
        return (Row2) super.fieldsRow();
    }
}
