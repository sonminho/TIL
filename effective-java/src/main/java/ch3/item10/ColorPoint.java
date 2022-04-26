package ch3.item10;

import java.awt.*;

public class ColorPoint extends Point {
    private final Color color;

    public ColorPoint(int x, int y, Color color) {
        super(x, y);
        this.color = color;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof ColorPoint)) return false;

        return super.equals(obj) && // Point 타입
                ((ColorPoint) obj).color == color;
    }
}
