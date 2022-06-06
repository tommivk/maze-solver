package mazesolver;

import org.junit.jupiter.api.Test;

import mazesolver.domain.*;
import mazesolver.domain.Tree;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;

class KruskalTest {
    Kruskal kruskal;

    @BeforeEach
    public void setup() {
        this.kruskal = new Kruskal();
    }

    @Test
    public void allTreesAreConnectedToFirstTree() {
        kruskal.generateEdges(30, 30);
        kruskal.generateMaze();
        Tree[][] trees = kruskal.getTrees();
        Tree first = trees[0][0];
        for (int i = 0; i < trees[0].length; i++) {
            for (int k = 0; k < trees[0].length; k++) {
                assertEquals(true, trees[i][k].isConnected(first));
            }
        }
    }

    @Test
    public void generateEdgesGeneratesCorrectAmountOfEdges() {
        kruskal.generateEdges(3, 3);
        List<Edge> edges = kruskal.getEdges();
        assertEquals(24, edges.size());
    }

}
