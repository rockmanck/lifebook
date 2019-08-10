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
    private static final LocalDate JULY_25TH = LocalDate.of(2019, 7, 25);
    private static final Plan PLAN_JULY_15 = Plan.builder()
        .setDueDate(LocalDateTime.of(JULY_15TH, LocalTime.now()))
        .createPlan();
    private static final Plan PLAN_JULY_25 = Plan.builder()
        .setDueDate(LocalDateTime.of(JULY_25TH, LocalTime.now()))
        .createPlan();
    private static final Moment MOMENT_JULY_15 = Moment.builder()
        .setDate(JULY_15TH)
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
        when(momentStorage.getByDateRange(eq(user.getId()), eq(JULY_15TH), eq(JULY_15TH)))
            .thenReturn(List.of(MOMENT_JULY_15));

        final List<ItemsByDay> items = dayItemsManager.getForDay(JULY_15TH, user);

        final ItemsByDay itemsByDay = assertThatSingleItemInList(items);
        assertSingleMoment(itemsByDay);
        assertSinglePlan(itemsByDay);
    }

    @Test
    void testMonthlyPlans() {
        final LocalDate beginOfJuly = LocalDate.of(2019, 7, 1);
        final LocalDate endOfJuly = LocalDate.of(2019, 7, 31);
        when(plansStorage.getPlans(eq(beginOfJuly), eq(endOfJuly), eq(user), Mockito.anySet()))
            .thenReturn(List.of(PLAN_JULY_15, PLAN_JULY_25));
        when(momentStorage.getByDateRange(eq(user.getId()), eq(beginOfJuly), eq(endOfJuly)))
            .thenReturn(List.of(MOMENT_JULY_15, MOMENT_JULY_25));

        final Map<Integer, ItemsByDay> items = dayItemsManager.getMonthlyPlans(2019, 7, user);

        assertEquals(2, items.size(), "There should be two days in the map");
        assertTrue(items.containsKey(JULY_15TH.getDayOfMonth()), "There are no items as of July 15th");
        assertTrue(items.containsKey(JULY_25TH.getDayOfMonth()), "There are no items as of July 25th");
    }

    @Test
    void testGetItemsForWeek() {
        final LocalDate begin = LocalDate.of(2019, 7, 10);
        final LocalDate end = LocalDate.of(2019, 7, 17);
        when(plansStorage.getPlans(eq(begin), eq(end), eq(user), Mockito.anySet()))
            .thenReturn(List.of(PLAN_JULY_15));
        when(momentStorage.getByDateRange(eq(user.getId()), eq(begin), eq(end)))
            .thenReturn(List.of(MOMENT_JULY_15));

        final List<ItemsByDay> items = dayItemsManager.getForWeek(begin, user);

        final ItemsByDay itemsByDay = assertThatSingleItemInList(items);
        assertSingleMoment(itemsByDay);
        assertSinglePlan(itemsByDay);
    }

    private ItemsByDay assertThatSingleItemInList(List<ItemsByDay> items) {
        assertEquals(1, items.size(), "There should be one item");
        final ItemsByDay itemsByDay = items.get(0);
        assertEquals(JULY_15TH, itemsByDay.getDayFully(), "Item should be as of July 15th");
        return itemsByDay;
    }

    private void assertSinglePlan(ItemsByDay itemsByDay) {
        final List<Plan> plans = itemsByDay.getPlans();
        assertEquals(1, plans.size(), "Should be only one plan");
        assertEquals(PLAN_JULY_15.getDueDate(), plans.get(0).getDueDate(), "Due datetime is not matched");
    }

    private void assertSingleMoment(ItemsByDay itemsByDay) {
        final List<Moment> moments = itemsByDay.getMoments();
        assertEquals(1, moments.size(), "Should be only one moment");
        final Moment moment = moments.get(0);
        assertEquals(USER_ID, moment.getUserId(), "Fetched moment for the wrong user");
        assertEquals(JULY_15TH, moment.getDate(), "Moment date is not matched");
    }
}