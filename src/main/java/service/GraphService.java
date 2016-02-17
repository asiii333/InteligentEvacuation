package service;

import model.Board;
import model.Cell;
import model.Room;
import model.State;
import service.graph.*;

import java.util.*;

import static model.State.ESCAPE;
import static model.State.WALL;

/**
 * Created by Asia on 2016-02-06.
 */
public class GraphService {
    private static final String WALL_NAME = "wall";
    private Board board;
    public Set<Cell> cornerList = new HashSet<>();
    public Set<Room> roomsList = new HashSet<>();
    public HashMap<Integer, Cell> doorsMap = new HashMap<>();
    FordaBellmana fordBellamn;
    private List<Integer> shortesPath;
    private int startCellIndex;
    private int endCellIndex;
    private IGraph<Integer> graph;

    public GraphService(Board board){
        this.graph = board.graph;
        this.board = board;
    }
    public void initializeGraph(){
        findAllCornerAndDoor();
        findAllRooms();
        initializeDoorGraph();
    }
    private int doorCounter = 0;

    private void findAllCornerAndDoor(){
        doorCounter = 0;
        for(List<Cell> cellRow : board.getCellBoard() ){
            for(Cell cell: cellRow){
                List<Cell> wallNeighList = getWallNeighList(cell);
                findCorner( wallNeighList,cell);
                findDoor(cell);
            }
        }
    }

    private void  findDoor(Cell cell) {
        if(cell.isDoor() && !cell.equals(board.getEndEscapeRoad()) &&  !cell.equals(board.getStartEscapeRoad()) ){
            doorsMap.put(doorCounter, cell);
            doorCounter ++;
        }
    }

    private void initializeDoorGraph(){
        List<Edge> data = createDoorGraph();
        int verticesNumber = doorsMap.size();
        graph = new MatrixGraph(data, verticesNumber);
        fordBellamn = new FordaBellmana(graph);

    }

    private List<Edge> createDoorGraph() {
        List<Edge> edgeList = new LinkedList<>();
        startCellIndex = doorsMap.size();
        doorsMap.put(startCellIndex, board.getStartEscapeRoad());
        endCellIndex = doorsMap.size();
        doorsMap.put(endCellIndex, board.getEndEscapeRoad());

        for(Integer key : doorsMap.keySet()){
            for(Integer insideKey : doorsMap.keySet()){
                if(key != insideKey &&  !isKeyEqualsEnding(key, insideKey)) {

                    /*Room room = findRoomBeetwen(doorsMap.get(key), doorsMap.get(insideKey));
                    if(room != null){
                        int distance = countDistances(doorsMap.get(key), doorsMap.get(insideKey));
                        Edge edge = new Edge(key, insideKey, distance);
                        edge.room = room;
                        edgeList.add(edge);
                    }

                }else if(key != insideKey && isKeyEqualsEnding(key,  insideKey)){*/
                   if( findConnection(doorsMap.get(key), doorsMap.get(insideKey))){
                       Room room = findRoomContainsEndings(doorsMap.get(key), doorsMap.get(insideKey));
                       int distance = countDistances(doorsMap.get(key), doorsMap.get(insideKey));
                       Edge edge = new Edge(key, insideKey, distance);
                       edge.room = room;
                       edgeList.add(edge);
                   }
                }
            }



        }
        return edgeList;
    }

    private Room findRoomContainsEndings(Cell cell, Cell cell1) {
//todo
        //todo -> poprawic obliczanie grafu - uzaleznic je od ognia
        return null;
    }

    private boolean findConnection(Cell cell1, Cell cell2) {

        int counterX =  cell1.x <= cell2.x ? 1 : -1;
        int counterY =  cell1.y <= cell2.y ? 1 : -1;

        boolean lookingForPath = true;
        int x = cell1.x;
        int y = cell1.y;

        while(lookingForPath){
            if(x != cell2.x){
              x += counterX;
            }
            if(y != cell2.y){
                y += counterY;
            }
            if(x == cell2.x && y == cell2.y){
                lookingForPath = false;
                continue;
            }
            if(board.getCellBoard().get(x).get(y).getState().equals(WALL) ||
                    board.getCellBoard().get(x).get(y).isDoor()){
                return false;
            }
        }

        return true;

    }

