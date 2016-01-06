package service

import model.Board
import model.Cell

/**
 * Created by Asia on 2016-01-06.
 */
class BoardServiceTest extends spock.lang.Specification {
    Board board = new Board();
    BoardService service = new BoardService(board);
    def maxHeight = service.boardHeight;
    def maxWidth = service.boardHeight;

    def "test board after fulfill - is fill by Cell"(){
        when:
            service.fullfillBoard()
        then:
            service.cellBoard.each {
                it.each {
                    it instanceof Cell
                    it != null
                }
            }
        service.cellBoard.size() == 100
        service.cellBoard.get(0).size() == 100
    }

    def "test board after fulfill - board size"(){
        when:
            service.fullfillBoard()
        then:
            service.cellBoard.size() == 100
            service.cellBoard.get(0).size() == 100
    }

    def "test set main neighbourg"(){
        given:
            service.fullfillBoard()
            def suscpectNeigh = [
                    service.cellBoard.get(0).get(0),
                    service.cellBoard.get(0).get(1),
                    service.cellBoard.get(0).get(2),
                    service.cellBoard.get(1).get(0),
                    service.cellBoard.get(1).get(2),
                    service.cellBoard.get(2).get(0),
                    service.cellBoard.get(2).get(1),
                    service.cellBoard.get(2).get(2),
            ]
        when:
            service.setMainNeighbourg()
        then:
            service.cellBoard.get(1).get(1).getNeighbors().sort() == suscpectNeigh.sort();

    }

    def "test set border neighbourg - left up narrow"(){
        given:
        service.fullfillBoard()
        def suscpectNeigh = [
                service.cellBoard.get(0).get(1),
                service.cellBoard.get(1).get(0),
                service.cellBoard.get(1).get(1)
        ]
        when:
        service.setBorderNeighbourg()
        then:
        service.cellBoard.get(0).get(0).getNeighbors().sort() == suscpectNeigh.sort();

    }

    def "test set border neighbourg - right up narrow"(){
        given:
            service.fullfillBoard()
        def suscpectNeigh = [
                service.cellBoard.get(0).get(maxHeight-2),
                service.cellBoard.get(1).get(maxHeight-1),
                service.cellBoard.get(1).get(maxHeight-2)
        ]
        when:
        service.setBorderNeighbourg()
        then:
        service.cellBoard.get(0).get(maxHeight-1).getNeighbors().sort() == suscpectNeigh.sort();
    }

    def "test set border neighbourg - right down narrow"(){
        given:
        service.fullfillBoard()
        def suscpectNeigh = [
                service.cellBoard.get(maxWidth-1).get(maxHeight-2),
                service.cellBoard.get(maxWidth-2).get(maxHeight-1),
                service.cellBoard.get(maxWidth-2).get(maxHeight-2),
        ]
        when:
        service.setBorderNeighbourg()
        then:
        service.cellBoard.get(maxHeight-1).get(maxHeight-1).getNeighbors().sort() == suscpectNeigh.sort();
    }

    def "test set border neighbourg - left down narrow"(){
        given:
        service.fullfillBoard()
        def suscpectNeigh = [
                service.cellBoard.get(maxWidth-1).get(1),
                service.cellBoard.get(maxWidth-2).get(0),
                service.cellBoard.get(maxWidth-2).get(1)
        ]
        when:
        service.setBorderNeighbourg()
        then:
        service.cellBoard.get(maxHeight-1).get(0).getNeighbors().sort() == suscpectNeigh.sort();
    }

    def "test set border - up border"(){
        given:
        service.fullfillBoard()
        def suscpectNeigh = [
                service.cellBoard.get(0).get(0),
                service.cellBoard.get(0).get(2),
                service.cellBoard.get(1).get(0),
                service.cellBoard.get(1).get(1),
                service.cellBoard.get(1).get(2)
        ]
        when:
        service.setBorderNeighbourg()
        then:
        service.cellBoard.get(0).get(1).getNeighbors().sort() == suscpectNeigh.sort();
    }

    def "test set border - down border"(){
        given:
        service.fullfillBoard()
        def suscpectNeigh = [
                service.cellBoard.get(maxWidth-1).get(0),
                service.cellBoard.get(maxWidth-1).get(2),
                service.cellBoard.get(maxWidth-2).get(0),
                service.cellBoard.get(maxWidth-2).get(1),
                service.cellBoard.get(maxWidth-2).get(2)
        ]
        when:
        service.setBorderNeighbourg()
        then:
        service.cellBoard.get(maxWidth-1).get(1).getNeighbors().sort() == suscpectNeigh.sort();
    }
    def "test set border - left border"(){
        given:
        service.fullfillBoard()
        def suscpectNeigh = [
                service.cellBoard.get(0).get(0),
                service.cellBoard.get(0).get(1),
                service.cellBoard.get(1).get(1),
                service.cellBoard.get(2).get(0),
                service.cellBoard.get(2).get(1),
        ]
        when:
        service.setBorderNeighbourg()
        then:
        service.cellBoard.get(1).get(0).getNeighbors().sort() == suscpectNeigh.sort();
    }

    def "test set border - right border"(){
        given:
        service.fullfillBoard()
        def suscpectNeigh = [
                service.cellBoard.get(0).get(maxHeight-1),
                service.cellBoard.get(0).get(maxHeight-2),
                service.cellBoard.get(1).get(maxHeight-2),
                service.cellBoard.get(2).get(maxHeight-1),
                service.cellBoard.get(2).get(maxHeight-2)
        ]
        when:
        service.setBorderNeighbourg()
        then:
        service.cellBoard.get(1).get(maxHeight-1).getNeighbors().sort() == suscpectNeigh.sort();
    }
}

