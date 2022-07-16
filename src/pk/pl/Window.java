package pk.pl;

import com.formdev.flatlaf.*;
import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {
    protected int gridWidth;
    protected int gridHeight;
    protected BorderLayout mainFrameLayout = new BorderLayout(0,0);

    public Window(int gridWidth, int gridHeight) {
        try {
            //  FlatLaf module -> see formdev.com/flatlaf/
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Failed to initialized flat laf theme.");
        }
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.setLayout(this.mainFrameLayout);
        this.setSize(this.gridWidth * 12, this.gridHeight * 12);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
