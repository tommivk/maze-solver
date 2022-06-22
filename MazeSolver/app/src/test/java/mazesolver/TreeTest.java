package mazesolver;

import org.junit.jupiter.api.Test;

import mazesolver.domain.Tree;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

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

    @Test
    public void connectingMultipleTreesShouldWork() {
        List<Tree> trees = new ArrayList<Tree>();

        trees.add(new Tree());

        for (int i = 1; i < 1000; i++) {
            Tree t = new Tree();
            trees.add(t);
            t.connect(trees.get(i - 1));
        }

        for (Tree tree : trees) {
            assertEquals(true, tree.isConnected(trees.get(0)));
        }
    }

}
