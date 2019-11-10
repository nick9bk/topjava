package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class UserUtil {

    public static final List<User> USERS;

    private static final int SIGN_USER = 1;

    public static int getSignUser() {
        return SIGN_USER;
    }

    static {
        int i = 0;
        USERS = Arrays.asList(new User(++i, "Nick" + i, "nick" + i + "@mail.ru", "1", Role.ROLE_ADMIN),
                new User(i++, "Nick" + i, "nick" + i + "@mail.ru", "1", Role.ROLE_USER),
                new User(i++, "Nick" + i, "nick" + i + "@mail.ru", "1", Role.ROLE_USER),
                new User(i++, "Nick" + i, "nick" + i + "@mail.ru", "1", Role.ROLE_USER),
                new User(i++, "Nick" + i, "nick" + i + "@mail.ru", "1", Role.ROLE_USER));
    }


}
