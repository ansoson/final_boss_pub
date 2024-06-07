package finalboss.custom_components.main_character.weapons.weapon_implementations;

import engine.Direction;
import engine.components.AnimationComponent;
import engine.components.CollisionComponent;
import engine.components.SpriteComponent;
import engine.components.Tag;
import engine.hierarchy.GameObject;
import engine.shapes.ShapeEnum;
import engine.sprite.Resource;
import engine.support.Vec2d;
import engine.systems.CollisionSystem;
import engine.systems.GraphicsSystem;
import engine.systems.PathfindingSystem;
import engine.systems.TimerSystem;
import finalboss.FBChalkboard;
import finalboss.custom_components.TimedDisappearanceComponent;
import finalboss.custom_components.main_character.PlayerControllerComponent;
import finalboss.custom_components.main_character.weapons.WeaponComponent;
import finalboss.custom_components.main_character.weapons.WeaponType;
import javafx.scene.image.Image;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Random;

public class Wand extends WeaponComponent {

    int freeble;

    ArrayList<GameObject> ferry;

    boolean snap;

    public Wand(GameObject g, PlayerControllerComponent pcc, FBChalkboard chalk) {
        super(g, pcc, chalk);
        this.type = WeaponType.WAND;
        this.tag = Tag.WEAPON;
        this.isFinal = false;
        this.movementLock = 5;
        initVars();
        this.freeble = 0;
        this.ferry = new ArrayList<>();
        this.snap = true;
    }

    public Wand(GameObject g, PlayerControllerComponent pcc, FBChalkboard chalk, boolean snap) {
        super(g, pcc, chalk);
        this.type = WeaponType.WAND;
        this.tag = Tag.WEAPON;
        this.isFinal = false;
        this.movementLock = 5;
        initVars();
        this.freeble = 0;
        this.ferry = new ArrayList<>();
        this.snap = false;
    }

    @Override
    public void tick(long nanos){
        current += 1;
        if(isFinal && snap){
            this.freeble += 1;
            if(this.freeble % 50 == 0){
                spawnViolence(pcc);
            }
        }
    }


    public void spawnViolence(PlayerControllerComponent pcc){
        int sprite_size = pcc.getSpriteSize();
        Direction d = pcc.getLastDirect();
        Vec2d pos = parent.getTransform().pos;
        if(d == Direction.LEFT){
            pos = pos.plus(new Vec2d(-sprite_size*range, 0));

        }
        else if(d == Direction.RIGHT){
            pos = pos.plus(new Vec2d(sprite_size, 0));
        }
        else{
            System.out.println("Error, please fix."); //TODO: does this ever trigger
        }
        violenceFactory(pos, sprite_size, pcc);
    }

    public void violenceFactory(Vec2d pos, int sprite_size, PlayerControllerComponent pcc) {
        Resource<Image> r = pcc.r;
        Random rand = new Random();
        int bound = rand.nextInt((int) (5+Math.floor(special)*2));
        for(int i = 0; i < bound; i++) {
            double mult = (rand.nextDouble() + 0.5)*range;
            GameObject violence = new GameObject(this.parent.parent, "bubble", pos, new Vec2d(sprite_size*mult, sprite_size*mult), this.parent.parent.windowSize, 3.0);
            violence.addComponent(new CollisionComponent(violence, ShapeEnum.AAB, 3, false));
            SpriteComponent s = new SpriteComponent(violence, r, 16, 0, 0, 32, 32);
            AnimationComponent ss = new AnimationComponent(violence, s, 5, 4);
            violence.addComponent(s);
            violence.addComponent(ss);
            Wand fp = new Wand(violence, pcc, chalk, false);
            violence.addComponent(fp);
            BubbleMoveComponent b = new BubbleMoveComponent(violence, pcc.getLastDirect(), mult);
            violence.addComponent(b);
            violence.addComponent(new TimedDisappearanceComponent(violence, 80, this.parent.parent));
            ferry.add(violence);
        }
    }

    @Override
    public void onLateTick() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        for(GameObject a : ferry){
            GraphicsSystem s = (GraphicsSystem) this.parent.parent.getSystem("GS");
            TimerSystem i = (TimerSystem) this.parent.parent.getSystem("TS");
            CollisionSystem c = (CollisionSystem) this.parent.parent.getSystem("CS");
            c.addGameObject(a);
            s.addGameObject(a);
            i.lateGrab(a);
        }
        ferry.clear();
    }

    public void pop(){
        System.out.println("tragically he was popped");
        this.parent.parent.throwAway(this.parent);
    }

}
