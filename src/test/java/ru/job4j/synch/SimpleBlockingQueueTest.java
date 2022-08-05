package ru.job4j.synch;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleBlockingQueueTest {
    @Test
    public void whenOffer() {
        SimpleBlockingQueue<Integer> sbq = new SimpleBlockingQueue<>(2);
        List<Integer> list = new ArrayList<>();
        Thread producer = new Thread(
                () -> {
                    try {
                        sbq.offer(4);
                        sbq.offer(1);
                        sbq.offer(3);
                    } catch (InterruptedException ie) {
                        ie.printStackTrace();
                    }
                }
        );
        Thread consumer = new Thread(
                () -> {
                    try {
                        list.add(sbq.poll());
                        list.add(sbq.poll());
                        list.add(sbq.poll());
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                    }
                }
        );
        producer.start();
        consumer.start();
        try {
            producer.join();
            consumer.join();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
        assertThat(list).isEqualTo(List.of(4, 1, 3));
    }
}