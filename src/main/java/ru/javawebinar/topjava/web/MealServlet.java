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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Meal> meals = new ArrayList<>(mealRepository.findAll());
        List<MealWithExceed> mealsWithExceed = MealsUtil.getFilteredWithExceeded(
                meals, LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY);

        req.setAttribute("mealsWithExceed", mealsWithExceed);
        req.getRequestDispatcher("meals.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String method = req.getParameter("method");
        String phase = req.getParameter("phase");

        if ("new".equals(method)) {
            newObject(req, resp, method, phase);
        } else if ("update".equals(method)) {
            update(req, resp, method, phase);
        } else if ("delete".equals(method)) {
            delete(req, resp);
        } else {
            log.info("Wrong parameter 'method' of transfer on the food page");
        }
    }

    private void newObject(final HttpServletRequest req, final HttpServletResponse resp, String method, String phase)
            throws ServletException, IOException {

        if ("start".equals(phase)) {
            req.setAttribute("method", method);
            req.getRequestDispatcher("mealsEditing.jsp").forward(req, resp);
        } else if ("end".equals(phase)) {
            LocalDateTime date = LocalDateTime.parse(req.getParameter("dateTime"));
            String description = req.getParameter("description");
            int calories = Integer.valueOf(req.getParameter("calories"));

            Meal meal = new Meal(date, description, calories);
            mealRepository.save(meal);

            doGet(req, resp);
        } else {
            log.info("Wrong parameter 'phase' of transfer on the food page");
        }
    }

    private void update(final HttpServletRequest req, final HttpServletResponse resp, String method, String phase)
            throws ServletException, IOException {

        if ("start".equals(phase)) {
            Long meal_id = Long.valueOf(req.getParameter("meal_id"));
            Optional<Meal> meal = mealRepository.findById(meal_id);

            if (meal.isPresent()) {
                req.setAttribute("method", method);
                req.setAttribute("meal", meal.get());
                req.getRequestDispatcher("mealsEditing.jsp").forward(req, resp);
            }
        } else if ("end".equals(phase)) {
            Long meal_id = Long.valueOf(req.getParameter("meal_id"));
            LocalDateTime date = LocalDateTime.parse(req.getParameter("dateTime"));
            String description = req.getParameter("description");
            int calories = Integer.valueOf(req.getParameter("calories"));

            Meal meal = new Meal(date, description, calories);
            meal.setId(meal_id);
            mealRepository.save(meal);

            doGet(req, resp);
        } else {
            log.info("Wrong parameter 'phase' of transfer on the food page");
        }
    }

    private void delete(final HttpServletRequest req, final HttpServletResponse resp)
            throws ServletException, IOException {

        Long meal_id = Long.valueOf(req.getParameter("meal_id"));
        mealRepository.deleteById(meal_id);

        doGet(req, resp);
    }
}