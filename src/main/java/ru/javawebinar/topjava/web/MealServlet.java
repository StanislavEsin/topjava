package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.dao.MealRepository;
import ru.javawebinar.topjava.dao.impl.MealRepositoryImpl;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.io.IOException;
import org.slf4j.Logger;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * MealServlet.
 *
 * @author Stanislav (376825@gmail.com)
 * @since 30.06.2018
 */
public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static final int CALORIES_PER_DAY = 2000;
    private final MealRepository mealRepository = new MealRepositoryImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String method = req.getParameter("method");
        String meal_id = req.getParameter("meal_id");

        if ("new".equals(method)) {
            req.getRequestDispatcher("mealsEditing.jsp").forward(req, resp);
        } else if ("update".equals(method)) {
            Optional<Meal> meal = mealRepository.findById(Long.valueOf(meal_id));

            if (meal.isPresent()) {
                req.setAttribute("meal", meal.get());
                req.getRequestDispatcher("mealsEditing.jsp").forward(req, resp);
            } else {
                resp.sendRedirect(req.getContextPath() + "/meals");
            }
        } else if ("delete".equals(method)) {
            mealRepository.deleteById(Long.valueOf(meal_id));
            resp.sendRedirect(req.getContextPath() + "/meals");
        } else {
            List<Meal> meals = new ArrayList<>(mealRepository.findAll());
            List<MealWithExceed> mealsWithExceed = MealsUtil.getFilteredWithExceeded(
                    meals, LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY);

            req.setAttribute("mealsWithExceed", mealsWithExceed);
            req.getRequestDispatcher("meals.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        Meal meal = new Meal(
                LocalDateTime.parse(req.getParameter("dateTime")),
                req.getParameter("description"),
                Integer.valueOf(req.getParameter("calories")));

        String meal_id = req.getParameter("meal_id");

        if (meal_id.isEmpty()) {
            mealRepository.save(meal);
        } else {
            meal.setId(Long.valueOf(meal_id));
            mealRepository.save(meal);
        }

        resp.sendRedirect(req.getContextPath() + "/meals");
    }
}