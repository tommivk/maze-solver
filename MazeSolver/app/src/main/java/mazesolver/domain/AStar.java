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
    private HashMap<Rect, Integer> distancesToStart = new HashMap<Rect, Integer>();
    private HashMap<Rect, Rect> parents = new HashMap<Rect, Rect>();
    private HashSet<Rect> visited = new HashSet<Rect>();
    private HashMap<Rect, Integer> predictedDistances = new HashMap<Rect, Integer>();
    private List<Rect> sequence = new ArrayList<Rect>();
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
     * Initializes new PriorityQueue with a distance comparator.
     * 
     * @return PriorityQueue
     */
    private PriorityQueue<Rect> initializePriorityQueue() {
        return new PriorityQueue<>(30, new Comparator<Rect>() {
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
     * Returns all the contiguous connected nodes of Node.
     * 
     * @param rect Node
     * 
     * @return Returns List of Rect objects
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
     * Calculates Manhattan distance from node to finish.
     * 
     * @param rect Node
     * 
     * @return Integer
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
     * @return Integer
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
