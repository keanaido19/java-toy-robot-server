package za.co.wethinkcode.robotworlds.database;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.RobotServer;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

public class AcceptanceTests {

    private void simulateServerConsole(
            String simulatedUserInput,
            String expectedLastLine
    ) {
        InputStream simulatedInputStream =
                new ByteArrayInputStream(simulatedUserInput.getBytes());
        System.setIn(simulatedInputStream);

        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));

        try {
            RobotServer.main(new String[]{});
        } catch (IOException e) {
            fail("Not expecting an exception.");
        }

        String[] linesOutput = outputStreamCaptor.toString().split("\n");
        String lastLine = linesOutput[linesOutput.length - 1];
        assertEquals(expectedLastLine, lastLine);
    }

    /**
     * As a RobotWorld creator, I wish to save my carefully constructed
     * RobotWorld to a database by entering a SAVE command on the server
     * console, So that I can use the same world with different
     * collections of robots and on more than one occasion.
     */
    @Test
    void saveTheWorld() {
        String simulatedUserInput = "save world\nquit\n";
    }
}
