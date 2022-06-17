package mazesolver.domain;

import java.util.ArrayList;
import java.util.List;

import mazesolver.enums.Direction;

import java.util.Collections;

/**
 * Randomized version of Kruskal's algorithm. Used for generating mazes.
 */
public class Kruskal {
    /**
     * List that contains all the possible edges in the maze.
     */
    private List<Edge> edges;
    /**
     * A two dimensional array of trees.
     */
    private Tree[][] trees;
    /**
     * A two dimensional array of Rect objects that represents the maze.
     */
    private Rect[][] rectangles;

    public Tree[][] getTrees() {
        return this.trees;
    }

    public List<Edge> getEdges() {
        return this.edges;
    }

    public Rect[][] getRectangles() {
        return this.rectangles;
    }

    /**
     * Generates all the possible edges from each node.
     *
     * @param height the amount of nodes in the maze in y-axis
     * @param width  the amount of nodes in the maze in x-axis
     * @return two dimensional array of Rect objects
     */
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
                rectangles[x][y] = new Rect(x, y);
            }
        }

        this.edges = shuffleEdges(edges);
        return this.rectangles;
    }

    /**
     * A method that generates a maze from the edges.
     *
     */
    public void generateMaze() {
        for (Edge edge : this.edges) {
            kruskalStep(edge);
        }

    }

    /**
     * Processes one edge of the ramdomized list of edges using the Kruskal's
     * algorithm.
     *
     * @param edge next edge from the list
     */
    public void kruskalStep(Edge edge) {
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

    /**
     * Shuffles a list of edges.
     *
     * @param edges List of edges
     * @return List of edges in random order
     */
    public List<Edge> shuffleEdges(List<Edge> edges) {
        Collections.shuffle(edges);
        return edges;
    }

}
