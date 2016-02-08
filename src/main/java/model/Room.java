package model;

import java.util.Comparator;
import java.util.List;

/**
 * Created by Asia on 2016-02-06.
 */
public class Room {
    public List<Cell> cornerList;
    public int allCell;
    public int burnCell;

    public Room(List<Cell> cornerList) {
        this.cornerList = cornerList;
        countAllCell();
        burnCell = 0;
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
