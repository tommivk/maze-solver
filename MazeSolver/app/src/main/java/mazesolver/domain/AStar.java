package mazesolver.domain;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

public class AStar {
    private HashMap<Rect, Integer> distancesToStart = new HashMap<Rect, Integer>();
    private HashMap<Rect, Rect> parents = new HashMap<Rect, Rect>();
    private HashSet<Rect> visited = new HashSet<Rect>();
    private HashMap<Rect, Integer> distances = new HashMap<Rect, Integer>();
    private Rect[][] maze;

    public AStar(Rect[][] maze) {
        this.maze = maze;
        initializeDistances();
    }

    private PriorityQueue<Rect> initializePriorityQueue() {
        return new PriorityQueue<>(30, new Comparator<Rect>() {
            public int compare(Rect a, Rect b) {
                if (distancesToStart.get(a) < distancesToStart.get(b)) {
                    return -1;
                }
                if (distancesToStart.get(a) > distancesToStart.get(b)) {
                    return 1;
                }
                return 0;
            };
        });
    }

    private void initializeDistances() {
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze.length; j++) {
                distances.put(maze[i][j], Integer.MAX_VALUE);
            }
        }
    }

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

    public int calculateDistanceToFinish(Rect rect) {
        int distanceX = this.maze.length - rect.getX();
        int distanceY = this.maze.length - rect.getY();

        return distanceX + distanceY;
    }

    public int calculateDistanceBetweenNodes(Rect a, Rect b) {
        int distanceX = Math.abs(a.getX() - b.getX());
        int distanceY = Math.abs(a.getY() - b.getY());

        return distanceX + distanceY;
    }

    public void solve() {
        Rect start = maze[0][0];
        Rect finish = maze[maze.length - 1][maze.length - 1];

        distancesToStart.put(start, 0);
        distances.put(start, 0);

        PriorityQueue<Rect> priorityQueue = initializePriorityQueue();
        priorityQueue.add(start);

        Rect current;

        while (!priorityQueue.isEmpty()) {
            current = priorityQueue.remove();

            if (current == finish) {
                System.out.println("Finish");
                return;
            }
            if (visited.contains(current)) {
                continue;
            }

            List<Rect> neighbours = getNeighbours(current);
            for (Rect node : neighbours) {
                if (visited.contains(node)) {
                    continue;
                }

                int distanceToFinish = calculateDistanceToFinish(node);
                int distanceToCurrent = calculateDistanceBetweenNodes(node, current);
                int totalDistance = distancesToStart.get(current) + distanceToFinish + distanceToCurrent;

                if (totalDistance < distances.get(node)) {
                    distances.put(node, totalDistance);
                    distancesToStart.put(node, totalDistance);
                    parents.put(node, current);

                    priorityQueue.add(node);
                }
            }

        }

        Rect node = finish;
        while (true) {
            Rect r = parents.get(node);
            if (r == null || r == start)
                return;
            r.paint();
            node = r;
        }
    }

}
