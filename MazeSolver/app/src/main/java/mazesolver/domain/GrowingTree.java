package mazesolver.domain;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Random;

public class GrowingTree {
    private Rect[][] maze;
    private boolean[][] visited;
    private Deque<Rect> stack;
    private Random random;
    private int mazeSize;
    private int x = 0;
    private int y = 0;

    public GrowingTree(int mazeSize) {
        this.maze = new Rect[mazeSize][mazeSize];
        this.visited = new boolean[mazeSize][mazeSize];
        this.mazeSize = mazeSize;

        for (int i = 0; i < mazeSize; i++) {
            for (int j = 0; j < mazeSize; j++) {
                this.maze[i][j] = new Rect(i, j);
            }
        }
        this.random = new Random();
        this.stack = new ArrayDeque<Rect>();
        this.stack.push(maze[0][0]);
    }

    public Rect[][] getMaze() {
        return this.maze;
    }

    public Deque<Rect> getStack() {
        return this.stack;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void reset() {
        this.maze = new Rect[mazeSize][mazeSize];

        for (int i = 0; i < mazeSize; i++) {
            for (int j = 0; j < mazeSize; j++) {
                this.maze[i][j] = new Rect(i, j);
            }
        }

        this.visited = new boolean[mazeSize][mazeSize];
        this.random = new Random();
        this.stack = new ArrayDeque<Rect>();
        this.stack.push(maze[0][0]);
    }

    public List<Rect> getUnvisitedNeighbours(Rect current) {
        List<Rect> neighbours = new ArrayList<Rect>();
        this.x = current.getX();
        this.y = current.getY();

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

    public Rect[][] generateMaze() {
        while (!stack.isEmpty()) {
            growingTreeStep();
        }
        return this.maze;
    }

    public void growingTreeStep() {
        Rect current = stack.peek();

        int x = current.getX();
        int y = current.getY();
        List<Rect> unvisited = getUnvisitedNeighbours(current);

        if (unvisited.isEmpty()) {
            stack.remove(current);
            return;
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
}
