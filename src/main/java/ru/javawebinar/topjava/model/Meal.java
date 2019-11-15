package ru.javawebinar.topjava.model;

import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "meals", uniqueConstraints = {@UniqueConstraint(name = "meals_uniq_date_id", columnNames = {"date_time", "user_id"})})
@NamedQueries({
        @NamedQuery(name = Meal.GET_ALL, query = "select m FROM Meal m WHERE m.user.id = :id order by m.dateTime desc"),
        @NamedQuery(name = Meal.GET_BETWEEN, query = "select m FROM Meal m WHERE m.user.id = :id and " +
                "m.dateTime > :startDate and m.dateTime < :endDate order by m.dateTime desc"),
        @NamedQuery(name = Meal.DELETE, query = "delete from Meal m where m.id = :id and m.user.id = :user_id")
})
public class Meal extends AbstractBaseEntity {
    public static final String GET_ALL = "Meal.getall";
    public static final String GET_BETWEEN = "Meal.between";
    public static final String DELETE = "Meal.delete";

    @NotNull
    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @Column(name = "description")
    @Size(max = 50)
    @NotBlank
    private String description;

    @Column(name = "calories")
    @Range(min = 200, max = 5000)
    private int calories;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public Meal() {
    }

    public Meal(LocalDateTime dateTime, String description, int calories) {
        this(null, dateTime, description, calories);
    }

    public Meal(Integer id, LocalDateTime dateTime, String description, int calories) {
        super(id);
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

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}
