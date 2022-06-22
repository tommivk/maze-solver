package mazesolver;

import org.junit.jupiter.api.Test;

import mazesolver.domain.*;
import mazesolver.domain.Tree;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;

class KruskalTest {
    Kruskal kruskal;

    @BeforeEach
    public void setup() {
        this.kruskal = new Kruskal();
    }

    @Test
    public void mazeShouldBeConnected() {
        kruskal.generateEdges(300, 300);
        kruskal.generateMaze();
        Rect[][] maze = kruskal.getMaze();

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
    public void allTreesAreConnectedToFirstTree() {
        kruskal.generateEdges(30, 30);
        kruskal.generateMaze();
        Tree[][] trees = kruskal.getTrees();
        Tree first = trees[0][0];
        for (int i = 0; i < trees[0].length; i++) {
            for (int k = 0; k < trees[0].length; k++) {
                assertEquals(true, trees[i][k].isConnected(first));
            }
        }
    }

    @Test
    public void generateEdgesGeneratesCorrectAmountOfEdges() {
        kruskal.generateEdges(3, 3);
        List<Edge> edges = kruskal.getEdges();
        assertEquals(24, edges.size());

        kruskal.generateEdges(30, 30);
        edges = kruskal.getEdges();
        assertEquals(3480, edges.size());

        kruskal.generateEdges(100, 100);
        edges = kruskal.getEdges();
        assertEquals(39600, edges.size());
    }

}
