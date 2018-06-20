package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );

        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime,
                                                                        LocalTime endTime, int caloriesPerDay) {

        //You can also use guava
        List<UserMealWithExceed> result = new ArrayList<>();

        Map<LocalDate, Map.Entry<AtomicBoolean, Integer>> mem = new HashMap<>();

        for (UserMeal userMeal : mealList) {
            int calories = userMeal.getCalories();
            LocalDateTime dateTime = userMeal.getDateTime();
            LocalDate date = dateTime.toLocalDate();

            mem.merge(
                    date,
                    new AbstractMap.SimpleEntry<>(new AtomicBoolean(calories > caloriesPerDay), calories),
                    (oldVal, newVal) -> {
                        int caloriesSum = oldVal.getValue() + calories;
                        oldVal.setValue(caloriesSum);
                        oldVal.getKey().set(caloriesSum > caloriesPerDay);
                        return oldVal;
                    }
            );

            if (TimeUtil.isBetween(dateTime.toLocalTime(), startTime, endTime)) {
                UserMealWithExceed userMealWithExceed = new UserMealWithExceed(
                        dateTime,
                        userMeal.getDescription(),
                        calories,
                        mem.get(date).getKey());

                result.add(userMealWithExceed);
            }
        }

        return result;
    }
}