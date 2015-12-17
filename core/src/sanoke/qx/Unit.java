package sanoke.qx;

public class Unit {

    // row and column that unit is currently at
    private float row;
    private int col;
    // row that unit should be at
    private int finalRow;

    private int type;

    private boolean isSelected;
    private boolean isVertMatch;
    private boolean isHoriMatch;

    public Unit(int row, int col, int type) {
        this.row = row;
        this.finalRow = row;
        this.col = col;
        this.type = type;
    }

    public float getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getType() {
        return type;
    }

    public int getFinalRow() {
        return finalRow;
    }

    public void setRow(float row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setType(int newType) {
        type = newType;
    }

    public void setFinalRow(int row) {
        this.finalRow = row;
    }

    public boolean isFalling() {
        return finalRow < row;
    }

    public boolean isNeighbour(Unit unit) {
        int colOffset = Math.abs((unit.getCol() - col));
        int rowOffset = Math.abs((unit.getFinalRow() - finalRow));
        if ((colOffset == 1 && rowOffset == 0)
                || (colOffset == 0 && rowOffset == 1)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isSelected() {
        return isSelected;
    }

    public boolean isVertMatch() {
        return isVertMatch;
    }

    public boolean isHoriMatch() {
        return isHoriMatch;
    }

    public void toggleSelected() {
        isSelected = !isSelected;
    }

    public void setVertMatch(boolean isMatch) {
        isVertMatch = isMatch;
    }

    public void setHoriMatch(boolean isMatch) {
        isHoriMatch = isMatch;
    }
}
