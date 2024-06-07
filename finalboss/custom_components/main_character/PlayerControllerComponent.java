package finalboss.custom_components.main_character;

import engine.components.AnimationComponent;
import engine.components.CollisionComponent;
import engine.components.Component;
import engine.components.SpriteComponent;
import engine.hierarchy.GameObject;
import engine.hierarchy.GameSystem;
import engine.shapes.ShapeEnum;
import engine.sprite.Resource;
import engine.support.Vec2d;
import engine.components.Tag;
import engine.systems.CollisionSystem;
import engine.systems.TimerSystem;
import finalboss.custom_components.main_character.weapons.WeaponComponent;
import finalboss.custom_components.main_character.weapons.WeaponType;
import finalboss.gameworlds.LevelGameworld;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import engine.Direction;
import finalboss.custom_components.TimedDisappearanceComponent;
import finalboss.gameworlds.Overworld;
import javafx.scene.input.MouseEvent;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;


public class PlayerControllerComponent extends Component {

    public MainCharacterAnimationComponent c;
    int interval;
    int current;
    Direction lastDirect = Direction.RIGHT;

    WeaponComponent lefthand;
    WeaponComponent righthand;

    ArrayList<Direction> movementqueueX;
    ArrayList<Direction> movementqueueY;

    public Direction d;
    int sprite_size;
    public Resource<Image> r;
    public LevelGameworld gw;

    private boolean lflip;
    private boolean rflip;

    public PlayerControllerComponent(GameObject g, MainCharacterAnimationComponent c, int interval, int spriteSize, Resource<Image> r, LevelGameworld gw, WeaponComponent lefthand, WeaponComponent righthand) {
        super(g);
        this.tag = Tag.INPUT;
        this.interval =interval;
        this.current = 0;
        this.sprite_size = spriteSize;
        this.c = c;
        this.d = Direction.NONE;
        this.r = r;
        this.gw = gw;
        movementqueueX = new ArrayList<>();
        movementqueueY = new ArrayList<>();
        this.lefthand = lefthand;
        this.righthand = righthand;
        this.dashTimer = 0;
        this.dashTimer2 = 0;
        this.dashDirection = Direction.NONE;
        this.stillEepy = true;
    }
    public int getSpriteSize() {
        return sprite_size;
    }

    public WeaponComponent getLefthand(){return lefthand;}
    public int dashTimer;
    public int dashTimer2;
    public int maxDashTime = 5;
    public int dashCD = -30;
    public Direction dashDirection;
    protected boolean stillEepy;
    protected boolean beginTimer = false;
    protected int eepyTimer = 100;
    public int damageTimer = 0;
    public int movLocked = -1;

    public void makeEepyAgain(){
        eepyTimer = 100;
        stillEepy = true;
        beginTimer = false;
        damageTimer = -1;
        dashTimer = 0;
        dashTimer2 = 0;
        ((MainCharacterLayeredAnimationComponent) c.s.LAC).hardSleepySet();
    }

    public void setLefthand(WeaponComponent l) {
        if(!lflip) {
            this.parent.removeComponent(this.lefthand);
            this.lefthand = l;
            this.parent.addComponent(l);
            this.gw.pparent.postWeapons(lefthand, righthand);
            this.parent.callSystems();
            lflip = true;
        }
    }

    public WeaponComponent getRighthand() {return righthand;}

    public void setRighthand(WeaponComponent r) {
        if(!rflip) {
            this.parent.removeComponent(this.righthand);
            this.righthand = r;
            this.parent.addComponent(r);
            this.gw.pparent.postWeapons(lefthand, righthand);
            this.parent.callSystems();
            rflip = true;
        }
    }


    public ArrayList<Direction> getMovementqueueX() {
        return movementqueueX;
    }

    public ArrayList<Direction> getMovementqueueY() {
        return movementqueueY;
    }

    @Override
    public void onMouseClicked(Vec2d e, MouseEvent event) {
        super.onMouseClicked(e, event);
        if (!stillEepy){
            if (event.getButton().name().equals("PRIMARY") && movLocked <=0){
                lefthand.Attack(this, true);
            }
            if (event.getButton().name().equals("SECONDARY") && movLocked <=0){
                righthand.Attack(this, false);
            }
        }
    }

