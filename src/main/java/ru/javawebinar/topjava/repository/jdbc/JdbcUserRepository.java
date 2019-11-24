package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.data.util.Pair;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class JdbcUserRepository implements UserRepository {

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    private static final ResultSetExtractor<List<Pair<Integer, Role>>> ROW_MAPPER_ROLE = new ResultSetExtractor<List<Pair<Integer, Role>>>() {
        @Override
        public List<Pair<Integer, Role>> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
            List<Pair<Integer, Role>> roles = new LinkedList<>();
            while(resultSet.next()){
                roles.add(Pair.of(resultSet.getInt("user_id"), Role.valueOf(resultSet.getString("role"))));
            }
            return roles;
        }
    };

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    @Autowired
    public JdbcUserRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public User save(User user) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);

        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            user.setId(newKey.intValue());
        } else if (namedParameterJdbcTemplate.update(
                "UPDATE users SET name=:name, email=:email, password=:password, " +
                        "registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id", parameterSource) == 0) {
            return null;
        }
        insertRoles(user);
        return user;
    }

    private void insertRoles(User user) {
        jdbcTemplate.update("DELETE from user_roles where user_id = ?", user.getId());
        Set<Role> roles = user.getRoles();
        if (roles == null) {
            return;
        }
        for (Role role: roles) {
            jdbcTemplate.batchUpdate("insert into user_roles(user_id, role) VALUES (?, ?)",
                    new BatchPreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement ps, int i)
                                throws SQLException {
                            ps.setInt(1, user.getId());
                            ps.setString(2, role.name());
                        }

                        @Override
                        public int getBatchSize() {
                            return user.getRoles().size();
                        }
                    });
        }

    }

    @Override
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        return getUser("SELECT * FROM users WHERE id=?", id);
    }

    @Override
    public User getByEmail(String email) {
        return getUser("SELECT * FROM users WHERE email=?", email);
    }

    private User getUser(String sql, Object id) {
        List<User> users = jdbcTemplate.query(sql, JdbcUserRepository.ROW_MAPPER, id);
        User user = DataAccessUtils.singleResult(users);
        if (user != null) {
            Collection<Role> roles = Objects.requireNonNull(jdbcTemplate.query("SELECT user_id, role FROM user_roles WHERE user_id = ?", ROW_MAPPER_ROLE, user.getId())).stream().map(Pair::getSecond).collect(Collectors.toList());
            user.setRoles(roles);
        }
        return user;
    }

    @Override
    public List<User> getAll() {
        Map<Integer, User> userMap = jdbcTemplate.query("SELECT * FROM users ORDER BY name, email", ROW_MAPPER).stream().collect(Collectors.toMap(User::getId, user -> user));
        Objects.requireNonNull(jdbcTemplate.query("SELECT user_id, role FROM user_roles", ROW_MAPPER_ROLE))
                .forEach(
                        pair -> {
                            int userId = pair.getFirst();
                            User user = userMap.get(userId);
                            Set<Role> roles = Objects.requireNonNullElse(user.getRoles(), new LinkedHashSet<>());
                            roles.add(pair.getSecond());
                            user.setRoles(roles);
                        });
        return new ArrayList<>(userMap.values());
    }
}
