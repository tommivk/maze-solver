package mazesolver;

import org.junit.jupiter.api.Test;

import mazesolver.domain.*;
import mazesolver.enums.Direction;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

public class TremauxTest {
    Tremaux tremaux;
    Rect[][] rects;

    @BeforeEach
    public void setup() {
        Rect[][] maze = new Rect[5][5];
        Rect[][] rects = new Rect[5][5];

        for (int i = 0; i < 5; i++) {
            for (int k = 0; k < 5; k++) {
                maze[i][k] = new Rect();
                rects[i][k] = new Rect();

            }
        }
        this.rects = rects;
        /**
         * > v - - -
         * - v - - -
         * v < - - -
         * > > > > v
         * - - - - v
         */
        maze[0][0].removeRightWall();
        maze[1][0].removeLeftWall();
        maze[1][0].removeBottomWall();
        maze[1][1].removeTopWall();
        maze[1][1].removeBottomWall();
        maze[1][2].removeTopWall();
        maze[1][2].removeLeftWall();
        maze[0][2].removeRightWall();
        maze[0][2].removeBottomWall();
        maze[0][3].removeTopWall();
        maze[0][3].removeRightWall();
        maze[1][3].removeLeftWall();
        maze[1][3].removeRightWall();
        maze[2][3].removeLeftWall();
        maze[2][3].removeRightWall();
        maze[3][3].removeLeftWall();
        maze[3][3].removeRightWall();
        maze[4][3].removeLeftWall();
        maze[4][3].removeBottomWall();
        maze[4][4].removeTopWall();

        this.tremaux = new Tremaux(maze);
    }

    @Test
    public void initialCoordinatesShouldBeZero() {
        assertEquals(0, tremaux.getX());
        assertEquals(0, tremaux.getY());
    }

    @Test
    public void advancingOnMazeWithoutJunctionsWorksCorrectly() {
        tremaux.advance();
        assertEquals(1, tremaux.getX());
        assertEquals(0, tremaux.getY());
        tremaux.advance();
        assertEquals(1, tremaux.getX());
        assertEquals(1, tremaux.getY());
        tremaux.advance();
        assertEquals(1, tremaux.getX());
        assertEquals(2, tremaux.getY());
        tremaux.advance();
        assertEquals(0, tremaux.getX());
        assertEquals(2, tremaux.getY());
        tremaux.advance();
        assertEquals(0, tremaux.getX());
        assertEquals(3, tremaux.getY());
        tremaux.advance();
        assertEquals(1, tremaux.getX());
        assertEquals(3, tremaux.getY());
        tremaux.advance();
        assertEquals(2, tremaux.getX());
        assertEquals(3, tremaux.getY());
        tremaux.advance();
        assertEquals(3, tremaux.getX());
        assertEquals(3, tremaux.getY());
        tremaux.advance();
        assertEquals(4, tremaux.getX());
        assertEquals(3, tremaux.getY());
        tremaux.advance();
        assertEquals(4, tremaux.getX());
        assertEquals(4, tremaux.getY());
    }

    @Test
    public void calculateNextMoveWorks() {
        for (int i = 0; i < 10; i++) {
            tremaux.calculateNextMove();
        }
        assertEquals(4, tremaux.getX());
        assertEquals(4, tremaux.getY());
    }

    @Test
    public void isDeadEndWorks() {
        Tremaux t = new Tremaux(rects);
        rects[2][1].removeBottomWall();
        rects[2][2].removeTopWall();
        rects[2][2].removeRightWall();
        rects[2][2].removeBottomWall();
        rects[2][2].removeLeftWall();
        rects[2][3].removeTopWall();
        rects[1][2].removeRightWall();
        rects[3][2].removeLeftWall();

        t.setX(2);
        t.setY(2);
        t.setPreviousDirection(Direction.North);
        assertEquals(false, t.isDeadEnd());
        t.setPreviousDirection(Direction.East);
        assertEquals(false, t.isDeadEnd());
        t.setPreviousDirection(Direction.South);
        assertEquals(false, t.isDeadEnd());
        t.setPreviousDirection(Direction.West);
        assertEquals(false, t.isDeadEnd());

        t.setX(2);
        t.setY(3);
        t.setPreviousDirection(Direction.South);
        assertEquals(true, t.isDeadEnd());

        t.setX(2);
        t.setY(1);
        t.setPreviousDirection(Direction.North);
        assertEquals(true, t.isDeadEnd());

        t.setX(3);
        t.setY(2);
        t.setPreviousDirection(Direction.East);
        assertEquals(true, t.isDeadEnd());

        t.setX(1);
        t.setY(2);
        t.setPreviousDirection(Direction.West);
        assertEquals(true, t.isDeadEnd());
    }

