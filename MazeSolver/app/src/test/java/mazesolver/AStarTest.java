package mazesolver;

import org.junit.jupiter.api.Test;

import mazesolver.domain.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

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

}
