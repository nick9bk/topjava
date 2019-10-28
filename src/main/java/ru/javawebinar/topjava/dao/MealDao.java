package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static ru.javawebinar.topjava.util.MealsUtil.getMeals;

public class MealDao {
    private AtomicInteger idCounter = new AtomicInteger(0);
    private Map<Integer, Meal> meals = new ConcurrentHashMap<>();

    public MealDao() {
        List<Meal> mealsList = getMeals();
        mealsList.forEach(meal -> {
            meal.setId(idCounter.incrementAndGet());
            meals.put(meal.getId(), meal);
        });
    }

    public Meal save(Meal meal) {
        if (meal.getId() == -1) {
            return add(meal);
        } else {
            return update(meal);
        }
    }

    public List<Meal> getAll() {
        return new ArrayList<>(meals.values());
    }

    public Meal get(int id) {
        return meals.get(id);
    }

    public void delete(int id) {
        meals.remove(id);
    }

    private Meal add(Meal meal) {
        meal.setId(idCounter.incrementAndGet());
        meals.put(meal.getId(), meal);
        return meal;
    }

    private Meal update(Meal meal) {
        meals.put(meal.getId(), meal);
        return meal;
    }
}
