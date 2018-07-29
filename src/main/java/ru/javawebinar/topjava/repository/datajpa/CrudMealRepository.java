package ru.javawebinar.topjava.repository.datajpa;

import ru.javawebinar.topjava.model.Meal;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {
    int deleteByIdAndUserId(Integer id, Integer user_id);
    Meal getByIdAndUserId(Integer id, Integer user_id);
    List<Meal> getAllByUserId(Integer user_id, Sort sort);
    List<Meal> findByDateTimeBetweenAndUserId(LocalDateTime startDate, LocalDateTime endDate, Integer user_id, Sort sort);
}