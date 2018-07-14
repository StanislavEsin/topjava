package ru.javawebinar.topjava.repository.jdbc;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import java.util.List;
import java.time.LocalDateTime;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcMealRepositoryImpl implements MealRepository {
    private static final BeanPropertyRowMapper<Meal> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Meal.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertMeal;

    @Autowired
    public JdbcMealRepositoryImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertMeal = new SimpleJdbcInsert(jdbcTemplate).withTableName("meals").usingGeneratedKeyColumns("id");
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Meal save(Meal meal, int userId) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", meal.getId())
                .addValue("dateTime", meal.getDateTime())
                .addValue("description", meal.getDescription())
                .addValue("calories", meal.getCalories())
                .addValue("user_id", userId);

        if (meal.isNew()) {
            Number newKey = insertMeal.executeAndReturnKey(map);
            meal.setId(newKey.intValue());
        } else {
            String sqlQuery = "UPDATE meals SET dateTime=:dateTime, description=:description, calories=:calories " +
                    "WHERE id=:id AND user_id=:user_id";

            if (namedParameterJdbcTemplate.update(sqlQuery, map) == 0) {
                return null;
            }
        }

        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        String sqlQuery = "DELETE FROM meals WHERE id=? AND user_id=?";
        return jdbcTemplate.update(sqlQuery, id, userId) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        String sqlQuery = "SELECT * FROM meals WHERE id=? AND user_id=?";
        List<Meal> meals = jdbcTemplate.query(sqlQuery, ROW_MAPPER, id, userId);
        return DataAccessUtils.singleResult(meals);
    }

    @Override
    public List<Meal> getAll(int userId) {
        String sqlQuery = "SELECT * FROM meals WHERE user_id=? ORDER BY dateTime DESC";
        return jdbcTemplate.query(sqlQuery, ROW_MAPPER, userId);
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        String sqlQuery = "SELECT * FROM meals WHERE user_id=? AND dateTime >=? AND dateTime <=? ORDER BY dateTime DESC";
        return jdbcTemplate.query(sqlQuery, ROW_MAPPER, userId, startDate, endDate);
    }
}