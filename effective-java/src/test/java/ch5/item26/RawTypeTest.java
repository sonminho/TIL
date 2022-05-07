package ch5.item26;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RawTypeTest {

    @Test
    void unsafeAddTest() {
        List<String> strings = new ArrayList<>();
        RawType.unsafeAdd(strings, Integer.valueOf(42));
        assertThrows(ClassCastException.class, () -> {
            String s = strings.get(0); // 컴파일러가 자동으로 형변환 코드를 넣어줌, Integer 를 String 으로 변환하다 익셉션
        });
    }

}