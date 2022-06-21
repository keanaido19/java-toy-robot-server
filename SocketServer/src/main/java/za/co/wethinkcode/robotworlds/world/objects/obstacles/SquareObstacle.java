package za.co.wethinkcode.robotworlds.world.objects.obstacles;

import za.co.wethinkcode.robotworlds.world.Position;

public class SquareObstacle implements Obstacle {

    private final int bottomLeftX;
    private final int bottomLeftY;
    private final int size;

    public SquareObstacle(int x, int y, int size) {
        this.bottomLeftX = x;
        this.bottomLeftY = y;
        this.size = Math.abs(size);
    }

    public SquareObstacle(int x, int y) {
        this(x, y, 5);
    }

    @Override
    public int getBottomLeftX() {
        return this.bottomLeftX;
    }

    @Override
    public int getBottomLeftY() {
        return this.bottomLeftY;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean blocksPosition(Position position) {
        if(
                bottomLeftX <= position.getX()
                && position.getX() <= (getMaximumXCoordinate())
        ){
            return
                    bottomLeftY <= position.getY()
                    && position.getY() <= (getMaximumYCoordinate());
        }
        return false;
    }

    @Override
    public boolean blocksPath(Position a, Position b){
        int startX = a.getX();
        int startY = a.getY();

        int endX = b.getX();
        int endY = b.getY();

        for(int x = Math.min(startX, endX); x <= Math.max(startX, endX); x++) {
            for (
                    int y = Math.min(startY, endY);
                    y <= Math.max(startY, endY);
                    y++
            ) {
                if (blocksPosition(new Position(x, y))) return true;
            }
        }
        return false;
    }

    public int getMaximumXCoordinate() {
        return bottomLeftX + (size - 1);
    }

    public int getMaximumYCoordinate() {
        return bottomLeftY + (size - 1);
    }

    public Position getBottomLeftPosition() {
        return new Position(bottomLeftX, bottomLeftY);
    }

    public Position getTopRightPosition() {
        return new Position(getMaximumXCoordinate(), getMaximumYCoordinate());
    }

    @Override
    public String toString() {
        return " * Square Obstacle - From " + getBottomLeftPosition() +
                " To " + getTopRightPosition() +" *";
    }
}
