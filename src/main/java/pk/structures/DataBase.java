package pk.structures;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DataBase {
    private int verticalLength;
    private int horizontalLength;
    private boolean[][] cellsStatuses;

    public DataBase(String filePath) throws FileNotFoundException {
        this.readStructure(filePath);
    }

    public void readStructure(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        Scanner line = new Scanner(file);
        while (line.hasNextLine()) {
            String arr = line.nextLine();
            if (this.verticalLength == 0) {
                for (int iterator = 0; iterator < arr.length(); iterator++) {
                    if (arr.charAt(iterator) == ' ') continue;
                    this.increaseHorizontalLength();
                }
            }
            this.increaseVerticalLength();
        }

        this.cellsStatuses = new boolean[this.verticalLength][this.horizontalLength];

        line = new Scanner(file);
        for (int iterator = 0; iterator < this.verticalLength; iterator++) {
            String arr = line.nextLine();
            for (int subIterator = 0; subIterator < this.horizontalLength; subIterator++) {
                if (arr.charAt(subIterator) == ' ') continue;
                this.setCellStatus(arr.charAt(subIterator), iterator, subIterator);
            }
        }
    }

    private void setCellStatus(int value, int yPosition, int xPosition) {
        if (value == '1') this.cellsStatuses[yPosition][xPosition] = true;
        else this.cellsStatuses[yPosition][xPosition] = false;
    }

    private void increaseVerticalLength() {
        this.verticalLength += 1;
    }

    private void increaseHorizontalLength() {
        this.horizontalLength += 1;
    }

    public boolean getCellsStatuses(int i, int j) {
        return cellsStatuses[i][j];
    }

    public int getVerticalLength() {
        return verticalLength;
    }

    public int getHorizontalLength() {
        return horizontalLength;
    }

    public boolean getCellsStatus (int yPosition, int xPosition) {
        return cellsStatuses[yPosition][xPosition];
    }
}

