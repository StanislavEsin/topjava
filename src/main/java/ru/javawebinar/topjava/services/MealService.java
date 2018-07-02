package ru.javawebinar.topjava.services;

import ru.javawebinar.topjava.model.Meal;
import java.util.List;
import java.util.Optional;

public interface MealService {
    Meal save(Meal meal);
    List<Meal> findAll();
    Optional<Meal> findById(final Long id);
    void deleteById(Long id);
}