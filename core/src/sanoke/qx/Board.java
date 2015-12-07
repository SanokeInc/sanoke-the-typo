package sanoke.qx;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class Board {
    public final int NUM_ROWS = 8;
    public final int NUM_COLS = 8;
    
    private Array<Array<Unit>> columns;
    
    public Board() {
        columns = new Array<Array<Unit>>(NUM_COLS);
        // initialize the units
        for (int i = 0; i < NUM_COLS; i++) {
            Array<Unit> col = new Array<Unit>(NUM_ROWS);
            for (int j = 0; j < NUM_ROWS; j++) {
                col.add(spawnUnit(j, 0));
            }
            columns.add(col);
        }
    }
    
    //spawns a random Unit at row, col
    public Unit spawnUnit(int row, int col) {
        return new Unit(row, col, MathUtils.random(4));
    }
    
    public Array<Unit> getCol(int col) {
        assert(col >= 0 && col < NUM_COLS);
        return columns.get(col);
    }
}
