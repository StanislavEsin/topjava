package ru.javawebinar.topjava.services.impl;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.dao.MealRepository;
import ru.javawebinar.topjava.dao.impl.MealRepositoryImpl;
import ru.javawebinar.topjava.services.MealService;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

/**
 * MealServiceImpl
 *
 * @author Stanislav (376825@gmail.com)
 * @since 30.06.2018
 */
public class MealServiceImpl implements MealService {
    private final MealRepository mealRepository = MealRepositoryImpl.getInstance();

    @Override
    public Meal save(final Meal meal) {
        return mealRepository.insert(meal);
    }

    @Override
    public List<Meal> findAll() {
        return new ArrayList<>(mealRepository.findAll());
    }

    @Override
    public Optional<Meal> findById(final Long id) {
        return mealRepository.findById(id);
    }

    @Override
    public void deleteById(final Long id) {
        mealRepository.findById(id).ifPresent(mealRepository::delete);
    }
}