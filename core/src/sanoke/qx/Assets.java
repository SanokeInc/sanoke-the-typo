package sanoke.qx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
    public static final int UNIT_HEIGHT = 64;
    public static final int UNIT_WIDTH = 64;
    
    public static Texture background;
    public static Texture boardImage;
    
    private static Texture unitsMap;
    
    private static TextureRegion blankImage;
    private static TextureRegion redImage;
    private static TextureRegion orangeImage;
    private static TextureRegion blueImage;
    private static TextureRegion greenImage;
    private static TextureRegion purpleImage;
    public static TextureRegion[] unselectedTextures;
    
    private static TextureRegion redSelectImage;
    private static TextureRegion orangeSelectImage;
    private static TextureRegion blueSelectImage;
    private static TextureRegion greenSelectImage;
    private static TextureRegion purpleSelectImage;
    
    public static TextureRegion[] selectedTextures;
    
    private static Music music;
    private static Sound clearSound;
    
    public static void loadAssets() {
        background = new Texture(Gdx.files.internal("morelegalphoto.jpg"));
        boardImage = new Texture(Gdx.files.internal("board.png"));
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
        clearSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
    }
    
    public static void playMusic() {
        music.play();
    }
    
    public static void playSound() {
        clearSound.play();
    }
}
