package za.co.wethinkcode.robotworlds.ClientCommands;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LaunchTest {


    @Test
    void executeTest(){
        Launch test = new Launch("normal", "bob");
        assertEquals("{\"robot\":\"bob\",\"command\":\"launch\",\"arguments\":[\"normal\"]}", test.execute("bob"));
    }
}