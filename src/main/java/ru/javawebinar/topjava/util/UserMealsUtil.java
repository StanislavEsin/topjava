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
import java.util.ArrayList;
import java.util.HashMap;

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

        Map<LocalDate, List<UserMealWithExceed>> dateUserMealWithExceedMap = new HashMap<>();
        Map<LocalDate, Integer> memCalories = new HashMap<>();

        for (UserMeal userMeal : mealList) {
            LocalDate userMealDate = userMeal.getDateTime().toLocalDate();

            memCalories.merge(userMealDate, userMeal.getCalories(), (oldVal, newVal) -> oldVal + newVal);

            if (TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime)) {
                UserMealWithExceed userMealWithExceed = new UserMealWithExceed(
                        userMeal.getDateTime(),
                        userMeal.getDescription(),
                        userMeal.getCalories(),
                        false);

                    dateUserMealWithExceedMap.merge(userMealDate, Arrays.asList(userMealWithExceed), (oldVal, newVal) -> {
                        oldVal.add(userMealWithExceed);
                        return oldVal;
                    });
            }
        }

        List<UserMealWithExceed> result = new ArrayList<>();

        for (Map.Entry<LocalDate, Integer> currentMem : memCalories.entrySet()) {
            List<UserMealWithExceed> userMealWithExceedList = dateUserMealWithExceedMap.get(currentMem.getKey());

            if (currentMem.getValue() > caloriesPerDay) {
                for (UserMealWithExceed userMealWithExceed : userMealWithExceedList) {
                    userMealWithExceed.setExceed(true);
                    result.add(userMealWithExceed);
                }
            } else {
                result.addAll(userMealWithExceedList);
            }
        }

        return result;
    }
}