package za.co.wethinkcode.robotworlds.world;

public class Dimensions {
    private final Position position;
    private final int width;
    private final int height;

    public Dimensions(int width, int height, Position position) {
        this.width = Math.abs(width);
        this.height = Math.abs(height);
        this.position = position;
    }

    public Dimensions(int size, Position position) {
        this(Math.abs(size), Math.abs(size), position);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    private int transformX(int x) {
        return position.getX() + x;
    }

    private int transformY(int y) {
        return position.getY() + y;
    }

    public int getMinX() {
        return transformX(-width/2);
    }

    public int getMaxX() {
        return transformX(width/2);
    }

    public int getMinY() {
        return transformY(-height/2);
    }

    public int getMaxY() {
        return transformY(height/2);
    }

    private Position transformPosition(int x, int y) {
        return new Position(transformX(x), transformY(y));
    }

    public Position getTopLeft() {
        return transformPosition(-width/2, height/2);
    }

    public Position getBottomRight() {
        return transformPosition(width/2, -height/2);
    }
}
