package controler;

import model.Board;
import model.Cell;
import model.Material;
import model.materials.Unknown;
import service.BoardService;
import service.CalculateEscapeRoad;
import service.CalculateState;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Asia on 2015-11-29.
 */
public class ControlBoard {
    private Board board;
    private  List<List<Cell>> cellBoard;
    private BoardService boardService;
    private int boardWidth;
    private int boardHeight;

    public ControlBoard(Board board) {
        this.board = board;
        boardWidth = board.WIGHT;
        boardHeight = board.WIGHT;
        boardService = new BoardService(board);
        boardService.initializeBoard();
        cellBoard = board.getCellBoard();
    }

    public void cleanBoard(){
            board.reset();
    }

    public void calculateNextState(){
        boardService.calculateState();
    }
}