    @Override
    public void onKeyPressed(KeyEvent e) {
        if (stillEepy && !beginTimer){
            beginTimer = true;
            c.s.LAC.cycleSwap("LESSEEPY");
        } else if (!stillEepy) {
            if (e.getCode() == KeyCode.UP || e.getCode() == KeyCode.W) {
            if (!movementqueueY.contains(Direction.UP)) {
                movementqueueY.add(0, Direction.UP);
            }
        }
        else if(e.getCode() == KeyCode.LEFT || e.getCode() == KeyCode.A){
            if (!movementqueueX.contains(Direction.LEFT)){
                movementqueueX.add(0, Direction.LEFT);
            }
        }
        else if(e.getCode() == KeyCode.RIGHT || e.getCode() == KeyCode.D){
            if (!movementqueueX.contains(Direction.RIGHT)){
                movementqueueX.add(0, Direction.RIGHT);
            }
        }
        else if(e.getCode() == KeyCode.DOWN || e.getCode() == KeyCode.S){
            if (!movementqueueY.contains(Direction.DOWN)){
                movementqueueY.add(0, Direction.DOWN);
            }
        }
        else if(e.getCode() == KeyCode.C){
            if(lefthand.canAttack() && righthand.canAttack()){
                WeaponComponent l = getLefthand();
                WeaponComponent r = getRighthand();
                setLefthand(r);
                setRighthand(l);
            }

        }
        else if(e.getCode() == KeyCode.Z && movLocked <=0){
                lefthand.Attack(this, true);
//                if (lastDirect == Direction.RIGHT){
//                    c.s.LAC.cycleSwap("BACKRIGHTATTACK1");
//                } else {
//                    c.s.LAC.cycleSwap("BACKLEFTATTACK1");
//                }
        }
        else if(e.getCode() == KeyCode.X && movLocked <=0){
            righthand.Attack(this, false);
//                if (lastDirect == Direction.RIGHT){
//                    c.s.LAC.cycleSwap("FRONTRIGHTATTACK1");
//                } else {
//                    c.s.LAC.cycleSwap("FRONTLEFTATTACK1");
//                }
        }else if(e.getCode() == KeyCode.SPACE && dashTimer < dashCD){
            if (!movementqueueX.isEmpty() && !movementqueueY.isEmpty()){
                if (movementqueueY.get(0) == Direction.UP && movementqueueX.get(0) == Direction.RIGHT){
                    dashDirection = Direction.UPRIHT;
                    dashTimer = maxDashTime;
                    c.s.LAC.cycleSwap("DASHINGRIGHT");
                }
                if (movementqueueY.get(0) == Direction.UP && movementqueueX.get(0) == Direction.LEFT){
                    dashDirection = Direction.UPLEFT;
                    dashTimer = maxDashTime;
                    c.s.LAC.cycleSwap("DASHINGLEFT");
                }
                if (movementqueueY.get(0) == Direction.DOWN && movementqueueX.get(0) == Direction.RIGHT){
                    dashDirection = Direction.DOWNRIHT;
                    dashTimer = maxDashTime;
                    c.s.LAC.cycleSwap("DASHINGRIGHT");
                }
                if (movementqueueY.get(0) == Direction.DOWN && movementqueueX.get(0) == Direction.LEFT){
                    dashDirection = Direction.DOWNLEFT;
                    dashTimer = maxDashTime;
                    c.s.LAC.cycleSwap("DASHINGLEFT");
                }
            } else if (!movementqueueY.isEmpty()) {
                if (movementqueueY.get(0) == Direction.UP) {
                    dashDirection = Direction.UP;
                    dashTimer = maxDashTime;
                    if (lastDirect == Direction.RIGHT){
                        c.s.LAC.cycleSwap("DASHINGRIGHT");
                    } else {
                        c.s.LAC.cycleSwap("DASHINGLEFT");
                    }
                    //SET RIGHT CYCLE
                }
                if (movementqueueY.get(0) == Direction.DOWN) {
                    dashDirection = Direction.DOWN;
                    dashTimer = maxDashTime;
                    if (lastDirect == Direction.RIGHT){
                        c.s.LAC.cycleSwap("DASHINGRIGHT");
                    } else {
                        c.s.LAC.cycleSwap("DASHINGLEFT");
                    }
                    //SET RIGHT CYCLE
                }
            } else if (!movementqueueX.isEmpty()) {
                if (movementqueueX.get(0) == Direction.LEFT) {
                    dashDirection = Direction.LEFT;
                    dashTimer = maxDashTime;
                    c.s.LAC.cycleSwap("DASHINGLEFT");
                    //SET LEFT CYCLE
                }
                if (movementqueueX.get(0) == Direction.RIGHT) {
                    dashDirection = Direction.RIGHT;
                    dashTimer = maxDashTime;
                    c.s.LAC.cycleSwap("DASHINGRIGHT");
                    //SET RIGHT CYCLE
                }
            }
        }
            else if(e.getCode() == KeyCode.SPACE && dashTimer2 < dashCD && !(dashTimer > 0)){
                if (!movementqueueX.isEmpty() && !movementqueueY.isEmpty()){
                    if (movementqueueY.get(0) == Direction.UP && movementqueueX.get(0) == Direction.RIGHT){
                        dashDirection = Direction.UPRIHT;
                        dashTimer2 = maxDashTime;
                        c.s.LAC.cycleSwap("DASHINGRIGHT");
                    }
                    if (movementqueueY.get(0) == Direction.UP && movementqueueX.get(0) == Direction.LEFT){
                        dashDirection = Direction.UPLEFT;
                        dashTimer2 = maxDashTime;
                        c.s.LAC.cycleSwap("DASHINGLEFT");
                    }
                    if (movementqueueY.get(0) == Direction.DOWN && movementqueueX.get(0) == Direction.RIGHT){
                        dashDirection = Direction.DOWNRIHT;
                        dashTimer2 = maxDashTime;
                        c.s.LAC.cycleSwap("DASHINGRIGHT");
                    }
                    if (movementqueueY.get(0) == Direction.DOWN && movementqueueX.get(0) == Direction.LEFT){
                        dashDirection = Direction.DOWNLEFT;
                        dashTimer2 = maxDashTime;
                        c.s.LAC.cycleSwap("DASHINGLEFT");
                    }
                } else if (!movementqueueY.isEmpty()) {
                    if (movementqueueY.get(0) == Direction.UP) {
                        dashDirection = Direction.UP;
                        dashTimer2 = maxDashTime;
                        if (lastDirect == Direction.RIGHT){
                            c.s.LAC.cycleSwap("DASHINGRIGHT");
                        } else {
                            c.s.LAC.cycleSwap("DASHINGLEFT");
                        }
                        //SET RIGHT CYCLE
                    }
                    if (movementqueueY.get(0) == Direction.DOWN) {
                        dashDirection = Direction.DOWN;
                        dashTimer2 = maxDashTime;
                        if (lastDirect == Direction.RIGHT){
                            c.s.LAC.cycleSwap("DASHINGRIGHT");
                        } else {
                            c.s.LAC.cycleSwap("DASHINGLEFT");
                        }
                        //SET RIGHT CYCLE
                    }
                } else if (!movementqueueX.isEmpty()) {
                    if (movementqueueX.get(0) == Direction.LEFT) {
                        dashDirection = Direction.LEFT;
                        dashTimer2 = maxDashTime;
                        c.s.LAC.cycleSwap("DASHINGLEFT");
                        //SET LEFT CYCLE
                    }
                    if (movementqueueX.get(0) == Direction.RIGHT) {
                        dashDirection = Direction.RIGHT;
                        dashTimer2 = maxDashTime;
                        c.s.LAC.cycleSwap("DASHINGRIGHT");
                        //SET RIGHT CYCLE
                    }
                }
            }
        }
    }

