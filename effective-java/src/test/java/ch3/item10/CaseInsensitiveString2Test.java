package ch3.item10;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CaseInsensitiveString2Test {

    @Test
    public void insensitiveString() {
        // given
        CaseInsensitiveString2 caseStr = new CaseInsensitiveString2("java");
        String str = "java";

        // when
        boolean b1 = caseStr.equals(str);
        boolean b2 = str.equals(caseStr);

        // then 동치성 만족
        assertFalse(b1);
        assertFalse(b2);
    }

}