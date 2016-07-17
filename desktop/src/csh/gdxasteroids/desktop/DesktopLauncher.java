package csh.gdxasteroids.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import csh.gdxasteroids.GDXAsteroids;

public class DesktopLauncher
{
	public static void main (String[] arg)
	{
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = GDXAsteroids.TARGET_RES_WIDTH;
		config.height = GDXAsteroids.TARGET_RES_HEIGHT;
		config.resizable = false;
		
		new LwjglApplication(new GDXAsteroids(), config);
	}
}