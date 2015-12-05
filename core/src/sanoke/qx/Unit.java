package sanoke.qx;

public class Unit {

    /*
     * Not sure if will need these here or not
     * private static final int RED = 0; 
     * private static final int ORANGE = 1;
     * private static final int GREEN = 2; 
     * private static final int BLUE = 3;
     */
    
    private int row;
    private int col;
    private int type;
    
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
    
    public void setVertMatch() {
        isVertMatch = true;
    }
    
    public void setHoriMatch() {
        isHoriMatch = true;
    }
}
