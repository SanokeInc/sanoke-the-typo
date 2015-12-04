package sanoke.qx.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import sanoke.qx.Sanoke;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Sanoke";
        config.width = 1000;
        config.height = 1000;
		new LwjglApplication(new Sanoke(), config);
	}
}
