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

    @BeforeEach
    public void setup() {
        this.maze = new Rect[4][4];
        for (int i = 0; i < 4; i++) {
            for (int k = 0; k < 4; k++) {
                maze[i][k] = new Rect(i, k);
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
    public void solvingMazeStopsAtEndCoordinates() {
        Kruskal k = new Kruskal();
        Rect[][] maze = k.generateEdges(30, 30);
        k.generateMaze();
        WallFollower wallFollower = new WallFollower(maze);
        wallFollower.solve();
        assertEquals(29, wallFollower.getX());
        assertEquals(29, wallFollower.getY());
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
                assertEquals("-fx-background-color: rgba(0,0,0,0)", maze[i][j].getRectangle().getStyle());
            }
        }
    }
}
