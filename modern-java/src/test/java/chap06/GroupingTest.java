package chap06;

import chap04.Dish;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

public class GroupingTest {

    enum CaloricLevel { DIET, NORMAL, FAT }

    static ObjectWriter objectWriter = new ObjectMapper().writerWithDefaultPrettyPrinter();

    static List<Dish> menuList;
    static Map<String, List<String>> dishTags;

    @BeforeAll
    static void dataset() {
        menuList = Arrays.asList(
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

        dishTags = new HashMap<>();
        dishTags.put("pork", Arrays.asList("greasy", "salty"));
        dishTags.put("beef", Arrays.asList("salty", "roasted"));
        dishTags.put("chicken", Arrays.asList("fried", "crisp"));
        dishTags.put("french fries", Arrays.asList("greasy", "fried"));
        dishTags.put("rice", Arrays.asList("light", "natural"));
        dishTags.put("season fruit", Arrays.asList("fresh", "natural"));
        dishTags.put("pizza", Arrays.asList("greasy", "salty"));
        dishTags.put("prawns", Arrays.asList("tasty", "roasted"));
        dishTags.put("salmon", Arrays.asList("delicious", "fresh"));
    }

    @Test
    void groupingByDishType() throws JsonProcessingException {
        Map<Dish.Type, List<Dish>> dishByType = menuList.stream()
                .collect(groupingBy(Dish::getType));
        System.out.println(objectWriter.writeValueAsString(dishByType));
    }

    @Test
    void groupingByCaloricLevel() throws JsonProcessingException {
        Map<CaloricLevel, List<Dish>> dishByCaloricLevel = menuList.stream()
                .collect(groupingBy(
                        dish-> {
                            if(dish.getCalories() <= 400) return CaloricLevel.DIET;
                            else if(dish.getCalories() <= 700) return CaloricLevel.NORMAL;
                            else return CaloricLevel.FAT;
                        }
                ));
        System.out.println(objectWriter.writeValueAsString(dishByCaloricLevel));
    }

    @Test
    void multiGrouping() throws JsonProcessingException {
        Map<Dish.Type, Map<CaloricLevel, List<Dish>>> dishesByTypeCaloricLevel = menuList.stream()
                .collect(
                        groupingBy(Dish::getType,
                                groupingBy(dish -> {
                                                if(dish.getCalories() <= 400) return CaloricLevel.DIET;
                                                else if(dish.getCalories() <= 700) return CaloricLevel.NORMAL;
                                                else return CaloricLevel.FAT;
                                        }))
                );

        System.out.println(objectWriter.writeValueAsString(dishesByTypeCaloricLevel));
    }

    @Test
    void partitioningGrouping() throws JsonProcessingException {
        Map<Boolean, List<Dish>> partitioningMenu = menuList.stream()
                    .collect(Collectors.partitioningBy(Dish::isVegetarian));

        System.out.println(objectWriter.writeValueAsString(partitioningMenu));
    }

}
