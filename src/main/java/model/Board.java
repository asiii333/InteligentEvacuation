package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Asia on 2015-11-29.
 */
public class Board {

    private static List<List<Cell>> callBoard = new ArrayList<List<Cell>>();

    private static Board boardSingleton = null;

    private Board(){}

    public static Board getInstance(){
        if(boardSingleton == null) {
            boardSingleton = new Board();
        }
        return boardSingleton;
    }
    public List<List<Cell>> getBoard() {
        return callBoard;
    }

    public void setBoard(List<List<Cell>> board) {
        this.callBoard = board;
    }
}
