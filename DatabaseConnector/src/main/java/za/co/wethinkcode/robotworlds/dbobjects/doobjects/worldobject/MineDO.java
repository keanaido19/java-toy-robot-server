package za.co.wethinkcode.robotworlds.dbobjects.doobjects.worldobject;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "mines")
public class MineDO extends WorldObjectDO {

    @DatabaseField(generatedId = true)
    private int mineID;

    public MineDO() {}

    public int getMineID() {
        return mineID;
    }

    public void setMineID(int mineID) {
        this.mineID = mineID;
    }
}

