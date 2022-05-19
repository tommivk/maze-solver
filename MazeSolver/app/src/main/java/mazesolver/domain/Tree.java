package mazesolver.domain;

public class Tree {
    private Tree parent = null;

    public void setParent(Tree tree) {
        this.parent = tree;
    }

    public Tree getParent() {
        return this.parent;
    }

    public Tree getRoot() {
        return this.parent == null ? this : this.parent.getRoot();
    }

    public boolean isConnected(Tree tree) {
        return tree.getRoot() == this.getRoot();
    }

    public void connect(Tree tree) {
        tree.getRoot().setParent(this);
    }

}
