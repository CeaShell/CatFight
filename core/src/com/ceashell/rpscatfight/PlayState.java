package com.ceashell.rpscatfight;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

import static com.ceashell.rpscatfight.Cat.CatState.*;
import static com.ceashell.rpscatfight.RPSCatFight.FONT;
import static com.ceashell.rpscatfight.RPSCatFight.whiteFont;

public class PlayState extends State {
    SpriteBatch sb;
    Texture background;
    Texture arena;
    ShapeRenderer sr;
    public String[] cats;
    int money;
    Texture healthBar;
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
    int r;
    int g;
    int b;
    int[] color;
    int colorCount;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        sb = new SpriteBatch();
        cats = new String[] {"Paige", "Tiger", "Elwood", "Coco", "Mochi"}; //sabrina, cheesecake, marshmallow, chub chub
        background = new Texture("arena_bg.png");
        arena = new Texture("arena.png");
        money = 500;

        cat1 = new Cat(getRandomCat(), 100, new Rectangle(200, 250, 240, 126));
        cat2 = new Cat(getRandomCat(), 100, new Rectangle(850, 250, 240, 126));
        healthBar = new Texture("bar.png");
        finished = false;
        rock = new Texture("rock.png");
        paper = new Texture("paper.png");
        scissors = new Texture("scissors.png");
        rpsTime = true;
        RPS = new String[] {"rock", "paper", "scissors"};
        roundSection = 1;
        colorCount = 0;
        randomTint();
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

    public int[] randomTint() {
        Random random = new Random();
        r = random.nextInt(2);
        g = random.nextInt(2);
        b = random.nextInt(2);
        color = new int[] {r, g, b};
        return color;
    }

    @Override
    public void render(SpriteBatch sb, ShapeRenderer sr) {
        sb.begin();
        sb.setColor(color[0], color[1], color[2], 1);
        sb.draw(background, 0, 0, RPSCatFight.WIDTH, RPSCatFight.HEIGHT);
        sb.setColor(1,1,1,0.9f);
        sb.draw(background, 0, 0, RPSCatFight.WIDTH, RPSCatFight.HEIGHT);
        sb.setColor(1,1,1,1);
        sb.draw(arena, 0, 0, RPSCatFight.WIDTH, RPSCatFight.HEIGHT);
        whiteFont.drawMiddle(sb, "Fight kitties FIGHT!", 0,475, RPSCatFight.WIDTH, 100, 6, 6);
        sb.draw(cat1.catSprite.getFrame(), cat1.bounds.x, cat1.bounds.y, cat1.bounds.width, cat1.bounds.height);
        sb.draw(cat2.catSprite.getFrame(), cat2.bounds.x, cat2.bounds.y, -cat2.bounds.width, cat2.bounds.height);
        sb.draw(healthBar, 100, 470, cat1.health*2.5f, 10);
        sb.draw(healthBar, 940, 470, cat2.health * -2.5f, 10);


        if (cat1.health <= 20 && cat1.health > 0 || cat2.health <= 20 && cat2.health > 0) {
            FONT.drawMiddle(sb, "FINISH HIM!", 0, 330, RPSCatFight.WIDTH, 30, 6, 6);
        }
        if(finished){
            if(cat1.catstate == dead){
                FONT.drawMiddle(sb, cat1.name + " is Dead! Victory goes to " + cat2.name, 0, 350, RPSCatFight.WIDTH, 50, 3, 3);
            } else if(cat2.catstate == dead){
                FONT.drawMiddle(sb, cat2.name + " is Dead! Victory goes to " + cat1.name, 0, 350, RPSCatFight.WIDTH, 50, 3, 3);
            }
        }
        FONT.draw(sb, "Round section " + roundSection, new Rectangle(100,100,100,100), 2, 2);
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
            if (!rpsTime || roundSection == 2) {
                if (cat1.bounds.contains(x, y)) {
                    System.out.println("Ouch! Cat1");
                    cat1.health -= 5;
                } else if (cat2.bounds.contains(x, y)) {
                    System.out.println("Ouch! Cat2");
                    cat2.health -= 5;
                }
            }
            else {
                if (roundSection == 1) {
                    if (rockBounds.contains(x, y)) {
                        System.out.println("rock");
                        yourChoice = "rock";
                        //rpsTime = false;
                        roundSection++;
                    } else if (paperBounds.contains(x, y)) {
                        System.out.println("paper");
                        yourChoice = "paper";
                        //rpsTime = false;
                        roundSection++;
                    } else if (scissorBounds.contains(x, y)) {
                        System.out.println("scissors");
                        yourChoice = "scissors";
                        //rpsTime = false;
                        roundSection++;
                    }
                }
                if (roundSection == 2) {
                    thingYouDid = checkWin();
                    switch (thingYouDid) {
                        case "win":
                            cat1.catstate = attacking;
                            cat2.catstate = alive;
                            cat1.changeAnimation = true;
                            cat2.changeAnimation = true;
                            break;
                        case "lose":
                            cat2.catstate = attacking;
                            cat1.catstate = alive;
                            cat2.changeAnimation = true;
                            cat1.changeAnimation = true;
                            break;
                        case "tie":
                            cat2.catstate = attacking;
                            cat1.catstate = attacking;
                            cat1.changeAnimation = true;
                            cat2.changeAnimation = true;
                            break;
                    }
                }
//                    Rectangle screen = new Rectangle(0,0, RPSCatFight.WIDTH, RPSCatFight.HEIGHT);
//                    if (screen.contains(x,y)) {
//                        rpsTime = false;
//                    }
//                }
            }
            //System.out.println("Click done " + x + " " + y);

        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            rpsTime = !rpsTime;
            roundSection = 1;
            computerChoice = null;
        }
    }

    public void drawRound(SpriteBatch sb) {
        Texture bg = new Texture("bg1.png");
        sb.begin();
        if (roundSection == 1) {
            sb.setColor(1, 1, 1, 0.9f);
            sb.draw(bg, 0, 0, RPSCatFight.WIDTH, RPSCatFight.HEIGHT);
            sb.setColor(1, 1, 1, 1);
            whiteFont.drawMiddle(sb, "Rock, paper, or scissors?", 220, 400, 600, 30, 4, 4);
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
//            sb.setColor(1, 1, 1, 0.9f);
//            sb.draw(bg, 0, 0, RPSCatFight.WIDTH, RPSCatFight.HEIGHT);
//            sb.setColor(1, 1, 1, 1);
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
        cat1.update(deltaTime);
        cat2.update(deltaTime);
        handleInput();
        if (computerChoice == null) {
            computerChoice = getRandomRPS();
        }

        if (colorCount <= 10) {
            colorCount++;
            if (colorCount == 10) {
                colorCount = 0;
                randomTint();
            }
        }

        if (cat1.health <= 0 && !finished) {
            cat1.catstate = Cat.CatState.dead;
            cat2.catstate = Cat.CatState.victorious;
            finished = true;
        }
        if (cat2.health <= 0 && !finished) {
            cat2.catstate = Cat.CatState.dead;
            cat1.catstate = Cat.CatState.victorious;
            finished = true;
        }
    }

    @Override
    public void dispose() {
        sb.dispose();
    }

}
