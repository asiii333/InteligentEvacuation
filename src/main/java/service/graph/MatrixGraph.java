package service.graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Asia on 2016-02-15.
 */
public class MatrixGraph implements IGraph<Integer> {

    private Integer [][] matrix;
    private Edge [][] edgeMatrix;
    private int verticesNumber;
    private int edgesNumber;
    private int firstVertexId; // 0 or 1

    public MatrixGraph(List<Edge> data, int verticesNumber) {
        this.verticesNumber = verticesNumber;
        this.firstVertexId = 0;

        this.matrix = new Integer[this.verticesNumber][this.verticesNumber];
        this.edgeMatrix = new Edge[this.verticesNumber][this.verticesNumber];
        for (Edge edge: data) {
            matrix[edge.getSource()-this.firstVertexId][edge.getTarget()-this.firstVertexId] = edge.getWeight();
            edgeMatrix[edge.getSource()-this.firstVertexId][edge.getTarget()-this.firstVertexId] = edge;
        }

        this.edgesNumber = this.countEdges();
    }

    // TODO:
    public void addVertex(Integer vertex) {
        this.verticesNumber +=1;
    }

    // TODO:

    public void removeVertex(Integer vertex) {
        this.verticesNumber -=1;
    }


    public void addEdge(Integer startVertex, Integer endVertex, Integer weight) {
        this.matrix[startVertex-this.firstVertexId][endVertex-this.firstVertexId] = weight;
        this.edgesNumber +=1;
    }


    public void removeEdge(Integer startVertex, Integer endVertex) {
        this.matrix[startVertex-this.firstVertexId][endVertex-this.firstVertexId] = null;
        this.edgesNumber -=1;
    }


    public LinkedList<Integer> getAdjacentVertices(Integer vertex) {
        LinkedList<Integer> adjacentVertices = new LinkedList<>();
        for (int i=0; i<this.verticesNumber; i++) {
            if (this.isEdge(vertex, i+1) || this.isEdge(i+1, vertex)) {
                adjacentVertices.push(i+1);
            }
        }

        return adjacentVertices;
    }


    public Integer[] getIncidenceEdges(Integer vertex) {
        return new Integer[0];
    }

    @Override
    public  List<Edge> getIncidenceEdgesToVertex(Integer vertex) {
        List<Edge> incydentEdges = new ArrayList<Edge>();
        for(int i =0;i<matrix.length;i++) {

            Edge edge = edgeMatrix[i][vertex];
            if(edge !=null && edge.getTarget() == vertex){
                incydentEdges.add(edge);
            }

        }

        return incydentEdges;
    }


    public int getVerticesCount() {
        return this.verticesNumber;
    }


    public int getEdgesCount() {
        return this.edgesNumber;
    }


    public boolean isNeighbour(Integer vertexA, Integer vertexB) {
        return this.isEdge(vertexA, vertexB) || this.isEdge(vertexB, vertexA);
    }


    public void modifiedWeight(Integer startVertex, Integer endVertex, Integer weight) {
        this.matrix[startVertex-this.firstVertexId][endVertex-this.firstVertexId] = weight;
    }


    public Integer getWeight(Integer startVertex, Integer endVertex) {
        return this.matrix[startVertex-this.firstVertexId][endVertex-this.firstVertexId];
    }


    public List<Edge> getEdges() {
        List<Edge> edges = new LinkedList<>();

        for (int i=0; i<this.getVerticesCount(); i++) {
            for (int j=0; j< this.getVerticesCount(); j++) {
                if (isEdge(i+this.firstVertexId,j+this.firstVertexId))
                    edges.add(new Edge(i+this.firstVertexId, j+this.firstVertexId, this.matrix[i][j]));
            }
        }

        return edges;
    }

    @Override
    public int getEdgeWeight(int u, int v) {
        if(matrix[u][v] != null){
            return matrix[u][v];
        }
        return 0;
    }

    private boolean isEdge(Integer startVertex, Integer endVertex) {
        Integer value = this.matrix[startVertex-this.firstVertexId][endVertex-this.firstVertexId];

        return value != null && value != Integer.MAX_VALUE && value != Integer.MIN_VALUE;
    }

    private int countEdges() {
        int edgesNumber = 0;
        for (int i=0; i<this.verticesNumber; i++) {
            for (int j=0; j<this.verticesNumber; j++) {
                if (isEdge(i+this.firstVertexId,j+this.firstVertexId))
                    edgesNumber +=1;
            }
        }

        return edgesNumber;
    }
}
