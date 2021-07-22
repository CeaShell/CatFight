package com.ceashell.rpscatfight;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class Cat {
    String name;
    int health;
    Texture catTexture;
    Animation catSprite;
    CatState catstate;

    public Cat(String name, int health, CatState catstate) {
        this.name = name;
        this.catstate = CatState.alive;
        this.health = health;
    }

    public enum CatState {
        alive,
        attacking,
        dead,
        victorious,
    }

    public void render() {
        catSprite.getFrame();
    }



    public Animation pickAnimation(String name) {
        if(catstate == CatState.alive) {
            try {
                catTexture = new Texture(name + "_walk.png");
            } catch(GdxRuntimeException e) {
                catTexture = new Texture("paige_walk.png");
            }
            TextureRegion catSrc = new TextureRegion(catTexture, 400, 42);
            catSprite = new Animation(catSrc, 5, 0.8f);
            return catSprite;
        }

        if(catstate == CatState.dead) {
            try {
                catTexture = new Texture(name + "_shleep.png");
            } catch(GdxRuntimeException e) {
                catTexture = new Texture("paige_shleep.png");
            }
            TextureRegion catSrc = new TextureRegion(catTexture, 160, 42);
            catSprite = new Animation(catSrc, 2, 1.5f);
        }

        if(catstate == CatState.victorious) {
            try {
                catTexture = new Texture(name + "_idle.png");
            } catch(GdxRuntimeException e) {
                catTexture = new Texture("mochi_idle.png");
            }
            TextureRegion catSrc = new TextureRegion(catTexture, 770, 60);
            catSprite = new Animation(catSrc, 14, 1.5f);
        }
        return catSprite;
    }

    public void update(float deltaTime) {
        catSprite.update(deltaTime);
    }


}
