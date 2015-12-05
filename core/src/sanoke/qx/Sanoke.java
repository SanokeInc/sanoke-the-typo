package sanoke.qx;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Sanoke extends Game {
    public SpriteBatch spriteBatch;
    public BitmapFont font;
	
    public final int HEIGHT = 800;
    public final int WIDTH = 1000;
    
    public void create() {
        spriteBatch = new SpriteBatch();
        // default Arial
        font = new BitmapFont();
        //TODO main menu
        //this.setScreen(new MainMenuScreen(this));
    }

    public void render() {
        super.render();
    }

    public void dispose() {
        spriteBatch.dispose();
        font.dispose();
    }
}
