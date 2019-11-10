package ru.javawebinar.topjava.util;

import org.springframework.context.ApplicationContext;

public class SpringApplicationHolder {
    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static void setApplicationContext(ApplicationContext applicationContext) {
        SpringApplicationHolder.applicationContext = applicationContext;
    }
}
