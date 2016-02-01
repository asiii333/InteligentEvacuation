package controler

import model.Board
import model.State
import spock.lang.Specification

/**
 * Created by Asia on 2016-01-06.
 */
class ControlBoardTest extends Specification {
    Board board  = new Board()
    ControlBoard control = new ControlBoard(board);

    def "CleanBoard"() {
        given:
            control.cellBoard.get(0).get(0).setBurnCounter(0);
            control.cellBoard.get(0).get(0).setSmokeCounter(0);
            control.cellBoard.get(0).get(0).setState(State.DEAD)
            control.cellBoard.get(1).get(1).setBurnCounter(0);
            control.cellBoard.get(1).get(1).setSmokeCounter(5);
            control.cellBoard.get(1).get(1).setState(State.BURN)
        when:
            control.cleanBoard();
        then:
           control.cellBoard.get(0).get(0).getState() == State.NORMAL
           control.cellBoard.get(0).get(0).getBurnCounter() == 5
           control.cellBoard.get(0).get(0).getSmokeCounter() == 5
            control.cellBoard.get(1).get(1).getState() == State.NORMAL
            control.cellBoard.get(1).get(1).getBurnCounter() == 5
            control.cellBoard.get(1).get(1).getSmokeCounter() == 5
    }

    def "CalculateNextState"() {

    }
}
