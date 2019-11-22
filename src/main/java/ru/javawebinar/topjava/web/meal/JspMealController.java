package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

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
    public String delete(@Param("id") int id) {
        mealController.delete(id);
        return "redirect:meals";
    }

    @GetMapping("createMeal")
    public String save(Model model) {
        final Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        model.addAttribute("meal", meal);
        return "mealForm";
    }
}
