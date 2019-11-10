package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

import static ru.javawebinar.topjava.util.DateTimeUtil.isBetween;
import static ru.javawebinar.topjava.util.MealsUtil.*;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController {
    @Autowired
    private MealService service;

    public Collection<MealTo> getAll() {
        return getTos(service.getAll(authUserId()), SecurityUtil.authUserCaloriesPerDay());
    }

    public Collection<MealTo> getAllByFilter(LocalDate dateFrom, LocalDate dateTo, LocalTime timeFrom, LocalTime timeTo) {
        return getFilteredTos(service.getAllByFilter(authUserId(), dateFrom, dateTo), SecurityUtil.authUserCaloriesPerDay(), timeFrom, timeTo);
    }

    public Meal get(int id) {
        return service.get(authUserId(), id);
    }

    public Meal save(Meal meal) {
        return service.save(authUserId(), meal);
    }

    public void delete(int id) {
        service.delete(authUserId(), id);
    }
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/spring-app.xml");
        MealRestController mealRestController = applicationContext.getBean(MealRestController.class);
        System.out.println(mealRestController.getAll());
    }
}