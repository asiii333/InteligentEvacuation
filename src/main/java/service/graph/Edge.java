package service.graph;


import model.Room;

public class Edge {
    private int source;
    private int target;
    private int weight;
    public Room room;

    public Edge(int source, int target, int weight) {
        this.source = source;
        this.target = target;
        this.weight = weight;
    }

    public Edge(int source, int target, int weight, Room room) {
        this(source, target, weight);
        this.room = room;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "source=" + source +
                ", target=" + target +
                ", weight=" + weight +
                '}';
    }

    @Override
    public int hashCode() {
        int result = source;
        result = 31 * result + target;
        result = 31 * result + weight;
        return result;
    }

    @Override
    public boolean equals(Object innyObiekt) {
        if (this == innyObiekt)
            return true;
        return false;

    }

}
