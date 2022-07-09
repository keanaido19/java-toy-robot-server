package za.co.wethinkcode.robotworlds;

import za.co.wethinkcode.robotworlds.console.Console;
import za.co.wethinkcode.robotworlds.world.World;
import za.co.wethinkcode.robotworlds.world.builders.WorldBuilder;

public class Play {
    private static World world;

    public static World getWorld() {
        return world;
    }

    public static void setWorld(World world) {
        Play.world = world;
    }

    public void start(String[] args) {
        new Console().start();
        if (null != world) return;
        world = WorldBuilder.getWorld(args);
    }

    public static void main(String[] args) {
        Play play = new Play();
        play.start(args);
    }
}
