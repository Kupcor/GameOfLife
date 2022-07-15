package pk.pl;


import javax.swing.*;
import java.awt.*;

public class Cell extends JLabel {
    private boolean isAlive;
    private int xPosition;
    private int yPosition;

    public Cell (boolean isAlive, int xPosition, int yPosition) {
        this.isAlive = isAlive;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.setOpaque(true);

        if (isAlive) {
            this.setBackground(Color.WHITE);
        } else {
            this.setBackground(Color.BLACK);
        }
    }

    public void revive() {
        this.isAlive = true;
        this.setBackground(Color.white);
    }

    public void kill() {
        this.isAlive = false;
        this.setBackground(Color.BLACK);
    }

    public boolean isAlive() {
        return isAlive;
    }

    public int getxPosition() {
        return xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }
}
