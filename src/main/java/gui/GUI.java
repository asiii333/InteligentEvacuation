package gui;

import service.BoardService;
import service.BoardServiceGUI;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Asia on 2016-02-09.
 */
public class GUI  extends JPanel implements ActionListener, ChangeListener {
    private static final long serialVersionUID = 1L;
    public static final String START_COMMAND = "Start";
    public static final String CLEAR_COMMAND = "clear";
    public static final String RESET_COMMAND = "reset";
    public static final String ADD_DOOR_COMMAND = "addDoor";
    public static final String ADD_PATH_COMMAND = "addPath";
    public static final String FIRE_COMMAND = "fire";


    private Timer timer;
    private BoardService boardService;
    private BoardServiceGUI boardServiceGUI;
    private JButton start;
    private JButton clear;
    private JButton fire;
    private JButton reset;
    private JButton addWall;
    private JButton addDoor;
    private JButton addPath;
    private JSlider pred;
    private JFrame frame;
    private int iterNum = 0;
    private final int maxDelay = 500;
    private final int initDelay = 100;
    private boolean running = false;


    public static final String ADD_WALL_COMMAND = "addWall";

    public GUI(JFrame jf, BoardService boardService) {
        this.boardService = boardService;
        frame = jf;
        timer = new Timer(initDelay, this);
        timer.stop();
        boardServiceGUI = new BoardServiceGUI(boardService);
    }

    /**
     * @param container to which GUI and boards is added
     */
    public void initialize(Container container) {
        container.setLayout(new BorderLayout());
        container.setSize(new Dimension(1024, 768));

        JPanel buttonPanel = new JPanel();

        start = new JButton("Start");
        start.setActionCommand(START_COMMAND);
        start.setToolTipText("Starts clock");
        start.addActionListener(this);

        clear = new JButton("Clear");
        clear.setActionCommand(CLEAR_COMMAND);
        clear.setToolTipText("Clear");
        clear.addActionListener(this);

        reset = new JButton("Reset");
        reset.setActionCommand(RESET_COMMAND);
        reset.setToolTipText("Reset building");
        reset.addActionListener(this);

        fire = new JButton("Fire");
        fire.setActionCommand(FIRE_COMMAND);
        fire.setToolTipText("Add burn cell");
        fire.addActionListener(this);

        addWall = new JButton("Wall");
        addWall.setActionCommand(ADD_WALL_COMMAND);
        addWall.setToolTipText("Add wall");
        addWall.addActionListener(this);

        addDoor = new JButton("Door");
        addDoor.setActionCommand(ADD_DOOR_COMMAND);
        addDoor.setToolTipText("Add door");
        addDoor.addActionListener(this);

        addPath = new JButton("Start path");
        addPath.setActionCommand(ADD_PATH_COMMAND);
        addPath.setToolTipText("Add end or start path");
        addPath.addActionListener(this);

        pred = new JSlider();
        pred.setMinimum(0);
        pred.setMaximum(maxDelay);
        pred.setToolTipText("Time speed");
        pred.addChangeListener(this);
        pred.setValue(maxDelay - timer.getDelay());

        buttonPanel.add(start);
        buttonPanel.add(clear);
        buttonPanel.add(reset);
        buttonPanel.add(fire);
        buttonPanel.add(addWall);
        buttonPanel.add(addDoor);
        buttonPanel.add(addPath);
        buttonPanel.add(pred);

        container.add(boardServiceGUI, BorderLayout.CENTER);
        container.add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * handles clicking on each button
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(timer)) {
            iterNum++;
            frame.setTitle("Fire Simulation (" + Integer.toString(iterNum) + " iteration)");
            boardServiceGUI.iteration();
        } else {
            String command = e.getActionCommand();
            boardServiceGUI.command = command;
            if (command.equals(START_COMMAND)) {
                if (!running) {
                    timer.start();
                    start.setText("Pause");
                    boardServiceGUI.initializeGraphBeforeStart();
                } else {
                    timer.stop();
                    start.setText("Start");
                }
                running = !running;
                clear.setEnabled(true);

            }else if (command.equals(ADD_PATH_COMMAND)) {

                if(boardServiceGUI.startPath){
                    addPath.setText("Start path");
                    boardServiceGUI.startPath = false;
                    boardServiceGUI.startPath = false;
                }else{
                    addPath.setText("End path");
                    boardServiceGUI.startPath = true;
                    boardServiceGUI.startPath = true;
                }
            }else if (command.equals(CLEAR_COMMAND)) {
                iterNum = 0;
                timer.stop();
                start.setEnabled(true);
                boardServiceGUI.clear();
                frame.setTitle("Fire Simulation");
            } else if (command.equals(RESET_COMMAND)) {
                iterNum = 0;
                timer.stop();
                start.setEnabled(true);
                boardServiceGUI.reset();
                frame.setTitle("Fire Simulation");
            }

        }
    }

    /**
     * slider to control simulation speed
     * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
     */
    public void stateChanged(ChangeEvent e) {
        timer.setDelay(maxDelay - pred.getValue());
    }
}