package za.co.wethinkcode.robotworlds.world1x1;

import org.junit.jupiter.api.Nested;

class AcceptanceTests {

    /**
     * As a player
     * I want to launch my robot in the online robot world
     * So that I can break the record for the most robot kills
     */
    @Nested
    class LaunchRobotTests
            extends za.co.wethinkcode.robotworlds.world1x1.LaunchRobotTests {}

    /**
     * As a player
     * I want to launch my robot in the online robot world
     * And look at my surroundings
     * So that I can see everything in the world
     */
    @Nested
    class LookRobotTests
            extends za.co.wethinkcode.robotworlds.world1x1.LookRobotTests {}

    /**
     * As a player
     * I want to launch my robot in the online robot world
     * And get the state of my robot
     * So that I can use the information to plan my next move
     */
    @Nested
    class StateRobotTests
            extends za.co.wethinkcode.robotworlds.world1x1.StateRobotTests {}

    /**
     * As a player I want to launch my robot in the online robot world And
     * move my robot forward, so I can explore the world
     */
    @Nested
    class ForwardRobotTests
            extends za.co.wethinkcode.robotworlds.world1x1.ForwardRobotTests {}
}
