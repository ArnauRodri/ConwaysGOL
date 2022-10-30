import java.awt.*;

import java.util.Timer;
import java.util.TimerTask;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.swing.*;

public class MyPanel extends JPanel {

    private static final Color[] RAINBOW = new Color[]{Color.BLUE, Color.GREEN, Color.MAGENTA, Color.CYAN, Color.RED, Color.ORANGE, Color.YELLOW, Color.WHITE};
    static int SIZE_X = 800;
    static int SIZE_Y = 800;

    static int FIELD_SIZE_X = 1000;
    static int FIELD_SIZE_Y = 1000;

    int winX = 400;
    int winY = 400;
    int winWidth = SIZE_X;
    int winHeight = SIZE_Y;
    int pixelPerCell = 10;

    Field field;

    Timer timer;

    public boolean runMode;
    public boolean makeStep;
    public boolean rainbowMode = false;

    public boolean showLines = false;

    public int timeStep = 1;

    MyFrame superFrame;


    MyPanel(MyFrame superFrame) {
        this.superFrame = superFrame;
        this.runMode = false;
        this.makeStep = false;

        this.setPreferredSize(new Dimension(MyPanel.SIZE_X,MyPanel.SIZE_Y));

        this.field = new Field(MyPanel.FIELD_SIZE_X,MyPanel.FIELD_SIZE_Y);

        timer = new Timer();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (runMode || makeStep) {
                    makeStep = false;
                    field.updateField();
                    repaint();
                    superFrame.repaint();
                    try {
                        TimeUnit.MILLISECONDS.sleep(timeStep);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };

        timer.schedule(task, 1, 1);
    }


    public void paint(Graphics g) {
        super.paint(g);
        super.setBackground(Color.BLACK);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setPaint(Color.DARK_GRAY);
        paintKilledCells(g2d);

        g2d.setPaint(Color.WHITE);
        if (showLines) drawLines(g2d);
        paintAliveCells(g2d);

        superFrame.rePaint();
    }

    private void drawLines(Graphics2D g) {
        for (int i = 1; i <= this.winWidth/this.pixelPerCell; i++) {
            for (int j = 1; j <= this.winHeight/this.pixelPerCell; j++) {
                g.drawLine(i*this.pixelPerCell, 0, i*this.pixelPerCell, this.winHeight);
                g.drawLine(0, j*this.pixelPerCell, this.winWidth, j*this.pixelPerCell);
            }
        }
    }

    private void paintAliveCells(Graphics2D g) {
        Random r = new Random();
        for (int i = 0; i < this.winWidth/this.pixelPerCell; i++) {
            for (int j = 0; j < this.winHeight/this.pixelPerCell; j++) {
                if (j+this.winX < 0 || i+this.winY < 0 || j+this.winX >= this.winWidth || i+this.winY >= this.winHeight) continue;
                if (field.alive.get(i+this.winY).get(j+this.winX)) {
                    if (this.rainbowMode) g.setPaint(RAINBOW[r.nextInt((RAINBOW.length))]);
                    g.fillRect(j*this.pixelPerCell, i*this.pixelPerCell, this.pixelPerCell, this.pixelPerCell); // swapped because of transpose
                }
            }
        }
    }

    private void paintKilledCells(Graphics2D g) {
        for (int i = 0; i < this.winWidth/this.pixelPerCell; i++) {
            for (int j = 0; j < this.winHeight/this.pixelPerCell; j++) {
                if (j+this.winX < 0 || i+this.winY < 0 || j+this.winX >= this.winWidth || i+this.winY >= this.winHeight) continue;
                if (field.killed.get(i+this.winY).get(j+this.winX)) {
                    g.fillRect(j*this.pixelPerCell, i*this.pixelPerCell, this.pixelPerCell, this.pixelPerCell); // swapped because of transpose
                }
            }
        }
    }
}
