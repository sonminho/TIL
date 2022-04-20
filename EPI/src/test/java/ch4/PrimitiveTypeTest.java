package ch4;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PrimitiveTypeTest {

    PrimitiveType primitiveType = new PrimitiveType();

    @Test
    public void countBits() {
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

    @Test
    public void parity() {
        // given
        long var1 = 7; // 111
        long var2 = 9; // 1001

        // when
        short result1 = primitiveType.parity(var1);
        short result2 = primitiveType.parity(var2);

        // then
        Assertions.assertEquals(result1, 1);
        Assertions.assertEquals(result2, 0);
    }

}