package za.co.wethinkcode.robotworlds.ClientCommands;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuitTest {
    @Test
    void executeTest(){
        Quit test = new Quit();
        assertEquals("{\"robot\":\"bob\",\"command\":\"quit\",\"arguments\":[]}", test.execute("bob"));
    }
}