package sanoke.qx;


public class Unit {

    /*
     * Not sure if will need these here or not
     * private static final int RED = 0; 
     * private static final int ORANGE = 1;
     * private static final int YELLOW = 2;
     * private static final int GREEN = 3; 
     * private static final int BLUE = 4;
     * private static final int PURPLE = 5;
     */

    // row and column unit is currently at
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
    
    public void toggleSelected() {
        isSelected = !isSelected;
    }
    
    public void setVertMatch(boolean isMatch) {
        isVertMatch = isMatch;
    }
    
    public boolean isVertMatch() {
        return isVertMatch;
    }
    
    public void setHoriMatch(boolean isMatch) {
        isHoriMatch = isMatch;
    }
    
    public boolean isHoriMatch() {
        return isHoriMatch;
    }
}
