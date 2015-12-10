package sanoke.qx.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import sanoke.qx.Sanoke;

public class DesktopLauncher {
	public static final String APP_TITLE = "Sanoke";
	public static final int APP_WIDTH = 1000;
	public static final int APP_HEIGHT = 800;
	
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = APP_TITLE;
        config.width = APP_WIDTH;
        config.height = APP_HEIGHT;
		new LwjglApplication(new Sanoke(), config);
	}
}
