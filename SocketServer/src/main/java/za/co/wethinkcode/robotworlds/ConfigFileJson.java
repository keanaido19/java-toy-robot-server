package za.co.wethinkcode.robotworlds;

import za.co.wethinkcode.robotworlds.world.SquareObstacle;

public class ConfigFileJson {

        GridJson gridSize;
        int visibility;
        SquareObstacle[] obstacles;
        int shieldRepairTime;
        int reloadTime;
        int maxShieldStrength;

    public GridJson getGridSize() {
        return gridSize;
    }

    public int getVisibility() {
        return visibility;
    }

    public SquareObstacle[] getObstacles() {
        return obstacles;
    }

    public int getShieldRepairTime() {
        return shieldRepairTime;
    }

    public int getReloadTime() {
        return reloadTime;
    }

    public ConfigFileJson(GridJson gridSize, int visibility, SquareObstacle[] obstacles,
                          int shieldRepairTime, int reloadTime, int maxShieldStrength) {

            this.gridSize = gridSize;
            this.visibility = visibility;
            this.obstacles = obstacles;
            this.shieldRepairTime = shieldRepairTime;
            this.reloadTime = reloadTime;
            this.maxShieldStrength = maxShieldStrength;
        }

    public static class GridJson{
        int x;
        int y;

        public GridJson(int x, int y){
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }
    }

