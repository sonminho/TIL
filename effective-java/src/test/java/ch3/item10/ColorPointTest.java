package ch3.item10;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class ColorPointTest {

    @Test
    public void 대칭성_위배() {
        // given
        Point point = new Point(1,1);
        ColorPoint colorPoint = new ColorPoint(1, 1, Color.RED);

        // when
        boolean b1 = point.equals(colorPoint);
        boolean b2 = colorPoint.equals(point);

        // then
        assertTrue(b1);
        assertFalse(b2);
    }

    @Test
    public void 추이성_위배() {
        // given
        ColorPoint2 cp = new ColorPoint2(1,2,Color.RED);
        Point p = new Point(1,2);
        ColorPoint2 cp2 = new ColorPoint2(1,2,Color.BLACK);

        // when
        boolean b1 = cp.equals(p);
        boolean b2 = p.equals(cp2);
        boolean b3 = cp.equals(cp2);

        // then
        assertTrue(b1);
        assertTrue(b2);
        assertFalse(b3); // 추이성이 위배된다!
    }

    @Test
    public void 컴포지션_활용() {
        // given
        ColorPoint3 cp = new ColorPoint3(1,2,Color.RED);
        Point p = new Point(1,2);
        ColorPoint3 cp2 = new ColorPoint3(1,2,Color.BLACK);

        // when
        boolean b1 = cp.asPoint().equals(p);
        boolean b2 = p.equals(cp2.asPoint());
        boolean b3 = cp.equals(cp2);

        // then
        assertTrue(b1);
        assertTrue(b2);
        assertFalse(b3); // Color 필드값이 서로 다름
    }
}