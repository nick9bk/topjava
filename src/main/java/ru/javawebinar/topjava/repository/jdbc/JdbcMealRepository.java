package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class JdbcMealRepository implements MealRepository {
    private static final BeanPropertyRowMapper<Meal> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Meal.class);

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final NamedParameterJdbcTemplate namedJdbcTemplate;

    @Autowired
    public JdbcMealRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("meals").usingGeneratedKeyColumns("id");
        this.namedJdbcTemplate = namedJdbcTemplate;
    }

    @Override
    public Meal save(Meal meal, int userId) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", meal.getId());
        parameters.addValue("description", meal.getDescription());
        parameters.addValue("calories", meal.getCalories());
        parameters.addValue("dateTime", meal.getDateTime());
        parameters.addValue("user_id", userId);
        if (meal.isNew()) {
            meal.setId(simpleJdbcInsert.executeAndReturnKey(parameters).intValue());
        } else {
            namedJdbcTemplate.update("UPDATE meals SET calories = :calories, description = :description, date_time = :dateTime " +
                    "WHERE user_id = :user_id and id= :id;", parameters);
        }
        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        return jdbcTemplate.update("DELETE FROM meals WHERE id=? and user_id = ?;", id, userId) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        return jdbcTemplate.queryForObject("select id, calories, description, date_time as dateTime from meals where user_id = ? and id = ?;",
                new Object[]{userId, id}, ROW_MAPPER);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return jdbcTemplate.query("select id, calories, description, date_time as dateTime from meals where user_id = ?;",
                new Object[]{userId}, ROW_MAPPER);
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return jdbcTemplate.query("select id, calories, description, date_time as dateTime from meals where user_id = ? and date_time > ? and date_time < ?;",
                new Object[]{userId, startDate, endDate}, ROW_MAPPER);
    }

}
