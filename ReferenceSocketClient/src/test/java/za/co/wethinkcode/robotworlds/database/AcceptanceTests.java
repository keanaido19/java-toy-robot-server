package za.co.wethinkcode.robotworlds.database;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.RobotServer;
import za.co.wethinkcode.robotworlds.databaseconnectors.DbConnector;
import za.co.wethinkcode.robotworlds.databaseconnectors.ORMLiteDbConnector;

import java.io.*;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class AcceptanceTests {

    void deleteWorld(String worldName) {
        try {
            DbConnector dbConnector = new ORMLiteDbConnector();
            dbConnector.deleteWorld(worldName);
        } catch (SQLException ignored) {
        }
    }

    @BeforeEach
    @AfterEach
    void deleteTestWorld() {
        deleteWorld("testWorld");
    }

    private List<String> getServerConsoleOutput(
            String simulatedUserInput,
            String[] serverArguments
    ) {
        InputStream simulatedInputStream =
                new ByteArrayInputStream(simulatedUserInput.getBytes());
        System.setIn(simulatedInputStream);

        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));

        try {
            RobotServer.main(serverArguments);
        } catch (IOException e) {
            fail("Not expecting an exception.");
        }

        String[] linesOutput = outputStreamCaptor.toString().split("\n");
        return List.of(linesOutput);
    }

    /**
     * As a RobotWorld creator, I wish to save my carefully constructed
     * RobotWorld to a database by entering a SAVE command on the server
     * console, So that I can use the same world with different
     * collections of robots and on more than one occasion.
     */
    @Test
    void saveTheWorld() {
        String simulatedUserInput = "save testWorld\nquit\n";
        List<String> serverOutput =
                getServerConsoleOutput(simulatedUserInput, new String[] {});
        assertTrue(
                serverOutput.contains(
                        "Saved world as \"testWorld\" in database."
                )
        );
    }

    /**
     * As a huge fan of certain talented RobotWorld creators,
     * I wish to be able to load a RobotWorld from a database
     * created using the server’s SAVE command, by entering a
     * RESTORE command to my server console, thus restoring a
     * live, playable world to my server, So that I can run
     * robots in the world stored in my vast RobotWorld database.
     */
    @Test
    void restoreWorld() {
        String simulatedUserInput = "save testWorld\nrestore testWorld\nquit\n";
        List<String> serverOutput =
                getServerConsoleOutput(simulatedUserInput, new String[] {});
        assertTrue(
                serverOutput.contains(
                        "World \"testWorld\" successfully restored."
                )
        );
    }

    @Test
    void manyWorld() {
        String simulatedUserInput =
                "save testWorld\n" +
                        "save testWorld\n" +
                        "restore testWorld\n" +
                        "save testWorld2\n" +
                        "restore testWorld2\n" +
                        "quit\n";
        List<String> serverOutput =
                getServerConsoleOutput(simulatedUserInput, new String[] {});
        assertTrue(
                serverOutput.contains(
                        "Unable to save World (\"testWorld\"), " +
                                "world name already in use."
                )
        );

        assertTrue(
                serverOutput.contains(
                        "World \"testWorld\" successfully restored."
                )
        );

        assertTrue(
                serverOutput.contains(
                        "Saved world as \"testWorld2\" in database."
                )
        );

        assertTrue(
                serverOutput.contains(
                        "World \"testWorld2\" successfully restored."
                )
        );

        deleteWorld("testWorld2");
    }
}
