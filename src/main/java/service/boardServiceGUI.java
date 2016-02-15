package service;

import gui.GUI;
import model.Board;
import model.State;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;

import static model.State.*;

/**
 * Created by Asia on 2016-02-09.
 */
public class BoardServiceGUI extends JComponent implements MouseInputListener, ComponentListener {
    private static final long serialVersionUID = 1L;
    public Board board;
    private int size = 14;
    BoardService boardService;
    public String command = "";
    public boolean startPath = false;
    private boolean firstStart = true;
    public BoardServiceGUI(BoardService boardService) {
        this.boardService = boardService;
        board = this.boardService.board;
        addMouseListener(this);
        addComponentListener(this);
        addMouseMotionListener(this);
        setBackground(Color.WHITE);
        setOpaque(true);
    }

    // single iteration
    public void iteration() {

        boardService.calculateNextBoardState();
        this.repaint();
    }

    // clearing board
    public void clear() {
        boardService.cleanBoard();
        this.repaint();
    }

    //reset board
    public void reset() {
        boardService.resetBoard();
        this.repaint();
        startPath = true;
    }

    public void initializeGraphBeforeStart(){
        if(firstStart) {
            boardService.initializeGraphBeforeStart();
            firstStart = false;
        }
    }
    private void initialize() {
        int width = (this.getWidth() / size) + 1;
        int height = (this.getHeight() / size) + 1;
        boardService.initialize(width,height);
    }


    //paint background and separators between cells
    protected void paintComponent(Graphics g) {
        if (isOpaque()) {
            g.setColor(getBackground());
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
        }
        g.setColor(Color.GRAY);
        drawNetting(g, size);
    }

    // draws the background netting
    private void drawNetting(Graphics g, int gridSpace) {
        Insets insets = getInsets();
        int firstX = insets.left;
        int firstY = insets.top;
        int lastX = this.getWidth() - insets.right;
        int lastY = this.getHeight() - insets.bottom;

        int x = firstX;
        while (x < lastX) {
            g.drawLine(x, firstY, x, lastY);
            x += gridSpace;
        }

        int y = firstY;
        while (y < lastY) {
            g.drawLine(firstX, y, lastX, y);
            y += gridSpace;
        }

        for (x = 0; x < board.getCellBoard().size(); ++x) {
            for (y = 0; y < board.getCellBoard().get(x).size(); ++y) {

                switch ( board.getCellBoard().get(x).get(y).getState()) {
                    case WALL:
                        g.setColor(new Color(0x8E4500));
                        break;
                    case ESCAPE:
                        g.setColor(new Color(0x66E600));
                        break;
                    case BURN:
                        g.setColor(new Color(0xFF840F));
                        break;
                    case DEAD:
                        g.setColor(new Color(0x000000));
                        break;
                    case NORMAL:
                        g.setColor(new Color(0xFFFFFF));
                        break;
                    default:
                        g.setColor(new Color(0xFFFFFF));
                }

                if(board.getCellBoard().get(x).get(y).isDoor()){
                    State state = board.getCellBoard().get(x).get(y).getState();
                    if(!state.equals(ESCAPE) && !state.equals(BURN)) {
                        g.setColor(new Color(0xDB9E41));
                    }
                }

                if(board.getCellBoard().get(x).get(y).equals(board.getEndEscapeRoad())
                        && board.getCellBoard().get(x).get(y).getState().equals(ESCAPE)){
                    g.setColor(new Color(0xDB000C));
                }
                g.fillRect((x * size) + 1, (y * size) + 1, (size - 1), (size - 1));
            }
        }
    }

    public void mouseClicked(MouseEvent e) {
        int x = e.getX() / size;
        int y = e.getY() / size;
        State state = mapBoardStateToCellState();
        if ((x < board.getCellBoard().size()) && (x > 0) && (y < board.getCellBoard().get(x).size()) && (y > 0)) {
            if(NORMAL.equals(state)){
                board.getCellBoard().get(x).get(y).setDoor(true);
            }
            if(ESCAPE.equals(state)){
                addPathEndings(x,y);
            }
            board.getCellBoard().get(x).get(y).setState(state);
            board.getCellBoard().get(x).get(y).setTempState(state);
            this.repaint();
        }
    }

    private void addPathEndings(int x, int y) {
        if(startPath){
            if(board.getStartEscapeRoad() != null){
                board.getStartEscapeRoad().setState(board.getPrevStartState());
            }
            board.setPrevStartState(board.getCellBoard().get(x).get(y).getState());
            board.setStartEscapeRoad(board.getCellBoard().get(x).get(y));
        } else{
            if(board.getEndEscapeRoad() != null){
                board.getEndEscapeRoad().setState(board.getPrevEndState());
            }
            board.setPrevEndState(board.getCellBoard().get(x).get(y).getState());
            board.setEndEscapeRoad(board.getCellBoard().get(x).get(y));
        }
    }

    private State mapBoardStateToCellState() {
        if(GUI.ADD_DOOR_COMMAND.equals(command)){
            return NORMAL;
        }
        if(GUI.ADD_WALL_COMMAND.equals(command)){
            return WALL;
        }
        if(GUI.ADD_PATH_COMMAND.equals(command)){
            return ESCAPE;
        }
        if(GUI.FIRE_COMMAND.equals(command)){
            return BURN;
        }
            return BURN;
    }

    public void componentResized(ComponentEvent e) {
        int dlugosc = (this.getWidth() / size) + 1;
        int wysokosc = (this.getHeight() / size) + 1;
        initialize();
    }

    public void mouseDragged(MouseEvent e) {
        int x = e.getX() / size;
        int y = e.getY() / size;
        State state = mapBoardStateToCellState();
        if ((x < board.getCellBoard().size()) && (x > 0) && (y < board.getCellBoard().get(x).size()) && (y > 0)) {
            board.getCellBoard().get(x).get(y).setState(state);
            board.getCellBoard().get(x).get(y).setTempState(state);
            this.repaint();
        }
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void componentShown(ComponentEvent e) {
    }

    public void componentMoved(ComponentEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
    }

    public void componentHidden(ComponentEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

}