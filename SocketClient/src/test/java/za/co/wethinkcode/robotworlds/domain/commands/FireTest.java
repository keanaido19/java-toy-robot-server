package za.co.wethinkcode.robotworlds.domain.commands;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.commands.Fire;

import static org.junit.jupiter.api.Assertions.*;

class FireTest {
    @Test
    void executeTest(){
        Fire test = new Fire();
        assertEquals("{\"robot\":\"bob\",\"command\":\"fire\",\"arguments\":[]}", test.execute("bob"));
    }
}