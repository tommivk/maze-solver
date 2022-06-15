package mazesolver.domain;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Random;

public class GrowingTree {
    private Rect[][] maze;
    private boolean[][] visited;

    public GrowingTree(int mazeSize) {
        this.maze = new Rect[mazeSize][mazeSize];
        this.visited = new boolean[mazeSize][mazeSize];

        for (int i = 0; i < mazeSize; i++) {
            for (int j = 0; j < mazeSize; j++) {
                this.maze[i][j] = new Rect(i, j);
            }
        }

    }

    public List<Rect> getUnvisitedNeighbours(Rect current) {
        List<Rect> neighbours = new ArrayList<Rect>();
        int x = current.getX();
        int y = current.getY();

        if (x > 0) {
            if (!visited[x - 1][y]) {
                Rect neighbour = maze[x - 1][y];
                neighbours.add(neighbour);
            }
        }
        if (y > 0) {
            if (!visited[x][y - 1]) {
                Rect neighbour = maze[x][y - 1];
                neighbours.add(neighbour);
            }
        }
        if (x < maze.length - 1) {
            if (!visited[x + 1][y]) {
                Rect neighbour = maze[x + 1][y];
                neighbours.add(neighbour);
            }
        }
        if (y < maze.length - 1) {
            if (!visited[x][y + 1]) {
                Rect neighbour = maze[x][y + 1];
                neighbours.add(neighbour);
            }
        }
        return neighbours;
    }

    public Rect[][] generate() {
        Random random = new Random();

        Deque<Rect> stack = new ArrayDeque<Rect>();
        stack.push(maze[0][0]);

        Rect current;

        while (!stack.isEmpty()) {
            current = stack.peek();
            int x = current.getX();
            int y = current.getY();
            List<Rect> unvisited = getUnvisitedNeighbours(current);

            if (unvisited.isEmpty()) {
                stack.remove(current);
                continue;
            }

            int r = random.nextInt(unvisited.size());

            Rect rect = unvisited.get(r);
            stack.push(rect);
            visited[rect.getX()][rect.getY()] = true;

            if (rect.getX() > x) {
                rect.removeLeftWall();
                current.removeRightWall();
            }
            if (rect.getX() < x) {
                rect.removeRightWall();
                current.removeLeftWall();
            }
            if (rect.getY() > y) {
                rect.removeTopWall();
                current.removeBottomWall();
            }
            if (rect.getY() < y) {
                rect.removeBottomWall();
                current.removeTopWall();
            }

        }

        return this.maze;
    }
}
