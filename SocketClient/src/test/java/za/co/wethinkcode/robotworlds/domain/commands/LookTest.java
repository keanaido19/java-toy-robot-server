package za.co.wethinkcode.robotworlds.domain.commands;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.commands.Look;

import static org.junit.jupiter.api.Assertions.*;

class LookTest {
    @Test
    void executeTest(){
        Look test = new Look();
        assertEquals("{\"robot\":\"bob\",\"command\":\"look\",\"arguments\":[]}", test.execute("bob"));
    }
}