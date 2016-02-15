package controler;

import model.Board;
import model.Cell;
import service.BoardService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Asia on 2015-11-29.
 */
public class ControlBoard {
    public Board board;
    private  List<List<Cell>> cellBoard;
    private BoardService boardService;

    public ControlBoard(Board board) {
        this.board = board;
        boardService = new BoardService(board);
    }


}
