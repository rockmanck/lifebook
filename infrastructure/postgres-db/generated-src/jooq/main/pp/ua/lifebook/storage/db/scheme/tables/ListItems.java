/*
 * This file is generated by jOOQ.
 */
package pp.ua.lifebook.storage.db.scheme.tables;


import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row5;
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
import pp.ua.lifebook.storage.db.scheme.tables.records.ListItemsRecord;

import java.util.Arrays;
import java.util.List;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ListItems extends TableImpl<ListItemsRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.list_items</code>
     */
    public static final ListItems LIST_ITEMS = new ListItems();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ListItemsRecord> getRecordType() {
        return ListItemsRecord.class;
    }

    /**
     * The column <code>public.list_items.id</code>.
     */
    public final TableField<ListItemsRecord, Integer> ID = createField(DSL.name("id"), SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>public.list_items.list_id</code>.
     */
    public final TableField<ListItemsRecord, Integer> LIST_ID = createField(DSL.name("list_id"), SQLDataType.INTEGER, this, "");

    /**
     * The column <code>public.list_items.name</code>.
     */
    public final TableField<ListItemsRecord, String> NAME = createField(DSL.name("name"), SQLDataType.VARCHAR(100), this, "");

    /**
     * The column <code>public.list_items.comment</code>.
     */
    public final TableField<ListItemsRecord, String> COMMENT = createField(DSL.name("comment"), SQLDataType.VARCHAR(500), this, "");

    /**
     * The column <code>public.list_items.completed</code>.
     */
    public final TableField<ListItemsRecord, Boolean> COMPLETED = createField(DSL.name("completed"), SQLDataType.BOOLEAN, this, "");

    private ListItems(Name alias, Table<ListItemsRecord> aliased) {
        this(alias, aliased, null);
    }

    private ListItems(Name alias, Table<ListItemsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.list_items</code> table reference
     */
    public ListItems(String alias) {
        this(DSL.name(alias), LIST_ITEMS);
    }

    /**
     * Create an aliased <code>public.list_items</code> table reference
     */
    public ListItems(Name alias) {
        this(alias, LIST_ITEMS);
    }

    /**
     * Create a <code>public.list_items</code> table reference
     */
    public ListItems() {
        this(DSL.name("list_items"), null);
    }

    public <O extends Record> ListItems(Table<O> child, ForeignKey<O, ListItemsRecord> key) {
        super(child, key, LIST_ITEMS);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public Identity<ListItemsRecord, Integer> getIdentity() {
        return (Identity<ListItemsRecord, Integer>) super.getIdentity();
    }

    @Override
    public UniqueKey<ListItemsRecord> getPrimaryKey() {
        return Keys.LIST_ITEMS_PKEY;
    }

    @Override
    public List<ForeignKey<ListItemsRecord, ?>> getReferences() {
        return Arrays.asList(Keys.LIST_ITEMS__LIST_ITEMS_LIST_ID_FKEY);
    }

    private transient Lists _lists;

    public Lists lists() {
        if (_lists == null)
            _lists = new Lists(this, Keys.LIST_ITEMS__LIST_ITEMS_LIST_ID_FKEY);

        return _lists;
    }

    @Override
    public ListItems as(String alias) {
        return new ListItems(DSL.name(alias), this);
    }

    @Override
    public ListItems as(Name alias) {
        return new ListItems(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public ListItems rename(String name) {
        return new ListItems(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public ListItems rename(Name name) {
        return new ListItems(name, null);
    }

    // -------------------------------------------------------------------------
    // Row5 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row5<Integer, Integer, String, String, Boolean> fieldsRow() {
        return (Row5) super.fieldsRow();
    }
}