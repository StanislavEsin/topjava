package ru.javawebinar.topjava.dao.impl;

import ru.javawebinar.topjava.dao.MealRepository;
import ru.javawebinar.topjava.model.Meal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Optional;
import java.util.Collection;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.slf4j.Logger;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * MealRepositoryImpl
 *
 * @author Stanislav (376825@gmail.com)
 * @since 30.06.2018
 */
public class MealRepositoryImpl implements MealRepository {
    private static final Logger log = getLogger(MealRepositoryImpl.class);
    private final ConcurrentMap<Long, Meal> ram = new ConcurrentHashMap<>();
    private final AtomicLong nextId = new AtomicLong();

    public MealRepositoryImpl() {
        init();
    }

    private void init() {
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
    }

    @Override
    public Meal save(final Meal meal) {
        if (meal.isNew()) {
            insert(meal);
        } else {
            update(meal);
        }

        return meal;
    }

    private void insert(Meal meal) {
        Long id = nextId.incrementAndGet();
        meal.setId(id);
        ram.put(id, meal);
    }

    private void update(Meal meal) {
        ram.computeIfPresent(meal.getId(), (key, value) -> meal);
    }

    @Override
    public Optional<Meal> findById(final Long id) {
        Meal result = ram.get(id);

        if (result == null) {
            log.info("Meal with id={} not found", id);
        }

        return Optional.ofNullable(result);
    }

    @Override
    public Collection<Meal> findAll() {
        return ram.values();
    }

    @Override
    public void deleteById(final Long id) {
        ram.remove(id);
    }
}