package ch3.item10;

public class Point {
    private final int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if( !(obj instanceof Point) ) return false;

        Point p = (Point) obj;
        return p.x == this.x && p.y == this.y; // ( x, y ) 좌표가 같으면 True
    }
}
