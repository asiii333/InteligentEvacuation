package service;

import model.Board;
import model.Cell;
import model.Material;
import model.materials.Unknown;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Asia on 2016-01-06.
 */
public class BoardService {
    Board board;
    private  List<List<Cell>> cellBoard;
    private int boardWidth;
    private int boardHeight;

    CalculateState calState = new CalculateState();
    CalculateEscapeRoad calEscapeRoad = new CalculateEscapeRoad();

    public BoardService(Board board){
        this.board = board;
        boardWidth = board.WIGHT;
        boardHeight = board.WIGHT;
        cellBoard = board.getCellBoard();
    }

    public void initializeBoard(){
        fullfillBoard();
        setMainNeighbourg();
        setBorderNeighbourg();
    }

    private void fullfillBoard() {
        for(int i = 0; i < boardWidth; i++){
            List<Cell> cellRow = new ArrayList<Cell>(boardHeight);
            for (int j = 0; j< boardHeight; j++ ){
                Cell cell = new Cell(new Unknown(), i, j);
                cellRow.add(cell);
            }
            board.getCellBoard().add(cellRow);
        }
    }

    private void setMainNeighbourg() {
        for(int i = 1; i < boardWidth-1; i++){
            for (int j = 1; j< boardHeight-1; j++ ){
                List<Cell> neighbors = new ArrayList<Cell>();
                neighbors.add(cellBoard.get(i).get(j-1));
                neighbors.add(cellBoard.get(i).get(j+1));
                neighbors.add(cellBoard.get(i+1).get(j+1));
                neighbors.add(cellBoard.get(i+1).get(j));
                neighbors.add(cellBoard.get(i+1).get(j-1));
                neighbors.add(cellBoard.get(i-1).get(j+1));
                neighbors.add(cellBoard.get(i-1).get(j));
                neighbors.add(cellBoard.get(i-1).get(j-1));
                cellBoard.get(i).get(j).setNeighbors(neighbors);
            }
        }
    }

    private void setBorderNeighbourg() {
        //inicjalizacja sasiadow dla komorek brzegowych poziomych
        for(int i = 0; i < boardWidth; i++){
            int max = boardWidth-1;
            int min = 0;
            if(i != min){
                cellBoard.get(min).get(i).getNeighbors().add(cellBoard.get(min+1).get(i-1));
                cellBoard.get(min).get(i).getNeighbors().add(cellBoard.get(min).get(i-1));
                cellBoard.get(max).get(i).getNeighbors().add(cellBoard.get(max-1).get(i-1));
                cellBoard.get(max).get(i).getNeighbors().add(cellBoard.get(max).get(i-1));
            }
            if(i != max){
                cellBoard.get(min).get(i).getNeighbors().add(cellBoard.get(min+1).get(i+1));
                cellBoard.get(min).get(i).getNeighbors().add(cellBoard.get(min).get(i+1));
                cellBoard.get(max).get(i).getNeighbors().add(cellBoard.get(max-1).get(i+1));
                cellBoard.get(max).get(i).getNeighbors().add(cellBoard.get(max).get(i+1));
            }
            cellBoard.get(min).get(i).getNeighbors().add(cellBoard.get(min+1).get(i));
            cellBoard.get(max).get(i).getNeighbors().add(cellBoard.get(max-1).get(i));

        }

        //inicjalizacja sasiadow dla komorek brzegowych pionowych
        for(int i=1; i< boardHeight-2; i++ ){
            int max = boardHeight-1;
            int min = 0;
            if(i!=min){
                cellBoard.get(i).get(min).getNeighbors().add(cellBoard.get(i-1).get(min+1));
                cellBoard.get(i).get(min).getNeighbors().add(cellBoard.get(i-1).get(min));
                cellBoard.get(i).get(max).getNeighbors().add(cellBoard.get(i-1).get(max-1));
                cellBoard.get(i).get(max).getNeighbors().add(cellBoard.get(i-1).get(max));
            }
            if(i != max){
                cellBoard.get(i).get(min).getNeighbors().add(cellBoard.get(i+1).get(min+1));
                cellBoard.get(i).get(min).getNeighbors().add(cellBoard.get(i+1).get(min));
                cellBoard.get(i).get(max).getNeighbors().add(cellBoard.get(i+1).get(max-1));
                cellBoard.get(i).get(max).getNeighbors().add(cellBoard.get(i+1).get(max));
            }
            cellBoard.get(i).get(min).getNeighbors().add(cellBoard.get(i).get(min+1));
            cellBoard.get(i).get(max).getNeighbors().add(cellBoard.get(i).get(max-1));
        }
    }

    public void calculateState() {
        calState.calculateState(board);
    }
    public void calculateEscpaeRoad() {
        calEscapeRoad.calculateEscapeRoad(board);
    }


}
