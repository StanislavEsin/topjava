package ru.javawebinar.topjava.services;

import ru.javawebinar.topjava.model.MealWithExceed;
import java.util.List;

public interface MealWithExceedService {
    List<MealWithExceed> findAll();
}