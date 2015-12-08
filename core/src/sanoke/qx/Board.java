package sanoke.qx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class Board {
	public final int NUM_ROWS = 8;
	public final int NUM_COLS = 8;
	public static final int EMPTY_SLOT = 0;

	private Array<Array<Unit>> columns;
	// private Array<Unit> matchingUnits;
	private Sound clearSound;

	public Board() {
		clearSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
		// matchingUnits = new Array<Unit>();
		columns = new Array<Array<Unit>>(NUM_COLS);
		// initialize the units
		for (int c = 0; c < NUM_COLS; c++) {
			Array<Unit> col = new Array<Unit>(NUM_ROWS);
			for (int r = 0; r < NUM_ROWS; r++) {
				col.add(spawnUnit(r, c));
			}
			columns.add(col);
		}
		updateBoard();
	}

	// spawns a random Unit at row, col
	public Unit spawnUnit(int row, int col) {
		return new Unit(row, col, MathUtils.random(1, 5));
	}

	public Array<Unit> getCol(int col) {
		assert (col >= 0 && col < NUM_COLS);
		return columns.get(col);
	}

	public Unit getUnit(int row, int col) {
		return getCol(col).get(row);
	}

	public void checkMatch(Unit unit) {
		if (unit.getType() != EMPTY_SLOT) {
			isHoriMatch(unit);
			isVertMatch(unit);
		}
	}

	public boolean removeMatches() {
		boolean hasMatch = false;
		for (int c = 0; c < NUM_COLS; c++) {
			for (int r = 0; r < NUM_ROWS; r++) {
				Unit currentUnit = getUnit(r, c);
				if (currentUnit.isVertMatch() || currentUnit.isHoriMatch()) {
					currentUnit.setHoriMatch(false);
					currentUnit.setVertMatch(false);
					currentUnit.setType(0);
					hasMatch = true;
				}
			}
		}
		return hasMatch;
	}

	public void updateBoard() {
		for (int c = 0; c < NUM_COLS; c++) {
			Array<Unit> col = getCol(c);
			for (int r = 0; r < NUM_ROWS; r++) {
				checkMatch(col.get(r));
			}
		}
		if (removeMatches()) {
			clearSound.play();
			pullDown();
			updateBoard();
		}
	}

	// Pulls tiles down and generates new units.
	public void pullDown() {
		for (int c = 0; c < NUM_COLS; c++) {
			for (int r = 0; r < NUM_ROWS; r++) {
				if (getUnit(r, c).getType() == EMPTY_SLOT) {
					int replacementRow = getLowestUnit(r + 1, c);
					if (replacementRow == -1) {
					    replacementRow = NUM_ROWS - 1;
					    columns.get(c).pop();
					    columns.get(c).add(spawnUnit(replacementRow, c));
                    }
                    columns.get(c).swap(r, replacementRow);
                    getUnit(r, c).setRow(r);
                    getUnit(replacementRow, c).setRow(replacementRow);

				}
			}
		}
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

	public boolean isVertMatch(Unit unit) {
		if (unit.isVertMatch()) {
			return true;
		}
		if (unit.getRow() > 2) {
			Unit belowUnit = getUnit(unit.getRow() - 1, unit.getCol());
			if (belowUnit.isVertMatch()
					&& unit.getType() == belowUnit.getType()) {
				unit.setVertMatch(true);
				return true;
			}
		}
		if (unit.getRow() < NUM_ROWS - 2) {
			Unit aboveUnit = getUnit(unit.getRow() + 1, unit.getCol());
			Unit aboveTwoUnit = getUnit(unit.getRow() + 2, unit.getCol());
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

	public boolean isHoriMatch(Unit unit) {
		if (unit.isHoriMatch()) {
			return true;
		}
		if (unit.getCol() > 2) {
			Unit leftUnit = getUnit(unit.getRow(), unit.getCol() - 1);
			if (leftUnit.isHoriMatch() && unit.getType() == leftUnit.getType()) {
				unit.setHoriMatch(true);
				return true;
			}
		}
		if (unit.getCol() < NUM_COLS - 2) {
			Unit rightUnit = getUnit(unit.getRow(), unit.getCol() + 1);
			Unit rightTwoUnit = getUnit(unit.getRow(), unit.getCol() + 2);
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

}
