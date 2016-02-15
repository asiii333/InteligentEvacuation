package service

import model.Board
import model.Cell
import model.State
import spock.lang.Specification

import java.text.Normalizer

/**
 * Created by Asia on 2016-01-07.
 */
class CalculateStateTest extends Specification {
    Board board = new Board();
    List<List<Cell>> cellBoard;
    CalculateState calculateState;
    BoardService service;

    def setup(){
        calculateState = new CalculateState();
        service = new BoardService(board);
        service.boardWidth = 100;
        service.boardHeight = 50;
        service.initializeBoard();
        cellBoard = board.cellBoard;
    }
    def "CalculateState - burn state one execution"() {
        given:
            cellBoard.get(1).get(1).setState(State.BURN);
            cellBoard.get(1).get(1).setTempState(State.BURN);
            cellBoard.get(1).get(1).setBurnCounter(0);
            cellBoard.get(1).get(1).setTempBurnCounter(0);
        when:
            calculateState.calculateState(board);
        then:
            cellBoard.get(1).get(1).getNeighbors().each{
                it.getState() == State.NORMAL
                it.getBurnCounter() == 4
            }
            cellBoard.get(1).get(1).getState() == State.BURN;
            cellBoard.get(1).get(1).getSmokeCounter() == 4;
    }
    //smokecounter - 5
    def "CalculateState - burn state - 1 burn cell - 5 x excution"() {
        given:
        cellBoard.get(1).get(1).setState(State.BURN);
        cellBoard.get(1).get(1).setTempState(State.BURN);
        cellBoard.get(1).get(1).setBurnCounter(0);
        cellBoard.get(1).get(1).setTempBurnCounter(0);
        when:
        calculateState.calculateState(board);
        calculateState.calculateState(board);
        calculateState.calculateState(board);
        calculateState.calculateState(board);
        calculateState.calculateState(board);
        then:
        cellBoard.get(1).get(1).getState() == State.DEAD
        cellBoard.get(1).get(1).getSmokeCounter() == 0
        cellBoard.get(1).get(1).getNeighbors().each{
            it.getState() == State.BURN
            it.getBurnCounter() == 0
        }
    }
    //burncounter - 5
    def "CalculateState - normal state - all cell neigh burn - 2 execution"() {
        given:
        cellBoard.get(1).get(1).getNeighbors().each {
            it.setState(State.BURN)
            it.setTempState(State.BURN)
        };
        when:
        calculateState.calculateState(board);
        calculateState.calculateState(board);
        then:
        cellBoard.get(1).get(1).getState() == State.BURN
        cellBoard.get(1).get(1).getBurnCounter() == 0
    }
    //burncounter - 5
    def "CalculateState - normal state - 5 neigh burn - 2 execution"() {
        given:
        def list = 0..4;
        list.each {
            cellBoard.get(1).get(1).getNeighbors().get(it).setState(State.BURN)
            cellBoard.get(1).get(1).getNeighbors().get(it).setTempState(State.BURN)
        }
        when:
        calculateState.calculateState(board);
        calculateState.calculateState(board);
        then:
        cellBoard.get(1).get(1).getState() == State.NORMAL
        cellBoard.get(1).get(1).getBurnCounter() == 1
    }
    //burncounter - 5
    def "CalculateState - normal state - 4 neigh burn - 2 execution"() {
        given:
        def list = 0..3;
        list.each {
            cellBoard.get(1).get(1).getNeighbors().get(it).setState(State.BURN)
            cellBoard.get(1).get(1).getNeighbors().get(it).setTempState(State.BURN)
        }
        when:
        calculateState.calculateState(board);
        calculateState.calculateState(board);
        then:
        cellBoard.get(1).get(1).getState() == State.NORMAL
        cellBoard.get(1).get(1).getBurnCounter() == 1
    }
    //burncounter - 5 , odejmujemy po 1
    def "CalculateState - normal state - 3 neigh burn - 2 execution"() {
        given:
        def list = 0..2;
        list.each {
            cellBoard.get(1).get(1).getNeighbors().get(it).setState(State.BURN)
            cellBoard.get(1).get(1).getNeighbors().get(it).setTempState(State.BURN)
        }
        when:
        calculateState.calculateState(board);
        calculateState.calculateState(board);
        then:
        cellBoard.get(1).get(1).getState() == State.NORMAL
        cellBoard.get(1).get(1).getBurnCounter() == 3
    }
}
