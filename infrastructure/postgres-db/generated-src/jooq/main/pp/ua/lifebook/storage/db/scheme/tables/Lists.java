/*
 * This file is generated by jOOQ.
 */
package pp.ua.lifebook.storage.db.scheme.tables;


import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row4;
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
import pp.ua.lifebook.storage.db.scheme.tables.records.ListsRecord;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Lists extends TableImpl<ListsRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.lists</code>
     */
    public static final Lists LISTS = new Lists();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ListsRecord> getRecordType() {
        return ListsRecord.class;
    }

    /**
     * The column <code>public.lists.id</code>.
     */
    public final TableField<ListsRecord, Integer> ID = createField(DSL.name("id"), SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>public.lists.user_id</code>.
     */
    public final TableField<ListsRecord, Integer> USER_ID = createField(DSL.name("user_id"), SQLDataType.INTEGER, this, "");

    /**
     * The column <code>public.lists.name</code>.
     */
    public final TableField<ListsRecord, String> NAME = createField(DSL.name("name"), SQLDataType.VARCHAR(100), this, "");

    /**
     * The column <code>public.lists.deleted</code>.
     */
    public final TableField<ListsRecord, Boolean> DELETED = createField(DSL.name("deleted"), SQLDataType.BOOLEAN.nullable(false).defaultValue(DSL.field("false", SQLDataType.BOOLEAN)), this, "");

    private Lists(Name alias, Table<ListsRecord> aliased) {
        this(alias, aliased, null);
    }

    private Lists(Name alias, Table<ListsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.lists</code> table reference
     */
    public Lists(String alias) {
        this(DSL.name(alias), LISTS);
    }

    /**
     * Create an aliased <code>public.lists</code> table reference
     */
    public Lists(Name alias) {
        this(alias, LISTS);
    }

    /**
     * Create a <code>public.lists</code> table reference
     */
    public Lists() {
        this(DSL.name("lists"), null);
    }

    public <O extends Record> Lists(Table<O> child, ForeignKey<O, ListsRecord> key) {
        super(child, key, LISTS);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public Identity<ListsRecord, Integer> getIdentity() {
        return (Identity<ListsRecord, Integer>) super.getIdentity();
    }

    @Override
    public UniqueKey<ListsRecord> getPrimaryKey() {
        return Keys.LISTS_PKEY;
    }

    @Override
    public Lists as(String alias) {
        return new Lists(DSL.name(alias), this);
    }

    @Override
    public Lists as(Name alias) {
        return new Lists(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Lists rename(String name) {
        return new Lists(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Lists rename(Name name) {
        return new Lists(name, null);
    }

    // -------------------------------------------------------------------------
    // Row4 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row4<Integer, Integer, String, Boolean> fieldsRow() {
        return (Row4) super.fieldsRow();
    }
}
