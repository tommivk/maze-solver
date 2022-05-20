package mazesolver.domain;

public class Edge {

    private int x;
    private int y;
    private Direction direction;

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
