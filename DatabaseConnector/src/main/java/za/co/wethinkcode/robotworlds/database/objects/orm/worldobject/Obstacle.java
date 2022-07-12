package za.co.wethinkcode.robotworlds.database.objects.orm.worldobject;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "obstacles")
public class Obstacle extends WorldObject {

    @DatabaseField(generatedId = true)
    private int obstacleID;

    public Obstacle() {}

    public int getObstacleID() {
        return obstacleID;
    }

    public void setObstacleID(int obstacleID) {
        this.obstacleID = obstacleID;
    }
}
