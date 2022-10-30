import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class MyFrame extends JFrame implements ActionListener {

    public JButton stepButton;
    public JButton runButton;
    public JButton pauseButton;

    public JButton upButton;
    public JButton downButton;
    public JButton leftButton;
    public JButton rightButton;

    public JButton zoomInButton;
    public JButton zoomOutButton;

    public JButton toggleLinesButton;

    public JButton rainbowButton;

    public JButton fasterButton;

    public JButton slowerButton;
    MyPanel panel;

    MyFrame() {
        this.stepButton = new JButton("STEP");
        this.stepButton.setBounds(20, 20, 100, 30);
        this.stepButton.addActionListener(this);
        this.stepButton.setBackground(Color.GRAY);
        this.stepButton.setBorderPainted(false);
        this.add(stepButton);

        this.runButton = new JButton("RUN");
        this.runButton.setBounds(20, 55, 100, 30);
        this.runButton.addActionListener(this);
        this.runButton.setBackground(Color.GRAY);
        this.runButton.setBorderPainted(false);
        this.add(runButton);

        this.pauseButton = new JButton("PAUSE");
        this.pauseButton.setBounds(20, 90, 100, 30);
        this.pauseButton.addActionListener(this);
        this.pauseButton.setBackground(Color.GRAY);
        this.pauseButton.setBorderPainted(false);
        this.add(pauseButton);

        this.upButton = new JButton("UP");
        this.upButton.setBounds(360, 20, 80, 80);
        this.upButton.addActionListener(this);
        this.upButton.setBackground(Color.GRAY);
        this.upButton.setBorderPainted(false);
        this.add(upButton);

        this.downButton = new JButton("DOWN");
        this.downButton.setBounds(360, 700, 80, 80);
        this.downButton.addActionListener(this);
        this.downButton.setBackground(Color.GRAY);
        this.downButton.setBorderPainted(false);
        this.add(downButton);

        this.leftButton = new JButton("LEFT");
        this.leftButton.setBounds(20, 360, 80, 80);
        this.leftButton.addActionListener(this);
        this.leftButton.setBackground(Color.GRAY);
        this.leftButton.setBorderPainted(false);
        this.add(leftButton);

        this.rightButton = new JButton("RIGHT");
        this.rightButton.setBounds(700, 360, 80, 80);
        this.rightButton.addActionListener(this);
        this.rightButton.setBackground(Color.GRAY);
        this.rightButton.setBorderPainted(false);
        this.add(rightButton);

        this.zoomInButton = new JButton("ZOOM IN");
        this.zoomInButton.setBounds(660, 20, 120, 30);
        this.zoomInButton.addActionListener(this);
        this.zoomInButton.setBackground(Color.GRAY);
        this.zoomInButton.setBorderPainted(false);
        this.add(zoomInButton);

        this.zoomOutButton = new JButton("ZOOM OUT");
        this.zoomOutButton.setBounds(660, 55, 120, 30);
        this.zoomOutButton.addActionListener(this);
        this.zoomOutButton.setBackground(Color.GRAY);
        this.zoomOutButton.setBorderPainted(false);
        this.add(zoomOutButton);

        this.toggleLinesButton = new JButton("TOGGLE LINES");
        this.toggleLinesButton.setBounds(630, 90, 150, 30);
        this.toggleLinesButton.addActionListener(this);
        this.toggleLinesButton.setBackground(Color.GRAY);
        this.toggleLinesButton.setBorderPainted(false);
        this.add(toggleLinesButton);

        this.rainbowButton = new JButton("RAINBOW");
        this.rainbowButton.setBounds(630, 125, 150, 60);
        this.rainbowButton.addActionListener(this);
        this.rainbowButton.setBackground(Color.GRAY);
        this.rainbowButton.setBorderPainted(false);
        this.add(rainbowButton);

        this.fasterButton = new JButton("FASTER");
        this.fasterButton.setBounds(20, 125, 100, 30);
        this.fasterButton.addActionListener(this);
        this.fasterButton.setBackground(Color.GRAY);
        this.fasterButton.setBorderPainted(false);
        this.add(fasterButton);

        this.slowerButton = new JButton("SLOWER");
        this.slowerButton.setBounds(20, 160, 100, 30);
        this.slowerButton.addActionListener(this);
        this.slowerButton.setBackground(Color.GRAY);
        this.slowerButton.setBorderPainted(false);
        this.add(slowerButton);

        this.panel = new MyPanel(this);
        this.add(panel);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setBackground(Color.BLACK);
    }

    public void rePaint(){
        this.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == stepButton) {
            this.panel.makeStep = true;
        } else if (e.getSource() == runButton) {
            this.panel.runMode = true;
        } else if (e.getSource() == pauseButton) {
            this.panel.runMode = false;
        } else if (e.getSource() == upButton) {
            this.panel.winY -= 100*(1/(float) this.panel.pixelPerCell);
        } else if (e.getSource() == downButton) {
            this.panel.winY += 100*(1/(float) this.panel.pixelPerCell);
        } else if (e.getSource() == leftButton) {
            this.panel.winX -= 100*(1/(float) this.panel.pixelPerCell);
        } else if (e.getSource() == rightButton) {
            this.panel.winX += 100*(1/(float) this.panel.pixelPerCell);
        } else if (e.getSource() == zoomInButton) {
            this.panel.pixelPerCell += 2;
        } else if (e.getSource() == zoomOutButton) {
            if (this.panel.pixelPerCell > 2) this.panel.pixelPerCell -= 2;
        } else if (e.getSource() == toggleLinesButton) {
            this.panel.showLines = !this.panel.showLines;
        } else if (e.getSource() == rainbowButton) {
            this.panel.rainbowMode = !this.panel.rainbowMode;
        } else if (e.getSource() == fasterButton) {
            if (this.panel.timeStep > 1) this.panel.timeStep -= 500;
        } else if (e.getSource() == slowerButton) {
            if (this.panel.timeStep < 1000) this.panel.timeStep += 500;
        }
    }
}
