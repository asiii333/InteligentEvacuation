package model

import spock.lang.Specification

/**
 * Created by Asia on 2016-02-07.
 */
class RoomTest extends Specification {

    def "countAllCell"(){
        given:
        List<Cell> cornerList = new ArrayList<>();
        cornerList.add(new Cell(0,0));
        cornerList.add(new Cell(0,2));
        cornerList.add(new Cell(2,0));
        cornerList.add(new Cell(2,2));
         Room room = new Room(cornerList);
        when:
        room.countAllCell();
        then:
        room.allCell == 4;
    }
}
