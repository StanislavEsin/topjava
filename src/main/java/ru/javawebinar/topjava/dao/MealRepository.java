package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import java.util.Collection;
import java.util.Optional;

public interface MealRepository {
    Meal save(Meal meal);
    Optional<Meal> findById(Long id);
    Collection<Meal> findAll();
    void deleteById(Long id);
}