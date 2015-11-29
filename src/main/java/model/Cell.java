package model;

import java.util.List;

/**
 * Created by Asia on 2015-11-29.
 */
public class Cell {
    private State state;
    private Material material;
    private List<Cell> neighbors;

    public Cell(State state, Material material, List<Cell> neighbors) {
        this.state = state;
        this.material = material;
        this.neighbors = neighbors;
    }

    public List<Cell> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(List<Cell> neighbors) {
        this.neighbors = neighbors;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material){
        this.material = material;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
