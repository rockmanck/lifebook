package ua.lifebook.plans;

import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
    public String getDay(/*Locale locale*/) {
        return day.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.US);
    }

    @Override public int compareTo(PlansByDay o) {
        return day.compareTo(o.day);
    }
}
