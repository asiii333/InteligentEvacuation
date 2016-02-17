import controler.ControlBoard;
import gui.GUI;
import model.Board;
import service.BoardService;

import javax.swing.*;

/**
 * Created by Asia on 2016-02-09.
 */
public class App extends JFrame {

    private static final long serialVersionUID = 1L;
    private GUI gof;

    public App() {
        setTitle("Fire Simulation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Board board = new Board();
        BoardService service = new BoardService(board);
        gof = new GUI(this, service);
        gof.initialize(this.getContentPane());

        this.setSize(1024, 668);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new App();
    }

}