    private boolean isKeyEqualsEnding(int key, int insideKey){
       if( insideKey == endCellIndex
                || insideKey == startCellIndex
                || key == endCellIndex
                || key == startCellIndex) {
           return true;
       }
        return false;
    }
    private Room findRoomBeetwen(Cell target, Cell source) {

        for(Room room : roomsList){
            int equalXTarget = 0;
            int equalYTarget = 0;
            int equalXSource = 0;
            int equalYSource = 0;
            for(Cell corner : room.cornerList){
                if(corner.x == source.x){
                    equalXSource++;
                }
                if(corner.y == source.y){
                    equalYSource++;
                }
                if(corner.x == target.x){
                    equalXTarget++;
                }
                if(corner.y == target.y){
                    equalYTarget++;
                }
            }
            if((equalXSource == 2 || equalYSource == 2) && (equalXTarget == 2 || equalYTarget == 2)){
                if(equalXSource == 2){
                   if(!isRoomWallContainDoorX(room, source.x, source.y)){
                       continue;
                   }
                }
                if(equalYSource == 2){
                    if(!isRoomWallContainDoorY(room, source.x, source.y)){
                        continue;
                    }
                }
                if(equalXTarget == 2){
                    if(!isRoomWallContainDoorX(room, target.x, target.y)){
                        continue;
                    }
                }
                if(equalYTarget == 2){
                    if(!isRoomWallContainDoorY(room, target.x, target.y)){
                        continue;
                    }
                }
                return room;
            }
        }
        return null;
    }
    private boolean isRoomWallContainDoorX(Room room, int x, int y){
        List<Cell> cell1 = room.sideMap.get(x);
        int fromY = cell1.get(0).y > cell1.get(1).y ? cell1.get(0).y : cell1.get(1).y;
        int toY = cell1.get(0).y < cell1.get(1).y ? cell1.get(0).y : cell1.get(1).y;
        if(y <= toY &&  y >= fromY ){
            return true;
        }
        return false;
    }
    private boolean isRoomWallContainDoorY(Room room, int x, int y){
        List<Cell> cell1 = room.sideMap.get(y);
        int fromX = cell1.get(0).x > cell1.get(1).x ? cell1.get(0).x : cell1.get(1).x;
        int toX = cell1.get(0).x < cell1.get(1).x ? cell1.get(0).x : cell1.get(1).x;
        if(x <= toX &&  x >= fromX ){
            return true;
        }
        return false;
    }
    /**
     * klasa liczy dystan miedzy podanymi komorakmi,
     * jesli istaniej polacznie >0 jesli brak zwraca 0
     * @param cell1
     * @param cell2
     * @return
     */

    private int countDistances(Cell cell1, Cell cell2){
        int fromX = cell1.x <= cell2.x ? cell1.x : cell2.x;
        int toX = cell1.x >= cell2.x ? cell1.x : cell2.x;
        int fromY = cell1.y <= cell2.y ? cell1.y : cell2.y;
        int toY = cell1.y >= cell2.y ? cell1.x : cell2.y;

/*        for(int i = fromX + 1 ; i < toX ; i ++){
            for(int j = fromY + 1 ; j < toY ; j ++ ){
                if(WALL.equals(board.getCellBoard().get(i).get(j).getState()) || board.getCellBoard().get(i).get(j).isDoor()){
                    return 0;
                }
            }
        }*/

        int distance = (toX - fromX) + (toY - fromY);

        return distance;
    }


    public void setDownShortesPath(){
        int distance = fordBellamn.run(startCellIndex,endCellIndex);
        shortesPath = fordBellamn.getPath(startCellIndex,endCellIndex);
        denoteEscapeDoor();
    }

