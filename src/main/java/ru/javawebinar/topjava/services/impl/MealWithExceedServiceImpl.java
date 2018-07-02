package ru.javawebinar.topjava.services.impl;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.services.MealService;
import ru.javawebinar.topjava.services.MealWithExceedService;
import ru.javawebinar.topjava.util.MealsUtil;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalTime;

/**
 * MealWithExceedServiceImpl
 *
 * @author Stanislav (376825@gmail.com)
 * @since 30.06.2018
 */
public class MealWithExceedServiceImpl implements MealWithExceedService {
    private final MealService mealService;
    private static final int CALORIES_PER_DAY = 2000;

    public MealWithExceedServiceImpl(final MealService mealService) {
        this.mealService = mealService;
    }

    @Override
    public List<MealWithExceed> findAll() {
        List<Meal> meals = new ArrayList<>(mealService.findAll());
        return MealsUtil.getFilteredWithExceeded(meals, LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY);
    }
}