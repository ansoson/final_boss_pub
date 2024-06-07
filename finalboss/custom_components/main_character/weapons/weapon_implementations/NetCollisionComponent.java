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

public class NetCollisionComponent extends CollisionComponent {
    int current;
    boolean debug;
    Rectangle r = null;
    Direction dir;
    double range;
    public NetCollisionComponent(GameObject g, ShapeEnum s, int layer, boolean stat, Direction d, double range) {
        super(g, s, layer, stat);
        current = 0;
        this.debug = false;
        this.dir = d;
        this.range = range;
    }

    @Override
    public void initShape() {
        if(debug){
            if(r != null){
                this.parent.parent.parent.ui.remove(r);
            }
            if(dir == Direction.LEFT) {
                this.shape = new ColAAB(parent.getTransform().pos.plus((float) (-10*range), (float) (-10*range)), new Vec2d(50*range,90*range), parent.getTransform().def);
                r = new Rectangle(converter(parent.getTransform().pos.plus((float) (-10*range), (float) (-10*range))), new Vec2d(50*range, 90*range), parent.getTransform().def, Color.RED);
            }
            else{
                this.shape = new ColAAB(parent.getTransform().pos.plus(10, (float) (-10*range)), new Vec2d(50*range,90*range), parent.getTransform().def);
                r = new Rectangle(converter(parent.getTransform().pos.plus(10, (float) (-10*range))), new Vec2d(50*range, 90*range), parent.getTransform().def, Color.RED);
            }
            this.parent.parent.parent.ui.add(r);
            if(current >= 9){
                this.parent.parent.parent.ui.remove(r);
            }
        }
        else{
            if(dir == Direction.LEFT) {
                this.shape = new ColAAB(parent.getTransform().pos.plus((float) (-10*range), (float) (-10*range)), new Vec2d(50*range,90*range), parent.getTransform().def);
            }
            else{
                this.shape = new ColAAB(parent.getTransform().pos.plus(10, (float) (-10*range)), new Vec2d(50*range,90*range), parent.getTransform().def);
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
