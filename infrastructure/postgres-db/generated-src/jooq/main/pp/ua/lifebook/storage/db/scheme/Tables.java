/*
 * This file is generated by jOOQ.
 */
package pp.ua.lifebook.storage.db.scheme;


import pp.ua.lifebook.storage.db.scheme.tables.Category;
import pp.ua.lifebook.storage.db.scheme.tables.ListItems;
import pp.ua.lifebook.storage.db.scheme.tables.Lists;
import pp.ua.lifebook.storage.db.scheme.tables.Moments;
import pp.ua.lifebook.storage.db.scheme.tables.PlanStatus;
import pp.ua.lifebook.storage.db.scheme.tables.Plans;
import pp.ua.lifebook.storage.db.scheme.tables.Reminders;
import pp.ua.lifebook.storage.db.scheme.tables.RepeatType;
import pp.ua.lifebook.storage.db.scheme.tables.Tag;
import pp.ua.lifebook.storage.db.scheme.tables.TagMomentRelation;
import pp.ua.lifebook.storage.db.scheme.tables.TagPlanRelation;
import pp.ua.lifebook.storage.db.scheme.tables.UserSettings;
import pp.ua.lifebook.storage.db.scheme.tables.Users;


/**
 * Convenience access to all tables in public.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Tables {

    /**
     * The table <code>public.category</code>.
     */
    public static final Category CATEGORY = Category.CATEGORY;

    /**
     * The table <code>public.list_items</code>.
     */
    public static final ListItems LIST_ITEMS = ListItems.LIST_ITEMS;

    /**
     * The table <code>public.lists</code>.
     */
    public static final Lists LISTS = Lists.LISTS;

    /**
     * The table <code>public.moments</code>.
     */
    public static final Moments MOMENTS = Moments.MOMENTS;

    /**
     * The table <code>public.plan_status</code>.
     */
    public static final PlanStatus PLAN_STATUS = PlanStatus.PLAN_STATUS;

    /**
     * The table <code>public.plans</code>.
     */
    public static final Plans PLANS = Plans.PLANS;

    /**
     * The table <code>public.reminders</code>.
     */
    public static final Reminders REMINDERS = Reminders.REMINDERS;

    /**
     * The table <code>public.repeat_type</code>.
     */
    public static final RepeatType REPEAT_TYPE = RepeatType.REPEAT_TYPE;

    /**
     * The table <code>public.tag</code>.
     */
    public static final Tag TAG = Tag.TAG;

    /**
     * The table <code>public.tag_moment_relation</code>.
     */
    public static final TagMomentRelation TAG_MOMENT_RELATION = TagMomentRelation.TAG_MOMENT_RELATION;

    /**
     * The table <code>public.tag_plan_relation</code>.
     */
    public static final TagPlanRelation TAG_PLAN_RELATION = TagPlanRelation.TAG_PLAN_RELATION;

    /**
     * The table <code>public.user_settings</code>.
     */
    public static final UserSettings USER_SETTINGS = UserSettings.USER_SETTINGS;

    /**
     * The table <code>public.users</code>.
     */
    public static final Users USERS = Users.USERS;
}
