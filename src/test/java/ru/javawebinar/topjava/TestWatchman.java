package ru.javawebinar.topjava;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

public class TestWatchman extends TestWatcher {
    private StringBuilder watchedLog = new StringBuilder();
    private long countTime;
    private long buffer;

    public TestWatchman(StringBuilder watchedLog) {
        this.watchedLog = watchedLog;
    }

    @Override
    protected void starting(Description description) {
        buffer = System.currentTimeMillis();
        super.starting(description);
    }

    @Override
    protected void finished(Description description) {
        watchedLog.append(description.getDisplayName()).append(" : ").append(System.currentTimeMillis() - buffer).append('\n');
        super.finished(description);
    }

    public String getWatchedLog() {
        return watchedLog.toString();
    }
}
