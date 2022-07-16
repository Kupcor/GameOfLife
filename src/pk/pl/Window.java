package pk.pl;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {
    protected int gridWidth;
    protected int gridHeight;
    protected GridLayout windowGridLayout = new GridLayout(2,1);

    public Window(int gridWidth, int gridHeight) {
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.setLayout(this.windowGridLayout);
        this.setSize(this.gridWidth * 5, this.gridHeight * 5);
        this.setVisible(true);
    }
}
