package pk.pl;


import javax.swing.*;
import java.awt.*;

public class Cell extends JLabel {
    private boolean isAlive;

    public Cell (boolean isAlive) {
        this.isAlive = isAlive;
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
}
