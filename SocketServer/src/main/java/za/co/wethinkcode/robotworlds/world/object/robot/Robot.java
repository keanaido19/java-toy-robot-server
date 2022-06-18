package za.co.wethinkcode.robotworlds.world.object.robot;

import za.co.wethinkcode.robotworlds.world.Position;
import za.co.wethinkcode.robotworlds.world.World;
import za.co.wethinkcode.robotworlds.world.enums.Direction;
import za.co.wethinkcode.robotworlds.world.enums.ObjectType;
import za.co.wethinkcode.robotworlds.world.interfaces.Obstructable;
import za.co.wethinkcode.robotworlds.world.interfaces.Orientable;
import za.co.wethinkcode.robotworlds.world.interfaces.health.Destroyable;
import za.co.wethinkcode.robotworlds.world.interfaces.health.Healable;
import za.co.wethinkcode.robotworlds.world.object.WorldObject;

public class Robot extends WorldObject implements Orientable, Obstructable,
        Destroyable, Healable {
    private final String name;

    private boolean isOrientable = true;
    private boolean isObstructable = true;
    private boolean isDamageable = true;
    private boolean isDestroyable = true;
    private boolean isHealable = true;

    private Direction direction = Direction.NORTH;
    private int health = 1;

    public Robot(String name, Position position) {
        super(position, ObjectType.ROBOT);
        this.name = name;
    }

    @Override
    public boolean isOrientable() {
        return isOrientable;
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public void turnLeft() {
        direction = direction.getLeftDirection();
    }

    @Override
    public void turnRight() {
        direction = direction.getRightDirection();
    }

    @Override
    public boolean isObstructable() {
        return isObstructable;
    }

    @Override
    public boolean obstructsPosition(WorldObject worldObject) {
        if (worldObject instanceof Obstructable) {
            Obstructable o = (Obstructable) worldObject;
            if (o.isObstructable()) return o.obstructsPosition(this);
        }
        return false;
    }

    @Override
    public boolean obstructsPath(
            WorldObject worldObject,
            Position startPosition,
            Position endPosition
    ) {
        return false;
    }

    @Override
    public boolean isDamageable() {
        return isDamageable;
    }

    @Override
    public void damage(int damage) {
        health -= damage;
        if (health <= 0 && isDestroyable) destroy(null); // TODO
    }

    @Override
    public boolean isDestroyable() {
        return isDestroyable;
    }

    @Override
    public void destroy(World world) {
//        TODO
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public boolean isHealable() {
        return isHealable;
    }

    @Override
    public void heal(int health) {
        this.health += health;
    }
}
