package model;

import java.util.*;

/**
 * Created by Asia on 2016-02-06.
 */
public class Room {
    public List<Cell> cornerList;
    public Map<Integer, List<Cell>> sideMap;
    public int allCell;
    public int burnCell;

    public Room(List<Cell> cornerList) {
        this.cornerList = cornerList;
        sideMap = fillSideMap();
        countAllCell();
        burnCell = 0;
    }

    private Map<Integer, List<Cell>> fillSideMap() {
        Map<Integer, List<Cell>> sideMap = new HashMap<>();
        for(Cell cell1: cornerList){
            for(Cell cell2: cornerList){
                if(cell1 != cell2){
                    if(cell1.x == cell2.x){
                        List<Cell> cells = new ArrayList<Cell>();
                        cells.add(cell1);
                        cells.add(cell2);
                        sideMap.put(cell1.x,cells);
                    }
                    if(cell1.y == cell2.y){
                        List<Cell> cells = new ArrayList<Cell>();
                        cells.add(cell1);
                        cells.add(cell2);
                        sideMap.put(cell1.y,cells);
                    }
                }
            }
        }
        return sideMap;
    }

    private void countAllCell() {
        cornerList.sort(new XComperator());
        int height = cornerList.get(3).x - cornerList.get(0).x;

        cornerList.sort(new YComperator());
        int width = cornerList.get(3).y - cornerList.get(0).y;

        allCell = height * width;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Room)) return false;

        Room room = (Room) o;

        return cornerList.containsAll(room.cornerList);

    }

    @Override
    public int hashCode() {
        return cornerList.hashCode();
    }

}

class XComperator implements Comparator<Cell> {
    @Override
    public int compare(Cell o1, Cell o2) {
        return new Integer(o1.x).compareTo(o2.x);
    }
}

 class YComperator implements Comparator<Cell> {
    @Override
    public int compare(Cell o1, Cell o2) {
        return new Integer(o1.y).compareTo(o2.y);
    }
}
