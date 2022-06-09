package za.co.wethinkcode.robotworlds.ClientCommands;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LookTest {
    @Test
    void executeTest(){
        Look test = new Look();
        assertEquals("{\"robot\":\"bob\",\"command\":\"look\",\"arguments\":[]}", test.execute("bob"));
    }
}