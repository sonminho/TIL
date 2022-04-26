package ch3.item10;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CaseInsensitiveStringTest {

    @Test
    public void caseInsensitive() {
        // given
        CaseInsensitiveString insensitiveStr = new CaseInsensitiveString("Java");
        String str = "java";

        // when
        boolean b1 = insensitiveStr.equals(str);
        boolean b2 = str.equals(insensitiveStr);
        
        // then
        assertTrue(b1);
        assertFalse(b2); // String 클래스는 CaseInsensitiveString 클래스를 알 수 없음
    }

}