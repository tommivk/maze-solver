package mazesolver;

import org.junit.jupiter.api.Test;

import mazesolver.domain.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;

public class AStarTest {
    Rect[][] mazeWithoutJunctions;
    Rect[][] mazeWithJunctions;

    @BeforeEach
    public void setup() {
        TestMaze tm = new TestMaze();
        this.mazeWithoutJunctions = tm.getMazeWithoutJunctions();
        this.mazeWithJunctions = tm.getMazeWithJunctions();
    }

    @Test
    public void aStarShouldBeAbleToSolveMazeWithoutJunctions() {
        int mazeSize = mazeWithoutJunctions.length;
        AStar aStar = new AStar(mazeWithoutJunctions);
        aStar.solve();
        HashMap<Rect, Rect> parents = aStar.getParents();
        Rect last = mazeWithoutJunctions[mazeSize - 1][mazeSize - 1];
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

        Rect first = mazeWithoutJunctions[0][0];

        assertEquals(first, current);
    }

    @Test
    public void aStarShouldBeAbleToSolveMazeWithJunctions() {
        int mazeSize = mazeWithJunctions.length;
        AStar aStar = new AStar(mazeWithJunctions);
        aStar.solve();
        HashMap<Rect, Rect> parents = aStar.getParents();
        Rect last = mazeWithJunctions[mazeSize - 1][mazeSize - 1];
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

        Rect first = mazeWithJunctions[0][0];

        assertEquals(first, current);
    }

    @Test
    public void getSequenceShouldWorkCorrectly() {
        AStar aStar = new AStar(mazeWithJunctions);
        List<Rect> sequence = aStar.getSequence();
        assertEquals(0, sequence.size());
        aStar.solve();
        sequence = aStar.getSequence();
        assertEquals(15, sequence.size());
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

}
