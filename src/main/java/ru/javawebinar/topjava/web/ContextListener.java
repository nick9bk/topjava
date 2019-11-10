package ru.javawebinar.topjava.web;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.util.SpringApplicationHolder;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        SpringApplicationHolder.setApplicationContext(new ClassPathXmlApplicationContext("classpath:spring/spring-app.xml"));
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ApplicationContext applicationContext = SpringApplicationHolder.getApplicationContext();
        ((ConfigurableApplicationContext)applicationContext).close();
        SpringApplicationHolder.setApplicationContext(null);
    }
}
