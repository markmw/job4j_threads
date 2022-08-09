package ru.job4j.pools;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class IndexSearchTest {
    @Test
    void whenSearchMiddleElementThenSuccess() {
        Integer[] array = new Integer[100000];
        int value = 55555;
        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }
        int result = IndexSearch.indexOf(array, value);
        int expected = 55555;
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void whenSearchLastElementThenSuccess() {
        Integer[] array = new Integer[100000];
        int value = 99999;
        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }
        int result = IndexSearch.indexOf(array, value);
        int expected = 99999;
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void whenCanNotFindThenReturnMinusOne() {
        Integer[] test = {5, 7, 8, 8, 0, 4, 2, 5, 1, 6, 8, 15, 2, 6, 7};
        int expected = IndexSearch.indexOf(test, 9);
        Assertions.assertEquals(expected, -1);
    }
}