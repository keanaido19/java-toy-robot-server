package za.co.wethinkcode.robotworlds;

import za.co.wethinkcode.robotworlds.ClientCommands.Look;

import java.util.HashMap;
import java.util.List;

public class DataObject {
    int visibility;
    int[] position;
    Look.ObjectJson[] objects;

    public DataObject(
            int visibility,
            int[] position,
            Look.ObjectJson[] objects
    ) {
        this.visibility = visibility;
        this.position = position;
        this.objects = objects;
    }
}
