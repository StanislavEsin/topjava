package ru.javawebinar.topjava.repository.jpa;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import java.util.List;
import java.time.LocalDateTime;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public class JpaMealRepositoryImpl implements MealRepository {
    @PersistenceContext
    private EntityManager em;

    @Autowired
    CriteriaBuilder builder;

    @Transactional
    @Override
    public Meal save(Meal meal, int userId) {
        Meal mealPersist = meal;

        if (meal.isNew()) {
            User ref = em.getReference(User.class, userId);
            meal.setUser(ref);
            em.persist(meal);
        } else {
            mealPersist = get(meal.getId(), userId);

            if (mealPersist != null) {
                mealPersist.setDateTime(meal.getDateTime());
                mealPersist.setCalories(meal.getCalories());
                mealPersist.setDescription(meal.getDescription());
                em.merge(mealPersist);
            }
        }

        return mealPersist;
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
//        List<Meal> meals = em.createNamedQuery(Meal.GET, Meal.class)
//                .setParameter("id", id)
//                .setParameter("userId", userId)
//                .getResultList();
//
//        return DataAccessUtils.singleResult(meals);

        CriteriaQuery<Meal> criteria = builder.createQuery(Meal.class);
        Root<Meal> m = criteria.from(Meal.class);
        criteria.select(m);

        Predicate p1 = builder.equal(m.get("id"), id);
        Predicate p2 = builder.equal(m.get("user").get("id"), userId);
        criteria.where(p1, p2);

        return em.createQuery(criteria).getResultStream().findFirst().orElse(null);
    }

    @Override
    public List<Meal> getAll(int userId) {
//        return em.createNamedQuery(Meal.GET_ALL, Meal.class)
//                .setParameter("userId", userId)
//                .getResultList();

        CriteriaQuery<Meal> criteria = builder.createQuery(Meal.class);
        Root<Meal> m = criteria.from(Meal.class);
        criteria.select(m);

        Predicate p = builder.equal(m.get("user").get("id"), userId);
        criteria.where(p).orderBy(builder.desc(m.get("dateTime")));

        return em.createQuery(criteria).getResultList();
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
//        return em.createNamedQuery(Meal.GET_BETWEEN, Meal.class)
//                .setParameter("startDate", startDate)
//                .setParameter("endDate", endDate)
//                .setParameter("userId", userId)
//                .getResultList();

        CriteriaQuery<Meal> criteria = builder.createQuery(Meal.class);
        Root<Meal> m = criteria.from(Meal.class);
        criteria.select(m);

        Predicate p1 = builder.between(m.get("dateTime"), startDate, endDate);
        Predicate p2 = builder.equal(m.get("user").get("id"), userId);
        criteria.where(p1, p2).orderBy(builder.desc(m.get("dateTime")));

        return em.createQuery(criteria).getResultList();
    }
}