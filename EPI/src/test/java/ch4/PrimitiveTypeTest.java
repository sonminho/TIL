package ch4;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PrimitiveTypeTest {

    PrimitiveType primitiveType = new PrimitiveType();

    @Test
    void countBits() {
        // given
        int var1 = 11; // 1011
        int var2 = 12; // 1100

        // when
        int result1 = primitiveType.countBits(var1);
        int result2 = primitiveType.countBits(var2);

        // then
        Assertions.assertEquals(result1, 3);
        Assertions.assertEquals(result2, 2);
    }

}