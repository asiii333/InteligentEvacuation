import controler.ControlBoard;
import gui.MainFrame;
import model.Board;

import javax.swing.*;

/**
 * Created by Asia on 2016-01-05.
 */
public class Main {

    public static void main(String[] args) {
        setLookAndFeel();

        Board board = new Board();
        final ControlBoard controll = new ControlBoard(board);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainFrame mainFrame = new MainFrame(controll);
                mainFrame.setVisible(true);
            }
        });
    }


    private static void setLookAndFeel(){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException |
                InstantiationException |
                UnsupportedLookAndFeelException |
                IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
