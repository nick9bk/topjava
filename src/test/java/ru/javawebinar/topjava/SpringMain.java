package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.meal.MealRestController;

import java.util.Arrays;

import static ru.javawebinar.topjava.util.MealsUtil.MEALS;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml", "spring/spring-db.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
//            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
//            adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ROLE_ADMIN));
//            System.out.println();

            MealRestController mealController = appCtx.getBean(MealRestController.class);
//            List<MealTo> filteredMealsWithExcess =
//                    mealController.getBetween(
//                            LocalDate.of(2015, Month.MAY, 30), LocalTime.of(10, 1),
//                            LocalDate.of(2015, Month.MAY, 31), LocalTime.of(11, 0));
//
//            filteredMealsWithExcess.forEach(System.out::println);
            Meal meal = MEALS.get(1);
            Meal meal1 = mealController.create(meal);
            System.out.println(meal1);
            meal.setId(100002);
            meal.setCalories(meal.getCalories() + 100);
            mealController.update(meal, meal.getId());
        }
    }
}
