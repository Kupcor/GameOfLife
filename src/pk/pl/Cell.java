package pk.pl;

import javax.swing.*;
import java.awt.*;

public class Cell extends JLabel {
    private boolean isAlive;
    private final int verticalPosition;
    private final int horizontalPosition;

    public Cell (int verticalPosition, int horizontalPosition) {
        this.verticalPosition = verticalPosition;
        this.horizontalPosition = horizontalPosition;
        this.setOpaque(true);
        this.kill();
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

    public int getVerticalPosition() {
        return verticalPosition;
    }

    public int getHorizontalPosition() {
        return horizontalPosition;
    }
}
