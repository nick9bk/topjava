package ru.javawebinar.topjava;

import org.junit.rules.ExternalResource;

public class MyExternalResource extends ExternalResource {
    private StringBuilder watchedLog = new StringBuilder();

    public StringBuilder getWatchedLog() {
        return watchedLog;
    }
}