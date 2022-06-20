package za.co.wethinkcode.robotworlds.world;

import com.google.gson.Gson;
import za.co.wethinkcode.robotworlds.ConfigFileJson;
import za.co.wethinkcode.robotworlds.Position;
import za.co.wethinkcode.robotworlds.robot.Robot;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class World {

    private Position topLeft;
    private Position bottomRight;

    private SquareObstacle[] obstacles;

    public ArrayList<Robot> robots;
    public int visibility = 5;

    public World(
            int width,
            int height,
            ArrayList<Robot> listOfRobots,
            SquareObstacle[] listOfObstacles
    ) {
        this.setBounds(width, height);
        this.robots = listOfRobots;
        this.obstacles = listOfObstacles;
    }

    public World(ArrayList<Robot> listOfRobots) {
        this.obstacles = new SquareObstacle[]{};
        this.robots = listOfRobots;
    }

    public void showObstacles() {
        System.out.println("There are some obstacles");
        for (Obstacle obstacle : obstacles) {
            System.out.println(
                    "- At position " + obstacle.getBottomLeftX() + "," +
                            obstacle.getBottomLeftY() + " (to " +
                            (obstacle.getBottomLeftX() +
                                    obstacle.getSize() - 1) + "," +
                            (obstacle.getBottomLeftY() +
                                    obstacle.getSize() - 1) + ")");
        }
    }

    private void setBounds(int width, int height) {
        int absWidth = Math.abs(width);
        int absHeight = Math.abs(height);

        topLeft = new Position(-absWidth/2, absHeight/2);
        bottomRight = new Position(absWidth/2, -absHeight/2);
    }

    public SquareObstacle[] getObstacles() {
        return obstacles;
    }

    public Position getTopLeft() {
        return topLeft;
    }

    public Position getBottomRight() {
        return bottomRight;
    }

    public void setTopLeft(Position topLeftPosition) {
        this.topLeft = topLeftPosition;
    }

    public void setBottomRight(Position bottomRightPosition) {
        this.bottomRight = bottomRightPosition;
    }

    public ArrayList<Robot> getRobots() {
        return robots;
    }

    public void setObstacles(SquareObstacle[] listOfObstacles) {
        this.obstacles = listOfObstacles;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }
}
