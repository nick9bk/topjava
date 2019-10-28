package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.MealsUtil.getFiltered;
import static ru.javawebinar.topjava.util.MealsUtil.getMeals;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);
    private MealDao mealDao = new MealDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        log.debug("doGet to meals Action -- " + action);

        if("delete".equalsIgnoreCase(action)) {
            int mealId = Integer.parseInt(request.getParameter("mealId"));
            mealDao.delete(mealId);
            response.sendRedirect("meals");
        } else if ("edit".equalsIgnoreCase(action)) {
            int mealId = Integer.parseInt(request.getParameter("mealId"));
            request.setAttribute("meal", mealDao.get(mealId));
            request.getRequestDispatcher("mealSave.jsp").forward(request, response);
        } else if ("add".equalsIgnoreCase(action)) {
            request.getRequestDispatcher("mealSave.jsp").forward(request, response);
        } else {
            List<MealTo> mealsTo = getFiltered(mealDao.getAll(), LocalTime.MIN, LocalTime.MAX, 2000);
            request.setAttribute("allMeals", mealsTo);
            request.getRequestDispatcher("meals.jsp").forward(request, response);
        }
//        response.sendRedirect("meals.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String idString = request.getParameter("id");
        log.debug("doGet to meals");
        log.debug(idString);
        log.debug(request.getParameter("date"));
        log.debug(request.getParameter("calories"));
        log.debug(request.getParameter("description"));
        Meal meal = null;
        if (idString == null || idString.isEmpty()) {
            meal = new Meal();
        } else {
            meal = mealDao.get(Integer.parseInt(idString));
        }
        meal.setCalories(Integer.parseInt(request.getParameter("calories")));
        meal.setDescription(request.getParameter("description"));
        meal.setDateTime(LocalDateTime.parse(request.getParameter("date") + " 00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        mealDao.save(meal);
        response.sendRedirect("meals");
    }
}
