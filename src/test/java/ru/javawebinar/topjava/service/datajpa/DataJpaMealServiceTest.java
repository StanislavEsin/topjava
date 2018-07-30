package ru.javawebinar.topjava.service.datajpa;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.service.AbstractMealServiceTest;
import ru.javawebinar.topjava.Profiles;
import org.springframework.test.context.ActiveProfiles;
import org.junit.Test;
import static ru.javawebinar.topjava.MealTestData.ADMIN_MEAL1;
import static ru.javawebinar.topjava.MealTestData.ADMIN_MEAL_ID;
import static ru.javawebinar.topjava.MealTestData.assertMatch;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaMealServiceTest extends AbstractMealServiceTest {
    @Test
    public void getWithUser() throws Exception {
        Meal actual = service.getWithUser(ADMIN_MEAL_ID, ADMIN_ID);
        assertMatch(actual, ADMIN_MEAL1);
        UserTestData.assertMatch(actual.getUser(), UserTestData.ADMIN);
    }
}