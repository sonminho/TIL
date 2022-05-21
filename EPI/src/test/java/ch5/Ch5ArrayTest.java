package ch5;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class Ch5ArrayTest {

    @Test
    public void plusOneTest() {
        List<Integer> A = Arrays.asList(1, 2, 9);
        Ch5Array.plusOne(A).forEach(i -> {
            System.out.print(i + " ");
        });
    }
}