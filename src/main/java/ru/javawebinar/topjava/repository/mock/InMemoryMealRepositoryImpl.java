package ru.javawebinar.topjava.repository.mock;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.List;
import java.util.Comparator;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(meal -> save(meal, meal.getUserId()));
    }

    @Override
    public Meal save(final Meal meal, final int userId) {
        Objects.requireNonNull(meal);

        Meal result = null;

        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            result = meal;
        } else if (get(meal.getId(), userId) != null) {
            result = repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
        }

        return result;
    }

    @Override
    public boolean delete(final int id, final int userId) {
        return get(id, userId) != null &&  repository.remove(id) != null;
    }

    @Override
    public Meal get(final int id, final int userId) {
        Meal meal = repository.get(id);
        return meal != null && meal.getUserId() == userId ? meal : null;
    }

    @Override
    public List<Meal> getAll(final int userId) {
        return getAllAsStream(userId).collect(Collectors.toList());
    }

    @Override
    public List<Meal> getBetween(final LocalDateTime startDateTime, final LocalDateTime endDateTime, final int userId) {
        Objects.requireNonNull(startDateTime);
        Objects.requireNonNull(endDateTime);

        return getAllAsStream(userId)
                .filter(meal -> DateTimeUtil.isBetween(meal.getDateTime(), startDateTime, endDateTime))
                .collect(Collectors.toList());
    }

    private Stream<Meal> getAllAsStream(final int userId) {
        return repository.values().stream()
                .filter(meal -> meal.getUserId().equals(userId))
                .sorted(Comparator.comparing(Meal::getDate).reversed().thenComparing(Meal::getTime));
    }
}