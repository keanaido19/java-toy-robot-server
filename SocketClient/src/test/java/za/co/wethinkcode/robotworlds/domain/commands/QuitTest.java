package za.co.wethinkcode.robotworlds.domain.commands;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.commands.Quit;

import static org.junit.jupiter.api.Assertions.*;

class QuitTest {
    @Test
    void executeTest(){
        Quit test = new Quit();
        assertEquals("{\"robot\":\"bob\",\"command\":\"quit\",\"arguments\":[]}", test.execute("bob"));
    }
}