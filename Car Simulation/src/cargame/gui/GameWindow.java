package cargame.gui;

import cargame.gui.utils.Util;
import java.awt.Color;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;

/**
 * Main window of the game
 * 
 * @author Carlos
 */
public class GameWindow {

    private JFrame frame;
    private GameCanvas canvas;

    public GameWindow() {
        initialize();
    }

    //Initialize the contents of the frame
    private void initialize() {
        frame = new JFrame();
        frame.setTitle("SOCOF Simulation");
        frame.setBounds(100, 100, 800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Util.centerScreen(frame);

        canvas = new GameCanvas();
        canvas.setIgnoreRepaint(true);
        canvas.setBackground(Color.WHITE);
        
        GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
            groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(
                Alignment.LEADING).addGroup(groupLayout.createSequentialGroup()
                        .addComponent(canvas, GroupLayout.DEFAULT_SIZE, 1024, Short.MAX_VALUE)));
        
            groupLayout.setVerticalGroup(groupLayout.createParallelGroup(
                Alignment.LEADING).addGroup(groupLayout.createSequentialGroup()
                        .addComponent(canvas, GroupLayout.DEFAULT_SIZE, 512, Short.MAX_VALUE)));        
        frame.getContentPane().setLayout(groupLayout);
        frame.setVisible(true);
    }
}