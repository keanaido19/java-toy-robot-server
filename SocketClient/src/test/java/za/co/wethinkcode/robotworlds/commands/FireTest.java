package za.co.wethinkcode.robotworlds.commands;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FireTest {
    @Test
    void executeTest(){
        Fire test = new Fire();
        assertEquals("{\"robot\":\"bob\",\"command\":\"fire\",\"arguments\":[]}", test.execute("bob"));
    }
}