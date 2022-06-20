package mazesolver.domain;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Random;

public class GrowingTree {
    /**
     * A two dimensional array of Rect objects that represents a maze.
     */
    private Rect[][] maze;
    /**
     * A Two dimensional boolean array. The value is true if the square has been
     * processed by the algorithm.
     */
    private boolean[][] visited;
    /**
     * A Stack of rect objects.
     */
    private Deque<Rect> stack;
    /**
     * Used for generating random numbers.
     */
    private Random random;
    /**
     * The X coordinate of the last node that has been processed.
     */
    private int x = 0;
    /**
     * The Y coordinate of the last node that has been processed.
     */
    private int y = 0;

    /**
     * A Growing tree algorithm that is used for generating mazes. It's basically a
     * iterative version of a Recursive backtracking algorithm.
     * 
     * @param mazeSize The size of the maze.
     */
    public GrowingTree(int mazeSize) {
        this.maze = new Rect[mazeSize][mazeSize];
        this.visited = new boolean[mazeSize][mazeSize];

        for (int i = 0; i < mazeSize; i++) {
            for (int j = 0; j < mazeSize; j++) {
                this.maze[i][j] = new Rect(i, j);
            }
        }
        this.random = new Random();
        this.stack = new ArrayDeque<Rect>();
        this.stack.push(maze[0][0]);
    }

    public void reset() {
        int size = this.maze.length;
        this.visited = new boolean[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
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

    public void setVisited(boolean[][] visited) {
        this.visited = visited;
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

    /**
     * Generates a maze using the Growing Tree algorithm.
     * 
     * @return Two dimensional array of Rect objects that represents the maze.
     */
    public Rect[][] generateMaze() {
        while (!stack.isEmpty()) {
            growingTreeStep();
        }
        return this.maze;
    }

    /**
     * Processes one iteration of the algorithm.
     * 
     * - Gets the first node of the stack and sets it as current.
     * - Chooses a random unvisited neigbour of the current node and removes the
     * wall between it and the current node.
     * - If the current node has no unvisited neighbours, the current node
     * is removed from the stack.
     */
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
