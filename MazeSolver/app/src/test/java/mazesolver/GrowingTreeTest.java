package mazesolver;

import org.junit.jupiter.api.Test;

import mazesolver.domain.*;

import static org.junit.jupiter.api.Assertions.*;

public class GrowingTreeTest {

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

}
