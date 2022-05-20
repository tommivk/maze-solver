package mazesolver.domain;

import mazesolver.enums.Direction;

/**
 * A class that represents a single edge in the maze.
 */
public class Edge {

    private int x;
    private int y;
    private Direction direction;

    /**
     * @param x         The x coordinate of the edge in the two dimensional maze
     *                  array
     * @param y         The y coordinate of the edge in the two dimensional maze
     *                  array
     * @param direction Direction that the edge is connected to
     */
    public Edge(int x, int y, Direction direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public Direction getDirection() {
        return this.direction;
    }

    public String toString() {
        return x + " - " + y + " " + direction;
    }

}
