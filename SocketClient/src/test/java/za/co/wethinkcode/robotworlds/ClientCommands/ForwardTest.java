package za.co.wethinkcode.robotworlds.ClientCommands;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ForwardTest {
    @Test
    void executeTest(){
        Forward test = new Forward("20");
        assertEquals("{\"robot\":\"bob\",\"command\":\"forward\",\"arguments\":[\"20\"]}", test.execute("bob"));
    }
}