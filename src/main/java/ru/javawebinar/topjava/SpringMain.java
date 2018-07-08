package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;
import java.util.List;
import java.util.Arrays;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 Automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ROLE_ADMIN));

            System.out.println("-------------------------------------------------");
            MealRestController mealController = appCtx.getBean(MealRestController.class);
            mealController.get(2);

            System.out.println("-------------------------------------------------");
            List<MealWithExceed> mealsWithExceed = mealController.getBetween(
                    LocalDate.of(2015, Month.MAY, 30), LocalTime.of(11, 0),
                    LocalDate.of(2015, Month.MAY, 31), LocalTime.of(15, 0));
            mealsWithExceed.forEach(System.out::println);

            System.out.println("-------------------------------------------------");
            mealController.delete(3);
        }
    }
}