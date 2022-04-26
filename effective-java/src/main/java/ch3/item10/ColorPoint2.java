package ch3.item10;

import java.awt.*;

public class ColorPoint2 extends Point {
    private final Color color;

    public ColorPoint2(int x, int y, Color color) {
        super(x, y);
        this.color = color;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Point))
            return false;
        
        // Point 형이면 (x,y) 만 같은지 체크
        if(!(obj instanceof ColorPoint2))
            return obj.equals(this);

        return super.equals(obj) && ((ColorPoint2) obj).color == color;
    }
}
