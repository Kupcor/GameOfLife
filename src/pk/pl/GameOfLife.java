package pk.pl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class GameOfLife extends Window implements MouseListener {
    private final ArrayList<ArrayList<Cell>> cellsContainer = new ArrayList<>();
    private final boolean[][] nextGenerationStatus;
    private final JButton button = new JButton("Click");
    private final JPanel mainPanel = new JPanel();

    public GameOfLife() {
        super(50, 50);
        this.nextGenerationStatus = new boolean[this.width][this.height];
        this.mainPanel.setBackground(Color.BLUE);
        this.mainPanel.setLayout(new GridLayout(this.width, this.height));
        this.button.addMouseListener(this);

        this.createGrid();

        this.add(mainPanel);
        this.add(this.button);
        this.mainPanel.setVisible(true);
    }

    // To jest dynamiczne to dlatego. Leci sprawdzenie przed tymi i dlatego niektóre komórki są eliminowane przed sprawdzeniem
    private void checkGameOfLifeRules() {
        for (int iterator = 0; iterator < this.height; iterator++) {
            for (int subIterator = 0; subIterator < this.width; subIterator++) {
                this.checkCellNeighbourhood(iterator, subIterator);
            }
        }
    }

    private void checkCellNeighbourhood(int iterator, int subIterator) {
        int aliveNeighbours = 0;
        for (int i = iterator - 1; i < iterator+2; i++) {
            for (int j = subIterator - 1; j < subIterator+2; j++) {
                if ((i >= 0 && i < this.height) && (j >= 0 && j < this.width)) {
                    if (this.cellsContainer.get(i).get(j).isAlive() && !(i == iterator && j == subIterator)) {
                        aliveNeighbours += 1;
                    }
                }
            }
        }

        if (aliveNeighbours > 3 || aliveNeighbours < 2) {
            this.nextGenerationStatus[iterator][subIterator] = false;
        }
        else if (aliveNeighbours == 3) {
            this.nextGenerationStatus[iterator][subIterator] = true;
        }
    }

    private void setNextGeneration() {
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                if (this.nextGenerationStatus[i][j]) {
                    this.cellsContainer.get(i).get(j).revive();
                }
                else {
                    this.cellsContainer.get(i).get(j).kill();
                }
            }
        }
    }

    private void createGrid() {
        for (int iterator = 0; iterator < this.width; iterator++) {
            ArrayList<Cell> tempCellsContainer = new ArrayList<>();
            for (int subIterator = 0; subIterator < this.height; subIterator++) {
                Cell cell = new Cell(false, iterator, subIterator);
                cell.addMouseListener(this);
                tempCellsContainer.add(cell);
                this.mainPanel.add(cell);

                nextGenerationStatus[iterator][subIterator] = false;
            }
            this.cellsContainer.add(tempCellsContainer);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == button) {
            checkGameOfLifeRules();
            setNextGeneration();
        }

        if (e.getSource() instanceof Cell) {
            if (((Cell) e.getSource()).isAlive()) {
                ((Cell) e.getSource()).kill();
                this.nextGenerationStatus[((Cell) e.getSource()).getxPosition()][((Cell) e.getSource()).getyPosition()] = false;
            }
            else {
                ((Cell) e.getSource()).revive();
                this.nextGenerationStatus[((Cell) e.getSource()).getxPosition()][((Cell) e.getSource()).getyPosition()] = true;
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