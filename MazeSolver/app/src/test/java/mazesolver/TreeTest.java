package mazesolver;

import org.junit.jupiter.api.Test;

import mazesolver.domain.Tree;

import static org.junit.jupiter.api.Assertions.*;

public class TreeTest {

    @Test
    public void initialParentIsNull() {
        Tree tree = new Tree();
        assertEquals(null, tree.getParent());
    }

    @Test
    public void initialGetRootReturnsTree() {
        Tree tree = new Tree();
        assertEquals(tree, tree.getRoot());
    }

    @Test
    public void settingParentWorksCorrectly() {
        Tree tree = new Tree();
        Tree tree2 = new Tree();
        tree.setParent(tree2);

        assertEquals(tree2, tree.getParent());
    }

    @Test
    public void connectingTreesWorks() {
        Tree tree = new Tree();
        Tree tree2 = new Tree();
        assertEquals(false, tree.isConnected(tree2));
        assertEquals(false, tree2.isConnected(tree));

        tree.connect(tree2);

        assertEquals(true, tree.isConnected(tree2));
        assertEquals(true, tree2.isConnected(tree));
    }

}
