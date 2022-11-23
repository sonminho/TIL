package chap04;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class StreamTest {

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
    void highCaloriesMenuTest() {
        List<String> highCaloriesMenu = menuList.stream() // 메뉴 리스트에서 스트림 획득
                                                .filter(dish -> dish.getCalories() > 300) // 파이프라인 연산 만들기, 고칼로리 메뉴 필터링
                                                .map(Dish::getName) // Function 인터페이스, 요리명 추출
                                                .sorted(String::compareTo) // 정렬
                                                .limit(3) // 스트림 크기 truncate
                                                .collect(Collectors.toList()); // 결과를 다른 리스트로 저장
        System.out.println(highCaloriesMenu);
    }

    
}