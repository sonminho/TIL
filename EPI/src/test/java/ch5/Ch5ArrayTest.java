package ch5;

import org.junit.jupiter.api.Assertions;
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

    @Test
    public void multiply() {
        List<Integer> num1 = Arrays.asList(4, 5);
        List<Integer> num2 = Arrays.asList(1,2,3);

        List<Integer> result = Ch5Array.multiply(num1, num2);
        StringBuilder sb = new StringBuilder();
        for(Integer i : result) sb.append(i);

        Assertions.assertEquals("5535", sb.toString());

        num1 = Arrays.asList(1,9,3,7,0,7,7,2,1);
        num2 = Arrays.asList(-7,6,1,8,3,8,2,5,7,2,8,7);
        result = Ch5Array.multiply(num1, num2);
        sb = new StringBuilder();
        for(Integer i : result) sb.append(i);

        Assertions.assertEquals("-147573952589676412927", sb.toString());
    }

    @Test
    public void canReachEnd() {
        List<Integer> list1 = Arrays.asList(2,4,1,1,0,2,3);
        List<Integer> list2 = Arrays.asList(0, 3, 4, 4, 4, 6, 6, 7);
        Assertions.assertTrue(Ch5Array.canReachEnd(list1));
        Assertions.assertFalse(Ch5Array.canReachEnd(list2));
    }

}