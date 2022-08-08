package ru.job4j.email;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {
    private final ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public void emailTo(User user) {
        String subject = "Notification " + user.getUsername() + " to email" + user.getEmail();
        String body = "Add new event to " + user.getUsername() + ".";
        pool.submit(
                () -> send(subject, body, user.getEmail())
        );
    }

    public void close() {
        pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }

    public void send(String subject, String body, String email) {
    }
}
