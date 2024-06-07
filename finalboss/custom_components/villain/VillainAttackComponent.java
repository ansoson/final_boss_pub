package finalboss.custom_components.villain;

import engine.Direction;
import engine.components.*;
import engine.shapes.ShapeEnum;
import engine.support.Vec2d;
import engine.hierarchy.GameObject;
import engine.sprite.Resource;
import engine.systems.CollisionSystem;
import engine.systems.GraphicsSystem;
import engine.systems.PathfindingSystem;
import engine.systems.TimerSystem;
import finalboss.FBChalkboard;
import finalboss.custom_components.villain.attacks.*;
import finalboss.gameworlds.BattleWorld;
import finalboss.screens.CharacterView;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Random;

public class VillainAttackComponent extends Component {
    private double current;
    private double cooldown;
    private CharacterView cView;
    private FBChalkboard board;
    private boolean tick;
    private boolean attacking;
    private Vec2d startpos;
    private Vec2d playerpos;
    private VillainAttack attack;
    private Resource<Image> r;

    ArrayList<GameObject> attacks;

    ArrayList<GameObject> ferry;
    VillainAnimationComponent VAC;

    boolean enraged;
    public VillainAttackComponent(Tag tag, GameObject g, CharacterView mv, FBChalkboard chalk, Resource<Image> res, VillainAnimationComponent vac) {
        super(tag, g);
        this.cooldown = 4;
        this.current = 0;
        this.cView = mv;
        this.board = chalk;
        this.tick = false;
        this.startpos = this.parent.getTransform().pos;
        this.playerpos = board.getPlayer().getTransform().pos;
        this.attacking = false;
        this.r = res;
        this.attacks = new ArrayList<>();
        this.ferry = new ArrayList<>();
        this.VAC = vac;
        this.enraged = false;
    }

    @Override
    public void tick(long nanos) {
        if (!this.tick) {
            this.tick = true;
        } else {
            long longMultiplier = (long) Math.pow(10, 8);
            current += ((double) nanos / longMultiplier / 10.0);
            if (attacking) {
                if (this.attack == VillainAttack.LASER){
                    this.laserAttack();
                    this.VAC.s.LAC.cycleSwap("LASERATTACK");
                }else if (this.attack == VillainAttack.PLUMES){
                    this.plumeAttack();
                    this.VAC.s.LAC.cycleSwap("PLUMEATTACK");
                }else if (this.attack == VillainAttack.LAVA){
                    this.lavaAttack();
                    this.VAC.s.LAC.cycleSwap("LAVAATTACK");
                }else if (this.attack == VillainAttack.BUBBLES){
                    this.bubbleAttack();
                    this.VAC.s.LAC.cycleSwap("BUBBLEATTACK");
                }else if (this.attack == VillainAttack.MAGMLE){
                    this.magmleAttack();
                    this.VAC.s.LAC.cycleSwap("BUBBLEATTACK");
                    //this.VAC.s.LAC.cycleSwap("BUBBLEATTACK");
                }
                else if (this.attack == VillainAttack.LAKE){
                    this.lakeAttack();
                    this.VAC.s.LAC.cycleSwap("PLUMEATTACK");
                    //this.VAC.s.LAC.cycleSwap("BUBBLEATTACK");
                }
            } else {
                if (current > cooldown) {
                        Random r = new Random();
                        ArrayList<VillainAttack> v = new ArrayList<>();
                        //v.add(VillainAttack.LAVA);
                        v.add(VillainAttack.LAKE);v.add(VillainAttack.LASER);v.add(VillainAttack.PLUMES); v.add(VillainAttack.LAVA); v.add(VillainAttack.BUBBLES); v.add(VillainAttack.MAGMLE);
                        this.attack = v.get(r.nextInt(6));
                        this.attacking = true;
                }
            }
        }
    }


    public void laserAttack() {
        GameObject attack = new GameObject(this.parent.parent, "Laser Attack", new Vec2d(-200, 250), new Vec2d(1200,300), this.parent.getTransform().def, 6.0);
        SpriteComponent s = new SpriteComponent(attack, this.r, 29, 0, 0, 300, 1400);
        AnimationComponent ss = new AnimationComponent(attack, s, 21, 2);
        attack.addComponent(s);
        attack.addComponent(ss);
        attack.addComponent(new VillainDisappearingStaticAttack(attack, 20, 60, this.parent.parent, ShapeEnum.AAB, VillainAttack.LASER));
        ferry.add(attack);
        this.attacking = false;
        current = 0;
    }

    public void plumeAttack() {
        Random r = new Random();
        for(int i = 0; i < 10+ r.nextInt(20); i++) {
            GameObject attack = new GameObject(this.parent.parent, "Plume Attack", new Vec2d(100+r.nextInt(12)*50, 100+r.nextInt(12)*50), new Vec2d(50, 50), this.parent.getTransform().def, 3.0);
            SpriteComponent s = new SpriteComponent(attack, this.r, 25, 0, 0, 48, 48);
            AnimationComponent ss = new AnimationComponent(attack, s, 14, 2);
            attack.addComponent(s);
            attack.addComponent(ss);
            attack.addComponent(new VillainDisappearingStaticAttack(attack, 25, 40, this.parent.parent, ShapeEnum.AAB, VillainAttack.PLUMES));
            ferry.add(attack);
        }
        this.attacking = false;
        current = 2;
    }

