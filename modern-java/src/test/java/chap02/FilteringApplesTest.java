package chap02;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class FilteringApplesTest {

    List<FilteringApples.Apple> filteringAppleInventory(List<FilteringApples.Apple> inventory, FilteringApples.ApplePredicate predicate) {
        List<FilteringApples.Apple> result = new ArrayList<>();
        for(FilteringApples.Apple apple: inventory) {
            if(predicate.check(apple)) {
                result.add(apple);
            }
        }
        return result;
    }

    /*
     * 리스트 형식으로 추상화 
     */
    <T> List<T> abstractFiltering(List<T> list, Predicate<T> predicate) {
        List<T> result = new ArrayList<>();
        for(T t : list) {
            if(predicate.test(t)) {
                result.add(t);
            }
        }
        return result;
    }

    @Test
    void filteringTest() {
        List<FilteringApples.Apple> inventory = Arrays.asList(new FilteringApples.Apple(80, FilteringApples.Color.GREEN),
                new FilteringApples.Apple(155, FilteringApples.Color.GREEN),
                new FilteringApples.Apple(120, FilteringApples.Color.RED));

        List<FilteringApples.Apple> result;

        result = filteringAppleInventory(inventory, new FilteringApples.AppleColorPredicate());
        if(result.size() > 0)
            Assertions.assertEquals(result.get(0).getColor(), FilteringApples.Color.RED);

        result = filteringAppleInventory(inventory, new FilteringApples.AppleWeightPredicate());
        if(result.size() > 0)
            Assertions.assertTrue(result.get(0).getWeight() > 150);

        result = abstractFiltering(inventory, (FilteringApples.Apple apple) -> apple.getColor().equals(FilteringApples.Color.RED));
        Assertions.assertTrue(result.size() == 1);
    }

}