package finalboss.custom_components.main_character.weapons.weapon_implementations;

import engine.Direction;
import engine.UIKit.Rectangle;
import engine.components.CollisionComponent;
import engine.components.Tag;
import engine.hierarchy.GameObject;
import engine.hierarchy.Viewport;
import engine.shapes.ColAAB;
import engine.shapes.ShapeEnum;
import engine.support.Vec2d;
import finalboss.custom_components.villain.attacks.VillainAttack;
import finalboss.custom_components.villain.attacks.VillainDisappearingAttack;
import finalboss.screens.CharacterView;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

public class ShieldCollisionComponent extends CollisionComponent {

    int current;
    boolean debug;
    Rectangle r = null;

    Direction dir;
    int mult;
    double range;
    CharacterView cv;
    Double special;
    public ShieldCollisionComponent(GameObject g, ShapeEnum s, int layer, boolean stat, Direction d, double range, CharacterView cv, Double special) {
        super(g, s, layer, stat);
        debug = false;
        this.dir = d;
        this.mult = 1;
        this.range = range;
        this.cv = cv;
        this.special = special;
    }

    //djL = sprite_size - djR - size
    @Override
    public void initShape() {
        if (debug) {
            if (r != null) {
                this.parent.parent.parent.ui.remove(r);
            }
            if (current < 2) {
                if(dir == Direction.LEFT) {
                    this.shape = new ColAAB(parent.getTransform().pos.plus((float) (-5*range), 0), new Vec2d(12*range, 50*range), parent.getTransform().def);
                    r = new Rectangle(converter(parent.getTransform().pos.plus((float) (-5*range), 0)), new Vec2d(12*range, 50*range), parent.getTransform().def, Color.RED);
                    this.parent.parent.parent.ui.add(r);

                }else {
                    this.shape = new ColAAB(parent.getTransform().pos.plus(new Vec2d(15, 0)), new Vec2d(12*range, 50*range), parent.getTransform().def);
                    r = new Rectangle(converter(parent.getTransform().pos.plus(new Vec2d(15, 0))), new Vec2d(12*range, 50*range), parent.getTransform().def, Color.RED);
                    this.parent.parent.parent.ui.add(r);
                }
            } else if (current < 4) {
                if(dir == Direction.LEFT) {
                    this.shape = new ColAAB(parent.getTransform().pos.plus((float) (-15*range), 0), new Vec2d(16*range, 50*range), parent.getTransform().def);
                    r = new Rectangle(converter(parent.getTransform().pos.plus((float) (-15*range), 0)), new Vec2d(16*range, 50*range), parent.getTransform().def, Color.YELLOW);
                    this.parent.parent.parent.ui.add(r);

                }else {
                    this.shape = new ColAAB(parent.getTransform().pos.plus(25, 0), new Vec2d(16*range, 50*range), parent.getTransform().def);
                    r = new Rectangle(converter(parent.getTransform().pos.plus(25, 0)), new Vec2d(16*range, 50*range), parent.getTransform().def, Color.YELLOW);
                    this.parent.parent.parent.ui.add(r);
                }
            } else if (current < 6) {
                if(dir == Direction.LEFT) {
                    this.shape = new ColAAB(parent.getTransform().pos.plus((float) (-25*range), 0), new Vec2d(22*range, 50*range), parent.getTransform().def);
                    r = new Rectangle(converter(parent.getTransform().pos.plus((float) (-25*range), 0)), new Vec2d(22*range, 50*range), parent.getTransform().def, Color.GREEN);
                    this.parent.parent.parent.ui.add(r);

                }else {
                    this.shape = new ColAAB(parent.getTransform().pos.plus(35, 0), new Vec2d(22*range, 50*range), parent.getTransform().def);
                    r = new Rectangle(converter(parent.getTransform().pos.plus(35, 0)), new Vec2d(22*range, 50*range), parent.getTransform().def, Color.GREEN);
                    this.parent.parent.parent.ui.add(r);
                }
            } else {
                if(dir == Direction.LEFT) {
                    this.shape = new ColAAB(parent.getTransform().pos.plus((float) (-20*range), 0), new Vec2d(20*range, 50*range), parent.getTransform().def);
                    r = new Rectangle(converter(parent.getTransform().pos.plus((float) (-20*range), 0)), new Vec2d(20*range, 50*range), parent.getTransform().def, Color.BLUE);
                    this.parent.parent.parent.ui.add(r);

                }else {
                    this.shape = new ColAAB(parent.getTransform().pos.plus(30, 0), new Vec2d(20*range, 50*range), parent.getTransform().def);
                    r = new Rectangle(converter(parent.getTransform().pos.plus(30, 0)), new Vec2d(20*range, 50*range), parent.getTransform().def, Color.BLUE);
                    this.parent.parent.parent.ui.add(r);
                }
                if (current >= 9) {
                    this.parent.parent.parent.ui.remove(r);
                }
            }
        } else {
            if (current < 2) {
                if(dir == Direction.LEFT) {
                    this.shape = new ColAAB(parent.getTransform().pos.plus((float) (-5*range), 0), new Vec2d(12*range, 50*range), parent.getTransform().def);

                }else {
                    this.shape = new ColAAB(parent.getTransform().pos.plus(new Vec2d(15, 0)), new Vec2d(12*range, 50*range), parent.getTransform().def);
                }
            } else if (current < 4) {
                if(dir == Direction.LEFT) {
                    this.shape = new ColAAB(parent.getTransform().pos.plus((float) (-15*range), 0), new Vec2d(16*range, 50*range), parent.getTransform().def);

                }else {
                    this.shape = new ColAAB(parent.getTransform().pos.plus(25, 0), new Vec2d(16*range, 50*range), parent.getTransform().def);
                }
            } else if (current < 6) {
                if(dir == Direction.LEFT) {
                    this.shape = new ColAAB(parent.getTransform().pos.plus((float) (-25*range), 0), new Vec2d(22*range, 50*range), parent.getTransform().def);

                }else {
                    this.shape = new ColAAB(parent.getTransform().pos.plus(35, 0), new Vec2d(22*range, 50*range), parent.getTransform().def);
                }
            } else {
                if (dir == Direction.LEFT) {
                    this.shape = new ColAAB(parent.getTransform().pos.plus((float) (-20 * range), 0), new Vec2d(20 * range, 50 * range), parent.getTransform().def);

                } else {
                    this.shape = new ColAAB(parent.getTransform().pos.plus(30, 0), new Vec2d(20 * range, 50 * range), parent.getTransform().def);
                }
            }
        }
    }

    public Vec2d converter(Vec2d vec){
        Viewport v = parent.parent.parent.viewport;
        Point2D ptd = v.gameToScreen().transform(vec.x, vec.y);
        return new Vec2d(ptd.getX(), ptd.getY());
    }


    @Override
    public void tick(long n){
        current += 1;
    }

    @Override
    public void onCollision(GameObject x2, Vec2d collides){
        if(x2.getComponent(Tag.VILL) != null){
            VillainDisappearingAttack va = (VillainDisappearingAttack) x2.getComponent(Tag.VILL);
            if(va.attack == VillainAttack.BUBBLES || va.attack == VillainAttack.LASER || va.attack == VillainAttack.PLUMES) {
                System.out.println("PARRIED!!!");
                x2.parent.throwAway(x2);
                cv.setCooldown((int) (special / 1));
            }
        }
    }



}
