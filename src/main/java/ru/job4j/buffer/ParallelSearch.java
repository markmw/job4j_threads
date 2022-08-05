package ru.job4j.buffer;

import ru.job4j.synch.SimpleBlockingQueue;

public class ParallelSearch {
    public static void main(String[] args)  throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<Integer>(2);
        final Thread consumer = new Thread(
                () -> {
                    while (!Thread.currentThread().isInterrupted()) {
                        try {
                            System.out.println(queue.poll());
                        } catch (InterruptedException ie) {
                            ie.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        Thread producer = new Thread(
                () -> {
                    for (int index = 0; index != 3; index++) {
                        try {
                            queue.offer(index);
                            Thread.sleep(500);
                        } catch (InterruptedException ie) {
                            Thread.currentThread().interrupt();
                            ie.printStackTrace();
                        }
                    }
                }
        );
        producer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
    }
}
