package za.co.wethinkcode.robotworlds.commands;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TurnTest {
    @Test
    void executeTest(){
        Turn test = new Turn("right");
        assertEquals("{\"robot\":\"bob\",\"command\":\"turn\",\"arguments\":[\"right\"]}", test.execute("bob"));
    }
}