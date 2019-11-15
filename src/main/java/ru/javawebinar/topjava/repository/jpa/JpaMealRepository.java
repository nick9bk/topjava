package ru.javawebinar.topjava.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaMealRepository implements MealRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional(readOnly = false)
    public Meal save(Meal meal, int userId) {
        User user = em.getReference(User.class, userId);
        meal.setUser(user);
        if (meal.isNew()) {
            em.persist(meal);
            return meal;
        } else {
            Meal meal1 = em.getReference(Meal.class, meal.getId());
            if (meal.getUser().getId().equals(meal1.getUser().getId())) {
                return em.merge(meal);
            }
            return null;
        }
    }

    @Override
    @Transactional(readOnly = false)
    public boolean delete(int id, int userId) {
        return em.createNamedQuery(Meal.DELETE)
                .setParameter("id", id)
                .setParameter("user_id", userId).executeUpdate() == 1;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = em.find(Meal.class, id);
        if (meal != null && meal.getUser().getId() == userId) {
            return meal;
        }
        return null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return em.createNamedQuery(Meal.GET_ALL, Meal.class).setParameter("id", userId).getResultList();
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return em.createNamedQuery(Meal.GET_BETWEEN, Meal.class)
                .setParameter("id", userId)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate).getResultList();
    }
}