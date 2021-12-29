package chap02;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FilteringApples {

    enum Color {
        RED,
        GREEN
    }

    @Getter @Setter
    @AllArgsConstructor
    public static class Apple {
        private int weight = 0;
        private Color color;

        @Override
        public String toString() {
            return String.format("Apple{color=%s, weight=%d}", color, weight);
        }
    }

    /*
     * 선택조건을 결정하는 인터페이스 정의
     */
    interface ApplePredicate {
        boolean check(Apple apple);
    }

    static class AppleWeightPredicate implements ApplePredicate {
        @Override
        public boolean check(Apple apple) {
            return apple.getWeight() > 150; // 무게 150이상인 사과
        }
    }

    static class AppleColorPredicate implements ApplePredicate {
        @Override
        public boolean check(Apple apple) {
            return apple.getColor() == Color.GREEN; // 녹색사과
        }
    }

    static class AppleRedAndHeavyPredicate implements ApplePredicate {
        @Override
        public boolean check(Apple apple) {
            return apple.getColor() == Color.RED && apple.getWeight() > 150;
        }
    }

    static List<Apple> filter(List<Apple> appleInventory, ApplePredicate p) {
        List<Apple> filteredList = new ArrayList<>();
        for(Apple apple : appleInventory) {
            if(p.check(apple)) filteredList.add(apple);
        }
        return filteredList;
    }

    public static void main(String[] args) {
        List<Apple> inventory = Arrays.asList(
                new Apple(80, Color.GREEN),
                new Apple(155, Color.GREEN),
                new Apple(180, Color.RED));

        List<Apple> filteredList = filter(inventory, new AppleRedAndHeavyPredicate());

        for(Apple apple : filteredList)
            System.out.println(apple);
    }

}
