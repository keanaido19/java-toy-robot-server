package za.co.wethinkcode.robotworlds.Robot;

import za.co.wethinkcode.robotworlds.World.World;

public class Tank extends Robot{

    public Tank(World world, String robotName, String robotType) {

        super(world, robotName, robotType);
        this.shields = 5;
        this.shots = 3;
        this.shotDistance = 2;
        this.maxShields = shields;
        this.maxShots = 3;
    }
}
