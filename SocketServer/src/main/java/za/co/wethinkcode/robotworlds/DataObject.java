package za.co.wethinkcode.robotworlds;

import za.co.wethinkcode.robotworlds.commands.Look;

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
