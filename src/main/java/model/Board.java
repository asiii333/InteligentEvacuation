package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Asia on 2015-11-29.
 */
public class Board {

    private static List<List<Cell>> cellBoard = new ArrayList<List<Cell>>();
    private static Cell startEscapeRoad;
    private static Cell endEscapeRoad;
    public static final int WIGHT = 100;
    public static final int HEIGHT = 100;
    private static Board boardSingleton = null;

    private Board(){}

    public static Board getInstance(){
        if(boardSingleton == null) {
            boardSingleton = new Board();
        }
        return boardSingleton;
    }

    public static Cell getEndEscapeRoad() {
        return endEscapeRoad;
    }

    public static void setEndEscapeRoad(Cell endEscapeRoad) {
        Board.endEscapeRoad = endEscapeRoad;
    }

    public static Cell getStartEscapeRoad() {
        return startEscapeRoad;
    }

    public static void setStartEscapeRoad(Cell startEscapeRoad) {
        Board.startEscapeRoad = startEscapeRoad;
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
