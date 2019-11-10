package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.DateTimeUtil.isBetween;

@Service
public class MealService {

    @Autowired
    private MealRepository repository;

    public Collection<Meal> getAll(int userId) {
        Collection<Meal> meals = repository.getAll(userId);
        if (meals == null) {
            throw new NotFoundException("Meal getAll userId = " + userId);
        }
        return meals;
    }

    public Collection<Meal> getAllByFilter(int userId, LocalDate dateFrom, LocalDate dateTo) {
        return getAll(userId).stream()
                .filter(meal -> isBetween(meal.getDateTime().toLocalDate(), dateFrom, dateTo))
                .collect(Collectors.toList());
    }

    public Meal get(int userId, int id) {
        Meal meal = repository.get(userId, id);
        if (meal == null) {
            throw new NotFoundException("Meal get userId = " + userId + " id = " + id);
        }
        return meal;
    }

    public Meal save(int userId, Meal meal) {
        Meal m = repository.save(userId, meal);
        if (m == null) {
            throw new NotFoundException("Meal save userId = " + userId + " meal = " + meal);
        }
        return m;
    }

    public void delete(int userId, int id) {
        if (!repository.delete(userId, id)) {
            throw new NotFoundException("Meal delete userId = " + userId + " id = " + id);
        }
    }
}