package ch3.item11;

import ch3.item11.PhoneNumber;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PhoneNumberTest {

    @Test
    public void hashCodeTest() {
        // given
        Map<PhoneNumber, String> map = new HashMap<>();
        map.put(new PhoneNumber(707, 867, 5309), "제니");

        // when
        String getStr = map.get(new PhoneNumber(707, 867, 5309));

        // then
        assertEquals(getStr, "제니");
    }

}