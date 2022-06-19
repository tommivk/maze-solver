package mazesolver;

import org.junit.jupiter.api.Test;

import mazesolver.domain.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.HashSet;
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
    public void resetShouldWorkCorrectly() {
        int size = mazeWithJunctions.length;
        AStar aStar = new AStar(mazeWithJunctions);
        aStar.solve();
        aStar.reset();

        HashMap<Rect, Integer> distancesToStart = aStar.getDistancesToStart();
        HashMap<Rect, Rect> parents = aStar.getParents();
        HashSet<Rect> visited = aStar.getVisited();
        HashMap<Rect, Integer> predictedDistances = aStar.getPredictedDistances();
        List<Rect> sequence = aStar.getSequence();

        assertEquals(true, parents.isEmpty());
        assertEquals(true, visited.isEmpty());
        assertEquals(true, sequence.isEmpty());
        assertEquals(true, distancesToStart.isEmpty());

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Rect rect = mazeWithJunctions[i][j];
                assertEquals(Integer.MAX_VALUE, predictedDistances.get(rect));
                assertEquals("", mazeWithJunctions[i][j].getRectangle().getStyle());
            }
        }
    }

}
