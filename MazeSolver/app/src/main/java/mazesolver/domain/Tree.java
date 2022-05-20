package mazesolver.domain;

/**
 * Tree that is used to keep track of connections between nodes in the maze
 */
public class Tree {
    private Tree parent = null;

    public void setParent(Tree tree) {
        this.parent = tree;
    }

    public Tree getParent() {
        return this.parent;
    }

    /**
     * Uses recursion to get the root object of the Tree.
     *
     * @return Tree object
     */
    public Tree getRoot() {
        return this.parent == null ? this : this.parent.getRoot();
    }

    /**
     * Checks if two Trees are connected together
     *
     * @param tree Tree objects
     * @return boolean
     */
    public boolean isConnected(Tree tree) {
        return tree.getRoot() == this.getRoot();
    }

    /**
     * Connects two trees together
     *
     * @param tree a Tree object
     */
    public void connect(Tree tree) {
        tree.getRoot().setParent(this);
    }

}
