package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import java.util.List;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealServiceImpl implements MealService {
    @Autowired
    private final MealRepository repository;

    public MealServiceImpl(final MealRepository repository) {
        this.repository = repository;
    }

    @Override
    public Meal save(final Meal meal, final int userId) {
        return repository.save(meal, userId);
    }

    @Override
    public Meal get(final int id, final int userId) throws NotFoundException {
        return checkNotFoundWithId(repository.get(id, userId), id);
    }

    @Override
    public Meal update(final Meal meal, final int userId) throws NotFoundException {
        return checkNotFoundWithId(repository.save(meal, userId), meal.getId());
    }

    @Override
    public void delete(final int id, final int userId) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id, userId), id);
    }

    @Override
    public List<Meal> getAll(final int userId) {
        return repository.getAll(userId);
    }

    @Override
    public List<Meal> getBetweenDateTimes(final LocalDateTime startDateTime, final LocalDateTime endDateTime,
                                          final int userId) {
        return repository.getBetween(startDateTime, endDateTime, userId);
    }
}