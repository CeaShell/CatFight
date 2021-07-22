package com.ceashell.rpscatfight;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class Cat {
    String name;
    boolean alive;
    int health;
    Texture catTexture;
    Animation catSprite;

    public Cat(String name, boolean alive, int health) {
        this.name = name;
        this.alive = alive;
        this.health = health;
        if (alive) {
            cat_walk(name);
        }
    }


    public void render() {
        catSprite.getFrame();
    }

    public Animation cat_walk(String name) {
        try {
            catTexture = new Texture(name + "_walk.png");
        } catch(GdxRuntimeException e) {
            catTexture = new Texture("paige_walk.png");
        }
        TextureRegion catSrc = new TextureRegion(catTexture, 400, 42);
        catSprite = new Animation(catSrc, 5, 0.8f);
        return catSprite;
    }

    public void update() {

    }


}
