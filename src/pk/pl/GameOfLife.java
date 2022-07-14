package pk.pl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

public class GameOfLife extends Window implements MouseListener {
    private final ArrayList<ArrayList<Cell>> cellsContainer = new ArrayList<>();
    private final JButton button = new JButton("Click");
    private final JPanel mainPanel = new JPanel();

    public GameOfLife() {
        super(50, 50);

        this.mainPanel.setBackground(Color.BLUE);
        this.mainPanel.setLayout(new GridLayout(this.width, this.height));
        this.button.addMouseListener(this);

        this.createGrid();

        this.add(mainPanel);
        this.add(this.button);
        this.mainPanel.setVisible(true);
    }

    private void checkGameOfLifeRules() {
        for (int iterator = 0; iterator < this.width; iterator++) {
            ArrayList<Cell> tempCellsContainer = new ArrayList<>();
            for (int subIterator = 0; subIterator < this.height; subIterator++) {
                this.checkCellNeighbourhood(iterator, subIterator);
            }
            this.cellsContainer.add(tempCellsContainer);
        }
    }

    private void checkCellNeighbourhood(int iterator, int subIterator) {
        int aliveNeighbours = 0;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if ((iterator + i >= 0 && iterator + i < this.width) && (subIterator + j >= 0 && subIterator + j < this.height)) {
                    if (i == 0 && j == 0) {
                        continue;
                    }
                    if (this.cellsContainer.get(iterator + i).get(subIterator + j).isAlive()) {
                        aliveNeighbours += 1;
                    }
                }
            }
        }
        if (aliveNeighbours > 3 || aliveNeighbours < 2) {
            this.cellsContainer.get(iterator).get(subIterator).kill();
        }
        else if (aliveNeighbours == 3) {
            this.cellsContainer.get(iterator).get(subIterator).revive();
        }
    }

    private void createGrid() {
        for (int iterator = 0; iterator < this.width; iterator++) {
            ArrayList<Cell> tempCellsContainer = new ArrayList<>();
            for (int subIterator = 0; subIterator < this.height; subIterator++) {
                Cell cell = new Cell(false);
                cell.addMouseListener(this);
                tempCellsContainer.add(cell);
                this.mainPanel.add(cell);
            }
            this.cellsContainer.add(tempCellsContainer);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == button) {
            checkGameOfLifeRules();
        }

        if (e.getSource() instanceof Cell) {
            if (((Cell) e.getSource()).isAlive()) {
                ((Cell) e.getSource()).kill();
            }
            else {
                ((Cell) e.getSource()).revive();
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}