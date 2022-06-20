package mazesolver;

import mazesolver.domain.*;

public class TestMaze {

    /**
     * > v - - -
     * - v - - -
     * v < - > v
     * > > > ^ v
     * - - - - v
     */
    public Rect[][] getMazeWithoutJunctions() {
        Rect[][] maze = new Rect[5][5];

        for (int x = 0; x < maze.length; x++) {
            for (int y = 0; y < maze.length; y++) {
                maze[x][y] = new Rect(x, y);
            }
        }

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
        maze[3][3].removeTopWall();
        maze[3][2].removeBottomWall();
        maze[3][2].removeRightWall();
        maze[4][2].removeLeftWall();
        maze[4][2].removeBottomWall();
        maze[4][3].removeTopWall();
        maze[4][3].removeBottomWall();
        maze[4][4].removeTopWall();

        return maze;
    }

    /**
     * > v < - -
     * - v - - v
     * v < > > v
     * > > > ^ v
     * ^ - - - v
     */
    public Rect[][] getMazeWithJunctions() {

        Rect[][] maze = getMazeWithoutJunctions();
        maze[1][0].removeRightWall();
        maze[2][0].removeLeftWall();

        maze[4][1].removeBottomWall();
        maze[4][2].removeTopWall();

        maze[0][3].removeBottomWall();
        maze[0][4].removeTopWall();

        maze[2][2].removeRightWall();
        maze[3][2].removeLeftWall();

        return maze;
    }

    public Rect[][] getMazeWithoutWalls(int size) {
        Rect[][] maze = new Rect[size][size];

        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                maze[x][y] = new Rect(x, y);
                if (x > 0) {
                    maze[x][y].removeLeftWall();
                }
                if (y > 0) {
                    maze[x][y].removeTopWall();
                }
                if (x < maze.length - 1) {
                    maze[x][y].removeRightWall();
                }
                if (y < maze.length - 1) {
                    maze[x][y].removeBottomWall();
                }
            }
        }

        return maze;
    }
}
