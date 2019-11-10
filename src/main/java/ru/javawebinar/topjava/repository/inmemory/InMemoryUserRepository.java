package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.AbstractNamedEntity;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.UserUtil.USERS;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);

    private Map<Integer, User> users = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger();

    {
        USERS.forEach(this::save);
    }

    @Override
    public boolean delete(int id) {
        log.info("delete {}", id);
        return users.remove(id) != null;
    }

    @Override
    public User save(User user) {
        log.info("save {}", user);
        users.put(user.getId(), user);
        if (user.isNew()) {
            user.setId(counter.incrementAndGet());
        }
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User get(int id) {
        log.info("get {}", id);
        return users.get(id);
    }

    @Override
    public List<User> getAll() {
        log.info("getAll");
        return users.values().stream().sorted(Comparator.comparing(AbstractNamedEntity::getName)).collect(Collectors.toList());
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        return users.values().stream().filter(user -> user.getName().equalsIgnoreCase(email)).findFirst().get();
    }
}
