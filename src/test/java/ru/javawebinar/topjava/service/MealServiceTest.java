package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {
    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService mealService;

    @Test
    public void get() {
        assertEquals(mealService.get(MEAL_ID, USER_ID), MEAL);
    }

    @Test(expected = NotFoundException.class)
    public void delete() {
        mealService.delete(MEAL_ID, USER_ID);
        mealService.get(MEAL_ID, USER_ID);
    }

    @Test
    public void getBetweenDates() {
    }

    @Test
    public void getAll() {
        assertArrayEquals(mealService.getAll(USER_ID).toArray(), MEALS.toArray());
    }

    @Test
    public void update() {
        Meal meal = new Meal(MEAL);
        meal.setCalories(1);
        meal.setDescription("2222");
        mealService.update(meal, USER_ID);
        assertEquals(mealService.get(meal.getId(), USER_ID), meal);
    }

    @Test
    public void create() {
        Meal meal = new Meal(MEAL);
        meal.setDateTime(meal.getDateTime().minusDays(1));
        meal.setId(null);
        meal = mealService.create(meal, USER_ID);
        assertEquals(mealService.get(meal.getId(), USER_ID), meal);
    }
}