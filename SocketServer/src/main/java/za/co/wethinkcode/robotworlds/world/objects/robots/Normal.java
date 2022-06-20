package za.co.wethinkcode.robotworlds.world.objects.robots;

import za.co.wethinkcode.robotworlds.world.World;

public class Normal extends Robot{

    public Normal(World world, String robotName, String robotType) {

        super(world, robotName, robotType);
        this.shields = 3;
        this.shots = 3;
        this.shotDistance = 3;
        this.maxShields = shields;
        this.maxShots = 3;
    }
}
