package mazesolver;

import org.junit.jupiter.api.Test;

import mazesolver.domain.*;
import mazesolver.enums.Direction;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

public class TremauxTest {
    Tremaux tremaux;
    Rect[][] rects;
    Rect[][] maze;
    Rect[][] mazeWithJunctions;

    @BeforeEach
    public void setup() {
        TestMaze tm = new TestMaze();
        Rect[][] rects = new Rect[5][5];

        for (int i = 0; i < 5; i++) {
            for (int k = 0; k < 5; k++) {
                rects[i][k] = new Rect(i, k);

            }
        }
        this.rects = rects;

        this.maze = tm.getMazeWithoutJunctions();
        this.mazeWithJunctions = tm.getMazeWithJunctions();

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
        assertEquals(3, tremaux.getX());
        assertEquals(2, tremaux.getY());
        tremaux.advance();
        assertEquals(4, tremaux.getX());
        assertEquals(2, tremaux.getY());
        tremaux.advance();
        assertEquals(4, tremaux.getX());
        assertEquals(3, tremaux.getY());
        tremaux.advance();
        assertEquals(4, tremaux.getX());
        assertEquals(4, tremaux.getY());
    }

    @Test
    public void calculateNextMoveWorks() {
        for (int i = 0; i < 12; i++) {
            tremaux.calculateNextMove();
        }
        assertEquals(4, tremaux.getX());
        assertEquals(4, tremaux.getY());
    }

    @Test
    public void calculateNextMoveWorksCorrectlyWithMazeWithJunctions() {
        Tremaux t = new Tremaux(mazeWithJunctions);

        t.calculateNextMove();
        assertEquals(1, t.getX());
        assertEquals(0, t.getY());

        t.calculateNextMove();
        assertEquals(1, t.getX());
        assertEquals(1, t.getY());

        t.calculateNextMove();
        assertEquals(1, t.getX());
        assertEquals(2, t.getY());

        t.calculateNextMove();
        assertEquals(0, t.getX());
        assertEquals(2, t.getY());

        t.calculateNextMove();
        assertEquals(0, t.getX());
        assertEquals(3, t.getY());

        t.calculateNextMove();
        assertEquals(0, t.getX());
        assertEquals(4, t.getY());

        t.calculateNextMove();
        assertEquals(0, t.getX());
        assertEquals(4, t.getY());

        t.calculateNextMove();
        assertEquals(0, t.getX());
        assertEquals(3, t.getY());

        t.calculateNextMove();
        assertEquals(1, t.getX());
        assertEquals(3, t.getY());

        t.calculateNextMove();
        assertEquals(2, t.getX());
        assertEquals(3, t.getY());

        t.calculateNextMove();
        assertEquals(3, t.getX());
        assertEquals(3, t.getY());

        t.calculateNextMove();
        assertEquals(3, t.getX());
        assertEquals(2, t.getY());

        t.calculateNextMove();
        assertEquals(4, t.getX());
        assertEquals(2, t.getY());

        t.calculateNextMove();
        assertEquals(4, t.getX());
        assertEquals(3, t.getY());

        t.calculateNextMove();
        assertEquals(4, t.getX());
        assertEquals(4, t.getY());
    }

    @Test
    public void calculateNextMoveTurnsAround() {
        Tremaux t = new Tremaux(rects);
        t.setPreviousDirection(Direction.East);
        t.setX(2);
        t.setY(2);

        rects[2][2].removeRightWall();
        rects[2][2].removeLeftWall();
        rects[2][2].removeTopWall();
        rects[2][2].removeBottomWall();

        rects[1][2].removeRightWall();

        rects[3][2].removeLeftWall();

        int[][] visited = new int[5][5];
        visited[1][2] = 1;
        visited[2][1] = 1;
        visited[2][3] = 1;
        visited[3][2] = 1;

        t.setVisited(visited);

        t.calculateNextMove();

        assertEquals(Direction.West, t.getPreviousDirection());
    }

    @Test
    public void calculateNextMoveMovesToOnceVisitedWhenPreviousIsVisitedTwice() {
        Tremaux t = new Tremaux(rects);
        t.setPreviousDirection(Direction.East);
        t.setX(2);
        t.setY(2);

        rects[2][2].removeRightWall();
        rects[2][2].removeLeftWall();
        rects[2][2].removeTopWall();
        rects[2][2].removeBottomWall();

        rects[1][2].removeRightWall();

        rects[3][2].removeLeftWall();

        int[][] visited = new int[5][5];
        visited[1][2] = 2;
        visited[2][1] = 2;
        visited[2][2] = 2;
        visited[2][3] = 1;
        visited[3][2] = 2;

        t.setVisited(visited);

        t.calculateNextMove();
        assertEquals(2, t.getX());
        assertEquals(3, t.getY());
    }

