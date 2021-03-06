package mazesolver;

import org.junit.jupiter.api.Test;

import mazesolver.domain.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class GrowingTreeTest {

    @Test
    public void mazeShouldBeConnected() {
        GrowingTree growingTree = new GrowingTree(300);
        growingTree.generateMaze();
        Rect[][] maze = growingTree.getMaze();

        boolean[][] visited = new boolean[maze.length][maze.length];

        Deque<Rect> stack = new ArrayDeque<Rect>();
        stack.add(maze[0][0]);

        while (!stack.isEmpty()) {
            Rect current = stack.pop();

            int x = current.getX();
            int y = current.getY();

            if (visited[x][y]) {
                continue;
            }

            visited[x][y] = true;

            if (!current.getLeftWall()) {
                Rect neighbour = maze[x - 1][y];
                stack.add(neighbour);
            }
            if (!current.getTopWall()) {
                Rect neighbour = maze[x][y - 1];
                stack.add(neighbour);
            }
            if (!current.getRightWall()) {
                Rect neighbour = maze[x + 1][y];
                stack.add(neighbour);
            }
            if (!current.getBottomWall()) {
                Rect neighbour = maze[x][y + 1];
                stack.add(neighbour);
            }
        }

        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze.length; j++) {
                assertEquals(true, visited[i][j] == true);
            }
        }

    }

    @Test
    public void growingTreeStepShouldProgressAndRemoveWallsCorrectly() {
        int mazeSize = 10;
        GrowingTree growingTree = new GrowingTree(mazeSize);

        boolean[][] visited = new boolean[mazeSize][mazeSize];
        for (int i = 0; i < mazeSize; i++) {
            for (int j = 0; j < mazeSize; j++) {
                visited[i][j] = true;
            }
        }

        visited[1][0] = false;
        visited[2][0] = false;
        visited[3][0] = false;

        growingTree.setVisited(visited);

        growingTree.growingTreeStep();
        assertEquals(0, growingTree.getX());
        assertEquals(0, growingTree.getY());

        growingTree.growingTreeStep();
        assertEquals(1, growingTree.getX());
        assertEquals(0, growingTree.getY());

        growingTree.growingTreeStep();
        assertEquals(2, growingTree.getX());
        assertEquals(0, growingTree.getY());

        growingTree.growingTreeStep();
        assertEquals(3, growingTree.getX());
        assertEquals(0, growingTree.getY());

        growingTree.growingTreeStep();
        assertEquals(2, growingTree.getX());
        assertEquals(0, growingTree.getY());

        growingTree.growingTreeStep();
        assertEquals(1, growingTree.getX());
        assertEquals(0, growingTree.getY());

        growingTree.growingTreeStep();
        assertEquals(0, growingTree.getX());
        assertEquals(0, growingTree.getY());

        assertEquals(true, visited[1][0]);
        assertEquals(true, visited[2][0]);
        assertEquals(true, visited[3][0]);

        Rect[][] maze = growingTree.getMaze();

        for (int i = 1; i < 4; i++) {
            Rect rect = maze[i][0];
            boolean isWallRemoved = !rect.getTopWall() || !rect.getRightWall() || !rect.getBottomWall()
                    || !rect.getLeftWall();
            assertEquals(true, isWallRemoved);
        }
    }

    @Test
    public void initialStackSizeAndContentIsCorrect() {
        int mazeSize = 10;
        GrowingTree growingTree = new GrowingTree(mazeSize);
        Deque<Rect> stack = growingTree.getStack();
        assertEquals(1, stack.size());
        Rect rect = stack.pop();
        assertEquals(0, rect.getX());
        assertEquals(0, rect.getY());
    }

    @Test
    public void getUnvisitedNeighboursShouldReturnCorrectAmountOfNeighbours() {
        int mazeSize = 10;
        GrowingTree growingTree = new GrowingTree(mazeSize);
        boolean[][] visited = new boolean[mazeSize][mazeSize];

        growingTree.setVisited(visited);

        Rect[][] maze = growingTree.getMaze();

        Rect rect = maze[5][5];
        List<Rect> neighbours = growingTree.getUnvisitedNeighbours(rect);
        assertEquals(4, neighbours.size());

        visited[5][4] = true;
        neighbours = growingTree.getUnvisitedNeighbours(rect);
        assertEquals(3, neighbours.size());

        visited[5][6] = true;
        neighbours = growingTree.getUnvisitedNeighbours(rect);
        assertEquals(2, neighbours.size());

        visited[4][5] = true;
        neighbours = growingTree.getUnvisitedNeighbours(rect);
        assertEquals(1, neighbours.size());

        visited[6][5] = true;
        neighbours = growingTree.getUnvisitedNeighbours(rect);
        assertEquals(0, neighbours.size());
    }

    @Test
    public void getUnvisitedNeighboursShouldNotGoOutOfBounds() {
        int mazeSize = 10;
        GrowingTree growingTree = new GrowingTree(mazeSize);
        boolean[][] visited = new boolean[mazeSize][mazeSize];

        growingTree.setVisited(visited);

        Rect[][] maze = growingTree.getMaze();

        Rect rect = maze[9][9];
        List<Rect> neighbours = growingTree.getUnvisitedNeighbours(rect);
        assertEquals(2, neighbours.size());

        rect = maze[0][0];
        neighbours = growingTree.getUnvisitedNeighbours(rect);
        assertEquals(2, neighbours.size());
    }

    @Test
    public void growingTreeStepRemovesWallsBetweenCurrentAndTopNode() {
        int mazeSize = 10;
        GrowingTree growingTree = new GrowingTree(mazeSize);
        boolean[][] visited = new boolean[mazeSize][mazeSize];

        Rect[][] maze = growingTree.getMaze();
        Deque<Rect> stack = growingTree.getStack();
        stack.clear();

        Rect current = maze[5][5];
        stack.add(current);

        visited[5][6] = true;
        visited[4][5] = true;
        visited[6][5] = true;
        growingTree.setVisited(visited);

        growingTree.growingTreeStep();

        assertEquals(false, current.getTopWall());
        assertEquals(false, maze[5][4].getBottomWall());
    }

    @Test
    public void growingTreeStepRemovesWallsBetweenCurrentAndRightNode() {
        int mazeSize = 10;
        GrowingTree growingTree = new GrowingTree(mazeSize);
        boolean[][] visited = new boolean[mazeSize][mazeSize];

        Rect[][] maze = growingTree.getMaze();
        Deque<Rect> stack = growingTree.getStack();
        stack.clear();

        Rect current = maze[5][5];
        stack.add(current);

        visited[5][4] = true;
        visited[5][6] = true;
        visited[4][5] = true;
        growingTree.setVisited(visited);

        growingTree.growingTreeStep();

        assertEquals(false, current.getRightWall());
        assertEquals(false, maze[6][5].getLeftWall());
    }

    @Test
    public void growingTreeStepRemovesWallsBetweenCurrentAndBottomNode() {
        int mazeSize = 10;
        GrowingTree growingTree = new GrowingTree(mazeSize);
        boolean[][] visited = new boolean[mazeSize][mazeSize];

        Rect[][] maze = growingTree.getMaze();
        Deque<Rect> stack = growingTree.getStack();
        stack.clear();

        Rect current = maze[5][5];
        stack.add(current);

        visited[5][4] = true;
        visited[6][5] = true;
        visited[4][5] = true;
        growingTree.setVisited(visited);

        growingTree.growingTreeStep();

        assertEquals(false, current.getBottomWall());
        assertEquals(false, maze[5][6].getTopWall());
    }

    @Test
    public void growingTreeStepRemovesWallsBetweenCurrentAndLeftNode() {
        int mazeSize = 10;
        GrowingTree growingTree = new GrowingTree(mazeSize);
        boolean[][] visited = new boolean[mazeSize][mazeSize];

        Rect[][] maze = growingTree.getMaze();
        Deque<Rect> stack = growingTree.getStack();
        stack.clear();

        Rect current = maze[5][5];
        stack.add(current);

        visited[5][4] = true;
        visited[6][5] = true;
        visited[5][6] = true;
        growingTree.setVisited(visited);

        growingTree.growingTreeStep();

        assertEquals(false, current.getLeftWall());
        assertEquals(false, maze[4][5].getRightWall());
    }

    @Test
    public void generateMazeShouldReturnCorrectAMountOfSteps() {
        GrowingTree growingTree = new GrowingTree(30);
        int steps = growingTree.generateMaze();
        assertEquals(1799, steps);

        growingTree = new GrowingTree(50);
        steps = growingTree.generateMaze();
        assertEquals(4999, steps);
    }

    @Test
    public void resetShouldResetStackAndMaze() {
        GrowingTree growingTree = new GrowingTree(30);
        growingTree.generateMaze();

        growingTree.reset();
        assertEquals(1, growingTree.getStack().size());

        Rect[][] maze = growingTree.getMaze();
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze.length; j++) {
                assertEquals("", maze[i][j].getRectangle().getStyle());
            }
        }

    }

}
