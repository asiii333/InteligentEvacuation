package model;

import java.util.ArrayList;
import java.util.List;

import static model.State.*;

/**
 * Created by Asia on 2015-11-29.
 */
public class Cell {
    private State state;
    private Material material;
    private List<Cell> neighbors;
    private int burnCounter;
    private int smokeCounter;
    private boolean escapeRoad;

    public Cell(Material material){
        state = NORMAL;
        this.material = material;
        escapeRoad = false;
        this.burnCounter = material.getConstantBurn();
        neighbors = new ArrayList<>();
    }

    public Cell(){
        state = NORMAL;
        material = null;
        neighbors = new ArrayList<>();
        escapeRoad = false;
        burnCounter = 3;
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

    public boolean isEscapeRoad() {
        return escapeRoad;
    }

    public void setIsEscapeRoad(boolean isEscapeRoad) {
        this.escapeRoad = isEscapeRoad;
    }

    public int getBurnCounter() {
        return burnCounter;
    }

    public void setBurnCounter(int burnCounter) {
        this.burnCounter = burnCounter;
    }

    public int getSmokeCounter() {
        return smokeCounter;
    }

    public void setSmokeCounter(int smokeCounter) {
        this.smokeCounter = smokeCounter;
    }

    public void reset(){
        setState(NORMAL);
        setBurnCounter(material.getConstantBurn());
        setSmokeCounter(material.getConstantBurn());
    }
}
