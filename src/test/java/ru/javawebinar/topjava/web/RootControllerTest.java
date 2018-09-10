package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.to.UserTo;
import org.junit.jupiter.api.Test;
import org.hamcrest.Matchers;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static ru.javawebinar.topjava.TestUtil.userAuth;
import static ru.javawebinar.topjava.UserTestData.ADMIN;
import static ru.javawebinar.topjava.UserTestData.USER;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

class RootControllerTest extends AbstractControllerTest {
    @Test
    void testUsers() throws Exception {
        mockMvc.perform(get("/users")
                .with(userAuth(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("users"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/users.jsp"));
    }

    @Test
    void testUnAuth() throws Exception {
        mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    void testMeals() throws Exception {
        mockMvc.perform(get("/meals")
                .with(userAuth(USER)))
                .andDo(print())
                .andExpect(view().name("meals"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/meals.jsp"));
    }

    @Test
    void testRegister() throws Exception {
        mockMvc.perform(get("/register"))
                .andDo(print())
                .andExpect(model().attribute("register", true))
                .andExpect(model().attribute("userTo", Matchers.allOf(
                        hasProperty("name", nullValue()),
                        hasProperty("email", nullValue()),
                        hasProperty("password", nullValue()),
                        hasProperty("caloriesPerDay", is(2000))
                )))
                .andExpect(view().name("profile"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/profile.jsp"));
    }

    @Test
    void testSaveRegister() throws Exception {
        UserTo created = new UserTo(null, "New2", "sdmin@gmail.com", "passwordNew", 2500);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/register")
                .param("name", created.getName())
                .param("email", created.getEmail())
                .param("password", created.getPassword())
                .param("caloriesPerDay", created.getCaloriesPerDay().toString())
                .with(csrf());

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(model().attribute("userTo", Matchers.allOf(
                        hasProperty("name", is(created.getName())),
                        hasProperty("email", is(created.getEmail())),
                        hasProperty("password", is(created.getPassword())),
                        hasProperty("caloriesPerDay", is(created.getCaloriesPerDay()))
                )))
                .andExpect(view().name("redirect:login?message=app.registered&username=sdmin@gmail.com"));
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void testSaveRegisterDuplicateEmail() throws Exception {
        UserTo created = new UserTo(null, "New2", ADMIN.getEmail(), "passwordNew", 2500);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/register")
                .param("name", created.getName())
                .param("email", created.getEmail())
                .param("password", created.getPassword())
                .param("caloriesPerDay", created.getCaloriesPerDay().toString())
                .with(csrf());

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeHasErrors("userTo"))
                .andExpect(model().attribute("register", true))
                .andExpect(view().name("profile"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/profile.jsp"));
    }
}