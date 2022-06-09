package za.co.wethinkcode.robotworlds.ClientCommands;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RepairTest {
    @Test
    void executeTest(){
        Repair test = new Repair();
        assertEquals("{\"robot\":\"bob\",\"command\":\"repair\",\"arguments\":[]}", test.execute("bob"));
    }
}