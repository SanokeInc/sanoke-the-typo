package sanoke.qx;

public class Level {
	public static final int[] UNIT_TYPES_NUM_PER_LVL = {4, 5, 6, 6, 6};
	public static final int MAX_LEVEL = UNIT_TYPES_NUM_PER_LVL.length;
	
	private int levelNumber;
	
	public Level() {
		levelNumber = 0;
	}
	
	public int getLevel() {
		return levelNumber + 1;
	}
	
	public void increaseLevel() {
		if (levelNumber < MAX_LEVEL - 1)
			levelNumber++;
	}
	
	public int getNumTypes() {
		return UNIT_TYPES_NUM_PER_LVL[levelNumber];
	}
	
}
