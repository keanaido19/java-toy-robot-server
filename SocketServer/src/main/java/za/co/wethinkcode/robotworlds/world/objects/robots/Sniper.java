package za.co.wethinkcode.robotworlds.world.objects.robots;

import za.co.wethinkcode.robotworlds.world.World;

public class Sniper extends Robot{

    public Sniper(World world, String robotName, String robotType) {

        super(world, robotName, robotType);
        this.shields = 1;
        this.shots = 2;
        this.shotDistance = 5;
        this.maxShields = shields;
        this.maxShots = 2;
    }
}
