package ru.javawebinar.topjava.repository.datajpa;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class DataJpaMealRepositoryImpl implements MealRepository {
    private static final Sort SORT_DATE_TIME = new Sort(Sort.Direction.DESC, "dateTime");

    @Autowired
    private CrudMealRepository crudMealRepository;

    @Autowired
    private CrudUserRepository crudUserRepository;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        if (!meal.isNew() && get(meal.getId(), userId) == null) {
            return null;
        }

        meal.setUser(crudUserRepository.getOne(userId));
        return crudMealRepository.save(meal);
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        return crudMealRepository.deleteByIdAndUserId(id, userId) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        return crudMealRepository.getByIdAndUserId(id, userId);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return crudMealRepository.getAllByUserId(userId, SORT_DATE_TIME);
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return crudMealRepository.findByDateTimeBetweenAndUserId(startDate, endDate, userId, SORT_DATE_TIME);
    }
}