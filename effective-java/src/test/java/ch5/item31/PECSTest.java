package ch5.item31;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

public class PECSTest {
    
    public static <E> Set<E> union(Set<? extends E> s1, Set<? extends  E> s2) { // 생산자
        Set<E> result = new HashSet<>(s1);
        result.addAll(s2);
        return result;
    }

    @Test
    void pecsTest() {
        Set<Integer> integers = new HashSet<>();
        Set<Double> doubles = new HashSet<>();

        integers.add(1);
        integers.add(3);
        integers.add(5);
        doubles.add(2.0);
        doubles.add(3.0);

        Set<Number> numbers = this.<Number>union(integers, doubles);

        System.out.println(numbers);
    }
}
