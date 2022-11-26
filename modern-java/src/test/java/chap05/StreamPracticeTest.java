package chap05;

import chap04.Dish;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StreamPracticeTest {

    List<Dish> menuList = Arrays.asList(
            new Dish("pork", false, 800, Dish.Type.MEAT),
            new Dish("beef", false, 700, Dish.Type.MEAT),
            new Dish("chicken", false, 400, Dish.Type.MEAT),
            new Dish("french fries", true, 530, Dish.Type.OTHER),
            new Dish("rice", true, 350, Dish.Type.OTHER),
            new Dish("season fruit", true, 120, Dish.Type.OTHER),
            new Dish("pizza", true, 550, Dish.Type.OTHER),
            new Dish("prawns", false, 300, Dish.Type.FISH),
            new Dish("salmon", false, 450, Dish.Type.FISH)
    );
    @Test
    void flatStream() {
        List<String> words = Arrays.asList("hello", "world");
        List<String> uniqueAlpha = words.stream()
                .map(word -> word.split(""))
                .flatMap(Arrays::stream) // 하나의 평면화된 스트림 반환
                .distinct()
                .collect(Collectors.toList());

        Assertions.assertEquals(uniqueAlpha.size(), 7);
    }

    @Test
    void predicateAllMatch() {
        List<Dish> dishes = new ArrayList<>();
        dishes.add(new Dish("pizza", true, 600, Dish.Type.OTHER));
        dishes.add(new Dish("bugger", false, 550, Dish.Type.OTHER));
        dishes.add(new Dish("rice", true, 450, Dish.Type.OTHER));

        boolean isAllVegetarian = dishes.stream()
                                        .allMatch(Dish::isVegetarian);

        Assertions.assertFalse(isAllVegetarian);
    }

    @Test
    void reduceAccumulate() {
        List<Integer> nums = Arrays.asList(1,2,3);
        int result = nums.stream().reduce(0, Integer::sum);
        Assertions.assertEquals(result, 6);
    }

}
