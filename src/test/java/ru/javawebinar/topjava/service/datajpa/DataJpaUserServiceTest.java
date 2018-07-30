package ru.javawebinar.topjava.service.datajpa;

import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import static ru.javawebinar.topjava.UserTestData.USER;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.UserTestData.assertMatch;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaUserServiceTest extends AbstractUserServiceTest {
    @Test
    public void getWithMeals() throws Exception {
        User user = service.getWithMeals(USER_ID);
        assertMatch(user, USER);
        MealTestData.assertMatch(user.getMeals(), MealTestData.MEALS);
    }

    @Test(expected = NotFoundException.class)
    public void getWithMealsNotFound() throws Exception {
        service.getWithMeals(1);
    }
}