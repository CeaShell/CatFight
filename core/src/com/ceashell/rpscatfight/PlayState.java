package com.ceashell.rpscatfight;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
//import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.GdxRuntimeException;
//import com.ceashell.rpscatfight.Animation;

import java.util.Random;
import static com.ceashell.rpscatfight.RPSCatFight.FONT;


public class PlayState extends State {
    SpriteBatch sb;
    Texture background;
    ShapeRenderer sr;
    public String[] cats;
    int money;
    Animation cat1;
    Animation cat2;
    Rectangle cat1Rect;
    Rectangle cat2Rect;
    Animation catSprite;
    Texture catTexture;
    int catHealth1;
    int catHealth2;
    Texture healthBar1;
    Texture healthBar2;
    String catName1;
    String catName2;
    boolean cat1Life;
    boolean cat2Life;


    public PlayState(GameStateManager gsm) {
        super(gsm);
        sb = new SpriteBatch();
        cats = new String[] {"Paige", "Sabrina", "Cheesecake", "Marshmallow", "Tiger", "Chub Chub", "Elwood", "Coco"};
        background = new Texture("arena.png");
        money = 500;
        catName1 = getRandom(cats);
        catName2 = getRandom(cats);
        cat1 = cat_walk(catName1);
        cat2 = cat_walk(catName2);
        cat1Rect = new Rectangle(150, 250, 240, 126);
        cat2Rect = new Rectangle(850, 250, -240, 126);
        catHealth1 = 100;
        catHealth2 = 100;
        healthBar1 = new Texture("bar.png");
        healthBar2 = new Texture("bar.png");
        cat1Life = true;
        cat2Life = true;
    }

    public Animation cat_ded(String name) {
        try {
            catTexture = new Texture(name + "_shleep.png");
        }
        catch(GdxRuntimeException e) {
            catTexture = new Texture("paige_shleep.png");
        }
        TextureRegion catSrc = new TextureRegion(catTexture, 160, 42);
        catSprite = new Animation(catSrc, 2, 1.5f);
        return catSprite;
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

    public String getRandom(String[] array) {
        Random random = new Random();
        int index = random.nextInt(cats.length);
        System.out.println(cats[index]);
        return cats[index];
    }

    @Override
    public void render(SpriteBatch sb, ShapeRenderer sr) {
        sb.begin();
        //sb.draw(img, boundRect.x, boundRect.y, boundRect.width, boundRect.height);
        sb.draw(background, 0, 0, RPSCatFight.WIDTH, RPSCatFight.HEIGHT);
        FONT.drawMiddle(sb, "Fight kitties FIGHT!", 0,475, RPSCatFight.WIDTH, 100, 6, 6);
        sb.draw(cat1.getFrame(), cat1Rect.x, cat1Rect.y, cat1Rect.width, cat1Rect.height);
        sb.draw(cat2.getFrame(), cat2Rect.x, cat2Rect.y, cat2Rect.width, cat2Rect.height);
        sb.draw(healthBar1, 100, 470, catHealth1*2.5f, 10);
        sb.draw(healthBar2, 940, 470, catHealth2 * -2.5f, 10);
        if (catHealth1 <= 0) {
            FONT.drawMiddle(sb, catName1 + " is Dead! Victory goes to " + catName2, 0, 350, RPSCatFight.WIDTH, 50, 4, 4);
            cat1 = cat_ded(catName1);
            cat1Life = false;
        }
        if (catHealth2 <= 0) {
            FONT.drawMiddle(sb, catName2 + " is Dead! Victory goes to " + catName1, 0, 350, RPSCatFight.WIDTH, 50, 4, 4);
            cat2 = cat_ded(catName2);
            cat2Life = false;
        }
        sb.end();
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
            float x = Gdx.input.getX();
            float y = RPSCatFight.HEIGHT - Gdx.input.getY();

            if (cat1Rect.contains(x, y)) {
                System.out.println("Ouch! Cat1");
                catHealth1 -= 5;
            }
            if(cat2Rect.contains(x,y)) {
                System.out.println("Ouch! Cat2");
                catHealth2 -= 5;
            }
//            Gdx.app.debug("POSITION", "X touched: " + x + " Y touched: " + y);
            System.out.println("Click done " + x + " " + y);

        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            System.out.println("Ouch! Cat2");
            catHealth2 -= 5;
            System.out.println(cat2Rect);
            System.out.println( Gdx.graphics.getDisplayMode().width);
            System.out.println( Gdx.graphics.getDisplayMode().height);
        }
    }

    @Override
    public void update(float deltaTime) {
        handleInput();
        cat1.update(deltaTime);
        cat2.update(deltaTime);
    }

    @Override
    public void dispose() {
        sb.dispose();
    }

}