    @Test
    public void turnAroundSholdWorkCorrectly() {
        tremaux.setPreviousDirection(Direction.North);
        tremaux.turnAround();
        assertEquals(Direction.South, tremaux.getPreviousDirection());
        tremaux.turnAround();
        assertEquals(Direction.North, tremaux.getPreviousDirection());
        tremaux.setPreviousDirection(Direction.East);
        tremaux.turnAround();
        assertEquals(Direction.West, tremaux.getPreviousDirection());
        tremaux.turnAround();
        assertEquals(Direction.East, tremaux.getPreviousDirection());
    }

    @Test
    public void isPreviousVisitedTwiceShouldWorkCorrectly() {
        int[][] visited = new int[6][6];
        tremaux.setX(3);
        tremaux.setY(3);
        visited[3][2] = 2;
        visited[3][4] = 2;
        visited[2][3] = 2;
        visited[4][3] = 2;
        tremaux.setVisited(visited);

        tremaux.setPreviousDirection(Direction.North);
        assertEquals(true, tremaux.isPreviousVisitedTwice());
        tremaux.setPreviousDirection(Direction.East);
        assertEquals(true, tremaux.isPreviousVisitedTwice());
        tremaux.setPreviousDirection(Direction.South);
        assertEquals(true, tremaux.isPreviousVisitedTwice());
        tremaux.setPreviousDirection(Direction.West);
        assertEquals(true, tremaux.isPreviousVisitedTwice());

        visited[3][2] = 1;
        visited[3][4] = 1;
        visited[2][3] = 1;
        visited[4][3] = 1;
        tremaux.setVisited(visited);

        tremaux.setPreviousDirection(Direction.North);
        assertEquals(false, tremaux.isPreviousVisitedTwice());
        tremaux.setPreviousDirection(Direction.East);
        assertEquals(false, tremaux.isPreviousVisitedTwice());
        tremaux.setPreviousDirection(Direction.South);
        assertEquals(false, tremaux.isPreviousVisitedTwice());
        tremaux.setPreviousDirection(Direction.West);
        assertEquals(false, tremaux.isPreviousVisitedTwice());
    }

    @Test
    public void moveToTimesVisitedShouldWorkCorrectly() {
        Tremaux t = new Tremaux(rects);
        rects[2][1].removeBottomWall();
        rects[2][2].removeTopWall();
        rects[2][2].removeRightWall();
        rects[2][2].removeBottomWall();
        rects[2][2].removeLeftWall();
        rects[2][3].removeTopWall();
        rects[1][2].removeRightWall();
        rects[3][2].removeLeftWall();

        t.setX(2);
        t.setY(2);

        int[][] visited = new int[4][4];
        visited[2][1] = 2;
        visited[2][3] = 2;
        visited[1][2] = 2;
        visited[3][2] = 1;
        t.setVisited(visited);
        t.setPreviousDirection(Direction.East);
        t.tryMoveToTimesVisited(1);
        assertEquals(3, t.getX());
        assertEquals(2, t.getY());

        visited[2][1] = 2;
        visited[2][3] = 1;
        visited[1][2] = 1;
        visited[3][2] = 1;
        t.setVisited(visited);

        t.setX(2);
        t.setY(2);

        t.tryMoveToTimesVisited(2);
        assertEquals(2, t.getX());
        assertEquals(1, t.getY());

        t.setPreviousDirection(Direction.West);

        visited[2][1] = 2;
        visited[2][3] = 2;
        visited[1][2] = 1;
        visited[3][2] = 2;
        t.setVisited(visited);

        t.setX(2);
        t.setY(2);

        t.tryMoveToTimesVisited(1);
        assertEquals(1, t.getX());
        assertEquals(2, t.getY());

        t.setPreviousDirection(Direction.South);
        visited[2][1] = 2;
        visited[2][3] = 1;
        visited[1][2] = 2;
        visited[3][2] = 2;
        t.setVisited(visited);

        t.setX(2);
        t.setY(2);

        assertEquals(false, t.tryMoveToTimesVisited(3));

        t.tryMoveToTimesVisited(1);
        assertEquals(2, t.getX());
        assertEquals(3, t.getY());

    }
}
