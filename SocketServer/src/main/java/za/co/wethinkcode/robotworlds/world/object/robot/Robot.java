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

public class Robot extends WorldObject implements
        Orientable, Obstructable, Destroyable, Healable
{
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
    public void setOrientable(boolean isOrientable) {
        this.isOrientable = isOrientable;
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public void turnLeft() {
        if (isOrientable()) direction = direction.getLeftDirection();
    }

    @Override
    public void turnRight() {
        if (isOrientable()) direction = direction.getRightDirection();
    }

    @Override
    public boolean isObstructable() {
        return isObstructable;
    }
    @Override
    public void setObstructable(boolean isObstructable) {
        this.isObstructable = isObstructable;
    }

    @Override
    public boolean obstructsPosition(Position position) {
        return getPosition().equals(position);
    }

    @Override
    public boolean obstructsWorldObject(WorldObject worldObject) {
        if (!(worldObject instanceof Obstructable)) return false;

        Obstructable o = (Obstructable) worldObject;

        return o.isObstructable() && o.obstructsPosition(this.getPosition());
    }

    @Override
    public boolean obstructsPath(
            WorldObject worldObject,
            Position endPosition
    ) {
        return false;
    }

    @Override
    public boolean isDamageable() {
        return isDamageable;
    }

    @Override
    public void setDamageable(boolean isDamageable) {
        this.isDamageable = isDamageable;
    }

    @Override
    public void damage(int damage) {
        if (!isDamageable()) return;
        health -= damage;
        if (health <= 0) destroy(null); // TODO
    }

    @Override
    public boolean isDestroyable() {
        return isDestroyable;
    }

    @Override
    public void setDestroyable(boolean isDestroyable) {
        this.isDestroyable = isDestroyable;
    }

    @Override
    public void destroy(World world) {
        if (!isDestroyable()) return;
//        TODO
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public boolean isHealable() {
        return isHealable;
    }

    @Override
    public void setHealable(boolean isHealable) {
        this.isHealable = isHealable;
    }

    @Override
    public void heal(int health) {
        if (isHealable()) this.health += health;
    }
}
