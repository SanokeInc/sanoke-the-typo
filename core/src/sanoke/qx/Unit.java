package sanoke.qx;


public class Unit {

    /*
     * Not sure if will need these here or not
     * private static final int RED = 0; 
     * private static final int ORANGE = 1;
     * private static final int GREEN = 2; 
     * private static final int BLUE = 3;
     * private static final int PURPLE = 4;
     */
    public final int UNIT_LENGTH = 64;
    public final int UNIT_WIDTH = 64;
    
    private int row;
    private int col;
    private int type;
    
    private boolean isSelected;
    private boolean isVertMatch;
    private boolean isHoriMatch;
    
    public Unit(int row, int col, int type) {
        this.row = row;
        this.col = col;
        this.type = type;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getType() {
        return type;
    }
    
    public void setType(int newType) {
        type = newType;
    }
    
    public boolean isNeighbour(Unit unit) {
        int colOffset = Math.abs((unit.getCol() - col));
        int rowOffset = Math.abs((unit.getRow() - row));
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
    
    public void setVertMatch() {
        isVertMatch = true;
    }
    
    public void setHoriMatch() {
        isHoriMatch = true;
    }
}
