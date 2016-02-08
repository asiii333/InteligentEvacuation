package service

import model.Board
import model.Cell
import model.Material
import model.materials.Wall
import spock.lang.Specification

/**
 * Created by Asia on 2016-02-06.
 */
class GraphServiceTest extends Specification {

    Board board = new Board();
    List<List<Cell>> cellBoard;
    //CalculateState calculateState = new CalculateState();
    BoardService boardService = new BoardService(board);
    GraphService graphService = new GraphService(board);

    def setup(){
        boardService.initializeBoard();
        cellBoard = boardService.cellBoard;
    }


    def "getWallNeighList"(){
        List<Cell> result;
        given:
        createSingleRoom()
        when:
        result = graphService.getWallNeighList(cellBoard.get(0).get(0));
        then:
        result.size() == 2;

    }

    def "findCorner single room"(){
        given:
        createSingleRoom();
        when:
        getFirstRoomCorner();
        then:
        graphService.cornerList.size() == 4;
        graphService.cornerList.contains(cellBoard.get(0).get(0));
        graphService.cornerList.contains(cellBoard.get(0).get(2));
        graphService.cornerList.contains(cellBoard.get(2).get(0));
        graphService.cornerList.contains(cellBoard.get(2).get(2));
    }

    def "findCorner 2 room"(){
        given:
        create2Room();
        getFirstRoomCorner();
        when:
        getSecondRoomCorner();
        then:
        graphService.cornerList.size() == 6;
        graphService.cornerList.contains(cellBoard.get(0).get(0));
        graphService.cornerList.contains(cellBoard.get(0).get(2));
        graphService.cornerList.contains(cellBoard.get(2).get(0));
        graphService.cornerList.contains(cellBoard.get(2).get(2));
        graphService.cornerList.contains(cellBoard.get(0).get(4));
        graphService.cornerList.contains(cellBoard.get(2).get(4));
    }

    def "findCorner 4 room"(){
        given:
        create4Room();
        getFirstRoomCorner();
        getSecondRoomCorner();
        when:
        getFourRoomCorner();
        then:
        graphService.cornerList.size() == 9;
        graphService.cornerList.contains(cellBoard.get(0).get(0));
        graphService.cornerList.contains(cellBoard.get(0).get(2));
        graphService.cornerList.contains(cellBoard.get(2).get(0));
        graphService.cornerList.contains(cellBoard.get(2).get(2));
        graphService.cornerList.contains(cellBoard.get(0).get(4));
        graphService.cornerList.contains(cellBoard.get(2).get(4));
        graphService.cornerList.contains(cellBoard.get(4).get(0));
        graphService.cornerList.contains(cellBoard.get(4).get(2));
        graphService.cornerList.contains(cellBoard.get(4).get(4));
    }

    def "FindAllCorner"() {

    }

    def "FindRoom"() {
        given:
        initializeFourRoom();
        when:
        graphService.findAllRooms();
        then:
        graphService.roomsList.size() == 4;
    }

    def "find single room"() {
        given:
        createSingleRoom()
        getFirstRoomCorner();
        initializeGraph();
        when:
        graphService.findAllRooms();
        then:
        graphService.roomsList.size() == 1;
    }




    def "initializeDoorGraph"(){
        given:
        create2BigRoomWithDoor()
        graphService.board.setStartEscapeRoad(cellBoard.get(0).get(1));
        graphService.board.setEndEscapeRoad(cellBoard.get(1).get(6));
        graphService.findAllCornerAndDoor();
        graphService.findAllRooms();


        when:
        graphService.initializeDoorGraph();
        then:
        graphService.graph.getEdges().size() == 4
    }





















    def createSingleRoom(){
        Material wall = new Wall();
        cellBoard.get(0).get(0).setMaterial(wall);
        cellBoard.get(0).get(1).setMaterial(wall);
        cellBoard.get(0).get(2).setMaterial(wall);
        cellBoard.get(1).get(0).setMaterial(wall);
        cellBoard.get(1).get(2).setMaterial(wall);
        cellBoard.get(2).get(0).setMaterial(wall);
        cellBoard.get(2).get(1).setMaterial(wall);
        cellBoard.get(2).get(2).setMaterial(wall);
    }

    def create2BigRoomWithDoor(){
        Material wall = new Wall();
        cellBoard.get(0).get(0).setMaterial(wall);
        cellBoard.get(0).get(1).setMaterial(wall);
        cellBoard.get(0).get(1).setDoor(true);
        cellBoard.get(0).get(2).setMaterial(wall);
        cellBoard.get(0).get(3).setMaterial(wall);
        cellBoard.get(0).get(4).setMaterial(wall);
        cellBoard.get(1).get(0).setMaterial(wall);
        cellBoard.get(1).get(4).setMaterial(wall);
        cellBoard.get(2).get(0).setMaterial(wall);
        cellBoard.get(2).get(4).setMaterial(wall);
        cellBoard.get(2).get(4).setDoor(true);
        cellBoard.get(3).get(0).setMaterial(wall);
        cellBoard.get(3).get(4).setMaterial(wall);
        cellBoard.get(3).get(0).setMaterial(wall);
        cellBoard.get(3).get(1).setMaterial(wall);
        cellBoard.get(3).get(2).setMaterial(wall);
        cellBoard.get(3).get(3).setMaterial(wall);
        cellBoard.get(3).get(4).setMaterial(wall);

        //second room
        cellBoard.get(0).get(5).setMaterial(wall);
        cellBoard.get(0).get(6).setMaterial(wall);
        cellBoard.get(0).get(7).setMaterial(wall);
        cellBoard.get(1).get(7).setMaterial(wall);
        cellBoard.get(2).get(7).setMaterial(wall);
        cellBoard.get(3).get(7).setMaterial(wall);
        cellBoard.get(3).get(5).setMaterial(wall);
        cellBoard.get(3).get(6).setMaterial(wall);
    }

