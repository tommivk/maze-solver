package mazesolver.domain.Kruskal;

/**
 * A tree that is used to keep track of the connections between nodes in the
 * maze.
 */
public class Tree {
    /**
     * The parent Tree of the tree.
     */
    private Tree parent = null;
    /**
     * Rank of the tree.
     */
    private int rank = 0;

    /**
     * Sets the parent of the tree.
     * 
     * @param tree Tree object.
     */
    public void setParent(Tree tree) {
        this.parent = tree;
    }

    /**
     * Increases trees rank by one.
     */
    public void increaseRank() {
        this.rank++;
    }

    /**
     * Returns the rank of the tree.
     * 
     * @return Integer
     */
    public int getRank() {
        return this.rank;
    }

    /**
     * Returns the parent tree of the tree object.
     * 
     * @return Tree object.
     */
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
     * Checks if the Tree given as a parameter and this are connected together.
     *
     * @param tree A tree object
     * @return returns true if the trees are connected.
     */
    public boolean isConnected(Tree tree) {
        return tree.getRoot() == this.getRoot();
    }

    /**
     * Connects two trees together using union by rank.
     *
     * @param tree a Tree object.
     */
    public void connect(Tree tree) {
        Tree xRoot = this.getRoot();
        Tree yRoot = tree.getRoot();

        if (xRoot.getRank() < yRoot.getRank()) {
            xRoot.setParent(yRoot);
        } else if (yRoot.getRank() < xRoot.getRank()) {
            yRoot.setParent(xRoot);
        } else {
            xRoot.setParent(yRoot);
            yRoot.increaseRank();
        }
    }

}
