package controler;

import model.Board;

/**
 * Created by Asia on 2015-11-29.
 */
public class ControlBoard {
    private Board board;
    private CalculateState calculateState;

    public ControlBoard(Board board) {
        this.board = board;
        calculateState = new CalculateState(board);
    }

    public void initializeBoard(){

    }

    public void cleanBoard(){

    }

    public void calculateNextState(){
        calculateState.calculateState();
    }
}
