package service;

import model.Board;
import model.Cell;

import java.util.List;

import static model.State.BURN;
import static model.State.DEAD;

/**
 * Created by Asia on 2015-11-29.
 */
public class CalculateState {

    public void calculateState(Board board){
        calculateNewState(board);
        updateBoard(board);
    }

    private void calculateNewState(Board board){
        for(List<Cell> cellRow : board.getCellBoard()){
            for(Cell cell: cellRow){
                switch (cell.getState()){
                    case NORMAL : checkCellForNormalState(cell);
                    case BURN : checkCellForBurnState(cell);
                }
            }
        }
    }
    private void checkCellForBurnState(Cell cell) {
        cell.setTempSmokeCounter(cell.getSmokeCounter() - 1);
        if(cell.getTempSmokeCounter() == 0){
            cell.setTempState(DEAD);
        }
    }

    private void updateBoard(Board board){
        for(List<Cell> cellRow : board.getCellBoard()){
            for(Cell cell: cellRow){
                cell.update();
            }
        }
    }

    private void checkCellForNormalState(Cell cell) {

        int burnCont = calcBurnCountFormNormalState(cell);
        cell.setTempBurnCounter(burnCont);

        if(burnCont <= 0){
            cell.setTempState(BURN);
            cell.setTempBurnCounter(0);
        }
    }

    private int calcBurnCountFormNormalState(Cell cell) {

        int burnNeighbour  = burnNeighCounter(cell);
        int burnCont = 0;

        if(allNeighBurn(burnNeighbour)) {
            burnCont = cell.getBurnCounter() - 3;
        }else if(moreThanHalfNeighBurn(burnNeighbour) ){
            burnCont = cell.getBurnCounter() - 2;
        }else if(atLeastOneNeighBurn(burnNeighbour)){
            burnCont = cell.getBurnCounter() - 1;
        }else{
            burnCont = cell.getBurnCounter();
        }

        return burnCont;
    }

    private boolean moreThanHalfNeighBurn(int burnCounter) {
        if(burnCounter >= Cell.MAX_NEIGHBOUR/2){
            return true;
        }
        return false;
    }

    private boolean allNeighBurn(int burnCounter) {
        if(burnCounter == Cell.MAX_NEIGHBOUR){
            return true;
        }
        return false;
    }

    private boolean atLeastOneNeighBurn(int burnCounter) {
        if(burnCounter >= 1){
            return true;
        }
        return false;
    }

    private int burnNeighCounter(Cell cell) {
        int counter = 0;
        for(Cell neigh : cell.getNeighbors()){
            if(neigh.getState().equals(BURN)){
                counter++;
            }
        }
        return counter;
    }

}
