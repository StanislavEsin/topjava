package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Meal extends BaseEntity {
    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    public Meal(LocalDateTime dateTime, String description, int calories) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof Meal)) return false;
        if (!super.equals(o)) return false;
        final Meal meal = (Meal) o;
        return calories == meal.calories &&
                Objects.equals(dateTime, meal.dateTime) &&
                Objects.equals(description, meal.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), dateTime, description, calories);
    }
}