    public void lavaAttack() {
        GameObject attack = new GameObject(this.parent.parent, "Lava Attack", new Vec2d(550, 150), new Vec2d(300,550), this.parent.getTransform().def, 1.0);
        SpriteComponent s = new SpriteComponent(attack, this.r, 27, 0, 0, 528, 288);
        AnimationComponent ss = new AnimationComponent(attack, s, 20, 4);
        attack.addComponent(s);
        attack.addComponent(ss);
        attack.addComponent(new VillainDisappearingStaticAttack(attack, 40, 80, this.parent.parent, ShapeEnum.AAB, VillainAttack.LAVA));
        ferry.add(attack);
        this.attacking = false;
        current = 1;
    }

    public void bubbleAttack() {
        GameObject b1 = new GameObject(this.parent.parent, "Bubble1", new Vec2d(800, 200), new Vec2d(150,100), this.parent.getTransform().def, 3.0);
        spawnBubble(b1, Direction.DOWNLEFT);
        GameObject b2 = new GameObject(this.parent.parent, "Bubble2", new Vec2d(800, 500), new Vec2d(150,100), this.parent.getTransform().def, 3.0);
        spawnBubble(b2, Direction.UPLEFT);
        GameObject b3 = new GameObject(this.parent.parent, "Bubble3", new Vec2d(900, 350), new Vec2d(150,100), this.parent.getTransform().def, 3.0);
        spawnBubble(b3, Direction.LEFT);
        this.attacking = false;
        current = 0;
    }

    private void spawnBubble(GameObject o, Direction d) {
        SpriteComponent s = new SpriteComponent(o, this.r, 26, 0, 0, 96, 96);
        AnimationComponent ss = new AnimationComponent(o, s, 30, 2);
        o.addComponent(s);
        o.addComponent(ss);
        o.addComponent(new VillainDisappearingMovingAttack(o, 0, 80, this.parent.parent, ShapeEnum.AAB, VillainAttack.BUBBLES));
        o.addComponent(new LavaBubbleMove(o, d));
        ferry.add(o);
    }

    public void magmleAttack() {
        System.out.println("AAAAH THE MAGMLES!!!");
        GameObject b1 = new GameObject(this.parent.parent, "Magmle1", new Vec2d(700, 300), new Vec2d(50,50), this.parent.getTransform().def, 3.0);
        spawnChildren(b1);
        GameObject b2 = new GameObject(this.parent.parent, "Magmle2", new Vec2d(800, 500), new Vec2d(50,50), this.parent.getTransform().def, 3.0);
        spawnChildren(b2);
        if(enraged) {
            GameObject b3 = new GameObject(this.parent.parent, "Magmle3", new Vec2d(700, 400), new Vec2d(50, 50), this.parent.getTransform().def, 3.0);
            spawnChildren(b3);
        }
        this.attacking = false;
        current = 2;
    }

    public void lakeAttack() {
        GameObject a1 = new GameObject(this.parent.parent, "InnerLake", new Vec2d(400, 350), new Vec2d(200,200), this.parent.getTransform().def, 6.0);
        a1.addComponent(new VillainDoubleTriggerAttack(a1, 24, 35, 100, this.parent.parent, VillainAttack.LAKE));
        ferry.add(a1);
        GameObject a2 = new GameObject(this.parent.parent, "Midlake", new Vec2d(300, 250), new Vec2d(400,400), this.parent.getTransform().def, 6.0);
        a2.addComponent(new VillainDoubleTriggerAttack(a2, 54, 55, 100, this.parent.parent, VillainAttack.LAKE));
        ferry.add(a2);
        GameObject a3 = new GameObject(this.parent.parent, "Outerlake", new Vec2d(200, 150), new Vec2d(600,600), this.parent.getTransform().def, 6.0);
        a3.addComponent(new VillainDoubleTriggerAttack(a3, 99, 100, 120, this.parent.parent, VillainAttack.LAKE));
        ferry.add(a3);
        SpriteComponent s = new SpriteComponent(a3, this.r, 28, 0, 0, 576, 576);
        AnimationComponent ss = new AnimationComponent(a3, s, 25, 4);
        a3.addComponent(s);
        a3.addComponent(ss);
        this.attacking = false;
        current = -2;
    }

    private void spawnChildren(GameObject o) {
        SpriteComponent vas = new SpriteComponent(o, r, 24, 0, 0, 32, 32);
        o.addComponent(vas);
        o.addComponent(new AnimationComponent(o, vas, 3, 3));
        o.addComponent(new VillainMagmleAttack(o, 80, this.parent.parent));
        o.addComponent(new MagmleCollisionComponent(o, ShapeEnum.AAB, 4, false, 1));
        BattleWorld lg = (BattleWorld) parent.parent;
        o.addComponent(new MagmleAI(o, lg.sprite_size, lg.playerObject, vas));
        ferry.add(o);
    }

    public void cleanup(){
        for(GameObject a : attacks){
            this.parent.parent.throwAway(a);
        }
        this.cooldown = 4;
        this.enraged = false;
    }

    @Override
    public void onLateTick() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        for(GameObject a : ferry){
            GraphicsSystem s = (GraphicsSystem) this.parent.parent.getSystem("GS");
            TimerSystem i = (TimerSystem) this.parent.parent.getSystem("TS");
            PathfindingSystem p = (PathfindingSystem) this.parent.parent.getSystem("PS");
            CollisionSystem c = (CollisionSystem) this.parent.parent.getSystem("CS");
            c.acceptObject(a);
            s.acceptObject(a);
            i.lateGrab(a);
            p.acceptObject(a);
            attacks.add(a);
        }
        ferry.clear();
    }

    public void setEnraged(boolean enraged) {
        this.enraged = enraged;
        this.cooldown -= 1;
    }

    public void stun(){
        this.current -= 0.5;
    }
}
