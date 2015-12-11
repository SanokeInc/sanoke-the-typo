package sanoke.qx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class GameScreen implements Screen {
	final Sanoke game;

	OrthographicCamera camera;

	boolean isReadyToSwap;
	Unit selectedUnit;
	boolean isGameOver;
	private static TextureRegion unitTexture;
	public static final int BOARD_Y_OFFSET = 100;
	public static final int BOARD_X_OFFSET = 300;
	public static final int SCORE_X_OFFSET = 5;
	public static final int SCORE_Y_OFFSET = 30;
	public static final int FALL_RATE = 3;
	public static final int POINTS_TO_LOSE = -10;
	private Board board;

	public GameScreen(final Sanoke game) {
		Assets.loadAssets();
		this.game = game;
		board = new Board();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, game.HEIGHT, game.WIDTH);
		isReadyToSwap = false;
		isGameOver = false;
		Assets.playMusic();
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		game.batch.begin();
		game.batch.draw(Assets.background, 0, 0);
		game.batch.draw(Assets.boardImage, BOARD_X_OFFSET, BOARD_Y_OFFSET);
		game.font.draw(game.batch, "Score: " + board.getPoints(),
				SCORE_X_OFFSET, SCORE_Y_OFFSET);
		updateUnitsPosition(delta);
		drawUnits();
		if (board.getPoints() <= POINTS_TO_LOSE) {
			isGameOver = true;
			game.batch.draw(Assets.gameOver, 0, 0);
		}
		game.batch.end();
		if (!isGameOver) {
			processInput();
			updateBoard();
		}
	}

	private void updateUnitsPosition(float delta) {
		Array<Unit> fallingUnits = board.getFalling();

		for (int i = 0; i < fallingUnits.size; i++) {
			Unit unit = fallingUnits.get(i);
			if (unit.isFalling()) {
				unit.setRow(Math.max(unit.getRow() - delta * FALL_RATE,
						unit.getFinalRow()));
			} else {
				fallingUnits.removeIndex(i);
				i--;
			}
		}
	}

	private void updateBoard() {
		if (board.isStable()) {
			board.updateBoard();
		}
	}

	private void processInput() {
		if (Gdx.input.justTouched() && board.isStable()) {
			float xPos = (Gdx.input.getX() - BOARD_X_OFFSET);
			float yPos = (game.HEIGHT - BOARD_Y_OFFSET - Gdx.input.getY());
			// lazy validation for positive numbers
			if (xPos >= 0 && yPos >= 0) {
				highlightAndSwapUnit((int) yPos / Assets.UNIT_LENGTH,
						(int) xPos / Assets.UNIT_WIDTH);
			}
		}

	}

	private void highlightAndSwapUnit(int rowNum, int colNum) {
		if (colNum < board.NUM_COLS && rowNum < board.NUM_ROWS) {
			Unit unit = board.getUnit(rowNum, colNum);
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

	// swaps units if they are neighbours, else de-select old unit
	private void swapUnit(Unit unit) {
		if (selectedUnit.isNeighbour(unit)) {
			swapTypesWithSelected(unit);

			unit.toggleSelected();
			selectedUnit.toggleSelected();

			isReadyToSwap = false;
			board.moveAttempt();
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

	private void drawUnits() {
		for (int c = 0; c < board.NUM_COLS; c++) {
			Array<Unit> col = board.getCol(c);
			for (int r = 0; r < board.NUM_ROWS; r++) {
				Unit unit = col.get(r);
				if (unit.isSelected()) {
					unitTexture = Assets.selectedTextures[unit.getType()];
				} else {
					unitTexture = Assets.unselectedTextures[unit.getType()];
				}
				float xPos = c * Assets.UNIT_WIDTH + BOARD_X_OFFSET;
				float yPos = unit.getRow() * Assets.UNIT_LENGTH
						+ BOARD_Y_OFFSET;
				if (yPos <= board.NUM_ROWS * Assets.UNIT_LENGTH
						+ BOARD_Y_OFFSET) {
					game.batch.draw(unitTexture, xPos, yPos);
				}

			}
		}
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
