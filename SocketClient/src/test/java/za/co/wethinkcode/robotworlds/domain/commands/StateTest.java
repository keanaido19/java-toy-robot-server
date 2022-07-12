package za.co.wethinkcode.robotworlds.domain.commands;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.commands.State;

import static org.junit.jupiter.api.Assertions.*;

class StateTest {
    @Test
    void executeTest(){
        State test = new State();
        assertEquals("{\"robot\":\"bob\",\"command\":\"state\",\"arguments\":[]}", test.execute("bob"));
    }

}