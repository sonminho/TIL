package chap03;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class LambdaTest {

    public <T> List<T> predicateFilter(List<T> list, Predicate<T> p) {
        List<T> results = new ArrayList<>();
        for(T t : list) {
            if(p.test(t)) {
                results.add(t);
            }
        }
        return results;
    }

    public <T> void consumeList(List<T> list, Consume<T> c) {
        List<T> results = new ArrayList<>();
        for(T t : list) {
            c.accept(t);
        }
    }

    public <T, R> List<R> map(List<T> list, Function<T, R> f) {
        List<R> results = new ArrayList<>();
        for(T t : list) {
            results.add(f.apply(t));
        }
        return results;
    }

    @Test
    void predicateTest() {
        Predicate<String> nonEmptyStringPredicate = (String s) -> !s.isEmpty();
        List<String> fruitsList = Arrays.asList( "apple", "banana", "");
        List<String> nonEmptyList = predicateFilter(fruitsList, nonEmptyStringPredicate);
        assertEquals(nonEmptyList.size(), 2);
    }

    @Test
    void consumeTest() {
        Consume<String> printUpperConsume = (String s) -> {
            System.out.println(s.toUpperCase());
        };
        consumeList(Arrays.asList("a","b","c"), printUpperConsume);
    }

    @Test
    void functionTest() {
        Function<String, Integer> function = (String s) -> s.length();
        List<Integer> list = map(Arrays.asList("apple", "banana"), function);
        for(int i : list) {
            System.out.println(i);
        }
    }

    @Test
    void supplierTest() {
        Supplier<Apple> supplier = Apple::new;
        assertNotNull(supplier.get());
    }

}