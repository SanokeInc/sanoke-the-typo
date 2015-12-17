package sanoke.qx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
    public static final int UNIT_LENGTH = 64;
    public static final int UNIT_WIDTH = 64;

    public static Texture background;
    public static Texture boardImage;
    public static Texture gameOver;

    private static Texture unitsMap;

    private static TextureRegion blankImage;
    private static TextureRegion redImage;
    private static TextureRegion orangeImage;
    private static TextureRegion yellowImage;
    private static TextureRegion blueImage;
    private static TextureRegion greenImage;
    private static TextureRegion purpleImage;
    public static TextureRegion[] unselectedTextures;

    private static TextureRegion redSelectImage;
    private static TextureRegion orangeSelectImage;
    private static TextureRegion yellowSelectImage;
    private static TextureRegion blueSelectImage;
    private static TextureRegion greenSelectImage;
    private static TextureRegion purpleSelectImage;
    public static TextureRegion[] selectedTextures;

    private static Music music;
    private static Sound clearSound;

    public static void loadAssets() {
        background = new Texture(Gdx.files.internal("morelegalphoto.jpg"));
        boardImage = new Texture(Gdx.files.internal("board.png"));
        gameOver = new Texture(Gdx.files.internal("gameover.png"));
        unitsMap = new Texture(Gdx.files.internal("units.png"));
        blankImage = new TextureRegion(unitsMap, 0, 0, UNIT_WIDTH, UNIT_LENGTH);
        redImage = new TextureRegion(unitsMap, UNIT_WIDTH * 1, 0, UNIT_WIDTH,
                UNIT_LENGTH);
        orangeImage = new TextureRegion(unitsMap, UNIT_WIDTH * 2, 0,
                UNIT_WIDTH, UNIT_LENGTH);
        yellowImage = new TextureRegion(unitsMap, UNIT_WIDTH * 3, 0,
                UNIT_WIDTH, UNIT_LENGTH);
        greenImage = new TextureRegion(unitsMap, UNIT_WIDTH * 4, 0, UNIT_WIDTH,
                UNIT_LENGTH);
        blueImage = new TextureRegion(unitsMap, UNIT_WIDTH * 5, 0, UNIT_WIDTH,
                UNIT_LENGTH);
        purpleImage = new TextureRegion(unitsMap, UNIT_WIDTH * 6, 0,
                UNIT_WIDTH, UNIT_LENGTH);
        unselectedTextures = new TextureRegion[] { blankImage, redImage,
                orangeImage, yellowImage, greenImage, blueImage, purpleImage };
        redSelectImage = new TextureRegion(unitsMap, UNIT_WIDTH * 1,
                UNIT_LENGTH, UNIT_WIDTH, UNIT_LENGTH);
        orangeSelectImage = new TextureRegion(unitsMap, UNIT_WIDTH * 2,
                UNIT_LENGTH, UNIT_WIDTH, UNIT_LENGTH);
        yellowSelectImage = new TextureRegion(unitsMap, UNIT_WIDTH * 3,
                UNIT_LENGTH, UNIT_WIDTH, UNIT_LENGTH);
        greenSelectImage = new TextureRegion(unitsMap, UNIT_WIDTH * 4,
                UNIT_LENGTH, UNIT_WIDTH, UNIT_LENGTH);
        blueSelectImage = new TextureRegion(unitsMap, UNIT_WIDTH * 5,
                UNIT_LENGTH, UNIT_WIDTH, UNIT_LENGTH);
        purpleSelectImage = new TextureRegion(unitsMap, UNIT_WIDTH * 6,
                UNIT_LENGTH, UNIT_WIDTH, UNIT_LENGTH);
        selectedTextures = new TextureRegion[] { blankImage, redSelectImage,
                orangeSelectImage, yellowSelectImage, greenSelectImage,
                blueSelectImage, purpleSelectImage };
        music = Gdx.audio.newMusic(Gdx.files.internal("morelegalmusic.mp3"));
        music.setLooping(true);
        music.setVolume(0.5f);
        clearSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
    }

    public static void playMusic() {
        music.play();
    }

    public static void playSound() {
        clearSound.play();
    }
}
