package za.co.wethinkcode.robotworlds.ClientCommands;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BackTest {
    @Test
    void executeTest(){
        Back test = new Back("20");
        assertEquals("{\"robot\":\"bob\",\"command\":\"back\",\"arguments\":[\"20\"]}", test.execute("bob"));
    }
}