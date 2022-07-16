package pk.pl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class BasicGameOfLife extends Window implements MouseListener {
    private final ArrayList<ArrayList<Cell>> cellsContainer = new ArrayList<>();
    private final boolean[][] nextGenerationStatuses;
    private int numberOfGeneration = 0;

    private final JButton nextGenerationButton = new JButton("Next generation");
    private final JButton startStopNextGenLoopButton = new JButton("Next generation loop");

    private final JTextField nextGenerationNumberField = new JTextField();

    private final JPanel gamePanel = new JPanel();
    private final JPanel menuPanel = new JPanel();

    private final JLabel generationNumberLabel = new JLabel("Number of generations: " + numberOfGeneration);

    public BasicGameOfLife() {
        super(50, 50);
        this.nextGenerationStatuses = new boolean[this.gridWidth][this.gridHeight];

        this.gamePanel.setBackground(Color.BLUE);
        this.gamePanel.setLayout(new GridLayout(this.gridWidth, this.gridHeight));

        this.menuPanel.setLayout(new GridLayout(0,4));

        this.nextGenerationButton.addMouseListener(this);
        this.startStopNextGenLoopButton.addMouseListener(this);

        this.menuPanel.add(nextGenerationButton);
        this.menuPanel.add(startStopNextGenLoopButton);
        this.menuPanel.add(nextGenerationNumberField);
        this.menuPanel.add(generationNumberLabel);
        this.createGrid();

        this.add(gamePanel);
        this.add(menuPanel);
        this.gamePanel.setVisible(true);
    }

    private void createGrid() {
        for (int verticalPosition = 0; verticalPosition < this.gridHeight; verticalPosition++) {

            ArrayList<Cell> tempCellsContainer = new ArrayList<>();

            for (int horizontalPosition = 0; horizontalPosition < this.gridWidth; horizontalPosition++) {
                Cell cell = new Cell(verticalPosition, horizontalPosition);
                cell.addMouseListener(this);

                tempCellsContainer.add(cell);
                this.gamePanel.add(cell);

                nextGenerationStatuses[verticalPosition][horizontalPosition] = false;
            }

            this.cellsContainer.add(tempCellsContainer);
        }
    }

    private void checkGameOfLifeRules() {
        for (int verticalPosition = 0; verticalPosition < this.gridHeight; verticalPosition++) {
            for (int horizontalPosition = 0; horizontalPosition < this.gridWidth; horizontalPosition++) {
                this.checkCellNeighbourhood(verticalPosition, horizontalPosition);
            }
        }
    }

    private void checkCellNeighbourhood(int verticalPosition, int horizontalPosition) {
        int aliveNeighbours = 0;
        for (int iterator = verticalPosition - 1; iterator < verticalPosition+2; iterator++) {
            for (int subIterator = horizontalPosition - 1; subIterator < horizontalPosition+2; subIterator++) {
                if ((iterator >= 0 && iterator < this.gridHeight) && (subIterator >= 0 && subIterator < this.gridWidth)) {
                    if (this.cellsContainer.get(iterator).get(subIterator).isAlive()
                            && !(iterator == verticalPosition && subIterator == horizontalPosition)) {
                        aliveNeighbours += 1;
                    }
                }
            }
        }
        this.setNextGenerationStatuses(aliveNeighbours, verticalPosition, horizontalPosition);
    }

    private void setNextGenerationStatuses(int aliveNeighbours, int verticalPosition, int horizontalPosition) {
        if (aliveNeighbours > 3 || aliveNeighbours < 2) {
            this.nextGenerationStatuses[verticalPosition][horizontalPosition] = false;
        }
        else if (aliveNeighbours == 3) {
            this.nextGenerationStatuses[verticalPosition][horizontalPosition] = true;
        }
    }

    private void setNextGeneration() {
        for (int verticalPosition = 0; verticalPosition < this.gridHeight; verticalPosition++) {
            for (int horizontalPosition = 0; horizontalPosition < this.gridWidth; horizontalPosition++) {
                if (this.nextGenerationStatuses[verticalPosition][horizontalPosition]) {
                    this.cellsContainer.get(verticalPosition).get(horizontalPosition).revive();
                }
                else {
                    this.cellsContainer.get(verticalPosition).get(horizontalPosition).kill();
                }
            }
        }
    }

    private void updateNextGenerationNumber() {
        this.numberOfGeneration += 1;
        generationNumberLabel.setText("Number of generations: " + numberOfGeneration);
    }

    private void nextGeneration(int n) {
        for (int i = 0; i < n; i++) {
            checkGameOfLifeRules();
            setNextGeneration();
            updateNextGenerationNumber();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == nextGenerationButton) {
            nextGeneration(1);
        }

        if (e.getSource() == startStopNextGenLoopButton){
            try {
                nextGeneration(Integer.parseInt(nextGenerationNumberField.getText()));
            } catch (NumberFormatException ex) {
                System.out.println("Wprowadzono błędy format!");
            }
        }

        if (e.getSource() instanceof Cell) {
            if (((Cell) e.getSource()).isAlive()) {
                ((Cell) e.getSource()).kill();
                this.nextGenerationStatuses[((Cell) e.getSource()).getVerticalPosition()][((Cell) e.getSource()).getHorizontalPosition()] = false;
            }
            else {
                ((Cell) e.getSource()).revive();
                this.nextGenerationStatuses[((Cell) e.getSource()).getVerticalPosition()][((Cell) e.getSource()).getHorizontalPosition()] = true;
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