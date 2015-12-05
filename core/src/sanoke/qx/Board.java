package sanoke.qx;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class Board {
    private static final int NUM_ROWS = 8;
    private static final int NUM_COLS = 8;
    
    private Array<Array<Unit>> columns;
    
    public Board() {
        columns = new Array<Array<Unit>>(NUM_COLS);
        Array<Unit> col = new Array<Unit>(NUM_ROWS);
        col.add(spawnUnit(0, 0));
        columns.add(col);
    }
    
    //spawns a random Unit at row, col
    public Unit spawnUnit(int row, int col) {
        return new Unit(row, col, MathUtils.random(3));
    }
    
    public Array<Unit> getCol(int col) {
        assert(col >= 0 && col < NUM_COLS);
        return columns.get(col);
    }
}
