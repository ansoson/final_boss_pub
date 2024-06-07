package finalboss.custom_components.enemy;

import engine.components.CollisionComponent;
import engine.hierarchy.GameObject;
import engine.shapes.ShapeEnum;
import engine.support.Vec2d;

public class HatmanCollisionComponent extends CollisionComponent {
    public HatmanCollisionComponent(GameObject g, ShapeEnum s, int layer, boolean stat) {
        super(g, s, layer, stat);
    }

    public void onCollision(GameObject x2, Vec2d collides){
        Vec2d trans = parent.getTransform().pos;
        trans = trans.minus(collides);
        parent.setTransform(trans);
    }
}
