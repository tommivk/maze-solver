package mazesolver.domain;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

/**
 * A* algorithm. Used for solving mazes.
 */
public class AStar {
    /**
     * A Map of manhattan distances from a node to the starting node.
     */
    private HashMap<Rect, Integer> distancesToStart = new HashMap<Rect, Integer>();
    /**
     * A node parent map.
     */
    private HashMap<Rect, Rect> parents = new HashMap<Rect, Rect>();
    /**
     * A Set of Rect objects that have been processed by the algorithm.
     */
    private HashSet<Rect> visited = new HashSet<Rect>();
    /**
     * A Map of predicted manhattan distances from a node to the end node.
     */
    private HashMap<Rect, Integer> predictedDistances = new HashMap<Rect, Integer>();
    /**
     * An ordered list of processed Rect objects.
     */
    private List<Rect> sequence = new ArrayList<Rect>();
    /**
     * Two dimensional array of Rect objects that represents a maze.
     */
    private Rect[][] maze;

    /**
     * Constructor for A* algorithm.
     * 
     * @param maze Two dimensional array of Rect objects.
     */
    public AStar(Rect[][] maze) {
        this.maze = maze;
        initializeDistances();
    }

    /**
     * Returns nodes parent map.
     * 
     * @return HashMap
     */
    public HashMap<Rect, Rect> getParents() {
        return this.parents;
    }

    /**
     * Gets list of processed nodes.
     * 
     * @return List of Rect objects.
     */
    public List<Rect> getSequence() {
        return this.sequence;
    }

    /**
     * Gets map of manhattan distances from a node to starting node.
     * 
     * @return A HashMap of manhattan distances.
     */
    public HashMap<Rect, Integer> getDistancesToStart() {
        return this.distancesToStart;
    }

    /**
     * Gets map of predicted manhattan distances from a node to the end node.
     * 
     * @return A HashMap of manhattan distances.
     */
    public HashMap<Rect, Integer> getPredictedDistances() {
        return this.predictedDistances;
    }

    /**
     * Gets the Set of the visited rectangles.
     * 
     * @return HashSet containing all of the visited rectangles.
     */
    public HashSet<Rect> getVisited() {
        return this.visited;
    }

    /**
     * Resets the state of the algorithm.
     */
    public void reset() {
        this.distancesToStart = new HashMap<Rect, Integer>();
        this.parents = new HashMap<Rect, Rect>();
        this.visited = new HashSet<Rect>();
        this.predictedDistances = new HashMap<Rect, Integer>();
        this.sequence = new ArrayList<Rect>();
        initializeDistances();

        for (int i = 0; i < maze.length; i++) {
            for (int k = 0; k < maze.length; k++) {
                maze[i][k].removeBackground();
            }
        }
    }

    /**
     * Initializes a new PriorityQueue with a distance comparator.
     * 
     * @return PriorityQueue
     */
    private PriorityQueue<Rect> initializePriorityQueue() {
        final int capacity = 30;
        return new PriorityQueue<>(capacity, new Comparator<Rect>() {
            public int compare(Rect a, Rect b) {
                if (predictedDistances.get(a) < predictedDistances.get(b)) {
                    return -1;
                }
                if (predictedDistances.get(a) > predictedDistances.get(b)) {
                    return 1;
                }
                return 0;
            };
        });
    }

    /**
     * Initializes all of the distances to Integer max value.
     */
    private void initializeDistances() {
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze.length; j++) {
                predictedDistances.put(maze[i][j], Integer.MAX_VALUE);
            }
        }
    }

    /**
     * Returns all of the contiguous connected nodes of a Node.
     * 
     * @param rect The node which neighbours will be returned
     * 
     * @return List of Neighbouring nodes
     */
    public List<Rect> getNeighbours(Rect rect) {
        List<Rect> neighbours = new ArrayList<Rect>();
        int x = rect.getX();
        int y = rect.getY();

        if (!rect.getTopWall()) {
            neighbours.add(maze[x][y - 1]);
        }
        if (!rect.getRightWall()) {
            neighbours.add(maze[x + 1][y]);
        }
        if (!rect.getBottomWall()) {
            neighbours.add(maze[x][y + 1]);
        }
        if (!rect.getLeftWall()) {
            neighbours.add(maze[x - 1][y]);
        }

        return neighbours;
    }

    /**
     * Calculates Manhattan distance from a node to the finish.
     * 
     * @param rect The node which distance to the finish will be returned.
     * 
     * @return The distance from the node to the finish
     */
    public int calculateDistanceToFinish(Rect rect) {
        int distanceX = (this.maze.length - 1) - rect.getX();
        int distanceY = (this.maze.length - 1) - rect.getY();

        return distanceX + distanceY;
    }

    /**
     * Calculates Manhattan distance between two nodes.
     * 
     * @param a Node A
     * @param b Node B
     * 
     * @return The distance between the two nodes.
     */
    public int calculateDistanceBetweenNodes(Rect a, Rect b) {
        int distanceX = Math.abs(a.getX() - b.getX());
        int distanceY = Math.abs(a.getY() - b.getY());

        return distanceX + distanceY;
    }

    /**
     * Solves the maze using the A* algorithm.
     */
    public void solve() {
        Rect start = maze[0][0];
        Rect finish = maze[maze.length - 1][maze.length - 1];

        distancesToStart.put(start, 0);
        predictedDistances.put(start, 0);

        PriorityQueue<Rect> priorityQueue = initializePriorityQueue();
        priorityQueue.add(start);

        Rect current;

        while (!priorityQueue.isEmpty()) {
            current = priorityQueue.remove();
            sequence.add(current);

            if (current == finish) {
                return;
            }
            if (visited.contains(current)) {
                continue;
            }

            visited.add(current);

            List<Rect> neighbours = getNeighbours(current);
            for (Rect neighbour : neighbours) {
                if (visited.contains(neighbour)) {
                    continue;
                }

                int distanceToFinish = calculateDistanceToFinish(neighbour);
                int distanceToCurrent = calculateDistanceBetweenNodes(neighbour, current);
                int predictedDistance = distancesToStart.get(current)
                        + distanceToCurrent
                        + distanceToFinish;

                if (predictedDistance < predictedDistances.get(neighbour)) {
                    predictedDistances.put(neighbour, predictedDistance);
                    distancesToStart.put(neighbour,
                            distancesToStart.get(current) + distanceToCurrent);
                    parents.put(neighbour, current);

                    priorityQueue.add(neighbour);
                }
            }

        }
    }

}
