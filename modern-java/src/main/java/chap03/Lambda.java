package chap03;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Lambda {

    @FunctionalInterface // 하나의 추상 메서드만 가짐
    public interface Predicate<T> {
        boolean test(T t);
        // boolean test2(T t); 메서드 2개 선언시 컴파일 에러
    }

    public static <T> List<T> filter(List<T> list, Predicate<T> p) {
        List<T> result = new ArrayList<>();
        for(T t : list) {
            if(p.test(t)) {
                result.add(t);
            }
        }
        return result;
    }

    @FunctionalInterface
    public interface Consumer<T> { // 인수로 T를 받아서 void 반환
        void accept(T t);
    }

    public static <T> void forEach(List<T> list, Consumer<T> c) {
        for(T t : list) {
            c.accept(t);
        }
    }

    @FunctionalInterface
    public interface Function<T, R> { // 인수로 T를 받아서 R을 반환
        R apply(T t);
    }

    public static <T, R> List<R> calStrLen(List<T> list, Function<T, R> f) {
        List<R> newList = new ArrayList<>();
        for(T t : list) {
            newList.add(f.apply(t));
        }
        return newList;
    }

    public static void main(String[] args) {
        List<String> listOfStrings = Arrays.asList("AAA", "BBB", "");
        Predicate<String> nonEmptyStringPredicate = (String s) -> !s.isEmpty();
        List<String> nonEmptyList = filter(listOfStrings, nonEmptyStringPredicate);
        System.out.println(nonEmptyList.size()); // 2

        List<String> consumeList = Arrays.asList("A","B","C");
        List<String> lowerList = new ArrayList<>();
        Consumer<String> lowerCaseAccept = (String s) -> lowerList.add(s.toLowerCase());
        forEach(consumeList, lowerCaseAccept);
        System.out.println(lowerList.toString());

        List<String> strList = Arrays.asList("red", "blue", "green");
        List<Integer> lenList = calStrLen(strList, (String s) -> s.length());
        System.out.println(lenList.toString()); // [3, 4, 5]
    }

}