    def create2Room(){
        createSingleRoom();
        Material wall = new Wall();
        cellBoard.get(0).get(3).setMaterial(wall);
        cellBoard.get(0).get(4).setMaterial(wall);
        cellBoard.get(1).get(4).setMaterial(wall);
        cellBoard.get(2).get(3).setMaterial(wall);
        cellBoard.get(2).get(4).setMaterial(wall);
    }
    def create4Room(){
        create2Room();
        Material wall = new Wall();

        cellBoard.get(3).get(0).setMaterial(wall);
        cellBoard.get(3).get(2).setMaterial(wall);
        cellBoard.get(4).get(0).setMaterial(wall);
        cellBoard.get(4).get(1).setMaterial(wall);
        cellBoard.get(4).get(2).setMaterial(wall);

        cellBoard.get(3).get(4).setMaterial(wall);
        cellBoard.get(4).get(3).setMaterial(wall);
        cellBoard.get(4).get(4).setMaterial(wall);
    }

    def getFirstRoomCorner(){
        graphService.findCorner(graphService.getWallNeighList(cellBoard.get(0).get(0)), cellBoard.get(0).get(0));
        graphService.findCorner(graphService.getWallNeighList(cellBoard.get(0).get(1)), cellBoard.get(0).get(1));
        graphService.findCorner(graphService.getWallNeighList(cellBoard.get(0).get(2)), cellBoard.get(0).get(2));
        graphService.findCorner(graphService.getWallNeighList(cellBoard.get(1).get(0)), cellBoard.get(1).get(0));
        graphService.findCorner(graphService.getWallNeighList(cellBoard.get(1).get(1)), cellBoard.get(1).get(1));
        graphService.findCorner(graphService.getWallNeighList(cellBoard.get(1).get(2)), cellBoard.get(1).get(2));
        graphService.findCorner(graphService.getWallNeighList(cellBoard.get(2).get(0)), cellBoard.get(2).get(0));
        graphService.findCorner(graphService.getWallNeighList(cellBoard.get(2).get(1)), cellBoard.get(2).get(1));
        graphService.findCorner(graphService.getWallNeighList(cellBoard.get(2).get(2)), cellBoard.get(2).get(2));
    }
    def getSecondRoomCorner(){
        graphService.findCorner(graphService.getWallNeighList(cellBoard.get(0).get(3)), cellBoard.get(0).get(3));
        graphService.findCorner(graphService.getWallNeighList(cellBoard.get(0).get(4)), cellBoard.get(0).get(4));
        graphService.findCorner(graphService.getWallNeighList(cellBoard.get(1).get(3)), cellBoard.get(1).get(3));
        graphService.findCorner(graphService.getWallNeighList(cellBoard.get(1).get(4)), cellBoard.get(1).get(4));
        graphService.findCorner(graphService.getWallNeighList(cellBoard.get(2).get(3)), cellBoard.get(2).get(3));
        graphService.findCorner(graphService.getWallNeighList(cellBoard.get(2).get(4)), cellBoard.get(2).get(4));
    }
    def getFourRoomCorner(){

        graphService.findCorner(graphService.getWallNeighList(cellBoard.get(3).get(0)), cellBoard.get(3).get(0));
        graphService.findCorner(graphService.getWallNeighList(cellBoard.get(3).get(1)), cellBoard.get(3).get(1));
        graphService.findCorner(graphService.getWallNeighList(cellBoard.get(3).get(2)), cellBoard.get(3).get(2));
        graphService.findCorner(graphService.getWallNeighList(cellBoard.get(4).get(0)), cellBoard.get(4).get(0));
        graphService.findCorner(graphService.getWallNeighList(cellBoard.get(4).get(1)), cellBoard.get(4).get(1));
        graphService.findCorner(graphService.getWallNeighList(cellBoard.get(4).get(2)), cellBoard.get(4).get(2));

        graphService.findCorner(graphService.getWallNeighList(cellBoard.get(3).get(3)), cellBoard.get(3).get(3));
        graphService.findCorner(graphService.getWallNeighList(cellBoard.get(3).get(4)), cellBoard.get(3).get(4));
        graphService.findCorner(graphService.getWallNeighList(cellBoard.get(3).get(3)), cellBoard.get(3).get(3));
        graphService.findCorner(graphService.getWallNeighList(cellBoard.get(4).get(4)), cellBoard.get(4).get(4));
        graphService.findCorner(graphService.getWallNeighList(cellBoard.get(4).get(3)), cellBoard.get(4).get(3));
        graphService.findCorner(graphService.getWallNeighList(cellBoard.get(4).get(4)), cellBoard.get(4).get(4));
    }

    def initializeFourRoom(){
        create4Room();
        getFirstRoomCorner();
        getSecondRoomCorner();
        getFourRoomCorner();
    }
}
