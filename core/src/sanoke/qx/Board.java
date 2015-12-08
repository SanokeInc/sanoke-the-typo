package sanoke.qx;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class Board {
    public final int NUM_ROWS = 8;
    public final int NUM_COLS = 8;
    
    private Array<Array<Unit>> columns;
    private Array<Unit> matchingUnits;
    
    public Board() {
        matchingUnits = new Array<Unit>();
        columns = new Array<Array<Unit>>(NUM_COLS);
        // initialize the units
        for (int i = 0; i < NUM_COLS; i++) {
            Array<Unit> col = new Array<Unit>(NUM_ROWS);
            for (int j = 0; j < NUM_ROWS; j++) {
                col.add(spawnUnit(j, i));
            }
            columns.add(col);
        }
    }
    
    //spawns a random Unit at row, col
    public Unit spawnUnit(int row, int col) {
        return new Unit(row, col, MathUtils.random(1, 5));
    }
    
    public Array<Unit> getCol(int col) {
        assert(col >= 0 && col < NUM_COLS);
        return columns.get(col);
    }
    
    public Unit getUnit(int row, int col) {
        return getCol(col).get(row);
    }
    
    public void checkMatch(Unit unit) {
        boolean isHori = isHoriMatch(unit);
        if (isVertMatch(unit) || isHori) {
            matchingUnits.add(unit);
            unit.setType(0);
        }
    }
    public boolean isVertMatch(Unit unit) {
        if (unit.isVertMatch()) {
            return true;
        }
        if (unit.getRow() > 2) {
            Unit belowUnit = getUnit(unit.getRow() - 1, unit.getCol());
            if (belowUnit.isVertMatch() && unit.getType() == belowUnit.getType()) {
                unit.setVertMatch();
                return true;
            }
        }
        if (unit.getRow() < NUM_ROWS - 2) {
            Unit aboveUnit = getUnit(unit.getRow() + 1, unit.getCol());
            Unit aboveTwoUnit = getUnit(unit.getRow() + 2, unit.getCol());
            if (unit.getType() == aboveUnit.getType() && unit.getType() == aboveTwoUnit.getType()) {
                unit.setVertMatch();
                aboveUnit.setVertMatch();
                aboveTwoUnit.setVertMatch();
                return true;
            }
        }
        return false;
    }
    
    public boolean isHoriMatch(Unit unit) {
        if (unit.isHoriMatch()) {
            return true;
        }
        if (unit.getCol() > 2) {
            Unit leftUnit = getUnit(unit.getRow(), unit.getCol() - 1);
            if (leftUnit.isHoriMatch() && unit.getType() == leftUnit.getType()) {
                unit.setHoriMatch();
                return true;
            }
        }
        if (unit.getCol() < NUM_COLS - 2) {
            Unit rightUnit = getUnit(unit.getRow(), unit.getCol() + 1);
            Unit rightTwoUnit = getUnit(unit.getRow(), unit.getCol() + 2);
            if (unit.getType() == rightUnit.getType() && unit.getType() == rightTwoUnit.getType()) {
                unit.setHoriMatch();
                rightUnit.setHoriMatch();
                rightTwoUnit.setHoriMatch();
                return true;
            }
        }
        return false;
    }
    
    
}