    @Override
    public void onKeyReleased(KeyEvent e){
        if (e.getCode() == KeyCode.UP || e.getCode() == KeyCode.W) {
            movementqueueY.remove(Direction.UP);
        }
        else if(e.getCode() == KeyCode.LEFT || e.getCode() == KeyCode.A){
            movementqueueX.remove(Direction.LEFT);
            lastDirect = Direction.LEFT;
        }
        else if(e.getCode() == KeyCode.RIGHT || e.getCode() == KeyCode.D){
            movementqueueX.remove(Direction.RIGHT);
            lastDirect = Direction.RIGHT;
        }
        else if(e.getCode() == KeyCode.DOWN || e.getCode() == KeyCode.S){
            movementqueueY.remove(Direction.DOWN);
        }
        if(movementqueueX.isEmpty() && movementqueueY.isEmpty()) {
            d = Direction.NONE;
            c.s.setHcel(4);
            c.s.setVcel(0);
        }
        current = 0;
    }

    int tickCount = 0;

    @Override
    public void tick(long nanosSinceLastTick) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        tickCount++;
        lflip = false;
        rflip = false;
        if (damageTimer > -100){
            damageTimer -= 1;
        }
        if (movLocked > -100){
            movLocked -= 1;
        }
        if (beginTimer && stillEepy){
            eepyTimer = eepyTimer - 1;
        }
        if (eepyTimer < 0){
            stillEepy = false;
        }
        if((!movementqueueX.isEmpty() && damageTimer <= 0 && movLocked <=0) || (!movementqueueY.isEmpty() && damageTimer <= 0 && movLocked <=0)) {
                Vec2d t = parent.getTransform().pos;
                if (dashTimer > 0 || dashTimer2 > 0){
                    int i = 4;
                    while (i > 0){
                        t = parent.getTransform().pos;
                        if (dashDirection == Direction.UP) {
                            parent.setTransform(new Vec2d(t.x, t.y - (double) sprite_size / 2 / 4));
                        }
                        if (dashDirection == Direction.DOWN) {
                            parent.setTransform(new Vec2d(t.x, t.y + (double) sprite_size / 2 / 4));
                        }
                        if (dashDirection == Direction.LEFT) {
                            parent.setTransform(new Vec2d(t.x - (double) sprite_size / 2 / 4, t.y));
                        }
                        if (dashDirection == Direction.RIGHT) {
                            parent.setTransform(new Vec2d(t.x + (double) sprite_size / 2 / 4, t.y));
                        }
                        if (dashDirection == Direction.UPLEFT) {
                            parent.setTransform(new Vec2d(t.x - (double) sprite_size / 2.3 / 4, t.y - (double) sprite_size / 2.3 / 4));
                        }
                        if (dashDirection == Direction.UPRIHT) {
                            parent.setTransform(new Vec2d(t.x + (double) sprite_size / 2.3 / 4, t.y - (double) sprite_size / 2.3 / 4));
                        }
                        if (dashDirection == Direction.DOWNLEFT) {
                            parent.setTransform(new Vec2d(t.x - (double) sprite_size / 2.3 / 4, t.y + (double) sprite_size / 2.3 / 4));
                        }
                        if (dashDirection == Direction.DOWNRIHT) {
                            parent.setTransform(new Vec2d(t.x + (double) sprite_size / 2.3 / 4, t.y + (double) sprite_size / 2.3 / 4));
                        }
                        parent.parent.thisCollision.collisionTickHelper(parent, 1);
                        i -= 1;
                    }

                } else {
                if (!movementqueueX.isEmpty() && !movementqueueY.isEmpty()){
                    if (movementqueueY.get(0) == Direction.UP && movementqueueX.get(0) == Direction.RIGHT){
                        parent.setTransform(new Vec2d(t.x + (double) sprite_size / 11, t.y - (double) sprite_size / 11));
                        c.s.LAC.cycleSwap("RUNNINGRIGHT");
                        lastDirect = Direction.RIGHT;
                        //SET RIGHT CYCLE
                    }
                    if (movementqueueY.get(0) == Direction.UP && movementqueueX.get(0) == Direction.LEFT){
                        parent.setTransform(new Vec2d(t.x - (double) sprite_size / 11, t.y - (double) sprite_size / 11));
                        c.s.LAC.cycleSwap("RUNNINGLEFT");
                        lastDirect = Direction.LEFT;
                        //SET LEFT CYCLE
                    }
                    if (movementqueueY.get(0) == Direction.DOWN && movementqueueX.get(0) == Direction.RIGHT){
                        parent.setTransform(new Vec2d(t.x + (double) sprite_size / 11, t.y + (double) sprite_size / 11));
                        c.s.LAC.cycleSwap("RUNNINGRIGHT");
                        lastDirect = Direction.RIGHT;
                        //SET RIGHT CYCLE
                    }
                    if (movementqueueY.get(0) == Direction.DOWN && movementqueueX.get(0) == Direction.LEFT){
                        parent.setTransform(new Vec2d(t.x - (double) sprite_size / 11, t.y + (double) sprite_size / 11));
                        c.s.LAC.cycleSwap("RUNNINGLEFT");
                        lastDirect = Direction.LEFT;
                        //SET LEFT CYCLE
                    }
                } else if (!movementqueueY.isEmpty()) {
                    if (movementqueueY.get(0) == Direction.UP) {
                        parent.setTransform(new Vec2d(t.x, t.y - (double) sprite_size / 8));
                        if (lastDirect == Direction.RIGHT){
                            c.s.LAC.cycleSwap("RUNNINGRIGHT");
                        } else {
                            c.s.LAC.cycleSwap("RUNNINGLEFT");
                        }
                        //SET RIGHT CYCLE
                    }
                    if (movementqueueY.get(0) == Direction.DOWN) {
                        c.s.setHcel(2);
                        parent.setTransform(new Vec2d(t.x, t.y + (double) sprite_size / 8));
                        if (lastDirect == Direction.RIGHT){
                            c.s.LAC.cycleSwap("RUNNINGRIGHT");
                        } else {
                            c.s.LAC.cycleSwap("RUNNINGLEFT");
                        }
                        //SET RIGHT CYCLE
                    }
                } else if (!movementqueueX.isEmpty()) {
                    if (movementqueueX.get(0) == Direction.LEFT) {
                        c.s.setHcel(1);
                        parent.setTransform(new Vec2d(t.x - (double) sprite_size / 8, t.y));
                        c.s.LAC.cycleSwap("RUNNINGLEFT");
                        lastDirect = Direction.LEFT;
                        //SET LEFT CYCLE
                    }
                    if (movementqueueX.get(0) == Direction.RIGHT) {
                        c.s.setHcel(0);
                        parent.setTransform(new Vec2d(t.x + (double) sprite_size / 8, t.y));
                        c.s.LAC.cycleSwap("RUNNINGRIGHT");
                        lastDirect = Direction.RIGHT;
                        //SET RIGHT CYCLE
                    }
                }
            }
        } else if (damageTimer <= 0 && movLocked <= 0){
            if (!stillEepy){
                if (lastDirect == Direction.RIGHT){
                    c.s.LAC.cycleSwap("IDLERIGHT");
                } else {
                    c.s.LAC.cycleSwap("IDLELEFT");
                }
            }
        } else if (damageTimer > 0){
            Vec2d t = parent.getTransform().pos;
            parent.setTransform(new Vec2d(t.x - (double) sprite_size / (80.0/(damageTimer * damageTimer)), t.y));
            c.s.LAC.cycleSwap("OUCH");
        } else {
            if (lastDirect == Direction.RIGHT){
                c.s.LAC.cycleSwap("LOCKATTACKRIGHT");
            } else {
                c.s.LAC.cycleSwap("LOCKATTACKLEFT");
            }
        }
        dashTimer2 -= 1;
        if (dashTimer > 0 || dashTimer2 < dashCD){
            dashTimer -= 1;
        }
        c.s.tick(nanosSinceLastTick);
    }

    public Direction getLastDirect() {
        return lastDirect;
    }

    public void resetMovementqueue() {
        this.movementqueueX = new ArrayList<>();
        this.movementqueueY = new ArrayList<>();
    }

    public void setGameWorld(LevelGameworld gw) {
        this.gw = gw;
    }
}
