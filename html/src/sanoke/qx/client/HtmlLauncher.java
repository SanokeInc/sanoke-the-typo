package sanoke.qx.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import sanoke.qx.Sanoke;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                return new GwtApplicationConfiguration(1000, 800);
        }

        @Override
        public ApplicationListener getApplicationListener () {
                return new Sanoke();
        }
}