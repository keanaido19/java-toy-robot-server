package za.co.wethinkcode.robotworlds.world;

public class Dimensions {
    private final int width;
    private final int height;

    public Dimensions(int width, int height) {
        this.width = Math.abs(width);
        this.height = Math.abs(height);
    }

    public Dimensions(int size) {
        this.width = Math.abs(size);
        this.height = Math.abs(size);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Position getTopLeft() {
        return new Position(-width/2, height/2);
    }

    public Position getBottomRight() {
        return new Position(width/2, -height/2);
    }
}
