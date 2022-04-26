package ch3.item10;

import java.awt.*;

public class ColorPoint3 {
    private final Point point;
    private final Color color;

    public ColorPoint3(int x, int y, Color color) {
        this.point = new Point(x, y);
        this.color = color;
    }

    public Point asPoint() {
        return this.point;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof ColorPoint3)) return false;
        ColorPoint3 cp = (ColorPoint3) obj;
        
        return cp.point.equals(this.point) && cp.color.equals(color); // 좌표와 색상이 같은지 검사
    }
}
