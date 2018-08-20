package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.javawebinar.topjava.UserTestData.USER;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

class RootControllerTest extends AbstractControllerTest {
    @Test
    void testUsers() throws Exception {
        mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("users"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/users.jsp"))
                .andExpect(model().attribute("users", hasSize(2)))
                .andExpect(model().attribute("users", hasItem(
                        allOf(
                                hasProperty("id", is(START_SEQ)),
                                hasProperty("name", is(USER.getName()))
                        )
                )));
    }

    @Test
    @SuppressWarnings("unchecked")
    void testMeals() throws Exception {
        List<MealWithExceed> mealsWithExceed = MealsUtil.getWithExceeded(MEALS, USER.getCaloriesPerDay());

        mockMvc.perform(get("/meals"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("meals"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/meals.jsp"))
                .andExpect(model().attribute("meals", hasSize(6)))
                .andExpect(model().attribute("meals",
                        hasItems(getMatchersAllOf(mealsWithExceed.toArray(new MealWithExceed[0])))));
    }
}