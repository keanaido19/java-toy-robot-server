package za.co.wethinkcode.robotworlds.domain.commands;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.commands.Launch;

import static org.junit.jupiter.api.Assertions.*;

class LaunchTest {


    @Test
    void executeTest(){
        Launch test = new Launch("normal", "bob");
        assertEquals("{\"robot\":\"bob\",\"command\":\"launch\",\"arguments\":[\"normal\"]}", test.execute("bob"));
    }
}