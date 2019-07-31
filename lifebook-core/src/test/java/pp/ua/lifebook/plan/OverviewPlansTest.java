package pp.ua.lifebook.plan;

import org.junit.jupiter.api.Test;
import pp.ua.lifebook.ItemsByDay;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class OverviewPlansTest {
    final Map<Integer, ItemsByDay> emptyPlans = new HashMap<>();

    @Test
    void weeksInMonthCountTest() {
        assertEquals(6, overviewPlans(2017, 1).getWeeksCount(), "January");
        assertEquals(5, overviewPlans(2017, 2).getWeeksCount(), "February");
        assertEquals(5, overviewPlans(2017, 3).getWeeksCount(), "March");
        assertEquals(5, overviewPlans(2017, 4).getWeeksCount(), "April");
        assertEquals(5, overviewPlans(2017, 5).getWeeksCount(), "May");
        assertEquals(5, overviewPlans(2017, 6).getWeeksCount(), "June");
        assertEquals(6, overviewPlans(2017, 7).getWeeksCount(), "July");
        assertEquals(5, overviewPlans(2017, 8).getWeeksCount(), "August");
        assertEquals(5, overviewPlans(2017, 9).getWeeksCount(), "September");
        assertEquals(6, overviewPlans(2017, 10).getWeeksCount(), "October");
        assertEquals(5, overviewPlans(2017, 11).getWeeksCount(), "November");
        assertEquals(5, overviewPlans(2017, 12).getWeeksCount(), "December");
        assertEquals(5, overviewPlans(2016, 2).getWeeksCount(), "2016 February");
        assertEquals(4, overviewPlans(2021, 2).getWeeksCount(), "2021 February");
    }

    @Test
    void allWeeksHave7Days() {
        final OverviewPlans overviewPlans = overviewPlans(2017, 7);
        int week = 1;
        for (List<ItemsByDay> plans : overviewPlans.getData()) {
            assertThat(plans.size())
                .as("week #%d contains wrong count of days", week)
                .isEqualTo(7);
            week += 1;
        }
    }

    @Test
    void checkDaysNumbers() {
        final OverviewPlans overviewPlans = overviewPlans(2017, 3);
        int day = 27;
        for (int i = 0; i < 5; i += 1) {
            final List<ItemsByDay> row = overviewPlans.getData().get(i);
            for (int j = 0; j < 7; j += 1) {
                final ItemsByDay itemsByDay = row.get(j);
                assertThat(day)
                    .as("Week #%d contains wrong day", i + 1)
                    .isEqualTo(itemsByDay.getDayOfMonth());
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