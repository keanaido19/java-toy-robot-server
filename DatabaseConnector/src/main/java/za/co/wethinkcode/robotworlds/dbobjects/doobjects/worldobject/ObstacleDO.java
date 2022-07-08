package za.co.wethinkcode.robotworlds.dbobjects.doobjects.worldobject;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "obstacles")
public class ObstacleDO extends WorldObjectDO {

    @DatabaseField(generatedId = true)
    private int obstacleID;

    public ObstacleDO() {}

    public int getObstacleID() {
        return obstacleID;
    }

    public void setObstacleID(int obstacleID) {
        this.obstacleID = obstacleID;
    }
}
