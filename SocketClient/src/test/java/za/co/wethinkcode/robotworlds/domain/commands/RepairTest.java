package za.co.wethinkcode.robotworlds.domain.commands;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.commands.Repair;

import static org.junit.jupiter.api.Assertions.*;

class RepairTest {
    @Test
    void executeTest(){
        Repair test = new Repair();
        assertEquals("{\"robot\":\"bob\",\"command\":\"repair\",\"arguments\":[]}", test.execute("bob"));
    }
}