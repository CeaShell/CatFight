package com.ceashell.rpscatfight;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

import static com.ceashell.rpscatfight.Cat.CatState.dead;
import static com.ceashell.rpscatfight.Cat.CatState.victorious;
import static com.ceashell.rpscatfight.RPSCatFight.FONT;
import static com.ceashell.rpscatfight.RPSCatFight.whiteFont;


public class PlayState extends State {
    SpriteBatch sb;
    Texture background;
    ShapeRenderer sr;
    public String[] cats;
    int money;
    Rectangle cat1Rect;
    Rectangle cat2Rect;
    Texture healthBar;
    String catName1;
    String catName2;
    Cat cat1;
    Cat cat2;
    boolean finished;
    Texture rock;
    Texture paper;
    Texture scissors;
    Rectangle rockBounds;
    Rectangle paperBounds;
    Rectangle scissorBounds;
    boolean rpsTime;
    String[] RPS;
    String computerChoice;
    String yourChoice;
    int roundSection;
    String thingYouDid;



    public PlayState(GameStateManager gsm) {
        super(gsm);
        sb = new SpriteBatch();
        cats = new String[] {"Paige", "Sabrina", "Cheesecake", "Marshmallow", "Tiger", "Chub Chub", "Elwood", "Coco", "Mochi"};
        background = new Texture("arena.png");
        money = 500;
        catName1 = getRandomCat();
        catName2 = getRandomCat();
        cat1 = new Cat(catName1, 100);
        cat2 = new Cat(catName2, 100);
        cat1Rect = new Rectangle(150, 250, 240, 126);
        cat2Rect = new Rectangle(850, 250, 240, 126);
        healthBar = new Texture("bar.png");
        finished = false;
        rock = new Texture("rock.png");
        paper = new Texture("paper.png");
        scissors = new Texture("scissors.png");
        rpsTime = true;
        RPS = new String[] {"rock", "paper", "scissors"};
        roundSection = 1;
    }


    public String getRandomCat() {
        Random random = new Random();
        int index = random.nextInt(cats.length);
        System.out.println(cats[index]);
        return cats[index];
    }

    public String getRandomRPS() {
        Random random = new Random();
        int index = random.nextInt(RPS.length);
        System.out.println(RPS[index]);
        return RPS[index];
    }

