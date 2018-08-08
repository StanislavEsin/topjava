package ru.javawebinar.topjava.web.meal;

import ru.javawebinar.topjava.model.Meal;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
@RequestMapping(value = "/meals")
public class JspMealController extends AbstractMealController {
    @GetMapping
    public ModelAndView list() {
        return new ModelAndView("meals", "meals", super.getAll());
    }

    @GetMapping(params = "form")
    public ModelAndView createForm() {
        Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        return new ModelAndView("mealForm", "meal", meal);
    }

    @PostMapping(params = "form")
    public String create(HttpServletRequest request) {
        super.create(new Meal(
                        LocalDateTime.parse(request.getParameter("dateTime")),
                        request.getParameter("description"),
                        Integer.parseInt(request.getParameter("calories"))
                )
        );

        return "redirect:/meals";
    }

    @GetMapping(value = "/{id}", params = "form")
    public ModelAndView updateForm(@PathVariable("id") Integer id) {
        return new ModelAndView("mealForm", "meal", super.get(id));
    }

    @PostMapping(value = "/{id}", params = "form")
    public String update(@PathVariable("id") Integer id, HttpServletRequest request) {
        super.update(new Meal(
                        LocalDateTime.parse(request.getParameter("dateTime")),
                        request.getParameter("description"),
                        Integer.parseInt(request.getParameter("calories"))
                ), id
        );

        return "redirect:/meals";
    }

    @GetMapping(value = "/{id}", params = "delete")
    public String delete(@PathVariable("id") Integer id) {
        super.delete(id);
        return "redirect:/meals";
    }

    @PostMapping(params = "filter")
    public ModelAndView filter(HttpServletRequest request) {
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));

        return new ModelAndView("meals", "meals",
                super.getBetween(startDate, startTime, endDate, endTime));
    }
}