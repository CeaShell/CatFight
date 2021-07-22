package com.ceashell.rpscatfight.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.ceashell.rpscatfight.RPSCatFight;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = RPSCatFight.WIDTH;
		config.height = RPSCatFight.HEIGHT;
		new LwjglApplication(new RPSCatFight(), config);
	}
}
