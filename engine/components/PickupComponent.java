package engine.components;

import engine.hierarchy.GameObject;
import engine.hierarchy.GameWorld;
import engine.support.Vec2d;
import engine.shapes.ShapeEnum;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class PickupComponent extends CollisionComponent {

    GameWorld gw;
    public PickupComponent(GameObject g, GameWorld gw) {
        super(g, ShapeEnum.AAB, 1, false);
        this.tag = Tag.COLLISION;
        this.gw = gw;
    }

    public PickupComponent(Element e, GameObject g){
        super(g, ShapeEnum.AAB, 1, false);
        this.tag = Tag.COLLISION;
        this.parent = g;
        this.gw = parent.parent;
    }

    @Override
    public Element savify(Document d) {
        return super.savify(d);
    }

    public void pickup(){}

    public void onCollision(GameObject x2, Vec2d collides){
        if(x2.getComponent(Tag.INPUT) != null){
            pickup();
            gw.throwAway(this.parent);
        }
    }
}
