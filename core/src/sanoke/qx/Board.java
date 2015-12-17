package sanoke.qx;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class Board {
    public static final int NUM_ROWS = 8;
    public static final int NUM_COLS = 8;
    public static final int EMPTY_SLOT = 0;

    public static final int NOT_MATCHED = -1;
    
    public static final int POINTS_FOR_MOVE = 4;
    public static final int POINTS_MATCH_THREE = 5;
    public static final int POINTS_MATCH_FOUR = 6;
    public static final int POINTS_MATCH_FIVE = 7;
    public static final int POINTS_MATCH_SIX = 8;
    public static final int POINTS_MATCH_SEVEN = 9;
    public static final int POINTS_MATCH_MANY = 10;

    public static final int MINIMUM_NUM_TYPE = 1;
    public static final int NUMBER_OF_UNIT_TYPES = 6;

    public static final int INITIAL_CONNECTED_POINTS = 1;
    public static final int PARAM_ADJACENT = 1;
    public static final int PARAM_VALID_BOUND = 0;

    public static final int PARAM_NO_REPLACEMENT = -1;
    public static final int PARAM_TOPMOST_ROW = NUM_ROWS - 1;

    private boolean isReadyToSwap;
    private Unit selectedUnit;
    
    private Array<Unit> fallingUnits;

    private Array<Array<Unit>> columns;

    private int points;
    private int[][] pointGraph;

    public Board() {
        isReadyToSwap = false;
        columns = new Array<Array<Unit>>(NUM_COLS);
        fallingUnits = new Array<Unit>(NUM_ROWS * NUM_COLS);

        initializeUnits();
        updateBoard();
    }

    public int getPoints() {
        return points;
    }

    public Array<Unit> getFallingUnits() {
        return fallingUnits;
    }

    public Array<Unit> getCol(int col) {
        assert (col >= 0 && col < NUM_COLS);
        return columns.get(col);
    }

    public Unit getUnit(int row, int col) {
        return getCol(col).get(row);
    }

    // returns true if nothing is falling
    public boolean isStable() {
        return fallingUnits.size == 0;
    }

    public void updateBoard() {
        findMatches();
    
        if (removeMatches() && isStable()) {
            Assets.playSound();
    
            pullDown();
        }
    }

    public void highlightAndSwapUnit(int rowNum, int colNum) {
        if (colNum < NUM_COLS && rowNum < NUM_ROWS) {
            Unit unit = getUnit(rowNum, colNum);
            unit.toggleSelected();
            if (unit.isSelected()) {
                if (isReadyToSwap) {
                    swapUnit(unit);
                } else {
                    isReadyToSwap = true;
                    selectedUnit = unit;
                }
            } else {
                isReadyToSwap = false;
                selectedUnit = null;
            }
        }
    }

    private void initializeUnits() {
        for (int c = 0; c < NUM_COLS; c++) {
            Array<Unit> col = new Array<Unit>(NUM_ROWS);
            for (int r = 0; r < NUM_ROWS; r++) {
                col.add(spawnRandomUnit(r, c));
            }
            columns.add(col);
        }
    }
    
    private Unit spawnRandomUnit(int row, int col) {
        return new Unit(row, col, generateRandomUnitType());
    }

    private int generateRandomUnitType() {
        return MathUtils.random(MINIMUM_NUM_TYPE, NUMBER_OF_UNIT_TYPES);
    }

    // swaps units if they are neighbours, else de-select old unit
    private void swapUnit(Unit unit) {
        if (selectedUnit.isNeighbour(unit)) {
            swapTypesWithSelected(unit);

            unit.toggleSelected();
            selectedUnit.toggleSelected();

            isReadyToSwap = false;
            updatePointsForMove();
        } else {
            selectedUnit.toggleSelected();
            selectedUnit = unit;
        }
    }

    private void swapTypesWithSelected(Unit unit) {
        int unitType = unit.getType();
        unit.setType(selectedUnit.getType());
        selectedUnit.setType(unitType);
    }
    
    private void findMatches() {
        for (int c = 0; c < NUM_COLS; c++) {
            Array<Unit> col = getCol(c);
            for (int r = 0; r < NUM_ROWS; r++) {
                Unit unit = col.get(r);
                checkMatch(unit);
            }
        }
    }

    private boolean removeMatches() {
        initializePointGraph();
    
        boolean hasMatch = false;
        for (int c = 0; c < NUM_COLS; c++) {
            for (int r = 0; r < NUM_ROWS; r++) {
                Unit currentUnit = getUnit(r, c);
                if (currentUnit.isVertMatch() || currentUnit.isHoriMatch()) {
                    currentUnit.setHoriMatch(false);
                    currentUnit.setVertMatch(false);
                    pointGraph[r][c] = currentUnit.getType();
                    currentUnit.setType(0);
                    hasMatch = true;
                }
            }
        }
        awardPoints();
        return hasMatch;
    }

    // Pulls tiles down and generates new units.
    private void pullDown() {
        for (int c = 0; c < NUM_COLS; c++) {
            int colOffset = 0;
            for (int r = 0; r < NUM_ROWS; r++) {
                if (getUnit(r, c).getType() == EMPTY_SLOT) {
                    int replacementRow = getLowestUnit(r + 1, c);
                    if (replacementRow == PARAM_NO_REPLACEMENT) {
                        replacementRow = PARAM_TOPMOST_ROW;
                        createReplacementUnitAtTop(c, colOffset);
                        colOffset++;
                    }
    
                    columns.get(c).swap(r, replacementRow);
    
                    getUnit(r, c).setFinalRow(r);
                    fallingUnits.add(getUnit(r, c));
                }
            }
        }
    }

    private void checkMatch(Unit unit) {
        if (unit.getType() != EMPTY_SLOT) {
            checkHoriMatch(unit);
            checkVertMatch(unit);
        }
    }

    private boolean checkVertMatch(Unit unit) {
        if (unit.isVertMatch()) {
            return true;
        }
        // Check for matches more than 3
        if (unit.getFinalRow() > 2) {
            Unit belowUnit = getUnit(unit.getFinalRow() - 1, unit.getCol());
            if (belowUnit.isVertMatch()
                    && unit.getType() == belowUnit.getType()) {
                unit.setVertMatch(true);
                return true;
            }
        }
        // Check for matches of 3 units
        if (unit.getFinalRow() < NUM_ROWS - 2) {
            Unit aboveUnit = getUnit(unit.getFinalRow() + 1, unit.getCol());
            Unit aboveTwoUnit = getUnit(unit.getFinalRow() + 2, unit.getCol());
            if (unit.getType() == aboveUnit.getType()
                    && unit.getType() == aboveTwoUnit.getType()) {
                unit.setVertMatch(true);
                aboveUnit.setVertMatch(true);
                aboveTwoUnit.setVertMatch(true);
                return true;
            }
        }
        return false;
    }

    private boolean checkHoriMatch(Unit unit) {
        if (unit.isHoriMatch()) {
            return true;
        }
        // Check for matches more than 3
        if (unit.getCol() > 2) {
            Unit leftUnit = getUnit(unit.getFinalRow(), unit.getCol() - 1);
            if (leftUnit.isHoriMatch() && unit.getType() == leftUnit.getType()) {
                unit.setHoriMatch(true);
                return true;
            }
        }
        // Check for matches of 3 units
        if (unit.getCol() < NUM_COLS - 2) {
            Unit rightUnit = getUnit(unit.getFinalRow(), unit.getCol() + 1);
            Unit rightTwoUnit = getUnit(unit.getFinalRow(), unit.getCol() + 2);
            if (unit.getType() == rightUnit.getType()
                    && unit.getType() == rightTwoUnit.getType()) {
                unit.setHoriMatch(true);
                rightUnit.setHoriMatch(true);
                rightTwoUnit.setHoriMatch(true);
                return true;
            }
        }
        return false;
    }

    private void updatePointsForMove() {
        points -= POINTS_FOR_MOVE;
    }

    private void initializePointGraph() {
        pointGraph = new int[NUM_ROWS][NUM_COLS];
        for (int r = 0; r < NUM_ROWS; r++) {
            for (int c = 0; c < NUM_COLS; c++) {
                pointGraph[r][c] = NOT_MATCHED;
            }
        }
    }

    private void awardPoints() {
        for (int r = 0; r < NUM_ROWS; r++) {
            for (int c = 0; c < NUM_COLS; c++) {
                if (pointGraph[r][c] != NOT_MATCHED) {
                    int numInConnectedComponents = DFS(r, c, pointGraph[r][c]);
                    addPoints(numInConnectedComponents);
                }
            }
        }
    }

    private int DFS(int row, int col, int type) {
        pointGraph[row][col] = NOT_MATCHED;
        int sumPoints = INITIAL_CONNECTED_POINTS;
        if (row - PARAM_ADJACENT >= PARAM_VALID_BOUND
                && pointGraph[row - PARAM_ADJACENT][col] == type) {
            sumPoints += DFS(row - PARAM_ADJACENT, col, type);
        }
        if (row + PARAM_ADJACENT < NUM_ROWS
                && pointGraph[row + PARAM_ADJACENT][col] == type) {
            sumPoints += DFS(row + PARAM_ADJACENT, col, type);
        }
        if (col - PARAM_ADJACENT >= PARAM_VALID_BOUND
                && pointGraph[row][col - PARAM_ADJACENT] == type) {
            sumPoints += DFS(row, col - PARAM_ADJACENT, type);
        }
        if (col + PARAM_ADJACENT < NUM_COLS
                && pointGraph[row][col + PARAM_ADJACENT] == type) {
            sumPoints += DFS(row, col + PARAM_ADJACENT, type);
        }
        return sumPoints;
    }

    private void addPoints(int numInConnectedComponents) {
        assert (numInConnectedComponents >= 3);
        switch (numInConnectedComponents) {
        case 3:
            points += POINTS_MATCH_THREE;
            break;
        case 4:
            points += POINTS_MATCH_FOUR;
            break;
        case 5:
            points += POINTS_MATCH_FIVE;
            break;
        case 6:
            points += POINTS_MATCH_SIX;
            break;
        case 7:
            points += POINTS_MATCH_SEVEN;
            break;
        default:
            points += POINTS_MATCH_MANY;
        }
    }

    private void createReplacementUnitAtTop(int c, int colOffset) {
        columns.get(c).pop();
        columns.get(c).add(spawnRandomUnit(NUM_ROWS + colOffset, c));
    }

    // returns row number of lowest unit above specified unit
    private int getLowestUnit(int rowToCheck, int columnNum) {
        if (rowToCheck >= NUM_ROWS) {
            return -1; // not found
        }
        if (getUnit(rowToCheck, columnNum).getType() != EMPTY_SLOT) {
            return rowToCheck;
        }
        return getLowestUnit(rowToCheck + 1, columnNum);
    }

}
