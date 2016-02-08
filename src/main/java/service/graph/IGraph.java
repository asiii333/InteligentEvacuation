package service.graph;

import java.util.List;


public interface IGraph <T> {

    void addVertex(T vertex);
    void removeVertex(T vertex);
    void addEdge(T startVertex, T endVertex, T weight); // add Edge or edit weight (if exists)
    void removeEdge(T startVertex, T endVertex);
    List<Integer> getAdjacentVertices(T vertex);
    T [] getIncidenceEdges(T vertex);
    List<Edge> getIncidenceEdgesToVertex(T vertex);
    int getVerticesCount();
    int getEdgesCount();
    boolean isNeighbour(T startVertex, T endVertex);

    void modifiedWeight(T startVertex, T endVertex, T weight);
    T getWeight(T startVertex, T endVertex);
    List<Edge> getEdges();
    int getEdgeWeight(int u, int v);
}
