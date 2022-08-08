package ru.job4j.pools;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;

class RolColSumTest {
    @Test
    public void whenSequentialSumThenColSumFour() {
        int[][] matrix = new int[][]{new int[]{1, 2}, new int[]{3, 4}};
        var sums = RolColSum.sum(matrix);
        assertThat(4).isEqualTo(sums[0].getColSum());
    }

    @Test
    public void whenSequentialSumThenRowSumSeven() {
        int[][] matrix = new int[][]{new int[]{1, 2}, new int[]{3, 4}};
        var sums = RolColSum.sum(matrix);
        assertThat(7).isEqualTo(sums[1].getRowSum());
    }

    @Test
    public void whenSequentialSumThenArray() {
        int[][] matrix = new int[][]{new int[]{1, 2}, new int[]{3, 4}};
        RolColSum.Sums[] rsl = new RolColSum.Sums[]{new RolColSum.Sums(3, 4),
                new RolColSum.Sums(7, 6)};
        assertThat(rsl).isEqualTo(RolColSum.sum(matrix));
    }

    @Test
    public void whenAsyncSumThenArray() throws ExecutionException, InterruptedException {
        int[][] matrix = new int[][]{new int[]{1, 2}, new int[]{3, 4}};
        RolColSum.Sums[] rsl = new RolColSum.Sums[]{new RolColSum.Sums(3, 4),
                new RolColSum.Sums(7, 6)};
        assertThat(rsl).isEqualTo(RolColSum.asyncSum(matrix));
    }

    @Test
    public void whenAsyncSumMatrixLengthThreeThenArray() throws ExecutionException, InterruptedException {
        int[][] array = new int[][]{new int[]{1, 2, 3}, new int[]{4, 5, 6}, new int[]{7, 8, 9}};
        RolColSum.Sums[] rsl = new RolColSum.Sums[]{new RolColSum.Sums(6, 12),
                new RolColSum.Sums(15, 15), new RolColSum.Sums(24,18)};
        assertThat(rsl).isEqualTo(RolColSum.asyncSum(array));
    }
}