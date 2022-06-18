package za.co.wethinkcode.robotworlds.world.interfaces.health;

import za.co.wethinkcode.robotworlds.world.World;

public interface Destroyable extends Damageable{
    public boolean isDestroyable();

    public void destroy(World world);
}
