package finalboss.custom_components.main_character.weapons.weapon_implementations;

import engine.Direction;
import engine.UIKit.Rectangle;
import engine.components.CollisionComponent;
import engine.components.SpriteComponent;
import engine.hierarchy.GameObject;
import engine.hierarchy.Viewport;
import engine.shapes.ColAAB;
import engine.shapes.ShapeEnum;
import engine.support.Vec2d;
import finalboss.gameworlds.LevelGameworld;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

public class RopeCollisionComponent extends CollisionComponent {
    int current;
    boolean debug;
    Direction dir;
    Rectangle a = null;

    double range;
    LevelGameworld lg;
    float offset;
    boolean isFinal;

    public RopeCollisionComponent(GameObject g, ShapeEnum s, int layer, boolean stat, Direction d, double range, boolean isFinal) {
        super(g, s, layer, stat);
        current = 0;
        this.debug = false;
        this.dir = d;
        this.range = range;
        this.lg = (LevelGameworld) parent.parent;
        this.offset = (float) (-3*range*lg.sprite_size);
        this.isFinal = isFinal;
    }

    @Override
    public void initShape() {
        if(debug) {
            if (a != null) {
                this.parent.parent.parent.ui.remove(a);
            }
            if (current < 5) {
                if(dir == Direction.LEFT) {

                    this.shape = new ColAAB(parent.getTransform().pos.plus((float) (-10*range)-offset, 15), new Vec2d(20*range, 20*range), parent.getTransform().def);
                    a = new Rectangle(converter(parent.getTransform().pos.plus((float) (-10*range)-offset, 15)), new Vec2d(20*range, 20*range), parent.getTransform().def, Color.RED);
                }
                else{
                    this.shape = new ColAAB(parent.getTransform().pos.plus(0, 15), new Vec2d(20*range, 20*range), parent.getTransform().def);
                    a = new Rectangle(converter(parent.getTransform().pos.plus(0, 15)), new Vec2d(20*range, 20*range), parent.getTransform().def, Color.RED);
                }
            } else if (current < 8) {
                if(dir == Direction.LEFT) {
                    this.shape = new ColAAB(parent.getTransform().pos.plus((float) (-20*range)-offset, 15), new Vec2d(30*range, 20*range), parent.getTransform().def);
                    a = new Rectangle(converter(parent.getTransform().pos.plus((float) (-20*range)-offset, 15)), new Vec2d(30*range, 20*range), parent.getTransform().def, Color.RED);
                }
                else{
                    this.shape = new ColAAB(parent.getTransform().pos.plus(0,15), new Vec2d(30*range, 20*range), parent.getTransform().def);
                    a = new Rectangle(converter(parent.getTransform().pos.plus(0, 15)), new Vec2d(30*range, 20*range), parent.getTransform().def, Color.RED);
                }
            } else if (current < 11) {
                if(dir == Direction.LEFT) {
                    this.shape = new ColAAB(parent.getTransform().pos.plus((float) (-50 * range)-offset, 15), new Vec2d(60*range, 20*range), parent.getTransform().def);
                    a = new Rectangle(converter(parent.getTransform().pos.plus((float) (-50 * range)-offset, 15)), new Vec2d(60*range, 20*range), parent.getTransform().def, Color.RED);
                }
                else{
                    this.shape = new ColAAB(parent.getTransform().pos.plus(0,15), new Vec2d(60*range, 20*range), parent.getTransform().def);
                    a = new Rectangle(converter(parent.getTransform().pos.plus(0, 15)), new Vec2d(60*range, 20*range), parent.getTransform().def, Color.RED);
                }
            } else {
                if(dir == Direction.LEFT) {
                    this.shape = new ColAAB(parent.getTransform().pos.plus((float) (-80*range)-offset, 15), new Vec2d(90*range, 40*range), parent.getTransform().def);
                    a = new Rectangle(converter(parent.getTransform().pos.plus((float) (-80*range)-offset, 15)), new Vec2d(90*range, 40*range), parent.getTransform().def, Color.RED);
                }
                else{
                    this.shape = new ColAAB(parent.getTransform().pos.plus(0,15), new Vec2d(90*range, 40*range), parent.getTransform().def);
                    a = new Rectangle(converter(parent.getTransform().pos.plus(0, 15)), new Vec2d(90*range, 40*range), parent.getTransform().def, Color.RED);
                }
            }
            this.parent.parent.parent.ui.add(a);
            if(current >= 11){
                this.parent.parent.parent.ui.remove(a);
            }
        }
        else{
            if (current < 5) {
                if(dir == Direction.LEFT) {
                    this.shape = new ColAAB(parent.getTransform().pos.plus((float) (-10*range)-offset, 15), new Vec2d(20*range, 20*range), parent.getTransform().def);
                }
                else{
                    this.shape = new ColAAB(parent.getTransform().pos.plus(0, 15), new Vec2d(20*range, 20*range), parent.getTransform().def);
                }
            } else if (current < 8) {
                if(dir == Direction.LEFT) {
                    this.shape = new ColAAB(parent.getTransform().pos.plus((float) (-20*range)-offset, 15), new Vec2d(30*range, 20*range), parent.getTransform().def);
                }
                else{
                    this.shape = new ColAAB(parent.getTransform().pos.plus(0,15), new Vec2d(30*range, 20*range), parent.getTransform().def);
                }
            } else if (current < 11) {
                if(dir == Direction.LEFT) {
                    this.shape = new ColAAB(parent.getTransform().pos.plus((float) (-50 * range)-offset, 15), new Vec2d(60*range, 20*range), parent.getTransform().def);
                }
                else{
                    this.shape = new ColAAB(parent.getTransform().pos.plus(0,15), new Vec2d(60*range, 20*range), parent.getTransform().def);
                }
            } else {
                if (dir == Direction.LEFT) {
                    if(isFinal){
                        this.shape = new ColAAB(parent.getTransform().pos.plus((float) (-80 * range) - offset, -15), new Vec2d(90 * range, 120 * range), parent.getTransform().def);
                    }else {
                        this.shape = new ColAAB(parent.getTransform().pos.plus((float) (-80 * range) - offset, 15), new Vec2d(90 * range, 40 * range), parent.getTransform().def);
                    }
                } else {
                    if(isFinal){
                        this.shape = new ColAAB(parent.getTransform().pos.plus(0, -15), new Vec2d(90 * range, 120 * range), parent.getTransform().def);

                    }else{
                        this.shape = new ColAAB(parent.getTransform().pos.plus(0, 15), new Vec2d(90 * range, 40 * range), parent.getTransform().def);
                    }
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
}
