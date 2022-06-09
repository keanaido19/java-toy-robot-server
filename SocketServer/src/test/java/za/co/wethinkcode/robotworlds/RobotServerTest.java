package za.co.wethinkcode.robotworlds;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RobotServerTest {

    @Test
    void portCheck(){
        assertFalse(RobotServer.portCheck("12345"));
        assertFalse(RobotServer.portCheck("145"));
        assertFalse(RobotServer.portCheck("123s"));
        assertFalse(RobotServer.portCheck("eeee"));
        assertTrue(RobotServer.portCheck("2345"));

    }

}