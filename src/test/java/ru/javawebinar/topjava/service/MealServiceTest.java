package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;

@ContextConfiguration({"classpath:spring/spring-app.xml"})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(MEAL1_ID, USER_ID);
        assertMatch(meal, MEAL1);
    }

    @Test(expected = NotFoundException.class)
    public void getSomeoneElseSMeal() throws Exception {
        service.get(ADMIN_MEAL_ID, USER_ID);
    }

    @Test
    public void delete() {
        service.delete(ADMIN_MEAL_ID, ADMIN_ID);
        assertMatch(service.getAll(ADMIN_ID), ADMIN_MEAL2);
    }

    @Test(expected = NotFoundException.class)
    public void deleteSomeoneElseSMeal() throws Exception {
        service.delete(ADMIN_MEAL_ID, USER_ID);
    }

    @Test
    public void getBetweenDateTimes() {
        LocalDateTime startDateTime = LocalDateTime.of(2015, Month.MAY, 30, 10, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2015, Month.MAY, 30, 15, 0);

        List<Meal> meals = service.getBetweenDateTimes(startDateTime, endDateTime, USER_ID);
        assertMatch(meals, MEAL2, MEAL1);
    }

    @Test
    public void getAll() {
        List<Meal> meals = service.getAll(USER_ID);
        assertMatch(meals, MEALS);
    }

    @Test
    public void update() {
        Meal updated = new Meal(ADMIN_MEAL1);
        updated.setDescription("test");
        updated.setCalories(100);

        service.update(updated, ADMIN_ID);

        assertMatch(service.get(ADMIN_MEAL_ID, ADMIN_ID), updated);
    }

    @Test(expected = NotFoundException.class)
    public void updateSomeoneElseSMeal() {
        Meal updated = new Meal(ADMIN_MEAL1);
        updated.setDescription("test");
        updated.setCalories(100);

        service.update(updated, USER_ID);
    }

    @Test
    public void create() {
        Meal newMeal = new Meal(LocalDateTime.of(2016, Month.JUNE, 2, 19, 0), "Админ test", 800);

        Meal created = service.create(newMeal, ADMIN_ID);
        newMeal.setId(created.getId());

        assertMatch(service.getAll(ADMIN_ID), newMeal, ADMIN_MEAL2, ADMIN_MEAL1);
    }
}