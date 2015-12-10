package sanoke.qx;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class GameScreen implements Screen {
    final Sanoke game;
    
    OrthographicCamera camera;
    private Texture background;
    private Texture unitsMap;
    private Texture boardImage;
    private TextureRegion blankImage;
    private TextureRegion redImage;
    private TextureRegion orangeImage;
    private TextureRegion blueImage;
    private TextureRegion greenImage;
    private TextureRegion purpleImage;
    private TextureRegion unitTexture;
    private TextureRegion redSelectImage;
    private TextureRegion orangeSelectImage;
    private TextureRegion blueSelectImage;
    private TextureRegion greenSelectImage;
    private TextureRegion purpleSelectImage;
    private TextureRegion[] unselectedTextures;
    private TextureRegion[] selectedTextures;
    public Music music;
    
    boolean isReadyToSwap;
    Unit selectedUnit;
    
    public static final int UNIT_HEIGHT = 64;
    public static final int UNIT_WIDTH = 64;
    public static final int BOARD_Y_OFFSET = 100;
    public static final int BOARD_X_OFFSET = 300;
    public static final int SCORE_X_OFFSET = 5;
    public static final int SCORE_Y_OFFSET = 30;
    public static final int FALL_RATE = 3;
    private Board board;
    
    public GameScreen(final Sanoke game) {
        background = new Texture(Gdx.files.internal("morelegalphoto.jpg"));
        boardImage = new Texture(Gdx.files.internal("board.png"));
        this.game = game;
        unitsMap = new Texture(Gdx.files.internal("units.png"));
        blankImage = new TextureRegion(unitsMap, 0, 0, UNIT_WIDTH,
                UNIT_HEIGHT);
        redImage = new TextureRegion(unitsMap, UNIT_WIDTH * 1, 0, UNIT_WIDTH,
                UNIT_HEIGHT);
        orangeImage = new TextureRegion(unitsMap, UNIT_WIDTH * 2, 0,
                UNIT_WIDTH, UNIT_HEIGHT);
        blueImage = new TextureRegion(unitsMap, UNIT_WIDTH * 3, 0, UNIT_WIDTH,
                UNIT_HEIGHT);
        greenImage = new TextureRegion(unitsMap, UNIT_WIDTH * 4, 0, UNIT_WIDTH,
                UNIT_HEIGHT);
        purpleImage = new TextureRegion(unitsMap, UNIT_WIDTH * 5, 0,
                UNIT_WIDTH, UNIT_HEIGHT);
        unselectedTextures = new TextureRegion[] {blankImage, redImage, orangeImage,
                blueImage, greenImage, purpleImage };
        redSelectImage = new TextureRegion(unitsMap, UNIT_WIDTH * 1,
                UNIT_HEIGHT, UNIT_WIDTH, UNIT_HEIGHT);
        orangeSelectImage = new TextureRegion(unitsMap, UNIT_WIDTH * 2,
                UNIT_HEIGHT, UNIT_WIDTH, UNIT_HEIGHT);
        blueSelectImage = new TextureRegion(unitsMap, UNIT_WIDTH * 3,
                UNIT_HEIGHT, UNIT_WIDTH, UNIT_HEIGHT);
        greenSelectImage = new TextureRegion(unitsMap, UNIT_WIDTH * 4,
                UNIT_HEIGHT, UNIT_WIDTH, UNIT_HEIGHT);
        purpleSelectImage = new TextureRegion(unitsMap, UNIT_WIDTH * 5,
                UNIT_HEIGHT, UNIT_WIDTH, UNIT_HEIGHT);
        selectedTextures = new TextureRegion[] {blankImage, redSelectImage,
                orangeSelectImage, blueSelectImage, greenSelectImage,
                purpleSelectImage };
        music = Gdx.audio.newMusic(Gdx.files.internal("morelegalmusic.mp3"));
        music.setLooping(true);
        music.setVolume(0.5f);
        music.play();
        board = new Board();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.HEIGHT, game.WIDTH);
        isReadyToSwap = false;
    }
    
    @Override
    public void show() {
        // TODO Auto-generated method stub

    }

    @Override
    public void render(float delta) {
        game.batch.begin();
        game.batch.draw(background, 0, 0);
        game.batch.draw(boardImage, BOARD_X_OFFSET, BOARD_Y_OFFSET);
        game.font.draw(game.batch, "Score: " + board.getPoints(), SCORE_X_OFFSET, SCORE_Y_OFFSET);
        updateUnitsPosition(delta);
        drawUnits();
        game.batch.end();
        processInput();
        updateBoard();
    }

    private void updateUnitsPosition(float delta) {
        /*for (int c = 0; c < board.NUM_COLS; c++) {
            Array<Unit> col = board.getCol(c);
            for (int r = 0; r < board.NUM_ROWS; r++) {
                Unit unit = col.get(r);
                if (unit.isFalling()) {
                    unit.setRow(Math.max(unit.getRow() - delta * FALL_RATE, unit.getFinalRow()));
                }
            }
        }*/      
        /*Iterator<Unit> unitIterator = board.getFalling().iterator();
		while (unitIterator.hasNext()) {
			Unit unit = unitIterator.next();
			if (unit.isFalling()) {
                unit.setRow(Math.max(unit.getRow() - delta * FALL_RATE, unit.getFinalRow()));
            } else {
            	unitIterator.remove();
            	unitIterator.
            }
		}*/
		for (int i=0; i<board.getFalling().size; i++) {
			Unit unit = board.getFalling().get(i);
			if (unit.isFalling()) {
                unit.setRow(Math.max(unit.getRow() - delta * FALL_RATE, unit.getFinalRow()));
            } else {
            	board.getFalling().removeIndex(i);
            }
		}
    }

    private void updateBoard() {
        if (board.isStable()) {
            board.updateBoard();
        }
    }

    private void processInput() {
        if (Gdx.input.justTouched()) {
            float xPos = (Gdx.input.getX() - BOARD_X_OFFSET);
            float yPos = (game.HEIGHT - BOARD_Y_OFFSET - Gdx.input.getY());
            // lazy validation for positive numbers
            if (xPos >= 0 && yPos >= 0) {
                highlightAndSwapUnit((int)yPos / UNIT_HEIGHT, (int)xPos / UNIT_WIDTH);
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
            int unitType = unit.getType();
            unit.setType(selectedUnit.getType());
            selectedUnit.setType(unitType);
            unit.toggleSelected();
            selectedUnit.toggleSelected();
            isReadyToSwap = false;
            board.moveAttempt();
        } else {
            selectedUnit.toggleSelected();
            selectedUnit = unit;
        }
    }

    private void drawUnits() {
        for (int c = 0; c < board.NUM_COLS; c++) {
            Array<Unit> col = board.getCol(c);
            for (int r = 0; r < board.NUM_ROWS; r++) {
                Unit unit = col.get(r);
                if (unit.isSelected()) {
                    unitTexture = selectedTextures[unit.getType()];
                } else {
                    unitTexture = unselectedTextures[unit.getType()];
                }
                float xPos = c * unit.UNIT_WIDTH + BOARD_X_OFFSET;
                float yPos = unit.getRow() * unit.UNIT_LENGTH + BOARD_Y_OFFSET;
                if (yPos <= board.NUM_ROWS * unit.UNIT_LENGTH + BOARD_Y_OFFSET) {
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
