package mazesolver;

import org.junit.jupiter.api.Test;

import javafx.scene.layout.Region;
import mazesolver.domain.*;
import mazesolver.enums.Direction;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

public class WallFollowerTest {
    WallFollower wf;
    Rect[][] maze;
    Rect[][] mazeWithJunctions;

    @BeforeEach
    public void setup() {
        this.maze = new Rect[10][10];
        for (int i = 0; i < 10; i++) {
            for (int k = 0; k < 10; k++) {
                maze[i][k] = new Rect(i, k);
            }
        }
        this.wf = new WallFollower(maze);
        TestMaze tm = new TestMaze();
        this.mazeWithJunctions = tm.getMazeWithJunctions();
    }

    @Test
    public void initalCoordinateValuesAreZero() {
        assertEquals(0, wf.getX());
        assertEquals(0, wf.getY());
    }

    @Test
    public void initialPreviousDirectionIsSet() {
        assertEquals(Direction.South, wf.getFacing());
    }

    @Test
    public void advancingOnMazeWithoutJunctionsWorksCorrectly() {
        TestMaze tm = new TestMaze();
        WallFollower wf = new WallFollower(tm.getMazeWithoutJunctions());
        wf.calculateNextMove();
        assertEquals(1, wf.getX());
        assertEquals(0, wf.getY());
        wf.calculateNextMove();
        assertEquals(1, wf.getX());
        assertEquals(1, wf.getY());
        wf.calculateNextMove();
        assertEquals(1, wf.getX());
        assertEquals(2, wf.getY());
        wf.calculateNextMove();
        assertEquals(0, wf.getX());
        assertEquals(2, wf.getY());
        wf.calculateNextMove();
        assertEquals(0, wf.getX());
        assertEquals(3, wf.getY());
        wf.calculateNextMove();
        assertEquals(1, wf.getX());
        assertEquals(3, wf.getY());
        wf.calculateNextMove();
        assertEquals(2, wf.getX());
        assertEquals(3, wf.getY());
        wf.calculateNextMove();
        assertEquals(3, wf.getX());
        assertEquals(3, wf.getY());
        wf.calculateNextMove();
        assertEquals(3, wf.getX());
        assertEquals(2, wf.getY());
        wf.calculateNextMove();
        assertEquals(4, wf.getX());
        assertEquals(2, wf.getY());
        wf.calculateNextMove();
        assertEquals(4, wf.getX());
        assertEquals(3, wf.getY());
        wf.calculateNextMove();
        assertEquals(4, wf.getX());
        assertEquals(4, wf.getY());
    }

    @Test
    public void calculateNextMoveTurnsAroundOnDeadEnd() {
        maze[3][3].removeTopWall();
        maze[3][3].removeRightWall();
        maze[3][3].removeBottomWall();
        maze[3][3].removeLeftWall();

        maze[3][2].removeBottomWall();
        maze[3][4].removeTopWall();
        maze[2][3].removeRightWall();
        maze[4][3].removeLeftWall();

        wf.setX(3);
        wf.setY(2);
        wf.setFacing(Direction.North);

        wf.calculateNextMove();
        assertEquals(3, wf.getX());
        assertEquals(3, wf.getY());

        wf.setX(3);
        wf.setY(4);
        wf.setFacing(Direction.South);

        wf.calculateNextMove();
        assertEquals(3, wf.getX());
        assertEquals(3, wf.getY());

        wf.setX(2);
        wf.setY(3);
        wf.setFacing(Direction.West);

        wf.calculateNextMove();
        assertEquals(3, wf.getX());
        assertEquals(3, wf.getY());

        wf.setX(4);
        wf.setY(3);
        wf.setFacing(Direction.East);

        wf.calculateNextMove();
        assertEquals(3, wf.getX());
        assertEquals(3, wf.getY());

    }

    @Test
    public void calculateMoveFacingNorthWorksCorrectly() {

        wf.setX(2);
        wf.setY(2);
        wf.setFacing(Direction.North);
        wf.calculateMoveFacingNorth(true, true, true, false);
        assertEquals(1, wf.getX());
        assertEquals(2, wf.getY());

        wf.setX(2);
        wf.setY(2);

        wf.setFacing(Direction.North);
        wf.calculateMoveFacingNorth(false, true, true, false);

        assertEquals(2, wf.getX());
        assertEquals(1, wf.getY());

    }

    @Test
    public void calculateMoveFacingWestWorksCorrectly() {
        wf.setX(2);
        wf.setY(2);
        wf.setFacing(Direction.West);
        wf.calculateMoveFacingWest(true, true, true, false);
        assertEquals(1, wf.getX());
        assertEquals(2, wf.getY());

        wf.setX(2);
        wf.setY(2);

        wf.setFacing(Direction.West);
        wf.calculateMoveFacingWest(false, true, true, false);

        assertEquals(2, wf.getX());
        assertEquals(1, wf.getY());
    }

    @Test
    public void paintRectangleAddsBackgroundToRectangle() {
        Rect[][] maze = wf.getMaze();
        Rect rect = maze[0][0];
        Region region = rect.getRectangle();
        assertEquals("", region.getStyle());
        assertEquals(0, wf.getX());
        assertEquals(0, wf.getY());
        wf.paintRectangle();
        assertEquals("-fx-background-color: rgb(255,0,0); -fx-background-insets: 4px", region.getStyle());
    }

    @Test
    public void paintGreenWorksCorrectly() {
        Rect[][] maze = wf.getMaze();
        Rect rect = maze[0][0];
        Region region = rect.getRectangle();
        assertEquals("", region.getStyle());
        wf.paintGreen();
        assertEquals("-fx-background-color: rgb(0,255,0); -fx-background-insets: 4px", region.getStyle());
    }

    @Test
    public void solveStopsWhenEndIsReached() {
        Rect[][] maze = wf.getMaze();
        int size = maze.length;
        wf.setX(size - 1);
        wf.setY(size - 1);
        int moves = wf.solve();
        assertEquals(0, moves);
    }

    @Test
    public void solvingMazeStopsAtEndCoordinates() {
        int size = mazeWithJunctions.length;

        WallFollower wallFollower = new WallFollower(mazeWithJunctions);
        wallFollower.solve();
        assertEquals(size - 1, wallFollower.getX());
        assertEquals(size - 1, wallFollower.getY());
    }

    @Test
    public void resetMethodWorks() {
        for (int i = 0; i < this.maze.length; i++) {
            for (int j = 0; j < this.maze.length; j++) {
                maze[i][j].paint();
                assertEquals("-fx-background-color: rgb(255,0,0); -fx-background-insets: 4px",
                        maze[i][j].getRectangle().getStyle());
            }
        }

        wf.setX(3);
        wf.setY(2);
        assertEquals(3, wf.getX());
        assertEquals(2, wf.getY());

        wf.reset();
        assertEquals(0, wf.getX());
        assertEquals(0, wf.getY());
        for (int i = 0; i < this.maze.length; i++) {
            for (int j = 0; j < this.maze.length; j++) {
                assertEquals("", maze[i][j].getRectangle().getStyle());
            }
        }
    }
}
