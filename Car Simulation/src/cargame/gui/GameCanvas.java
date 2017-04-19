package cargame.gui;

import cargame.input.GameKeyListener;
import cargame.objects.ObjectList;

import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

/**
 * GameCanvas holds and handles the objects and graphics
 * 
 * @author Carlos
 */
public class GameCanvas extends JPanel {

    private static final long serialVersionUID = 1L;

    ObjectList objHolder;
    Graphics2D g2d;

    public GameCanvas() {
        addKeyListener(new GameKeyListener());

        objHolder = new ObjectList();

        setFocusable(true);
        requestFocus();
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        //Hints para melhorar a qualidade dos gráficos
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHints(rh);

        super.paintComponent(g2);
        objHolder.paint(g2);
        g2.dispose();
    }

    @Override
    public void addNotify() {
        super.addNotify();
        new Updater(objHolder, this).start();
    }
}