package ua.lifebook.plan;

import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class PlansByDay implements Comparable<PlansByDay> {
    private final LocalDateTime day;
    private final List<Plan> plans = new ArrayList<>();

    public PlansByDay(LocalDateTime day) {
        this.day = day;
    }

    public List<Plan> getPlans() {
        return plans;
    }

    public void addPlan(Plan plan) {
        plans.add(plan);
    }

    /**
     * Returns capitalized full representation of {@link java.time.DayOfWeek} using method {@link java.time.DayOfWeek#getDisplayName(TextStyle, Locale) getDisplayName}.
     */
    public String getDay() {
        return day.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.US);
    }

    public int getDayOfMonth() {
        return day.getDayOfMonth();
    }

    LocalDateTime getDayFully() {
        return day;
    }

    @Override
    public int compareTo(PlansByDay o) {
        return day.compareTo(o.day);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlansByDay that = (PlansByDay) o;
        return Objects.equals(day, that.day);
    }

    @Override
    public int hashCode() {
        return Objects.hash(day);
    }

    @Override
    public String toString() {
        return "PlansByDay{" +
            "day=" + day +
            '}';
    }
}
