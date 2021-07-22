package com.ceashell.rpscatfight;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

import java.awt.*;

public class RPSCatFight extends ApplicationAdapter {
	SpriteBatch batch;
	ShapeRenderer sr;
	GameStateManager gsm;
	public static int WIDTH = 1040;
	public static int HEIGHT = 585;
	public static Font FONT;

	@Override
	public void create () {
		batch = new SpriteBatch();
		gsm = new GameStateManager();
		sr = new ShapeRenderer();
		gsm.push(new PlayState(gsm));
		FONT = new Font();
	}

	@Override
	public void resize(int width, int height) {
//		super.resize(width, height);
		WIDTH = width;
		HEIGHT = height;
		System.out.println(width + " " + height);
	}

	@Override
	public void render () {
		ScreenUtils.clear(0.9f, 0.9f, 1f, 1);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch, sr);
	}

	@Override
	public void dispose () {
		batch.dispose();
		FONT.dispose();
	}
}