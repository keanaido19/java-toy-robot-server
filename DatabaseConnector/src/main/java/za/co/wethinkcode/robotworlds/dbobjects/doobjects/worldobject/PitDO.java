package za.co.wethinkcode.robotworlds.dbobjects.doobjects.worldobject;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "pits")
public class PitDO extends WorldObjectDO {

    @DatabaseField(generatedId = true)
    private int pitID;

    public PitDO() {}

    public int getPitID() {
        return pitID;
    }

    public void setPitID(int pitID) {
        this.pitID = pitID;
    }
}

