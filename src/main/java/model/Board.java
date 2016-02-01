package model;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private List<List<Cell>> cellBoard = new ArrayList<List<Cell>>();
    private  Cell startEscapeRoad;
    private  Cell endEscapeRoad;
    private  boolean safeEscapeRoad;
    public static final int WIGHT = 100;
    public static final int HEIGHT = 100;

    public  Cell getEndEscapeRoad() {
        return endEscapeRoad;
    }

    public  void setEndEscapeRoad(Cell endEscapeRoad) {
        this.endEscapeRoad = endEscapeRoad;
    }

    public  Cell getStartEscapeRoad() {
        return startEscapeRoad;
    }

    public  void setStartEscapeRoad(Cell startEscapeRoad) {
        this.startEscapeRoad = startEscapeRoad;
    }

    public  boolean isSafeEscapeRoad() {
        return safeEscapeRoad;
    }

    public void setSafeEscapeRoad(boolean safeEscapeRoad) {
        this.safeEscapeRoad = safeEscapeRoad;
    }

    public List<List<Cell>> getCellBoard() {
        return cellBoard;
    }

    public void setCellBoard(List<List<Cell>> board) {
        this.cellBoard = board;
    }

    public void reset(){
        for(List<Cell> rowCell : cellBoard){
            for(Cell cell : rowCell){
                cell.reset();
            }
        }
    }
}
