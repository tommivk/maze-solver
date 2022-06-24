package mazesolver.PerformanceTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mazesolver.domain.AStar;
import mazesolver.domain.GrowingTree;
import mazesolver.domain.Kruskal;
import mazesolver.domain.Rect;
import mazesolver.domain.Tremaux;
import mazesolver.domain.WallFollower;

public class PerformanceTest {
    int loops = 0;
    int mazeSize = 0;
    double ONE_MILLION = 1000000.0;

    @BeforeEach
    public void setup() {
        try {
            this.loops = Integer.valueOf(System.getProperty("loops"));
            this.mazeSize = Integer.valueOf(System.getProperty("mazeSize"));
        } catch (Error e) {
            System.out.println("invalid params");
        }
    }

    public void testPerformance(boolean isKruskal) {
        long kruskalTotal = 0;
        long growingTreeTotal = 0;
        long tremauxTotal = 0;
        long wallFollowerTotal = 0;
        long aStarTotal = 0;

        for (int i = 0; i < loops; i++) {
            Rect[][] maze;
            if (isKruskal) {
                long kruskalStart = System.nanoTime();
                Kruskal kruskal = new Kruskal();
                kruskal.generateEdges(mazeSize, mazeSize);
                kruskal.generateMaze();
                long kruskalEnd = System.nanoTime();
                kruskalTotal += kruskalEnd - kruskalStart;

                maze = kruskal.getMaze();
            } else {
                long growingTreeStart = System.nanoTime();
                GrowingTree growingTree = new GrowingTree(mazeSize);
                growingTree.generateMaze();
                long growingTreeEnd = System.nanoTime();
                growingTreeTotal += growingTreeEnd - growingTreeStart;

                maze = growingTree.getMaze();
            }

            long tremauxStart = System.nanoTime();
            Tremaux tremaux = new Tremaux(maze);
            tremaux.solve();
            long tremauxEnd = System.nanoTime();
            tremauxTotal += tremauxEnd - tremauxStart;

            long wallFollowerStart = System.nanoTime();
            WallFollower wallFollower = new WallFollower(maze);
            wallFollower.solve();
            long wallFollowerEnd = System.nanoTime();
            wallFollowerTotal += wallFollowerEnd - wallFollowerStart;

            long aStarStart = System.nanoTime();
            AStar aStar = new AStar(maze);
            aStar.solve();
            long aStarEnd = System.nanoTime();
            aStarTotal += aStarEnd - aStarStart;

        }

        System.out.println("\n**************************************");
        System.out.println(mazeSize + "x" + mazeSize + " Maze generated with "
                + (isKruskal ? "Kruskal" : "Growing Tree") + " (Time in ms) \n");
        if (isKruskal) {
            System.out.println("Kruskal: " + (kruskalTotal / loops) / ONE_MILLION);
        } else {
            System.out.println("Growing tree: " + (growingTreeTotal / loops) / ONE_MILLION);
        }
        System.out.println("\nWall follower: " + (wallFollowerTotal / loops) / ONE_MILLION);
        System.out.println("Tremaux: " + (tremauxTotal / loops) / ONE_MILLION);
        System.out.println("A*: " + (aStarTotal / loops) / ONE_MILLION);
        System.out.println("**************************************\n");
    }

    @Test
    public void performanceTest() {
        testPerformance(true);
        testPerformance(false);
    }

}
