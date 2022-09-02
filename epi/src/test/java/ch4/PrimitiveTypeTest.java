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

    @Test
    public void swapBits() {
        // given
        long x1 = 17; // 10001
        long x2 = 18; // 10010

        // when
        long rs1 = primitiveType.swapBits(x1, 4, 0);
        long rs2 = primitiveType.swapBits(x2, 4, 0);

        // then
        Assertions.assertEquals(rs1, 17L);
        Assertions.assertEquals(rs2, 3L);
    }

    @Test
    public void reverseBits() {
        // given
        long x1 = 1;  // 00 00 00 01
        long x2 = 18; // 00 01 00 10

        // when
        long rs1 = primitiveType.reverseBits(x1);
        long rs2 = primitiveType.reverseBits(x2);

        // then
        Assertions.assertEquals(rs1, 128L); // 128 > 10 00 00 00
        Assertions.assertEquals(rs2, 72L);  // 72  > 01 00 10 00
    }

    @Test
    public void closestIntSameBitCount() {
        // given
        long x1 = 6;
        long x2 = 0;
        long x3 = ~0;

        // when
        long rs1 = primitiveType.closestIntSameBitCount(x1);

        // then
        Assertions.assertEquals(rs1, 5);
        Assertions.assertThrows(IllegalArgumentException.class,() -> {
            primitiveType.closestIntSameBitCount(x2);
        });
        Assertions.assertThrows(IllegalArgumentException.class,() -> {
            primitiveType.closestIntSameBitCount(x3);
        });
    }
}