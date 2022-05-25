package mazesolver;

import org.junit.jupiter.api.Test;

import mazesolver.domain.*;
import mazesolver.enums.Direction;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

public class WallFollowerTest {
    WallFollower wf;
    Rect[][] maze;

    @BeforeEach
    public void setup() {
        this.maze = new Rect[4][4];
        for (int i = 0; i < 4; i++) {
            for (int k = 0; k < 4; k++) {
                maze[i][k] = new Rect();
            }
        }
        this.wf = new WallFollower(maze);
    }

    @Test
    public void initalCoordinateValuesAreZero() {
        assertEquals(0, wf.getX());
        assertEquals(0, wf.getY());
    }

    @Test
    public void initialPreviousDirectionIsSet() {
        assertEquals(Direction.South, wf.getPreviousDirection());
    }

    @Test
    public void calculateNextMoveWorksCorrectly() {

        /**
         * v X X X
         * > v X X
         * X v > v
         * X > ^ E
         */
        maze[0][0].removeBottomWall();
        maze[0][1].removeTopWall();
        maze[0][1].removeRightWall();
        maze[1][1].removeLeftWall();
        maze[1][1].removeBottomWall();
        maze[1][2].removeTopWall();
        maze[1][2].removeBottomWall();
        maze[1][3].removeTopWall();
        maze[1][3].removeRightWall();
        maze[2][3].removeLeftWall();
        maze[2][3].removeTopWall();
        maze[2][2].removeBottomWall();
        maze[2][2].removeRightWall();
        maze[3][2].removeLeftWall();
        maze[3][2].removeBottomWall();
        maze[3][3].removeTopWall();

        assertEquals(0, wf.getX());
        assertEquals(0, wf.getY());

        wf.calculateNextMove();
        assertEquals(0, wf.getX());
        assertEquals(1, wf.getY());
        assertEquals(Direction.South, wf.getPreviousDirection());

        wf.calculateNextMove();
        assertEquals(1, wf.getX());
        assertEquals(1, wf.getY());
        assertEquals(Direction.East, wf.getPreviousDirection());

        wf.calculateNextMove();
        assertEquals(1, wf.getX());
        assertEquals(2, wf.getY());
        assertEquals(Direction.South, wf.getPreviousDirection());

        wf.calculateNextMove();
        assertEquals(1, wf.getX());
        assertEquals(3, wf.getY());
        assertEquals(Direction.South, wf.getPreviousDirection());

        wf.calculateNextMove();
        assertEquals(2, wf.getX());
        assertEquals(3, wf.getY());
        assertEquals(Direction.East, wf.getPreviousDirection());

        wf.calculateNextMove();
        assertEquals(2, wf.getX());
        assertEquals(2, wf.getY());
        assertEquals(Direction.North, wf.getPreviousDirection());

        wf.calculateNextMove();
        assertEquals(3, wf.getX());
        assertEquals(2, wf.getY());
        assertEquals(Direction.East, wf.getPreviousDirection());

        wf.calculateNextMove();
        assertEquals(3, wf.getX());
        assertEquals(3, wf.getY());
        assertEquals(Direction.South, wf.getPreviousDirection());

    }
}
