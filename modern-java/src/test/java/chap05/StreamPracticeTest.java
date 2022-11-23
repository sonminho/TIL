package chap05;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamPracticeTest {

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

}