    private void denoteEscapeDoor() {
        for(Integer doorKey: shortesPath){
            doorsMap.get(doorKey).setIsEscapeRoad(true);
            doorsMap.get(doorKey).setState(ESCAPE);
            doorsMap.get(doorKey).setTempState(ESCAPE);
        }
    }

    public void findAllRooms(){

        List<Cell> roomCorner = new ArrayList<>();
        List<Cell> cellWithTheSameX = new ArrayList<>();
        List<Cell> cellWithTheSameY = new ArrayList<>();

        for(Cell examineCorner : cornerList){
            int thisX = examineCorner.x;
            int thisY = examineCorner.y;
            for(Cell otherCorner: cornerList){
                if(otherCorner != examineCorner){
                    if(otherCorner.x == thisX && otherCorner.y > thisY ){
                        cellWithTheSameX.add(otherCorner);
                    }
                    if(otherCorner.y == thisY && otherCorner.x > thisX){
                        cellWithTheSameY.add(otherCorner);
                    }
                }
            }
            if(!cellWithTheSameX.isEmpty() && !cellWithTheSameY.isEmpty()){
                roomCorner = getNearestCorners(cellWithTheSameX, cellWithTheSameY, examineCorner);
                Cell cornerAtCross = getCornerAtCross(roomCorner, examineCorner);
                if(cornerAtCross != null){
                    roomCorner.add(cornerAtCross);
                    roomCorner.add(examineCorner);
                    roomsList.add(new Room(roomCorner));
                }
            }

            cellWithTheSameX = new ArrayList<>();
            cellWithTheSameY = new ArrayList<>();

        }
    }
    private Cell getCornerAtCross(List<Cell> nearestNeigh, Cell corner){
        int searchX = 0;
        int searchY = 0;
        for(Cell cell : nearestNeigh){
            if(cell.x == corner.x){
                searchY = cell.y;
            }

            if(cell.y == corner.y){
                searchX = cell.x;
            }
        }

        for(Cell cell : cornerList){
            if(cell.x == searchX && cell.y == searchY){
                return cell;
            }
        }

        return null;
    }

    private List<Cell> getNearestCorners(List<Cell> cellWithTheSameX, List<Cell> cellWithTheSameY, Cell corner) {

        List<Cell> nearestNeigh = new ArrayList<>();

        int x = corner.x;
        int y = corner.y;

        Cell nearestXCell = cellWithTheSameX.get(0);
        for(Cell otherCorner : cellWithTheSameX){
           if( nearestXCell.y > otherCorner.y  ){
               nearestXCell = otherCorner;
           }
        }
        nearestNeigh.add(nearestXCell);

        Cell nearestYCell = cellWithTheSameY.get(0);
        for(Cell otherCorner : cellWithTheSameY){
            if( nearestYCell.x > otherCorner.x  ){
                nearestYCell = otherCorner;
            }
        }
        nearestNeigh.add(nearestYCell);

        return nearestNeigh;
    }

    private List<Cell> getWallNeighList(Cell cell) {
        List<Cell> wallNeighList = new ArrayList<>(8);
        for(Cell neigh : cell.getNeighbors()){
            if(WALL.equals(neigh.getState())) {
                wallNeighList.add(neigh);
            }
        }
        return wallNeighList;
    }

    private void findCorner(List<Cell> wallNeighList, Cell examCell){
        if(wallNeighList.size() < 2 || wallNeighList.size() >= 5 || !WALL.equals(examCell.getState())){
            return;
        }
        int xEqualsCount = 0;
        int yEqualsCount = 0;

        for(Cell neighCell : wallNeighList){
            if(examCell.x == neighCell.x){
                xEqualsCount++;
            }
            if(examCell.y == neighCell.y){
                yEqualsCount++;
            }
        }

        if(xEqualsCount >= 1 && yEqualsCount >= 1 ) {
            cornerList.add(examCell);
        }

    }

}
