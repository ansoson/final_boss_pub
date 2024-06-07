package finalboss.custom_components.main_character.weapons.weapon_implementations;

import engine.Direction;
import engine.UIKit.Rectangle;
import engine.components.CollisionComponent;
import engine.hierarchy.GameObject;
import engine.hierarchy.Viewport;
import engine.shapes.ColAAB;
import engine.shapes.ShapeEnum;
import engine.support.Vec2d;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

public class TipCollisionComponent extends CollisionComponent {
    int current;
    boolean debug;
    Rectangle a = null;
    Direction dir;
    double range;

    public TipCollisionComponent(GameObject g, ShapeEnum s, int layer, boolean stat, Direction dir, Double range) {
        super(g, s, layer, stat);
        current = 0;
        debug = false;
        this.dir = dir;
        this.range = range;
    }


    @Override
    public void initShape() {
        if (debug) {
            if (a != null) {
                this.parent.parent.parent.ui.remove(a);
            }
            if (current < 19 && current > 16) {
                if(dir == Direction.LEFT) {
                    this.shape = new ColAAB(parent.getTransform().pos.plus((float) (-240*range),22.5f), new Vec2d(10*range,10*range), parent.getTransform().def);
                    a = new Rectangle(converter(parent.getTransform().pos.plus((float) (-240*range), 22.5f)), new Vec2d(10*range, 10*range), parent.getTransform().def, Color.RED);
                }
                else{
                    this.shape = new ColAAB(parent.getTransform().pos.plus(280,22.5f), new Vec2d(10*range,10*range), parent.getTransform().def);
                    a = new Rectangle(converter(parent.getTransform().pos.plus(280, 22.5f)), new Vec2d(10*range, 10*range), parent.getTransform().def, Color.RED);
                }
            }
            else{
                this.shape = new ColAAB(new Vec2d(Double.MAX_VALUE, Double.MAX_VALUE), new Vec2d(0,0), parent.getTransform().def);
                a = new Rectangle(new Vec2d(0,0), new Vec2d(0,0), parent.getTransform().def, Color.RED);
            }
            this.parent.parent.parent.ui.add(a);
            if (current >= 20) {
                this.parent.parent.parent.ui.remove(a);
            }
        } else {
            if (current < 19 && current > 16) {
                if(dir == Direction.LEFT) {
                    this.shape = new ColAAB(parent.getTransform().pos.plus((float) (-240*range),22.5f), new Vec2d(10*range,10*range), parent.getTransform().def);
                }
                else{
                    this.shape = new ColAAB(parent.getTransform().pos.plus(280,22.5f), new Vec2d(10*range,10*range), parent.getTransform().def);
                }
            }
            else{
                this.shape = new ColAAB(new Vec2d(Double.MAX_VALUE, Double.MAX_VALUE), new Vec2d(0,0), parent.getTransform().def);
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
