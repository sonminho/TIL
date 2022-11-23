package chap03;

public class Apple {
    private int weight;
    private Color color;

    Apple() {}
    Apple(int weight, Color color) {
        this.weight = weight;
        this.color = color;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
    public int getWeight() {
        return this.weight;
    }
    public void setColor(Color color) {
        this.color = color;
    }
    public Color getColor() {
        return this.color;
    }
}
