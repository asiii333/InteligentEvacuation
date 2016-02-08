package service;

import model.Board;
import model.Cell;
import model.Room;
import service.graph.AdjacentListGraph;
import service.graph.Edge;
import service.graph.FordaBellmana;
import service.graph.IGraph;

import java.util.*;

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
    public IGraph<Integer> graph;

    public GraphService(Board board){
        this.board = board;
    }
    public void initializeGraph(){
        findAllCornerAndDoor();
        findAllRooms();
        initializeDoorGraph();
    }
    public void findAllCornerAndDoor(){
        int doorCounter = 0;
        for(List<Cell> cellRow : board.getCellBoard() ){
            for(Cell cell: cellRow){
                List<Cell> wallNeighList = getWallNeighList(cell);
                findCorner( wallNeighList,cell);
                doorCounter = findDoor(cell, doorCounter);
            }
        }
    }

    private int findDoor(Cell cell, int doorCounter) {
        if(WALL_NAME.equals(cell.getMaterial().getName()) && cell.isDoor() &&
                !cell.equals(board.getEndEscapeRoad()) &&  !cell.equals(board.getStartEscapeRoad()) ){
            doorsMap.put(doorCounter, cell);
            doorCounter ++;
        }

        return doorCounter;
    }

    private void initializeDoorGraph(){
        List<Edge> data = createDoorGraph();
        int verticesNumber = doorsMap.size();
        graph = new AdjacentListGraph(data, verticesNumber);
        fordBellamn = new FordaBellmana(graph);

    }

    private List<Edge> createDoorGraph() {
        List<Edge> edgeList = new LinkedList<>();
        startCellIndex = doorsMap.size();
        doorsMap.put(startCellIndex, board.getStartEscapeRoad());
        endCellIndex = doorsMap.size();
        doorsMap.put(endCellIndex, board.getStartEscapeRoad());

        for(Integer key : doorsMap.keySet()){
            for(Integer insideKey : doorsMap.keySet()){
                if(key != insideKey) {
                    int distance = countDistances(doorsMap.get(key), doorsMap.get(insideKey));
                    if (distance > 0) {
                        Edge edge = new Edge(key, insideKey, distance);
                        Room room = findRoomBeetwen(doorsMap.get(key), doorsMap.get(insideKey));
                        edge.room = room;
                        edgeList.add(edge);

                    }
                }
            }

        }

        return edgeList;
    }

    private Room findRoomBeetwen(Cell target, Cell source) {
        for(Room room : roomsList){
            int equalXTarget = 0;
            int equalYTarget = 0;
            int equalXSource = 0;
            int equalYSource = 0;
            for(Cell corner : room.cornerList){
                if(corner.x == target.x){
                    equalXTarget++;
                }
                if(corner.y == target.y){
                    equalYTarget++;
                }
                if(corner.x == source.x){
                    equalXTarget++;
                }
                if(corner.y == source.y){
                    equalYTarget++;
                }
            }
            if((equalXTarget == 2 || equalXTarget == 2) && (equalYTarget == 2 || equalYTarget == 2)){
                return room;
            }
        }
        return null;
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

        for(int i = fromX + 1 ; i < toX ; i ++){
            for(int j = fromY + 1 ; j < toY ; j ++ ){
                if(WALL_NAME.equals(board.getCellBoard().get(i).get(j).getMaterial().getName())){
                    return 0;
                }
            }
        }

        int distance = (toX - fromX) + (toY - fromY);

        return distance;
    }


    public void countShortesPath(){
        int distance = fordBellamn.run(startCellIndex,endCellIndex);
        shortesPath = fordBellamn.getPath(startCellIndex,endCellIndex);
        denoteEscapeDoor();
    }

    private void denoteEscapeDoor() {
        for(Integer doorKey: shortesPath){
            doorsMap.get(doorKey).setIsEscapeRoad(true);
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
           if( nearestXCell.x < otherCorner.x  ){
               nearestXCell = otherCorner;
           }
        }
        nearestNeigh.add(nearestXCell);

        Cell nearestYCell = cellWithTheSameY.get(0);
        for(Cell otherCorner : cellWithTheSameY){
            if( nearestYCell.y > otherCorner.y  ){
                nearestYCell = otherCorner;
            }
        }
        nearestNeigh.add(nearestYCell);

        return nearestNeigh;
    }

    private List<Cell> getWallNeighList(Cell cell) {
        List<Cell> wallNeighList = new ArrayList<>(8);
        for(Cell neigh : cell.getNeighbors()){
            if(WALL_NAME.equals(neigh.getMaterial().getName())) {
                wallNeighList.add(neigh);
            }
        }
        return wallNeighList;
    }

    private void findCorner(List<Cell> wallNeighList, Cell examCell){
        if(wallNeighList.size() < 2 || wallNeighList.size() >= 5 || !WALL_NAME.equals(examCell.getMaterial().getName())){
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
