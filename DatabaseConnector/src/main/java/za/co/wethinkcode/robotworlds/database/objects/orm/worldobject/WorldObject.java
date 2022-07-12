package za.co.wethinkcode.robotworlds.database.objects.orm.worldobject;

import com.j256.ormlite.field.DatabaseField;
import za.co.wethinkcode.robotworlds.database.objects.orm.World;

public abstract class WorldObject {

    @DatabaseField(canBeNull = false)
    private int width;

    @DatabaseField(canBeNull = false)
    private int height;

    @DatabaseField(canBeNull = false)
    private int x;

    @DatabaseField(canBeNull = false)
    private int y;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private World world;

    public WorldObject() {}

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }
}

