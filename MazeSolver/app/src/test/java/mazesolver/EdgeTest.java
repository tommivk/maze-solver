package mazesolver;

import org.junit.jupiter.api.Test;

import mazesolver.domain.Kruskal.Edge;
import mazesolver.enums.Direction;

import static org.junit.jupiter.api.Assertions.*;

public class EdgeTest {
    @Test
    public void creatingEdgeAndGettersWorksCorrectly() {
        Edge edge = new Edge(2, 5, Direction.South);
        assertEquals(2, edge.getX());
        assertEquals(5, edge.getY());
        assertEquals(Direction.South, edge.getDirection());

        edge = new Edge(0, 0, Direction.North);
        assertEquals(0, edge.getX());
        assertEquals(0, edge.getY());
        assertEquals(Direction.North, edge.getDirection());
    }

    @Test
    public void toStringWorksCorrectly() {
        Edge edge = new Edge(4, 10, Direction.South);
        assertEquals("4 - 10 South", edge.toString());
    }

}
