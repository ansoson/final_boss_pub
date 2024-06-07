package finalboss.screens;

import engine.UIKit.*;
import engine.sprite.SpriteResource;
import finalboss.FBChalkboard;
import finalboss.custom_components.main_character.PlayerControllerComponent;
import finalboss.custom_components.main_character.weapons.WeaponComponent;
import finalboss.custom_components.main_character.weapons.WeaponType;
import finalboss.custom_components.main_character.weapons.weapon_implementations.*;
import finalboss.gameworlds.LevelGameworld;
import finalboss.objects.Debuggton;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import finalboss.App;
import engine.hierarchy.GameWorld;
import engine.hierarchy.Screen;
import engine.support.Vec2d;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class CharacterView extends Screen {

    int cooldown;
    int invincibilityTime;
    List<UISprite> lives;
    int fullTime;

    long longMultiplier = (long) Math.pow(10, 8);
    public double time;
    public double maxTime;

    SpriteResource r;
    public Text timer;
    boolean debug;
    public Text giltText;
    public StatsBar bossHealth;
    public Text bossHealthBarText;
    public Rectangle upperUIBox;
    public Rectangle lowerUIBox;
    public UISprite mcSprite;
    public StatsBar mcHealth;

    private UISprite dashOne;
    private UISprite dashTwo;

    protected UISprite lefthand;
    protected UISprite righthand;
    Button debugButt;
    protected FBChalkboard fbChalkboard;

    Map<WeaponType, Integer> correlator;
    public PlayerControllerComponent MCCONTROLER;

    public CharacterView(String name, Vec2d defaultWindowSize, App parent, boolean active) {
        super(name, defaultWindowSize, parent, new PCViewport(new Vec2d(0,0), defaultWindowSize, new Vec2d(0,0), defaultWindowSize), active);
    }


    public void setUpUI(){
        this.correlate();
        this.invincibilityTime = 20; this.cooldown = 0;
        this.lives = new ArrayList<>();
        this.time = 0;
        this.maxTime = 1000;
        this.upperUIBox = new Rectangle(new Vec2d(0, 10),
            new Vec2d(windowSize.x * 3/8, 50), windowSize, Color.TRANSPARENT,
            HAlignment.CENTER, VAlignment.UPPER, true);

        this.lowerUIBox = new Rectangle(new Vec2d(0, -10),
            new Vec2d(windowSize.x, 75), windowSize, Color.BLACK,
            HAlignment.CENTER, VAlignment.LOWER, true);



        this.timer = new Text(Color.RED.darker(), new Vec2d(-20, 20),
            20, windowSize, "0:00", "Pixel",
            HAlignment.RIGHT, VAlignment.UPPER, true);
        this.giltText = new Text(Color.DEEPPINK, new Vec2d(10, 50),
            50, windowSize, "0", "Pixel");
        giltText.active = false;
        //ui.add(timer); TODO: do timing without timer


        this.bossHealthBarText = new Text(upperUIBox, Color.BLACK, new Vec2d(0, 0),
            20, windowSize, "FINAL BOSS", "Pixel",
            HAlignment.CENTER, VAlignment.UPPER, true);

        this.bossHealth = new StatsBar(upperUIBox,
            new Vec2d(0, 0), new Vec2d(windowSize.x/2, windowSize.y/32),
            windowSize, Color.RED.darker(), HAlignment.CENTER, VAlignment.LOWER, true, 75, 75);

        this.bossHealthBarText = new Text(upperUIBox, Color.BLACK, new Vec2d(0, 0),
            20, windowSize, "FINAL BOSS", "Pixel",
            HAlignment.CENTER, VAlignment.UPPER, true);

        this.mcSprite = new UISprite(lowerUIBox, new Vec2d(10, -10), new Vec2d(120, 120),
            windowSize, r, 10, 0, 0, 48, 48,
            HAlignment.LEFT, VAlignment.LOWER, true);

        this.mcHealth = new StatsBar(lowerUIBox,
            new Vec2d(150, -25), new Vec2d(windowSize.x/3, windowSize.y/24),
            windowSize, Color.RED.darker(), HAlignment.LEFT, VAlignment.LOWER, true, 60, 60);
        this.mcHealth.active = false;


        this.dashTwo = new UISprite(lowerUIBox, new Vec2d(50, -25),
                new Vec2d(50, 50), windowSize, r, 13, 0, 0, 32, 32,
                HAlignment.CENTER, VAlignment.LOWER, true);
        this.dashOne = new UISprite(lowerUIBox, new Vec2d(110, -10),
            new Vec2d(50, 50), windowSize, r, 13, 0, 0, 32, 32,
            HAlignment.CENTER, VAlignment.LOWER, true);
        ui.add(upperUIBox);
        ui.add(lowerUIBox);

//        this.debugButt = new Debuggton(this, "add", new Vec2d(230, 0), new Vec2d(50, 50), windowSize, Color.DARKGRAY);
//        ui.add(debugButt);
        this.fullTime = 0;
        this.debug = false;
        this.correlator = correlate();
    }

    public void TakeDamage(double numDamage){
        if(cooldown > 30){ //TODO: this is a playtestable thing for certain
            System.out.println("BOSS DID DAMAGE " + numDamage);
            mcHealth.updateStatsBar(numDamage);
            cooldown = 0;
            this.MCCONTROLER.damageTimer = 7;
        }

    }

    public void onStartup() throws IOException, ParserConfigurationException, TransformerException, InvocationTargetException, SAXException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        worlds = new ArrayList<>();
        for(GameWorld w : worlds){
            w.onStartup();
        }


    }

    public void postWeapons(WeaponComponent l, WeaponComponent rr){
        fbChalkboard.setLefthand(l);
        fbChalkboard.setRighthand(rr);
        //POST LEFT HAND
        if (lefthand == null){
            lefthand = new UISprite(lowerUIBox, new Vec2d(-150, -10), new Vec2d(80, 80), windowSize,
                r, correlator.get(l.getType()), 0, 0, 48, 48,
                HAlignment.RIGHT, VAlignment.LOWER, true);
                ui.add(lefthand);
        }
        else{
            lefthand.setSprite(correlator.get(l.getType()));

        }

        //POST RIGHT HAND
        if (righthand == null){
            righthand = new UISprite(lowerUIBox, new Vec2d(-40, -10), new Vec2d(100, 100), windowSize,
                r, correlator.get(rr.getType()), 0, 0, 48, 48,
                HAlignment.RIGHT, VAlignment.LOWER, true);
                ui.add(righthand);
        }
        else{
            righthand.setSprite(correlator.get(rr.getType()));
        }



    }
    public void showWeps(Boolean tf){
        this.righthand.setOn(tf);
        this.lefthand.setOn(tf);
    }
    public Map<WeaponType, Integer> correlate() {
        Map<WeaponType, Integer> cor = new HashMap<>();
        cor.put(WeaponType.NONE, 11);
        cor.put(WeaponType.WAND, 7);
        cor.put(WeaponType.WATERING_CAN, 8);
        cor.put(WeaponType.NET, 5);
        cor.put(WeaponType.SHIELD, 6);
        cor.put(WeaponType.ROPE, 4);
        cor.put(WeaponType.FISHING_POLE, 9);
        return cor;
    }


    public void Advance(){
        fullTime += (int) Math.floor((time));

        parent.screenOn += 1;
        parent.screens.get(parent.screenOn).setActive();
        this.MCCONTROLER = null;
        this.active = false;
    }

    public void Reset(){
        ui.clear();
        ui.add(upperUIBox);
        ui.add(lowerUIBox);
//        ui.add(new UISprite(new Vec2d(100,0), new Vec2d(50,50), windowSize, r, 2, 0,0, 32,32));
        ui.add(giltText);
        //ui.add(timer);


//        ui.add(debugButt);
//        ui.add(new Text(Color.RED,new Vec2d(230, 50), 100, windowSize,"+", "Pixel"));
        cooldown = 0;
        this.time = 0;
        this.fullTime = 0;
        this.bossHealth.ResetHealth();
        this.mcHealth.ResetHealth();
    }


    public void updateTimer(long nanosSincePreviousTick){
            this.time += (double) nanosSincePreviousTick/longMultiplier/10.0;
            int w = (int) Math.floor(maxTime-time);
            String s;
            if(w%60 > 9) {
                s = (w / 60) + ":" + w % 60;
            } else{
                s = (w/60) + ":0" + w%60;
            }
            timer.setText(s);
    }

    public void updateCoins(int giltNum){
        this.giltText.setText(String.valueOf(giltNum));
    }


    //OVERRIDES

    @Override
    public void onKeyPressed(KeyEvent e) {
        if(e.getCode() == KeyCode.L){
            System.out.println("CURRENT WEAPONS : " + this.lefthand.sprite + " " + this.righthand.sprite);
            debug = true;
            App a = (App) this.parent;
            a.winView.debugText.setOn(true);
        }
        for(GameWorld o: worlds){
            o.onKeyPressed(e);
        }
    }

    public void dashCheck(){
        dashOne.setOn(MCCONTROLER.dashTimer < -30);
        dashTwo.setOn(MCCONTROLER.dashTimer2 < -30);
    }


    /**
     * Called periodically and used to update the state of your game.
     * @param nanosSincePreviousTick	approximate number of nanoseconds since the previous call
     */
    @Override
    public void onTick(long nanosSincePreviousTick) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IOException, TransformerException {
        if (active){
            for(GameWorld o: worlds){
                o.onTick(nanosSincePreviousTick);
            }
            updateTimer(nanosSincePreviousTick);
            cooldown += 1;
            dashCheck();
        }

        if (parent.screenOn == 1){
            upperUIBox.active = false;
            mcHealth.active = false;
        }

        else{
            upperUIBox.active = true;
            mcHealth.active = true;

        }

        //if screen is not active, only tick sound system
        if (!active){
            for (GameWorld o : worlds) {
                if (o.getSystem("SS") != null){
                    o.getSystem("SS").onTick(nanosSincePreviousTick);
                }
            }
        }

//        if (fbChalkboard.)
    }
    public void setChalkboard(FBChalkboard chalkboard) {
        this.fbChalkboard = chalkboard;
        this.r = fbChalkboard.getSprites();
    }

    public int getCooldown() {
        return cooldown;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    public void setMCCONTROLER(PlayerControllerComponent p){
        this.MCCONTROLER = p;
    }

}
