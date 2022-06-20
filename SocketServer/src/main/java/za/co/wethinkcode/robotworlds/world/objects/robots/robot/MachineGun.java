package za.co.wethinkcode.robotworlds.world.objects.robots.robot;

import za.co.wethinkcode.robotworlds.world.World;

public class MachineGun extends Robot{

    public MachineGun(World world, String robotName, String robotType) {

        super(world, robotName, robotType);
        this.shields = 2;
        this.shots = 6;
        this.shotDistance = 2;
        this.maxShields = shields;
        this.maxShots = 6;
    }
}
