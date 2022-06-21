package za.co.wethinkcode.robotworlds.world;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.world.objects.obstacles.SquareObstacle;

import static org.junit.jupiter.api.Assertions.*;

class SquareObstacleTest {
    @Test
    void getBottomLeftX(){
        SquareObstacle squares = new SquareObstacle(20, 50);
        assertEquals(20, squares.getBottomLeftX());
    }

    @Test
    void isPositionBlocked(){
        SquareObstacle squares = new SquareObstacle(0,0);
        Position position = new Position(2, 2);
        assertTrue(squares.blocksPosition(position));
        Position position2 = new Position(-1, -1);
        assertFalse(squares.blocksPosition(position2));
        Position position3 = new Position(5, 5);
        assertFalse(squares.blocksPosition(position3));
        Position position4 = new Position(3,3);
        assertTrue(squares.blocksPosition(position4));
    }

    @Test
    void isPathBlocked(){
        SquareObstacle squares = new SquareObstacle(0,0);
        assertTrue(squares.blocksPath(new Position(0, -5), new Position(0, 10)));
        SquareObstacle squares2 = new SquareObstacle(10, 10);
        assertTrue(squares2.blocksPath(new Position(0, 10), new Position(15, 10)));
        assertTrue(squares2.blocksPath(new Position(15, 10), new Position(0, 10)));
        assertTrue(squares2.blocksPath(new Position(10, 15), new Position(10, -5)));
    }
}