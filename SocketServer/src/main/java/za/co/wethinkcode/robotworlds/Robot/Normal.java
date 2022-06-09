package za.co.wethinkcode.robotworlds.Robot;

import za.co.wethinkcode.robotworlds.World.World;

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
