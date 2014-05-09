/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multiplayersnail;

/**
 * @author Dhruv Batheja
 * @author Yadhu Ravinath
 */
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import javax.swing.*;

public abstract class Core{

    boolean running;
    static boolean replay;
    JFrame w;

    //stop method

    public void stop() {
        running = false;
    }

    //call init and gameloop
    public void run() {
        try {
            init();
            gameLoop();
        } finally {
            if (!replay) {
                System.exit(0);
            }
        }
    }

    //set to full screen
    public void init() {

       //startRunning();
        //System.out.println("YUP");
        w = new JFrame();

        w.setUndecorated(true);
        w.setIgnoreRepaint(true);
        w.setResizable(false);

        w.setSize(600, 500);
        w.setLocationRelativeTo(null);

        w.setFont(new Font("Arial", Font.PLAIN, 24));
        w.setBackground(Color.BLACK);
        w.setForeground(Color.WHITE);
        w.setVisible(true);

        w.createBufferStrategy(2);
        replay = false;
        running = true;
    }
    static long sleep;

    //main game loop
    public void gameLoop() {
        while (running) {

            Graphics2D g = (Graphics2D) w.getBufferStrategy().getDrawGraphics();
            draw(g);
            g.dispose();
            this.update();

            if (w != null) {
                BufferStrategy s = w.getBufferStrategy();
                if (!s.contentsLost()) {
                    s.show();
                }
            }

            try {
                Thread.sleep(sleep);
            } catch (Exception e) {
            }
        }
    }

    //update game
    public abstract void update();

    //draws to the screen
    public abstract void draw(Graphics2D g);

    //sets blank cursor
    public void removeCursor() {
        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
        w.setCursor(blankCursor);
    }
}