    @Override
    public void render(SpriteBatch sb, ShapeRenderer sr) {
        sb.begin();
        if (cat1.catstate == victorious) {
            cat1Rect.height = 180;
            cat1Rect.width = 165;
        }
        if (cat2.catstate == victorious) {
            cat2Rect.height = 180;
            cat2Rect.width = 165;
        }
        if (computerChoice == null) {
            computerChoice = getRandomRPS();
        }
        sb.draw(background, 0, 0, RPSCatFight.WIDTH, RPSCatFight.HEIGHT);
        FONT.drawMiddle(sb, "Fight kitties FIGHT!", 0,475, RPSCatFight.WIDTH, 100, 6, 6);
        sb.draw(cat1.catSprite.getFrame(), cat1Rect.x, cat1Rect.y, cat1Rect.width, cat1Rect.height);
        sb.draw(cat2.catSprite.getFrame(), cat2Rect.x, cat2Rect.y, -cat2Rect.width, cat2Rect.height);
        sb.draw(healthBar, 100, 470, cat1.health*2.5f, 10);
        sb.draw(healthBar, 940, 470, cat2.health * -2.5f, 10);
        if (cat1.health <= 0 && !finished) {
            cat1.catstate = Cat.CatState.dead;
            cat2.catstate = Cat.CatState.victorious;
            cat1.pickAnimation(catName1);
            cat2.pickAnimation(catName2);
            finished = true;
        }
        if (cat2.health <= 0 && !finished) {
            cat2.catstate = Cat.CatState.dead;
            cat1.catstate = Cat.CatState.victorious;
            cat1.pickAnimation(catName1);
            cat2.pickAnimation(catName2);
            finished = true;
        }
        if (cat1.health <= 20 && cat1.health > 0 || cat2.health <= 20 && cat2.health > 0) {
            FONT.drawMiddle(sb, "FINISH HIM!", 0, 330, RPSCatFight.WIDTH, 30, 6, 6);
        }
        if(finished){
            if(cat1.catstate == dead){
                FONT.drawMiddle(sb, catName1 + " is Dead! Victory goes to " + catName2, 0, 350, RPSCatFight.WIDTH, 50, 3, 3);
            } else if(cat2.catstate == dead){
                FONT.drawMiddle(sb, catName2 + " is Dead! Victory goes to " + catName1, 0, 350, RPSCatFight.WIDTH, 50, 3, 3);
            }
        }
        sb.end();
        if (rpsTime == true) {
            drawRound(sb);
        }
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
            else if(cat2Rect.contains(x,y)) {
                System.out.println("Ouch! Cat2");
                cat2.health -= 5;
            }
            else if(rockBounds.contains(x,y)) {
                System.out.println("rock");
                yourChoice = "rock";
                //rpsTime = false;
                roundSection++;
            }
            else if(paperBounds.contains(x,y)) {
                System.out.println("paper");
                yourChoice = "paper";
                //rpsTime = false;
                roundSection++;
            }
            else if(scissorBounds.contains(x,y)) {
                System.out.println("scissors");
                yourChoice = "scissors";
                //rpsTime = false;
                roundSection++;
            }
            System.out.println("Click done " + x + " " + y);

        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            System.out.println("Ouch! Cat2");
            rpsTime = true;
            roundSection = 1;
            computerChoice = null;
            System.out.println( Gdx.graphics.getDisplayMode().width);
            System.out.println( Gdx.graphics.getDisplayMode().height);
        }
    }

    public void drawRound(SpriteBatch sb) {
        Texture bg = new Texture("bg1.png");
        sb.begin();
        if (roundSection == 1) {
            sb.setColor(1, 1, 1, 0.9f);
            sb.draw(bg, 0, 0, RPSCatFight.WIDTH, RPSCatFight.HEIGHT);
            sb.setColor(1, 1, 1, 1);
            whiteFont.drawMiddle(sb, "Rock paper or scissors?", 220, 400, 600, 30, 4, 4);
            String[] buttons = {"rock", "paper", "scissors"};
            int spacing = 30;
            int size = 120;
            int margin = (600 - 3 * size - 2 * spacing) / 2;
            for (int i = 0; i < 3; i++) {
                Texture button = new Texture(buttons[i] + ".png");
                int xCoord = 220 + margin + i * (spacing + size);
                Rectangle buttonBox = new Rectangle(xCoord, 220, size, size);
                sb.draw(button, buttonBox.x, buttonBox.y, buttonBox.width, buttonBox.height);
                if (i == 0) {
                    rockBounds = buttonBox;
                }
                if (i == 1) {
                    paperBounds = buttonBox;
                }
                if (i == 2) {
                    scissorBounds = buttonBox;
                }
            }
        }

        if (roundSection == 2) {
            sb.setColor(1, 1, 1, 0.9f);
            sb.draw(bg, 0, 0, RPSCatFight.WIDTH, RPSCatFight.HEIGHT);
            sb.setColor(1, 1, 1, 1);
            thingYouDid = checkWin();
            whiteFont.drawMiddle(sb, "You " + thingYouDid + "!", 0, 400, RPSCatFight.WIDTH, 40, 6, 6);
        }
        sb.end();
    }


    public String checkWin(){
        if (yourChoice == "rock") {
            if (computerChoice == "rock") {
                thingYouDid = "tie";
            }
            else if (computerChoice == "paper") {
                thingYouDid = "lose";
            }
            else if (computerChoice == "scissors") {
                thingYouDid = "win";
            }
        }
        if (yourChoice == "paper") {
            if (computerChoice == "rock") {
                thingYouDid = "win";
            }
            else if (computerChoice == "paper") {
                thingYouDid = "tie";
            }
            else if (computerChoice == "scissors") {
                thingYouDid = "lose";
            }
        }
        if (yourChoice == "scissors") {
            if (computerChoice == "rock") {
                thingYouDid = "lose";
            }
            else if (computerChoice == "paper") {
                thingYouDid = "win";
            }
            else if (computerChoice == "scissors") {
                thingYouDid = "tie";
            }
        }
        return thingYouDid;
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
