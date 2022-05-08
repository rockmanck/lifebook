package pp.ua.lifebook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pp.ua.lifebook.moments.Moment;
import pp.ua.lifebook.moments.MomentStorage;
import pp.ua.lifebook.plan.Plan;
import pp.ua.lifebook.plan.PlansStorage;
import pp.ua.lifebook.user.User;
import pp.ua.lifebook.user.parameters.UserSettings;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DayItemsManagerTest {

    private static final int USER_ID = 1;
    private static final LocalDate JULY_15TH = LocalDate.of(2019, 7, 15);
    private static final LocalDate JULY_17TH = LocalDate.of(2019, 7, 17);
    private static final LocalDate JULY_18TH = LocalDate.of(2019, 7, 18);
    private static final LocalDate JULY_25TH = LocalDate.of(2019, 7, 25);
    private static final Plan PLAN_JULY_15 = Plan.builder()
        .setDueDate(LocalDateTime.of(JULY_15TH, LocalTime.now()))
        .createPlan();
    private static final Plan PLAN_JULY_17 = Plan.builder()
        .setDueDate(LocalDateTime.of(JULY_17TH, LocalTime.now()))
        .createPlan();
    private static final Plan PLAN_JULY_18 = Plan.builder()
        .setDueDate(LocalDateTime.of(JULY_18TH, LocalTime.now()))
        .createPlan();
    private static final Plan PLAN_JULY_25 = Plan.builder()
        .setDueDate(LocalDateTime.of(JULY_25TH, LocalTime.now()))
        .createPlan();
    private static final Moment MOMENT_JULY_15 = Moment.builder()
        .setDate(JULY_15TH)
        .setUserId(USER_ID)
        .createMoment();
    private static final Moment MOMENT_JULY_17 = Moment.builder()
        .setDate(JULY_17TH)
        .setUserId(USER_ID)
        .createMoment();
    private static final Moment MOMENT_JULY_18 = Moment.builder()
        .setDate(JULY_18TH)
        .setUserId(USER_ID)
        .createMoment();
    private static final Moment MOMENT_JULY_25 = Moment.builder()
        .setDate(JULY_15TH)
        .setUserId(USER_ID)
        .createMoment();
    private User user;
    private DayItemsManager dayItemsManager;
    private PlansStorage plansStorage = mock(PlansStorage.class);
    private MomentStorage momentStorage = mock(MomentStorage.class);

    @BeforeEach
    void setup() {
        user = User.builder()
            .setId(USER_ID)
            .setUserSettings(new UserSettings())
            .createUser();
        dayItemsManager = new DayItemsManager(plansStorage, momentStorage);
    }

    @Test
    void testGetItemsForSingleDay() {
        when(plansStorage.getPlans(eq(JULY_15TH), eq(JULY_15TH), eq(user), Mockito.anySet()))
            .thenReturn(List.of(PLAN_JULY_15));
        when(momentStorage.getByDateRange(user.getId(), JULY_15TH, JULY_15TH))
            .thenReturn(List.of(MOMENT_JULY_15));

        final List<ItemsByDay> items = dayItemsManager.getForDay(JULY_15TH, user);

        assertEquals(1, items.size(), "There should be one item");
        final ItemsByDay itemsByDay = items.get(0);
        assertEquals(JULY_15TH, itemsByDay.getDayFully(), "Item should be as of July 15th");

        final List<Moment> moments = itemsByDay.getMoments();
        assertEquals(1, moments.size(), "Should be only one moment");
        final Moment moment = moments.get(0);
        assertEquals(USER_ID, moment.getUserId(), "Fetched moment for the wrong user");
        assertEquals(JULY_15TH, moment.getDate(), "Moment date is not matched");

        final List<Plan> plans = itemsByDay.getPlans();
        assertEquals(1, plans.size(), "Should be only one plan");
        assertEquals(PLAN_JULY_15.getDueDate(), plans.get(0).getDueDate(), "Due datetime is not matched");
    }

    @Test
    void testMonthlyPlans() {
        final LocalDate beginOfJuly = LocalDate.of(2019, 7, 1);
        final LocalDate endOfJuly = LocalDate.of(2019, 7, 31);
        when(plansStorage.getPlans(eq(beginOfJuly), eq(endOfJuly), eq(user), Mockito.anySet()))
            .thenReturn(List.of(PLAN_JULY_15, PLAN_JULY_25));
        when(momentStorage.getByDateRange(user.getId(), beginOfJuly, endOfJuly))
            .thenReturn(List.of(MOMENT_JULY_15, MOMENT_JULY_25));

        final Map<Integer, ItemsByDay> items = dayItemsManager.getMonthlyPlans(2019, 7, user);

        assertEquals(2, items.size(), "There should be two days in the map");
        assertTrue(items.containsKey(JULY_15TH.getDayOfMonth()), "There are no items as of July 15th");
        assertTrue(items.containsKey(JULY_25TH.getDayOfMonth()), "There are no items as of July 25th");
    }

    @Test
    void testGetItemsForWeekSortedByDate() {
        final LocalDate begin = LocalDate.of(2019, 7, 10);
        final LocalDate end = LocalDate.of(2019, 7, 17);
        when(plansStorage.getPlans(eq(begin), eq(end), eq(user), Mockito.anySet()))
            .thenReturn(List.of(PLAN_JULY_17, PLAN_JULY_15, PLAN_JULY_18));
        when(momentStorage.getByDateRange(user.getId(), begin, end))
            .thenReturn(List.of(MOMENT_JULY_17, MOMENT_JULY_15, MOMENT_JULY_18));

        final List<ItemsByDay> items = dayItemsManager.getForWeek(begin, user);

        assertEquals(3, items.size(), "There should be three items");
        final ItemsByDay itemsByDay1 = items.get(0);
        final ItemsByDay itemsByDay2 = items.get(1);
        final ItemsByDay itemsByDay3 = items.get(2);
        assertEquals(JULY_15TH, itemsByDay1.getDayFully(), "Item should be as of July 15th");
        assertEquals(JULY_17TH, itemsByDay2.getDayFully(), "Item should be as of July 17th");
        assertEquals(JULY_18TH, itemsByDay3.getDayFully(), "Item should be as of July 18th");

        final List<Moment> moments = itemsByDay1.getMoments();
        assertEquals(1, moments.size(), "Should be only one moment");
        final Moment moment = moments.get(0);
        assertEquals(USER_ID, moment.getUserId(), "Fetched moment for the wrong user");
        assertEquals(JULY_15TH, moment.getDate(), "Moment date is not matched");

        final List<Plan> plans = itemsByDay1.getPlans();
        assertEquals(1, plans.size(), "Should be only one plan");
        assertEquals(PLAN_JULY_15.getDueDate(), plans.get(0).getDueDate(), "Due datetime is not matched");
    }

}