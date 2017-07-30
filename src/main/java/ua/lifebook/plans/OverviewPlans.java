package ua.lifebook.plans;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class OverviewPlans {
    private final int weeksCount;
    private final List<List<PlansByDay>> data = new ArrayList<>();
    private final LocalDate firstDayOfMonth;

    public OverviewPlans(int year, int month, Map<Integer, PlansByDay> plans) {
        firstDayOfMonth = LocalDate.of(year, month, 1);
        weeksCount = calculateWeeksCount();
        int dayIndex = 0;
        for (int i = 0; i < weeksCount; i += 1) {
            final List<PlansByDay> row = new ArrayList<>();
            if (i == 0)
                dayIndex = buildFirstRow(row, plans);
            else if (i == weeksCount - 1)
                dayIndex = buildLastRow(row, plans, dayIndex);
            else
                dayIndex = buildRow(row, plans, dayIndex);
            data.add(row);
        }
    }

    public int getWeeksCount() {
        return weeksCount;
    }

    public List<List<PlansByDay>> getData() {
        return data;
    }

    private int calculateWeeksCount() {
        final int daysInMonth = firstDayOfMonth.lengthOfMonth();
        final DayOfWeek dayOfWeek = firstDayOfMonth.getDayOfWeek();

        if (dayOfWeek == DayOfWeek.MONDAY && daysInMonth == 28)                    return 4;
        if (dayOfWeek.ordinal() > DayOfWeek.FRIDAY.ordinal() && daysInMonth == 31) return 6;
        if (dayOfWeek == DayOfWeek.SUNDAY && daysInMonth == 30)                    return 6;
        return 5;
    }

    private int buildFirstRow(List<PlansByDay> row, Map<Integer, PlansByDay> plans) {
        final int ordinal = firstDayOfMonth.getDayOfWeek().ordinal();
        prependPreviousMonthDays(row, ordinal);

        int index = 0;
        for (int i = ordinal; i < 7; i += 1) {
            index = putDayPlansIntoRow(row, plans, index);
        }
        return index;
    }

    private int buildLastRow(List<PlansByDay> row, Map<Integer, PlansByDay> plans, int dayIndex) {
        int populatedDaysOnThisWeek = 0;
        for (int i = dayIndex; i <= firstDayOfMonth.lengthOfMonth(); i += 1) {
            putDayPlansIntoRow(row, plans, i);
            populatedDaysOnThisWeek += 1;
        }
        appendNextMonthDays(row, 7 - populatedDaysOnThisWeek);
        return populatedDaysOnThisWeek;
    }

    private int buildRow(List<PlansByDay> row, Map<Integer, PlansByDay> plans, int dayIndex) {
        final int lengthOfMonth = firstDayOfMonth.lengthOfMonth();
        int weekDaysCount = 1;
        while (dayIndex < lengthOfMonth && weekDaysCount <= 7) {
            dayIndex = putDayPlansIntoRow(row, plans, dayIndex);
            weekDaysCount += 1;
        }
        return dayIndex;
    }

    private int putDayPlansIntoRow(List<PlansByDay> row, Map<Integer, PlansByDay> plans, int index) {
        final LocalDate date = firstDayOfMonth.plusDays(index);
        final int day = date.getDayOfMonth();
        final PlansByDay plansByDay = plans.getOrDefault(day, getEmptyPlansByDate(date));
        row.add(plansByDay);
        index += 1;
        return index;
    }

    private void prependPreviousMonthDays(List<PlansByDay> row, int dayOfWeekOrdinal) {
        for (int i = 0; i < dayOfWeekOrdinal; i += 1) {
            final LocalDate localDate = firstDayOfMonth.minusDays(dayOfWeekOrdinal - i);
            row.add(getEmptyPlansByDate(localDate));
        }
    }

    private void appendNextMonthDays(List<PlansByDay> row, int daysCount) {
        final int lengthOfMonth = firstDayOfMonth.lengthOfMonth();
        for (int i = 1; i <= daysCount; i += 1) {
            final LocalDate localDate = firstDayOfMonth.plusDays(lengthOfMonth + i);
            row.add(getEmptyPlansByDate(localDate));
        }
    }

    private PlansByDay getEmptyPlansByDate(LocalDate localDate) {
        return new PlansByDay(LocalDateTime.of(localDate, LocalTime.MIDNIGHT));
    }
}