    @Test
    public void calculateNextMoveChoosesLeastVisitedJunction() {
        Tremaux t = new Tremaux(rects);
        t.setPreviousDirection(Direction.East);
        t.setX(2);
        t.setY(2);

        rects[2][2].removeRightWall();
        rects[2][2].removeLeftWall();
        rects[2][2].removeTopWall();
        rects[2][2].removeBottomWall();

        rects[1][2].removeRightWall();

        rects[3][2].removeLeftWall();
        rects[3][2].removeRightWall();
        rects[3][2].removeTopWall();

        int[][] visited = new int[5][5];
        visited[1][2] = 3;
        visited[2][1] = 2;
        visited[2][2] = 4;
        visited[2][3] = 4;
        visited[3][2] = 4;

        t.setVisited(visited);

        t.calculateNextMove();
        assertEquals(3, t.getX());
        assertEquals(2, t.getY());

    }

    @Test
    public void calculateNextMoveTurnsSideWaysIfNoValidPathOrContiguosJunction() {
        Tremaux t = new Tremaux(rects);
        t.setPreviousDirection(Direction.East);
        t.setX(2);
        t.setY(2);

        rects[2][2].removeRightWall();
        rects[2][2].removeLeftWall();
        rects[2][2].removeTopWall();
        rects[2][2].removeBottomWall();
        rects[1][2].removeRightWall();
        rects[3][2].removeLeftWall();

        int[][] visited = new int[5][5];
        visited[1][2] = 3;
        visited[2][1] = 2;
        visited[2][2] = 4;
        visited[2][3] = 4;
        visited[3][2] = 4;

        t.setVisited(visited);

        t.calculateNextMove();
        assertEquals(Direction.South, t.getPreviousDirection());
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

    @Test
    public void paintRectangleShouldPaintCurrentRectangle() {
        assertEquals(0, tremaux.getX());
        assertEquals(0, tremaux.getY());
        assertEquals("", maze[0][0].getRectangle().getStyle());
        tremaux.paintRectangle();
        assertEquals("-fx-background-color: rgb(255,0,0); -fx-background-insets: 4px",
                maze[0][0].getRectangle().getStyle());
    }

    @Test
    public void paintGreenShouldPaintCurrentRectangleGreen() {
        assertEquals(0, tremaux.getX());
        assertEquals(0, tremaux.getY());
        assertEquals("", maze[0][0].getRectangle().getStyle());
        tremaux.paintGreen();
        assertEquals("-fx-background-color: rgb(0,255,0); -fx-background-insets: 4px",
                maze[0][0].getRectangle().getStyle());
    }

    @Test
    public void resetShouldWorkCorrectly() {
        tremaux.solve();
        assertEquals(maze.length - 1, tremaux.getX());
        assertEquals(maze.length - 1, tremaux.getY());

        maze[0][2].paint();
        assertEquals("-fx-background-color: rgb(255,0,0); -fx-background-insets: 4px",
                maze[0][2].getRectangle().getStyle());

        maze[2][2].paintGreen();
        assertEquals("-fx-background-color: rgb(0,255,0); -fx-background-insets: 4px",
                maze[2][2].getRectangle().getStyle());

        tremaux.reset();

        assertEquals(0, tremaux.getX());
        assertEquals(0, tremaux.getY());
        assertEquals("",
                maze[0][2].getRectangle().getStyle());
        assertEquals("",
                maze[2][2].getRectangle().getStyle());
    }

    @Test
    public void turnSideWaysShouldWorkCorrectly() {
        tremaux.setPreviousDirection(Direction.South);
        tremaux.turnSideWays();
        assertEquals(Direction.East, tremaux.getPreviousDirection());
        tremaux.turnSideWays();
        assertEquals(Direction.North, tremaux.getPreviousDirection());
        tremaux.turnSideWays();
        assertEquals(Direction.West, tremaux.getPreviousDirection());
        tremaux.turnSideWays();
        assertEquals(Direction.South, tremaux.getPreviousDirection());
    }

    @Test
    public void hasContiguousJunctionShouldWorkCorrectly() {
        Tremaux t = new Tremaux(rects);

        rects[2][2].removeBottomWall();
        rects[2][2].removeRightWall();
        rects[2][2].removeLeftWall();
        rects[2][2].removeTopWall();

        rects[2][3].removeTopWall();
        rects[2][3].removeBottomWall();
        rects[2][1].removeBottomWall();

        rects[1][2].removeRightWall();
        rects[3][2].removeLeftWall();

        assertEquals(true, t.hasContiguousJunction(2, 3, Direction.North));
        assertEquals(false, t.hasContiguousJunction(2, 2, Direction.North));

        assertEquals(true, t.hasContiguousJunction(2, 1, Direction.South));
        assertEquals(false, t.hasContiguousJunction(2, 3, Direction.South));

        assertEquals(true, t.hasContiguousJunction(1, 2, Direction.East));
        assertEquals(false, t.hasContiguousJunction(2, 2, Direction.East));

        assertEquals(false, t.hasContiguousJunction(1, 2, Direction.West));
        assertEquals(true, t.hasContiguousJunction(3, 2, Direction.West));
    }

    @Test
    public void hasValidPathShouldWorkCorrectly() {
        Tremaux t = new Tremaux(rects);
        int[][] visited = new int[5][5];
        t.setVisited(visited);
        visited[2][1] = 3;
        visited[2][3] = 3;
        visited[1][2] = 3;
        visited[3][2] = 3;

        assertEquals(false, t.hasValidPath(2, 2, Direction.North));
        assertEquals(false, t.hasValidPath(2, 2, Direction.East));
        assertEquals(false, t.hasValidPath(2, 2, Direction.South));
        assertEquals(false, t.hasValidPath(2, 2, Direction.West));

        rects[2][2].removeTopWall();
        rects[2][2].removeRightWall();
        rects[2][2].removeBottomWall();
        rects[2][2].removeLeftWall();

        assertEquals(false, t.hasValidPath(2, 2, Direction.North));
        assertEquals(false, t.hasValidPath(2, 2, Direction.East));
        assertEquals(false, t.hasValidPath(2, 2, Direction.South));
        assertEquals(false, t.hasValidPath(2, 2, Direction.West));

        visited[2][3] = 1;
        assertEquals(false, t.hasValidPath(2, 2, Direction.North));
        assertEquals(true, t.hasValidPath(2, 2, Direction.East));
        assertEquals(true, t.hasValidPath(2, 2, Direction.South));
        assertEquals(true, t.hasValidPath(2, 2, Direction.West));
        visited[2][3] = 3;

        visited[2][1] = 1;
        assertEquals(true, t.hasValidPath(2, 2, Direction.North));
        assertEquals(true, t.hasValidPath(2, 2, Direction.East));
        assertEquals(false, t.hasValidPath(2, 2, Direction.South));
        assertEquals(true, t.hasValidPath(2, 2, Direction.West));
        visited[2][1] = 3;

        visited[1][2] = 1;
        assertEquals(true, t.hasValidPath(2, 2, Direction.North));
        assertEquals(false, t.hasValidPath(2, 2, Direction.East));
        assertEquals(true, t.hasValidPath(2, 2, Direction.South));
        assertEquals(true, t.hasValidPath(2, 2, Direction.West));
        visited[1][2] = 3;

        visited[3][2] = 1;
        assertEquals(true, t.hasValidPath(2, 2, Direction.North));
        assertEquals(true, t.hasValidPath(2, 2, Direction.East));
        assertEquals(true, t.hasValidPath(2, 2, Direction.South));
        assertEquals(false, t.hasValidPath(2, 2, Direction.West));

    }

    @Test
    public void getLeastVisitedJunctionWorksCorrectly() {
        Tremaux t = new Tremaux(rects);
        int[][] visited = new int[5][5];
        visited[2][1] = 1;
        visited[2][3] = 2;
        visited[1][2] = 3;
        visited[3][2] = 4;
        t.setVisited(visited);
        t.setX(2);
        t.setY(2);

        t.setPreviousDirection(Direction.North);
        assertEquals(Integer.MAX_VALUE, t.getLeastVisitedJunction());
        t.setPreviousDirection(Direction.South);
        assertEquals(Integer.MAX_VALUE, t.getLeastVisitedJunction());
        rects[2][2].removeTopWall();
        rects[2][2].removeRightWall();
        rects[2][2].removeBottomWall();
        rects[2][2].removeLeftWall();

        t.setPreviousDirection(Direction.North);
        assertEquals(Integer.MAX_VALUE, t.getLeastVisitedJunction());
        t.setPreviousDirection(Direction.East);
        assertEquals(Integer.MAX_VALUE, t.getLeastVisitedJunction());
        t.setPreviousDirection(Direction.South);
        assertEquals(Integer.MAX_VALUE, t.getLeastVisitedJunction());
        t.setPreviousDirection(Direction.West);
        assertEquals(Integer.MAX_VALUE, t.getLeastVisitedJunction());

        for (int i = 0; i < rects.length; i++) {
            for (int j = 0; j < rects.length; j++) {
                if (i > 0) {
                    rects[i][j].removeLeftWall();
                }
                if (i < rects.length) {
                    rects[i][j].removeRightWall();
                }
                if (j > 0) {
                    rects[i][j].removeTopWall();
                }
                if (j < rects.length) {
                    rects[i][j].removeBottomWall();
                }
            }
        }

        assertEquals(1, t.getLeastVisitedJunction());
        t.setPreviousDirection(Direction.East);
        visited[2][1] = 90000;
        visited[2][3] = 2;
        visited[1][2] = 3;
        visited[3][2] = 4;
        assertEquals(2, t.getLeastVisitedJunction());
        t.setPreviousDirection(Direction.West);
        visited[2][1] = 3;
        visited[2][3] = 2;
        visited[1][2] = 3;
        visited[3][2] = 1;
        t.setPreviousDirection(Direction.South);
        visited[2][1] = 0;
        visited[2][3] = 3;
        visited[1][2] = 3;
        visited[3][2] = 2;
        assertEquals(2, t.getLeastVisitedJunction());

        t.setPreviousDirection(Direction.North);
        visited[2][1] = 2;
        visited[2][3] = 0;
        visited[1][2] = 1;
        visited[3][2] = 2;
        assertEquals(1, t.getLeastVisitedJunction());
    }

    @Test
    public void tryMoveToContiguousJunctionWorksCorrectly() {
        Tremaux t = new Tremaux(rects);

        int[][] visited = new int[5][5];
        t.setVisited(visited);

        for (int i = 0; i < rects.length; i++) {
            for (int j = 0; j < rects.length; j++) {
                if (i > 0) {
                    rects[i][j].removeLeftWall();
                }
                if (i < rects.length) {
                    rects[i][j].removeRightWall();
                }
                if (j > 0) {
                    rects[i][j].removeTopWall();
                }
                if (j < rects.length) {
                    rects[i][j].removeBottomWall();
                }
            }
        }

        t.setX(2);
        t.setY(2);
        t.setPreviousDirection(Direction.North);

        visited[2][1] = 1;
        visited[2][3] = 3;
        visited[1][2] = 2;
        visited[3][2] = 2;

        boolean res = t.tryMoveToContiguousJunction(1);
        assertEquals(false, t.tryMoveToContiguousJunction(3));
        assertEquals(true, res);
        assertEquals(2, t.getX());
        assertEquals(1, t.getY());
        t.setX(2);
        t.setY(2);

        t.setPreviousDirection(Direction.South);
        visited[2][1] = 3;
        visited[2][3] = 4;
        visited[1][2] = 2;
        visited[3][2] = 4;

        assertEquals(false, t.tryMoveToContiguousJunction(3));

        res = t.tryMoveToContiguousJunction(2);
        assertEquals(true, res);
        assertEquals(1, t.getX());
        assertEquals(2, t.getY());
        t.setX(2);
        t.setY(2);

        t.setPreviousDirection(Direction.West);

        visited[2][1] = 3;
        visited[2][3] = 1;
        visited[1][2] = 2;
        visited[3][2] = 4;

        assertEquals(false, t.tryMoveToContiguousJunction(4));

        res = t.tryMoveToContiguousJunction(1);
        assertEquals(true, res);
        assertEquals(2, t.getX());
        assertEquals(3, t.getY());
        t.setX(2);
        t.setY(2);

        t.setPreviousDirection(Direction.East);

        visited[2][1] = 4;
        visited[2][3] = 4;
        visited[1][2] = 2;
        visited[3][2] = 3;

        assertEquals(false, t.tryMoveToContiguousJunction(2));

        res = t.tryMoveToContiguousJunction(3);
        assertEquals(true, res);
        assertEquals(3, t.getX());
        assertEquals(2, t.getY());

    }

    @Test
    public void tremauxShouldBeAbleToSolveMazesGeneratedByKruskal() {
        for (int i = 4; i < 70; i++) {
            Kruskal k = new Kruskal();
            Rect[][] maze = k.generateEdges(i, i);
            k.generateMaze();
            Tremaux t = new Tremaux(maze);
            assertEquals(0, t.getX());
            assertEquals(0, t.getY());

            t.solve();

            assertEquals(i - 1, t.getX());
            assertEquals(i - 1, t.getY());
        }
    }
}
