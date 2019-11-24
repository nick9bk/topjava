package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.javawebinar.topjava.model.Meal;

import javax.servlet.http.HttpServletRequest;
import java.net.http.HttpRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
public class JspMealController {

    @Autowired
    private MealRestController mealController;

    @GetMapping("meals")
    public String getAll(Model model) {
        model.addAttribute("meals", mealController.getAll());
        return "meals";
    }

    @GetMapping("deleteMeal")
    public String delete(@RequestParam int id) {
        mealController.delete(id);
        return "redirect:meals";
    }

    @GetMapping("createMeal")
    public String create(Model model) {
        final Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @GetMapping("updateMeal")
    public String update(Model model, @RequestParam int id) {
        final Meal meal = mealController.get(id);
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @PostMapping("saveMeal")
    public String save(@ModelAttribute("meal") Meal meal) {
        if (meal.getId() == null) {
            mealController.create(meal);
        } else {
            mealController.update(meal, meal.getId());
        }
        return "redirect:meals";
    }

    @GetMapping("getMealsFilter")
    public String getWithFilter(HttpServletRequest request, Model model) {
            LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
            LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
            LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
            LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
            model.addAttribute("meals", mealController.getBetween(startDate, startTime, endDate, endTime));
            return "meals";
    }
}
