package sanoke.qx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;

public class GameScreen implements Screen {
    final Sanoke game;
    
    OrthographicCamera camera;
    
    private Texture redImage;
    private Texture orangeImage;
    private Texture blueImage;
    private Texture greenImage;
    private Texture background;
    private Texture unitTexture;
    private Texture[] textures;
    public Music music;
    
    private Board board;
    public GameScreen(final Sanoke game) {
        background = new Texture(Gdx.files.internal("bamboo.jpg"));
        this.game = game;
        redImage = new Texture(Gdx.files.internal("red.png"));
        orangeImage = new Texture(Gdx.files.internal("orange.png"));
        blueImage = new Texture(Gdx.files.internal("blue.png"));
        greenImage = new Texture(Gdx.files.internal("green.png"));
        textures = new Texture[]{redImage, orangeImage, blueImage, greenImage};
        music = Gdx.audio.newMusic(Gdx.files.internal("skypirate.mp3"));
        music.setLooping(true);
        music.setVolume(0.5f);
        music.play();
        board = new Board();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.HEIGHT, game.WIDTH);
        

    }
    
    @Override
    public void show() {
        // TODO Auto-generated method stub

    }

    @Override
    public void render(float delta) {
        game.batch.begin();
        game.batch.draw(background, 0, 0);
        drawUnits();
        game.batch.end();

    }

    private void drawUnits() {
        Array<Unit> col = board.getCol(0);
        for (int i = 0; i < 1; i++) {
            Unit unit = col.get(i);
            unitTexture = textures[unit.getType()];
            game.batch.draw(unitTexture, 0, 0);
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
