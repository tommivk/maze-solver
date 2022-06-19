package mazesolver.IntegrationTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

import mazesolver.domain.AStar;
import mazesolver.domain.GrowingTree;
import mazesolver.domain.Kruskal;
import mazesolver.domain.Rect;
import mazesolver.domain.Tremaux;
import mazesolver.domain.WallFollower;

public class IntegrationTest {
    @Test
    public void tremauxShouldBeAbleToSolveMazesGeneratedByKruskal() {
        for (int i = 4; i < 70; i++) {
            Kruskal k = new Kruskal();
            Rect[][] maze = k.generateEdges(i, i);
            k.generateMaze();
            Tremaux t = new Tremaux(maze);
            assertEquals(0, t.getX());
            assertEquals(0, t.getY());

            t.solve();

            assertEquals(i - 1, t.getX());
            assertEquals(i - 1, t.getY());
        }
    }

    @Test
    public void tremauxShouldBeAbleToSolveMazesGeneratedByGrowingTree() {
        int i = 30;
        for (int x = 5; x < 70; x++) {
            GrowingTree growingTree = new GrowingTree(i);
            Rect[][] maze = growingTree.generateMaze();
            Tremaux t = new Tremaux(maze);

            t.solve();

            assertEquals(i - 1, t.getX());
            assertEquals(i - 1, t.getY());

        }
    }

    @Test
    public void aStarShouldSolveMazesGeneratedWithKruskal() {
        for (int i = 5; i < 80; i++) {
            Kruskal k = new Kruskal();
            Rect[][] maze = k.generateEdges(i, i);
            k.generateMaze();
            AStar aStar = new AStar(maze);

            aStar.solve();

            HashMap<Rect, Rect> parents = aStar.getParents();
            Rect last = maze[i - 1][i - 1];
            Rect rect = parents.get(last);
            assertEquals(true, rect != null);

            Rect current = last;
            while (true) {
                Rect next = parents.get(current);
                if (next == null) {
                    break;
                }
                current = next;
            }

            Rect first = maze[0][0];

            assertEquals(first, current);
        }
    }

    @Test
    public void aStarShouldSolveMazesGeneratedWithGrowingTree() {
        for (int i = 5; i < 80; i++) {
            GrowingTree growingTree = new GrowingTree(i);
            Rect[][] maze = growingTree.generateMaze();
            AStar aStar = new AStar(maze);

            aStar.solve();

            HashMap<Rect, Rect> parents = aStar.getParents();
            Rect last = maze[i - 1][i - 1];
            Rect rect = parents.get(last);
            assertEquals(true, rect != null);

            Rect current = last;
            while (true) {
                Rect next = parents.get(current);
                if (next == null) {
                    break;
                }
                current = next;
            }

            Rect first = maze[0][0];

            assertEquals(first, current);
        }
    }

    @Test
    public void wallFollowerShouldBeAbleToSolveMazesGeneratedByKruskal() {
        for (int i = 4; i < 70; i++) {
            Kruskal k = new Kruskal();
            Rect[][] maze = k.generateEdges(i, i);
            k.generateMaze();
            WallFollower wallFollower = new WallFollower(maze);
            assertEquals(0, wallFollower.getX());
            assertEquals(0, wallFollower.getY());

            wallFollower.solve();

            assertEquals(i - 1, wallFollower.getX());
            assertEquals(i - 1, wallFollower.getY());
        }
    }

    @Test
    public void wallFollowerShouldBeAbleToSolveMazesGeneratedByGrowingTree() {
        for (int i = 4; i < 70; i++) {
            GrowingTree growingTree = new GrowingTree(i);
            Rect[][] maze = growingTree.generateMaze();

            WallFollower wallFollower = new WallFollower(maze);
            assertEquals(0, wallFollower.getX());
            assertEquals(0, wallFollower.getY());

            wallFollower.solve();

            assertEquals(i - 1, wallFollower.getX());
            assertEquals(i - 1, wallFollower.getY());
        }
    }
}
