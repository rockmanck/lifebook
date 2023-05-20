/*
 * This file is generated by jOOQ.
 */
package pp.ua.lifebook.storage.db.scheme;


import org.jooq.ForeignKey;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;
import pp.ua.lifebook.storage.db.scheme.tables.Category;
import pp.ua.lifebook.storage.db.scheme.tables.ListItems;
import pp.ua.lifebook.storage.db.scheme.tables.Lists;
import pp.ua.lifebook.storage.db.scheme.tables.Moments;
import pp.ua.lifebook.storage.db.scheme.tables.PlanStatus;
import pp.ua.lifebook.storage.db.scheme.tables.Plans;
import pp.ua.lifebook.storage.db.scheme.tables.Reminders;
import pp.ua.lifebook.storage.db.scheme.tables.RepeatType;
import pp.ua.lifebook.storage.db.scheme.tables.UserSettings;
import pp.ua.lifebook.storage.db.scheme.tables.Users;
import pp.ua.lifebook.storage.db.scheme.tables.records.CategoryRecord;
import pp.ua.lifebook.storage.db.scheme.tables.records.ListItemsRecord;
import pp.ua.lifebook.storage.db.scheme.tables.records.ListsRecord;
import pp.ua.lifebook.storage.db.scheme.tables.records.MomentsRecord;
import pp.ua.lifebook.storage.db.scheme.tables.records.PlanStatusRecord;
import pp.ua.lifebook.storage.db.scheme.tables.records.PlansRecord;
import pp.ua.lifebook.storage.db.scheme.tables.records.RemindersRecord;
import pp.ua.lifebook.storage.db.scheme.tables.records.RepeatTypeRecord;
import pp.ua.lifebook.storage.db.scheme.tables.records.UserSettingsRecord;
import pp.ua.lifebook.storage.db.scheme.tables.records.UsersRecord;


/**
 * A class modelling foreign key relationships and constraints of tables in
 * public.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<CategoryRecord> CATEGORY_PK = Internal.createUniqueKey(Category.CATEGORY, DSL.name("category_pk"), new TableField[] { Category.CATEGORY.ID }, true);
    public static final UniqueKey<ListItemsRecord> LIST_ITEMS_PKEY = Internal.createUniqueKey(ListItems.LIST_ITEMS, DSL.name("list_items_pkey"), new TableField[] { ListItems.LIST_ITEMS.ID }, true);
    public static final UniqueKey<ListsRecord> LISTS_PKEY = Internal.createUniqueKey(Lists.LISTS, DSL.name("lists_pkey"), new TableField[] { Lists.LISTS.ID }, true);
    public static final UniqueKey<MomentsRecord> MOMENTS_PK = Internal.createUniqueKey(Moments.MOMENTS, DSL.name("moments_pk"), new TableField[] { Moments.MOMENTS.ID }, true);
    public static final UniqueKey<PlanStatusRecord> PLAN_STATUS_PK = Internal.createUniqueKey(PlanStatus.PLAN_STATUS, DSL.name("plan_status_pk"), new TableField[] { PlanStatus.PLAN_STATUS.CODE }, true);
    public static final UniqueKey<PlansRecord> PLANS_PK = Internal.createUniqueKey(Plans.PLANS, DSL.name("plans_pk"), new TableField[] { Plans.PLANS.ID }, true);
    public static final UniqueKey<RemindersRecord> REMINDER_PK = Internal.createUniqueKey(Reminders.REMINDERS, DSL.name("reminder_pk"), new TableField[] { Reminders.REMINDERS.ID }, true);
    public static final UniqueKey<RepeatTypeRecord> REPEAT_TYPE_PK = Internal.createUniqueKey(RepeatType.REPEAT_TYPE, DSL.name("repeat_type_pk"), new TableField[] { RepeatType.REPEAT_TYPE.CODE }, true);
    public static final UniqueKey<UserSettingsRecord> PK_USER_SETTINGS = Internal.createUniqueKey(UserSettings.USER_SETTINGS, DSL.name("pk_user_settings"), new TableField[] { UserSettings.USER_SETTINGS.USER_ID }, true);
    public static final UniqueKey<UsersRecord> USER_PK = Internal.createUniqueKey(Users.USERS, DSL.name("user_pk"), new TableField[] { Users.USERS.ID, Users.USERS.LOGIN }, true);
    public static final UniqueKey<UsersRecord> USERS_ID_UK = Internal.createUniqueKey(Users.USERS, DSL.name("users_id_uk"), new TableField[] { Users.USERS.ID }, true);

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------

    public static final ForeignKey<ListItemsRecord, ListsRecord> LIST_ITEMS__LIST_ITEMS_LIST_ID_FKEY = Internal.createForeignKey(ListItems.LIST_ITEMS, DSL.name("list_items_list_id_fkey"), new TableField[] { ListItems.LIST_ITEMS.LIST_ID }, Keys.LISTS_PKEY, new TableField[] { Lists.LISTS.ID }, true);
    public static final ForeignKey<PlansRecord, CategoryRecord> PLANS__CATEGORY_FK = Internal.createForeignKey(Plans.PLANS, DSL.name("category_fk"), new TableField[] { Plans.PLANS.CATEGORY }, Keys.CATEGORY_PK, new TableField[] { Category.CATEGORY.ID }, true);
    public static final ForeignKey<PlansRecord, UsersRecord> PLANS__USER_FK = Internal.createForeignKey(Plans.PLANS, DSL.name("user_fk"), new TableField[] { Plans.PLANS.USER_ID }, Keys.USERS_ID_UK, new TableField[] { Users.USERS.ID }, true);
    public static final ForeignKey<RemindersRecord, PlansRecord> REMINDERS__REMINDER_PLAN_FK = Internal.createForeignKey(Reminders.REMINDERS, DSL.name("reminder_plan_fk"), new TableField[] { Reminders.REMINDERS.PLAN_ID }, Keys.PLANS_PK, new TableField[] { Plans.PLANS.ID }, true);
}
