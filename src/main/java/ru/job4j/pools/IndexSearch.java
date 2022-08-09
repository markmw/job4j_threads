package ru.job4j.pools;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class IndexSearch<T> extends RecursiveTask<Integer> {
    private final T[] array;
    private final int from;
    private final int to;
    private final T value;
    private final static int LIMIT = 10;

    public IndexSearch(T[] array, int from, int to, T obj) {
        this.array = array;
        this.from = from;
        this.to = to;
        this.value = obj;
    }

    @Override
    protected Integer compute() {
        int mid = (from + to) / 2;
        if (to - from <= LIMIT) {
            return lightSearch();
        }
        IndexSearch<T> leftSearch = new IndexSearch<>(array, from, mid, value);
        IndexSearch<T> rightSearch = new IndexSearch<>(array, mid + 1, to, value);
        leftSearch.fork();
        rightSearch.fork();
        int leftIndex = leftSearch.join();
        int rightIndex = rightSearch.join();
        return Math.max(leftIndex, rightIndex);
    }

    public int lightSearch() {
        int rsl = -1;
        for (int i = from; i <= to; i++) {
            if (value.equals(array[i])) {
                rsl = i;
                break;
            }
        }
        return rsl;
    }

    public static <T> int indexOf(T[] array, T value) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        IndexSearch<T> indexSearch = new IndexSearch<>(array, 0, array.length - 1, value);
        return forkJoinPool.invoke(indexSearch);
    }
}
