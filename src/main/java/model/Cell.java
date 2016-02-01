package model;

import model.materials.Unknown;

import java.util.ArrayList;
import java.util.List;

import static model.State.*;

/**
 * Created by Asia on 2015-11-29.
 */
public class Cell {
    public static final int MAX_NEIGHBOUR = 8;
    private State state;
    private State tempState;
    private Material material;
    private List<Cell> neighbors;
    private int burnCounter;
    private int tempBurnCounter;
    private int smokeCounter;
    private int tempSmokeCounter;
    private boolean escapeRoad;
    private boolean tempEscapeRoad;
    private int x,y;

    public Cell(Material material){
        state = NORMAL;
        tempState = state;
        this.material = material;
        escapeRoad = false;
        tempEscapeRoad = escapeRoad;
        burnCounter = material.getConstantBurn();
        tempBurnCounter = material.getConstantBurn();
        smokeCounter = material.getConstantSmoke();
        tempSmokeCounter = material.getConstantSmoke();
        neighbors = new ArrayList<>();
    }

    public Cell(){
        state = NORMAL;
        material = null;
        neighbors = new ArrayList<>();
        escapeRoad = false;
        burnCounter = 3;
        smokeCounter = 3;
    }

    public Cell(Material material, int i, int j) {
        state = NORMAL;
        tempState = state;
        this.material = material;
        escapeRoad = false;
        tempEscapeRoad = escapeRoad;
        burnCounter = material.getConstantBurn();
        tempBurnCounter = material.getConstantBurn();
        smokeCounter = material.getConstantSmoke();
        tempSmokeCounter = material.getConstantSmoke();
        neighbors = new ArrayList<>();
        x = i;
        y = j;
    }

    public void reset(){
        setState(NORMAL);
        setTempState(NORMAL);
        setBurnCounter(material.getConstantBurn());
        setTempBurnCounter(material.getConstantBurn());
        setSmokeCounter(material.getConstantBurn());
        setTempSmokeCounter(material.getConstantBurn());
    }
    public void update(){
        state = tempState;
        burnCounter = tempBurnCounter;
        smokeCounter = tempSmokeCounter;
        escapeRoad = tempEscapeRoad;
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

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isTempEscapeRoad() {
        return tempEscapeRoad;
    }

    public void setTempEscapeRoad(boolean tempEscapeRoad) {
        this.tempEscapeRoad = tempEscapeRoad;
    }

    public int getTempSmokeCounter() {
        return tempSmokeCounter;
    }

    public void setTempSmokeCounter(int tempSmokeCounter) {
        this.tempSmokeCounter = tempSmokeCounter;
    }

    public int getTempBurnCounter() {
        return tempBurnCounter;
    }

    public void setTempBurnCounter(int tempBurnCounter) {
        this.tempBurnCounter = tempBurnCounter;
    }

    public State getTempState() {
        return tempState;
    }

    public void setTempState(State tempState) {
        this.tempState = tempState;
    }
}
