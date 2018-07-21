package ru.javawebinar.topjava.repository.jpa;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import java.util.List;
import java.time.LocalDateTime;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.dao.support.DataAccessUtils;

@Repository
@Transactional(readOnly = true)
public class JpaMealRepositoryImpl implements MealRepository {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
    public Meal save(Meal meal, int userId) {
        User ref = em.getReference(User.class, userId);
        meal.setUser(ref);

        if (meal.isNew()) {
            em.persist(meal);
        } else {
            if (em.createNamedQuery(Meal.UPDATE)
                    .setParameter("id", meal.getId())
                    .setParameter("userId", meal.getUser().getId())
                    .setParameter("dateTime", meal.getDateTime())
                    .setParameter("calories", meal.getCalories())
                    .setParameter("description", meal.getDescription())
                    .executeUpdate() == 0) {
                return null;
            }
        }

        return meal;
    }

    @Transactional
    @Override
    public boolean delete(int id, int userId) {
        return em.createNamedQuery(Meal.DELETE)
                .setParameter("id", id)
                .setParameter("userId", userId)
                .executeUpdate() != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        List<Meal> meals = em.createNamedQuery(Meal.GET, Meal.class)
                .setParameter("id", id)
                .setParameter("userId", userId)
                .getResultList();

        return DataAccessUtils.singleResult(meals);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return em.createNamedQuery(Meal.GET_ALL, Meal.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return em.createNamedQuery(Meal.GET_BETWEEN, Meal.class)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .setParameter("userId", userId)
                .getResultList();
    }
}