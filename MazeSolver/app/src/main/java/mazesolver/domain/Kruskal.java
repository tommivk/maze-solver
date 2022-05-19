package mazesolver.domain;

import java.util.ArrayList;
import java.util.List;

import java.util.Collections;

public class Kruskal {
    private List<Edge> edges;
    private Tree[][] trees;
    private Rect[][] rectangles;

    public Tree[][] getTrees() {
        return this.trees;
    }

    public Rect[][] generateEdges(int height, int width) {
        this.trees = new Tree[width][height];
        this.rectangles = new Rect[width][height];

        ArrayList<Edge> edges = new ArrayList<Edge>();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (x > 0) {
                    Edge edge = new Edge(x, y, Direction.West);
                    edges.add(edge);
                }

                if (y > 0) {
                    Edge edge = new Edge(x, y, Direction.North);
                    edges.add(edge);
                }

                if (x + 1 < width) {
                    Edge edge = new Edge(x, y, Direction.East);
                    edges.add(edge);
                }
                if (y + 1 < height) {
                    Edge edge = new Edge(x, y, Direction.South);
                    edges.add(edge);
                }

                trees[x][y] = new Tree();
                rectangles[x][y] = new Rect();
            }
        }

        this.edges = shuffleEdges(edges);
        return this.rectangles;
    }

    public void generateMaze() {
        for (Edge edge : this.edges) {

            int x = edge.getX();
            int y = edge.getY();
            Tree current = trees[x][y];

            if (edge.getDirection() == Direction.West) {
                Tree tree = trees[x - 1][y];
                if (!current.isConnected(tree)) {
                    current.connect(tree);

                    rectangles[x][y].removeLeftWall();
                    rectangles[x - 1][y].removeRightWall();
                }
            }

            if (edge.getDirection() == Direction.North) {
                Tree tree = trees[x][y - 1];
                if (!current.isConnected(tree)) {
                    current.connect(tree);

                    rectangles[x][y].removeTopWall();
                    rectangles[x][y - 1].removeBottomWall();
                }
            }

            if (edge.getDirection() == Direction.East) {
                Tree tree = trees[x + 1][y];
                if (!current.isConnected(tree)) {
                    current.connect(tree);

                    rectangles[x][y].removeRightWall();
                    rectangles[x + 1][y].removeLeftWall();
                }
            }

            if (edge.getDirection() == Direction.South) {
                Tree tree = trees[x][y + 1];
                if (!current.isConnected(tree)) {
                    current.connect(tree);
                    rectangles[x][y].removeBottomWall();
                    rectangles[x][y + 1].removeTopWall();
                }
            }
        }

    }

    public List<Edge> shuffleEdges(List<Edge> edges) {
        Collections.shuffle(edges);
        return edges;
    }

}

enum Direction {
    North,
    East,
    South,
    West
}

class Edge {
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
