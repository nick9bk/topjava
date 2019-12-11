package ru.javawebinar.topjava.to;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.beans.ConstructorProperties;
import java.time.LocalDateTime;
import java.util.Objects;

public class MealTo extends BaseTo {

    @PastOrPresent(message = "time must be in past")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @NotNull(message = "must be not null")
    private final LocalDateTime dateTime;

    @Size(min = 2, max = 120, message = "length must be between 2 and 120 characters")
    private final String description;

    @Min(value = 10, message = "must be more 10")
    @Max(value = 5000, message = "must be less 5000")
    private final int calories;

    private final boolean excess;

    @ConstructorProperties({"id", "dateTime", "description", "calories", "excess"})
    public MealTo(Integer id, LocalDateTime dateTime, String description, Integer calories, Boolean excess) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = Objects.requireNonNullElse(calories, 0);
        this.excess = Objects.requireNonNullElse(excess, false);
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

    public boolean isExcess() {
        return excess;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MealTo that = (MealTo) o;
        return calories == that.calories &&
                excess == that.excess &&
                Objects.equals(id, that.id) &&
                Objects.equals(dateTime, that.dateTime) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateTime, description, calories, excess);
    }

    @Override
    public String toString() {
        return "MealTo{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                ", excess=" + excess +
                '}';
    }
}