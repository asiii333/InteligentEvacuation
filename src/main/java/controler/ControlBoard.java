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
    public Board board;
    private  List<List<Cell>> cellBoard;
    private BoardService boardService;

    public ControlBoard(Board board) {
        this.board = board;
        boardService = new BoardService(board);
    }


}
