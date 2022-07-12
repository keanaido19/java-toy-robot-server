package za.co.wethinkcode.robotworlds.database.objects.orm.worldobject;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "mines")
public class Mine extends WorldObject {

    @DatabaseField(generatedId = true)
    private int mineID;

    public Mine() {}

    public int getMineID() {
        return mineID;
    }

    public void setMineID(int mineID) {
        this.mineID = mineID;
    }
}

