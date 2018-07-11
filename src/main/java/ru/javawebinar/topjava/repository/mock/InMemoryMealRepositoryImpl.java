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
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(meal -> save(meal, meal.getUserId()));
    }

    @Override
    public Meal save(final Meal meal, final int userId) {
        Objects.requireNonNull(meal);

        meal.setUserId(userId);

        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        } else if (get(meal.getId(), userId) == null) {
            return null;
        }

        Map<Integer, Meal> meals = repository.computeIfAbsent(userId, key -> new ConcurrentHashMap<>());
        meals.put(meal.getId(), meal);

        return meal;
    }

    @Override
    public boolean delete(final int id, final int userId) {
        Map<Integer, Meal> meals = repository.get(userId);
        return meals != null && meals.remove(id) != null;
    }

    @Override
    public Meal get(final int id, final int userId) {
        Map<Integer, Meal> meals = repository.get(userId);
        return meals == null ? null : meals.get(id);
    }

    @Override
    public List<Meal> getAll(final int userId) {
        return getAllAsStream(userId, meal -> true).collect(Collectors.toList());
    }

    @Override
    public List<Meal> getBetween(final LocalDateTime startDateTime, final LocalDateTime endDateTime, final int userId) {
        Objects.requireNonNull(startDateTime);
        Objects.requireNonNull(endDateTime);

        Predicate<Meal> predicate = meal -> DateTimeUtil.isBetween(meal.getDateTime(), startDateTime, endDateTime);
        return getAllAsStream(userId, predicate).collect(Collectors.toList());
    }

    private Stream<Meal> getAllAsStream(final int userId, Predicate<Meal> predicate) {
        Objects.requireNonNull(predicate);

        Map<Integer, Meal> meals = repository.get(userId);

        return meals == null ?
                Stream.empty() :
                meals.values().stream()
                        .filter(predicate)
                        .sorted(Comparator.comparing(Meal::getDateTime).reversed());
    }
}