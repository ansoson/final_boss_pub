package finalboss.gameworlds;

import engine.Direction;
import engine.components.CenterComponent;
import engine.components.SpriteComponent;
import engine.components.TrackingComponent;
import engine.hierarchy.GameObject;
import engine.hierarchy.GameSystem;
import engine.hierarchy.GameWorld;
import engine.shapes.ShapeEnum;
import engine.sprite.SpriteResource;
import engine.support.Vec2d;
import engine.systems.*;
import finalboss.FBChalkboard;
import finalboss.custom_components.main_character.MainCharacterAnimationComponent;
import finalboss.custom_components.main_character.MainCharacterCollision;
import finalboss.custom_components.main_character.PlayerControllerComponent;
import finalboss.custom_components.main_character.weapons.WeaponComponent;
import finalboss.custom_components.main_character.weapons.weapon_implementations.FishingPole;
import finalboss.custom_components.main_character.weapons.weapon_implementations.WateringCan;
import finalboss.screens.CharacterView;
import finalboss.screens.PCViewport;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public abstract class LevelGameworld extends GameWorld {
    public Vec2d windowSize;
    public GameObject playerObject;
    public PlayerControllerComponent controller;
    public boolean advance;
    public CharacterView pparent;

    protected PCViewport viewport;
    public int num_enemies;
    public int sprite_size;
    Vec2d start_position;

    public SpriteResource r;
    public int goldNum;
    WeaponComponent lefthand;
    WeaponComponent righthand;

    public List<List<GameObject>> grid;
    protected FBChalkboard fbChalkboard;

    boolean ticked;

    public LevelGameworld(String name, CharacterView parent, Vec2d defaultWindowSize) {
        super(name);
        this.parent = parent;
        this.pparent = parent;
        this.windowSize = defaultWindowSize;
        this.viewport = (PCViewport) parent.viewport;
        this.sprite_size = 50;
        this.start_position = new Vec2d(4,4.5);
        this.r = getSpriteResource();
        this.playerObject = new GameObject(this, "player", new Vec2d(start_position.x*sprite_size, start_position.y*sprite_size), new Vec2d(sprite_size, sprite_size), windowSize, 3.0);
        this.goldNum =0;
        this.ticked = false;
    }

    public void gameReset(){
        for(List<GameObject> g : grid){
            for(GameObject h : g){
                throwAway(h);
            }
        }
        CharacterView m = pparent;
        m.Reset(); //reset health bars in here :D
        this.playerObject = fbChalkboard.getPlayer();
        this.playerObject.parent = this;
        this.lefthand = new WeaponComponent(this.playerObject, this.controller, fbChalkboard);
        this.righthand = new WeaponComponent(this.playerObject, this.controller, fbChalkboard);
        m.postWeapons(lefthand, righthand);
        this.controller.setLefthand(new WeaponComponent(this.playerObject, this.controller, fbChalkboard));
        this.controller.setRighthand(new WeaponComponent(this.playerObject, this.controller, fbChalkboard));
        controller.resetMovementqueue();
        this.pparent.setMCCONTROLER(controller);
        this.pparent.giltText.setText(Integer.toString(fbChalkboard.getMoney()));
        levelParse();
        playerObject.setTransform(new Vec2d(start_position.x*sprite_size, start_position.y*sprite_size));
    }

    public void levelParse(){System.out.println("should be using the override.");}

    @Override
    public void onStartup() {
        int sprite_size = 50;
        GraphicsSystem g = new GraphicsSystem("GS", windowSize, this);
        systems.add(g);
        TimerSystem t = new TimerSystem("TS", windowSize, this);
        systems.add(t);
        InputSystem i = new InputSystem("IS", windowSize, this);
        systems.add(i);
        CollisionSystem cc = new CollisionSystem("CS", windowSize, this);
        systems.add(cc);
        PathfindingSystem p = new PathfindingSystem("PS", windowSize, this, 25, 25);
        systems.add(p);
        SpriteComponent ss = new SpriteComponent(this.playerObject, r, 2, 4, 0,32, 32);
        this.playerObject.addComponent(ss);
        this.playerObject.addComponent(new MainCharacterCollision(this.playerObject, ShapeEnum.AAB, 1, (CharacterView) parent));
        this.playerObject.addComponent((new CenterComponent(this.playerObject, viewport)));
        MainCharacterAnimationComponent mc = new MainCharacterAnimationComponent(this.playerObject, ss, 3);
        this.playerObject.addComponent(mc);
        this.playerObject.addComponent(controller);
        this.pparent.setMCCONTROLER(controller);
        this.playerObject.addComponent(new TrackingComponent(this.playerObject, sprite_size));
        this.playerObject.callSystems();
        cc.addCollisionLayer(1, 2);
        cc.addCollisionLayer(2, 3);
    }

      abstract SpriteResource getSpriteResource();


    public void levelAdvance(){
        for(List<GameObject> g : grid){
            for(GameObject h : g){
                throwAway(h);
            }
        }
        grid.clear();
        CharacterView pp = this.pparent;
        pp.Advance();
        this.ticked = false;
    }

    public FBChalkboard getFbChalkboard() {
        return fbChalkboard;
    }

    public void setChalkboard(FBChalkboard chalkboard) throws ParserConfigurationException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        this.fbChalkboard = chalkboard;
    }


    @Override
    public void onTick(long nanosSincePreviousTick) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IOException, TransformerException {
        for(GameObject o: trash){
            removeGameObject(o);
        }
        trash = new ArrayList<>();
        //CharacterView m = (CharacterView) this.parent;
        //m.updateCoins(goldNum);
        if(advance){
            advance = false;
            levelAdvance();
        }
        for(GameSystem o: systems){
            o.onTick(nanosSincePreviousTick);
        }


    }
    @Override
    protected void onLateTick() throws InvocationTargetException, NoSuchMethodException, InstantiationException,
                    IllegalAccessException {
            super.onLateTick();
            try {
                    if (!ticked) {
                            this.onTick(0);
                            this.ticked = true;
                    }
            } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
            } catch (TransformerException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
            }
    }
}
