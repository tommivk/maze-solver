package mazesolver;

import org.junit.jupiter.api.Test;

import mazesolver.domain.*;
import mazesolver.domain.Tree;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;

class KruskalTest {
    Kruskal kruskal;

    @Test
    public void allTreesAreConnectedToFirstTree() {
        this.kruskal = new Kruskal();
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

}
