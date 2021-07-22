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

import static com.ceashell.rpscatfight.Cat.CatState.dead;
import static com.ceashell.rpscatfight.RPSCatFight.FONT;


public class PlayState extends State {
    SpriteBatch sb;
    Texture background;
    ShapeRenderer sr;
    public String[] cats;
    int money;
    Rectangle cat1Rect;
    Rectangle cat2Rect;
    Animation catSprite;
    Texture catTexture;
    Texture healthBar1;
    Texture healthBar2;
    String catName1;
    String catName2;
    Cat cat1;
    Cat cat2;
    boolean changeCatAnimation;


    public PlayState(GameStateManager gsm) {
        super(gsm);
        sb = new SpriteBatch();
        cats = new String[] {"Paige", "Sabrina", "Cheesecake", "Marshmallow", "Tiger", "Chub Chub", "Elwood", "Coco"};
        background = new Texture("arena.png");
        money = 500;
        catName1 = getRandom(cats);
        catName2 = getRandom(cats);
        cat1 = new Cat(catName1, 100, Cat.CatState.alive);
        cat2 = new Cat(catName2, 100, Cat.CatState.alive);
        cat1Rect = new Rectangle(150, 250, 240, 126);
        cat2Rect = new Rectangle(850, 250, -240, 126);
        healthBar1 = new Texture("bar.png");
        healthBar2 = new Texture("bar.png");
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
        sb.draw(background, 0, 0, RPSCatFight.WIDTH, RPSCatFight.HEIGHT);
        FONT.drawMiddle(sb, "Fight kitties FIGHT!", 0,475, RPSCatFight.WIDTH, 100, 6, 6);
        sb.draw(cat1.pickAnimation(catName1).getFrame(), cat1Rect.x, cat1Rect.y, cat1Rect.width, cat1Rect.height);
        sb.draw(cat2.pickAnimation(catName2).getFrame(), cat2Rect.x, cat2Rect.y, cat2Rect.width, cat2Rect.height);
        sb.draw(healthBar1, 100, 470, cat1.health*2.5f, 10);
        sb.draw(healthBar2, 940, 470, cat2.health * -2.5f, 10);
        if (cat1.health <= 0) {
            FONT.drawMiddle(sb, catName1 + " is Dead! Victory goes to " + catName2, 0, 350, RPSCatFight.WIDTH, 50, 4, 4);
            cat1.catstate = Cat.CatState.dead;
            cat2.catstate = Cat.CatState.victorious;
        }
        if (cat2.health <= 0) {
            FONT.drawMiddle(sb, catName2 + " is Dead! Victory goes to " + catName1, 0, 350, RPSCatFight.WIDTH, 50, 4, 4);
            cat2.catstate = Cat.CatState.dead;
            cat1.catstate = Cat.CatState.victorious;
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
                cat1.health -= 5;
            }
            if(cat2Rect.contains(x,y)) {
                System.out.println("Ouch! Cat2");
                cat2.health -= 5;
            }
//            Gdx.app.debug("POSITION", "X touched: " + x + " Y touched: " + y);
            System.out.println("Click done " + x + " " + y);

        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            System.out.println("Ouch! Cat2");
            cat2.health -= 5;
            System.out.println(cat2Rect);
            System.out.println( Gdx.graphics.getDisplayMode().width);
            System.out.println( Gdx.graphics.getDisplayMode().height);
        }
    }

    @Override
    public void update(float deltaTime) {
        handleInput();
        cat1.pickAnimation(catName1).update(deltaTime);
        cat2.pickAnimation(catName2).update(deltaTime);
    }

    @Override
    public void dispose() {
        sb.dispose();
    }

}
