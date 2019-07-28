package pp.ua.lifebook.plan;

import org.junit.Test;
import pp.ua.lifebook.ItemsByDay;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class OverviewPlansTest {
    final Map<Integer, ItemsByDay> emptyPlans = new HashMap<>();

    @Test
    public void weeksInMonthCountTest() {
        assertEquals("January", 6, overviewPlans(2017, 1).getWeeksCount());
        assertEquals("February", 5, overviewPlans(2017, 2).getWeeksCount());
        assertEquals("March", 5, overviewPlans(2017, 3).getWeeksCount());
        assertEquals("April", 5, overviewPlans(2017, 4).getWeeksCount());
        assertEquals("May", 5, overviewPlans(2017, 5).getWeeksCount());
        assertEquals("June", 5, overviewPlans(2017, 6).getWeeksCount());
        assertEquals("July", 6, overviewPlans(2017, 7).getWeeksCount());
        assertEquals("August", 5, overviewPlans(2017, 8).getWeeksCount());
        assertEquals("September", 5, overviewPlans(2017, 9).getWeeksCount());
        assertEquals("October", 6, overviewPlans(2017, 10).getWeeksCount());
        assertEquals("November", 5, overviewPlans(2017, 11).getWeeksCount());
        assertEquals("December", 5, overviewPlans(2017, 12).getWeeksCount());
        assertEquals("2016 February", 5, overviewPlans(2016, 2).getWeeksCount());
        assertEquals("2021 February", 4, overviewPlans(2021, 2).getWeeksCount());
    }

    @Test
    public void allWeeksHave7Days() {
        final OverviewPlans overviewPlans = overviewPlans(2017, 7);
        int week = 1;
        for (List<ItemsByDay> plans : overviewPlans.getData()) {
            assertThat(week + " week contains wrong count of days", plans.size(), is(7));
            week += 1;
        }
    }

    @Test
    public void checkDaysNumbers() {
        final OverviewPlans overviewPlans = overviewPlans(2017, 3);
        int day = 27;
        for (int i = 0; i < 5; i += 1) {
            final List<ItemsByDay> row = overviewPlans.getData().get(i);
            for (int j = 0; j < 7; j += 1) {
                final ItemsByDay itemsByDay = row.get(j);
                assertThat("Week #" + (i + 1) + " contains wrong day", day, is(itemsByDay.getDayOfMonth()));
                final LocalDate localDate = itemsByDay.getDayFully();
                day = nextDay(day, localDate.lengthOfMonth());
            }
        }
    }

    private OverviewPlans overviewPlans(int year, int month) {
        return new OverviewPlans(year, month, emptyPlans);
    }

    private int nextDay(int day, int daysInMonth) {
        final int result = day + 1;
        return result > daysInMonth ? 1 : result;
    }
}