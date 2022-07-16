package pk.pl;

/*
Implementation of Game Of Life by John Horton Conway
Program author: Piotr Kupczyk
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class BasicGameOfLife extends Window implements MouseListener {
    private final ArrayList<ArrayList<Cell>> cellsContainer = new ArrayList<>();
    private final boolean[][] nextGenerationStatuses;
    private int numberOfGeneration = 0;
    private boolean infiniteNextGenLoop = false;
    private boolean repeatedGrid = true;
    private boolean generateStructure = false;
    private final JButton nextGenerationButton = new JButton("Next generation");
    private final JButton startStopNextGenLoopButton = new JButton("Next generation loop");
    private final JButton clearGridButton = new JButton("Clear");
    private final JButton repeatedGridButton = new JButton("Repeated grid: ON");
    private final JPanel gamePanel = new JPanel();
    private final JLabel generationNumberLabel = new JLabel("Number of generations: " + this.numberOfGeneration, SwingConstants.CENTER);
    private final String[] optionJComboBoxList = {"Select structure...", "Oscilator 1", "Oscilator 2", "Voyager"};
    private final JComboBox generateStructureList = new JComboBox(this.optionJComboBoxList);

    public BasicGameOfLife()  {
        super(50, 50);
        this.setTitle("Basic Game of Life");

        this.nextGenerationStatuses = new boolean[this.gridWidth][this.gridHeight];

        this.gamePanel.setLayout(new GridLayout(this.gridWidth, this.gridHeight, 0, 0));

        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(2,4,0,0));

        this.nextGenerationButton.addMouseListener(this);
        this.startStopNextGenLoopButton.addMouseListener(this);
        this.clearGridButton.addMouseListener(this);
        this.repeatedGridButton.addMouseListener(this);

        menuPanel.add(this.nextGenerationButton);
        menuPanel.add(this.startStopNextGenLoopButton);
        menuPanel.add(this.clearGridButton);
        menuPanel.add(this.repeatedGridButton);
        menuPanel.add(this.generateStructureList);
        menuPanel.add(this.generationNumberLabel);
        this.createGrid();

        this.generateStructureList.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                generateStructure = true;
                infiniteNextGenLoop = false;
            }
        });

        this.add(gamePanel, BorderLayout.CENTER);
        this.add(menuPanel, BorderLayout.PAGE_END);
        this.revalidate();
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

                this.nextGenerationStatuses[verticalPosition][horizontalPosition] = false;
            }

            this.cellsContainer.add(tempCellsContainer);
        }
    }

    private void checkGameOfLifeRules() {
        for (int verticalPosition = 0; verticalPosition < this.gridHeight; verticalPosition++) {
            for (int horizontalPosition = 0; horizontalPosition < this.gridWidth; horizontalPosition++) {
                if (this.repeatedGrid) {
                    this.checkCellInfiniteNeighbourhood(verticalPosition, horizontalPosition);
                }
                else {
                    this.checkCellNeighbourhood(verticalPosition, horizontalPosition);
                }
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

    private void checkCellInfiniteNeighbourhood(int verticalPosition, int horizontalPosition) {
        int aliveNeighbours = 0;
        for (int iterator = verticalPosition - 1; iterator < verticalPosition+2; iterator++) {
            for (int subIterator = horizontalPosition - 1; subIterator < horizontalPosition+2; subIterator++) {
                int yIndex = iterator;
                int xIndex = subIterator;
                if (iterator < 0) {yIndex = this.gridHeight - 1;}
                if (iterator >= this.gridHeight) {yIndex = 0;}
                if (subIterator < 0) {xIndex = gridWidth - 1;}
                if (subIterator >= this.gridWidth) {xIndex = 0;}
                if (this.cellsContainer.get(yIndex).get(xIndex).isAlive()
                        && !(iterator == verticalPosition && subIterator == horizontalPosition)) {
                    aliveNeighbours += 1;
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
        this.generationNumberLabel.setText("Number of generations: " + this.numberOfGeneration);
    }

    private void nextGeneration() {
        checkGameOfLifeRules();
        setNextGeneration();
        updateNextGenerationNumber();
        this.revalidate();
    }

    private void nextGenerationLoop() {
        Timer timer = new Timer(150, e -> {
            if (!infiniteNextGenLoop) {
                ((Timer)e.getSource()).stop();
            }
            else {
                nextGeneration();
            }
        });
        timer.start();
    }

    private void setInfiniteLoop() {
        this.infiniteNextGenLoop = !this.infiniteNextGenLoop;
    }

    private void setRepeatedGrid() {
        this.repeatedGrid = !this.repeatedGrid;
        if (this.repeatedGrid) this.repeatedGridButton.setText("Repeated grid: ON");
        else this.repeatedGridButton.setText("Repeated grid: OFF");
    }

    private void setGenerateStructure() {
        this.generateStructure = !this.generateStructure;
    }

    private void clearGrid() {
        this.setInfiniteLoop();
        for (int verticalPosition = 0; verticalPosition < this.gridHeight; verticalPosition++) {
            for (int horizontalPosition = 0; horizontalPosition < this.gridWidth; horizontalPosition++) {
                this.nextGenerationStatuses[verticalPosition][horizontalPosition] = false;
            }
        }
        this.setNextGeneration();
        this.numberOfGeneration = 0;
        this.generationNumberLabel.setText("Number of generations: " + numberOfGeneration);
    }

    private void structureGeneration(String structureType, int verticalPosition, int horizontalPosition) {
        if (this.repeatedGrid) {
            if ("Oscilator 1".equals(structureType)) {
                for (int iterator = verticalPosition - 1; iterator < verticalPosition + 2; iterator++) {
                    int yPosition;
                    if (iterator < 0) yPosition = this.gridHeight - 1;
                    else if (iterator >= this.gridHeight) yPosition = 0;
                    else yPosition = iterator;

                    this.cellsContainer.get(yPosition).get(horizontalPosition).revive();
                    this.nextGenerationStatuses[yPosition][horizontalPosition] = true;
                }
            }
        } else {
            if ("Oscilator 1".equals(structureType)) {
                for (int iterator = verticalPosition - 1; iterator < verticalPosition + 2; iterator++) {

                }
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == this.nextGenerationButton) {
            nextGeneration();
        }
        if (e.getSource() == this.startStopNextGenLoopButton){
            this.setInfiniteLoop();
            this.nextGenerationLoop();
        }
        if (e.getSource() == this.clearGridButton) {
            this.clearGrid();
        }
        if (e.getSource() == this.repeatedGridButton) {
            this.setRepeatedGrid();
        }
        if (e.getSource() instanceof Cell) {
            if (this.generateStructure) {
                if (this.infiniteNextGenLoop) this.setInfiniteLoop();
                this.structureGeneration(String.valueOf(this.generateStructureList.getSelectedItem()), ((Cell) e.getSource()).getVerticalPosition()
                , ((Cell) e.getSource()).getHorizontalPosition());
                this.generateStructureList.setSelectedIndex(0);
                this.setGenerateStructure();
            }
            else {
                if (((Cell) e.getSource()).isAlive()) {
                    ((Cell) e.getSource()).kill();
                    this.nextGenerationStatuses[((Cell) e.getSource()).getVerticalPosition()][((Cell) e.getSource()).getHorizontalPosition()] = false;
                } else {
                    ((Cell) e.getSource()).revive();
                    this.nextGenerationStatuses[((Cell) e.getSource()).getVerticalPosition()][((Cell) e.getSource()).getHorizontalPosition()] = true;
                }
                if (this.infiniteNextGenLoop) this.setInfiniteLoop();
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