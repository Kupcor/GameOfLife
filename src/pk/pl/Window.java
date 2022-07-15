package pk.pl;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {
    protected int width;
    protected int height;
    protected GridLayout mainGridLayout = new GridLayout(2,1);

    public Window(int width, int height) {
        this.width = width;
        this.height = height;
        this.setLayout(this.mainGridLayout);
        this.setSize(this.width*100, this.height*100);
        this.setVisible(true);
    }
}
