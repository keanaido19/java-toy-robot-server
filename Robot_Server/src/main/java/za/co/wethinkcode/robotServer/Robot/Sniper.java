package za.co.wethinkcode.robotServer.Robot;

import za.co.wethinkcode.robotServer.World.World;

public class Sniper extends Robot{

    public Sniper(World world, String robotName, String robotType) {

        super(world, robotName, robotType);
        this.shields = 1;
        this.shots = 2;
        this.shotDistance = 5;
        this.maxShields = shields;
        this.maxShots = 3;
    }
}
