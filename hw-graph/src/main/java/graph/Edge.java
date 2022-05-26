package graph;

public class Edge<N, E> {

    private N parent;
    private N child;

    private E label;

    public Edge(N parent, N child, E label) {
        this.parent = parent;
        this.child = child;
        this.label = label;
    }

    public N getChild() {
        return this.child;
    }

    public E getLabel() {
        return this.label;
    }
}
