package za.co.wethinkcode.robotworlds.world.object.obstacle;

import za.co.wethinkcode.robotworlds.world.Dimensions;
import za.co.wethinkcode.robotworlds.world.Position;
import za.co.wethinkcode.robotworlds.world.enums.ObjectType;
import za.co.wethinkcode.robotworlds.world.interfaces.Containable;
import za.co.wethinkcode.robotworlds.world.interfaces.Obstructable;
import za.co.wethinkcode.robotworlds.world.object.WorldObject;

public class SquareObstacle extends WorldObject implements
        Containable, Obstructable
{
    
    private final Dimensions squareObstacleDimensions;
    
    public SquareObstacle(int size, Position position, ObjectType objectType) {
        super(position, objectType);
        this.squareObstacleDimensions = new Dimensions(size, position);
    }

    @Override
    public boolean isContainable() {
        return true;
    }

    @Override
    public void setContainable(boolean isContainable) {}

    @Override
    public boolean isContained(Object o) {
        if (!(o instanceof Position)) return false;
        Position p = (Position) o;
        boolean withinTop = p.getY() <= squareObstacleDimensions.getMaxY();
        boolean withinBottom = p.getY() >= squareObstacleDimensions.getMinY();
        boolean withinLeft = p.getX() >= squareObstacleDimensions.getMaxX();
        boolean withinRight = p.getX() <= squareObstacleDimensions.getMinX();
        return withinTop && withinBottom && withinLeft && withinRight;
    }

    @Override
    public boolean isObstructable() {
        return true;
    }

    @Override
    public void setObstructable(boolean isObstructable) {}

    @Override
    public boolean obstructsPosition(Position position) {
        return isContained(position);
    }

    @Override
    public boolean obstructsWorldObject(WorldObject worldObject) {
        if (!(worldObject instanceof Obstructable)) return false;

        Obstructable o = (Obstructable) worldObject;

        if (!o.isObstructable()) return false;

        int minX = squareObstacleDimensions.getMinX();
        int maxX = squareObstacleDimensions.getMaxX();
        int minY = squareObstacleDimensions.getMinY();
        int maxY = squareObstacleDimensions.getMaxY();

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                Position p = new Position(x, y);
                if (o.obstructsPosition(p)) return true;
            }
        }
        return false;
    }

    @Override
    public boolean obstructsPath(
            WorldObject worldObject,
            Position endPosition
    ) {
        if (!(worldObject instanceof Obstructable)) return false;

        Obstructable o = (Obstructable) worldObject;

        if (!o.isObstructable()) return false;

        int startX = worldObject.getPosition().getX();
        int startY = worldObject.getPosition().getY();
        int endX = endPosition.getX();
        int endY = endPosition.getY();
        return false;
    }
}
