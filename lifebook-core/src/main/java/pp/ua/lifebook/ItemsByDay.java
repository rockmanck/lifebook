package pp.ua.lifebook;

import pp.ua.lifebook.moments.Moment;
import pp.ua.lifebook.plan.Plan;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class ItemsByDay implements Comparable<ItemsByDay> {
    private final LocalDate day;
    private final List<Plan> plans = new ArrayList<>();
    private final List<Moment> moments = new ArrayList<>();

    public ItemsByDay(LocalDate date) {
        this.day = date;
    }

    public List<Plan> getPlans() {
        return plans;
    }

    public List<Moment> getMoments() {
        return moments;
    }

    public void addPlan(Plan plan) {
        plans.add(plan);
    }

    public void addMoment(Moment moment) {
        moments.add(moment);
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

    public LocalDate getDayFully() {
        return day;
    }

    @Override
    public int compareTo(ItemsByDay o) {
        return day.compareTo(o.day);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemsByDay that = (ItemsByDay) o;
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
