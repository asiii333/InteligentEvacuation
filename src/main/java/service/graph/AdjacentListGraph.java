package service.graph;

import java.util.*;


public class AdjacentListGraph implements IGraph<Integer> {

    public Edge [][] list;
    public Set<Edge> setEdges;
    public int verticesNumber;
    public int firstVertexId;

    public AdjacentListGraph(List<Edge> data, int verticesNumber) {
        this.verticesNumber = verticesNumber;
        this.firstVertexId = 0;

        this.list = new Edge[this.verticesNumber][];
        setEdges = new HashSet<>(this.verticesNumber);
        for (int i=0;i <this.verticesNumber; i++) {
            list[i] = new Edge[0];
        }

        for (Edge edge: data) {
            list[edge.getSource() - this.firstVertexId] =  addElement(list[edge.getSource() - this.firstVertexId], edge);
            setEdges.add(edge);
        }
    }

    @Override
    public void addVertex(Integer vertex) {

    }

    @Override
    public void removeVertex(Integer vertex) {

    }

    @Override
    public void addEdge(Integer startVertex, Integer endVertex, Integer weight) {

    }

    @Override
    public void removeEdge(Integer startVertex, Integer endVertex) {

    }

    @Override
    public LinkedList<Integer> getAdjacentVertices(Integer vertex) {
        return null;
    }

    @Override
    public Integer[] getIncidenceEdges(Integer vertex) {
        return new Integer[0];
    }

    @Override
    public  List<Edge> getIncidenceEdgesToVertex(Integer vertex) {
        List<Edge> incydentEdges = new ArrayList<Edge>();
        for(Edge[] array: list){
            for(Edge edge:array){
                if(edge.getTarget() == vertex){
                    incydentEdges.add(edge);
                }
            }
        }

        return incydentEdges;
    }

    @Override
    public int getVerticesCount() {
        return this.verticesNumber;
    }

    @Override
    public int getEdgesCount() {
        return setEdges.size();
    }

    @Override
    public boolean isNeighbour(Integer startVertex, Integer endVertex) {
        return false;
    }

    @Override
    public void modifiedWeight(Integer startVertex, Integer endVertex, Integer weight) {

    }

    @Override
    public Integer getWeight(Integer startVertex, Integer endVertex) {
        return null;
    }

    @Override
    public List<Edge> getEdges() {
        List<Edge> edges = new LinkedList<>();

        for (int i=0; i< this.list.length; i++) {
            Edge [] row = this.list[i];
            for (int j=0; j< row.length; j++) {
                edges.add(row[j]);
            }
        }

        return edges;
    }

    @Override
    public int getEdgeWeight(int u, int v) {
        for(Edge edge : list[u]){
            if(edge.getTarget()==v){
                return edge.getWeight();
            }
        }
        return 0;
    }


    private static Edge[] addElement(Edge[] array, Edge element) {

        array  = Arrays.copyOf(array, array.length + 1);
        array[array.length - 1] = element;
        return array;
    }
}
