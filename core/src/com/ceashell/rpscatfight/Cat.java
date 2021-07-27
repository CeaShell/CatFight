package com.ceashell.rpscatfight;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.GdxRuntimeException;

import static com.ceashell.rpscatfight.Cat.CatState.attacking;
import static com.ceashell.rpscatfight.Cat.CatState.victorious;

public class Cat {
    String name;
    int health;
    Texture catTexture;
    Animation catSprite;
    CatState catstate;
    Rectangle bounds;
    boolean changeAnimation;

    public Cat(String name, int health, Rectangle rectangle) {
        this.name = name;
        this.catstate = CatState.alive;
        this.health = health;
        this.bounds = rectangle;
        changeAnimation = true;
        this.catSprite = pickAnimation();
    }

    public enum CatState {
        alive,
        attacking,
        dead,
        victorious,
    }

    public Animation pickAnimation() {
        if(changeAnimation) {
            System.out.println("Changing animation");
            if (catstate == CatState.alive) {
                bounds.width = 240;
                bounds.height = 126;
                try {
                    catTexture = new Texture(this.name + "_idle1.png");
                } catch (GdxRuntimeException e) {
                    catTexture = new Texture("paige_idle1.png");
                }
                TextureRegion catSrc = new TextureRegion(catTexture, 160, 42);
                catSprite = new Animation(catSrc, 2, 1f);
                return catSprite;
            }

            if (catstate == CatState.dead) {
                bounds.width = 240;
                bounds.height = 126;
                try {
                    catTexture = new Texture(this.name + "_shleep.png");
                } catch (GdxRuntimeException e) {
                    catTexture = new Texture("paige_shleep.png");
                }
                TextureRegion catSrc = new TextureRegion(catTexture, 160, 42);
                catSprite = new Animation(catSrc, 2, 1.5f);
            }

            if (catstate == CatState.victorious) {
                bounds.width = 180;
                bounds.height = 165;
                try {
                    catTexture = new Texture(this.name + "_idle.png");
                } catch (GdxRuntimeException e) {
                    catTexture = new Texture("paige_idle.png");
                }
                TextureRegion catSrc = new TextureRegion(catTexture, 770, 60);
                catSprite = new Animation(catSrc, 14, 1.5f);
            }

            if (catstate == CatState.attacking) {
                bounds.width = 180;
                bounds.height = 180;
                try {
                    catTexture = new Texture(this.name + "_claw.png");
                } catch (GdxRuntimeException e) {
                    catTexture = new Texture("paige_claw.png");
                }
                TextureRegion catSrc = new TextureRegion(catTexture, 360, 60);
                catSprite = new Animation(catSrc, 6, 1.2f);
            }
        }
        changeAnimation = false;
        return catSprite;
    }

    public void update(float deltaTime) {
        pickAnimation();
        catSprite.update(deltaTime);
    }

    public Rectangle getBounds() {
        return bounds;
    }

}
