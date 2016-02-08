package service.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by Asia on 2015-10-20.
 */
public class FordaBellmana {
    private IGraph<Integer> graph;
    private int distances[];
    private Integer[] predecesor;
    private int firstIndexId ;

    private int numberofvertices;

    public static final int MAX_VALUE = 999;

    public FordaBellmana(IGraph<Integer> graph){
        this.graph = graph;

        numberofvertices = graph.getVerticesCount();
        distances = new int[numberofvertices+1];
        predecesor = new Integer[numberofvertices+1];
        this.firstIndexId = 0;

    }

    public int run(int source, int target) {

        for (int node = firstIndexId ; node < numberofvertices + firstIndexId; node++) {
            distances[node] = MAX_VALUE;
        }


        distances[source] = 0;
        //predecesor[source] = null;
        for (int node = firstIndexId; node < numberofvertices + firstIndexId; node++) {
            for (int sourcenode = firstIndexId; sourcenode < numberofvertices + firstIndexId; sourcenode++) {
                for (int destinationnode = firstIndexId; destinationnode < numberofvertices + firstIndexId; destinationnode++) {
                    Integer weight = graph.getWeight(sourcenode, destinationnode);
                    if (weight !=null &&  weight != MAX_VALUE) {
                        if (distances[destinationnode] > distances[sourcenode] + weight) {
                            distances[destinationnode] = distances[sourcenode] + weight;
                            predecesor[destinationnode] = sourcenode;
                        }
                    }

                }

            }

        }


        for (int sourcenode = firstIndexId; sourcenode < numberofvertices + firstIndexId; sourcenode++) {
            for (int destinationnode = firstIndexId; destinationnode < numberofvertices + firstIndexId; destinationnode++) {
                Integer weight = graph.getWeight(sourcenode, destinationnode);
                if (weight !=null &&  weight != MAX_VALUE) {
                    if (distances[destinationnode] > distances[sourcenode]+ graph.getWeight(sourcenode, destinationnode))
                        System.out.println("The Graph contains negative egde cycle");
                }
            }
        }

        return distances[target];

    }

    public List<Integer> getPath(int source, int target){

        List<Integer> result = new ArrayList<>();
        int temp = target;
        while(temp != source){
            result.add(temp);
            temp = predecesor[temp];
        }
        result.add(source);

        Collections.reverse(result);
        return result;
    }
}
