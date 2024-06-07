package engine.components;

import engine.UIKit.Circle;
import engine.UIKit.Rectangle;
import engine.hierarchy.GameObject;
import engine.hierarchy.Viewport;
import engine.physics.PhysicsComponent;
import engine.shapes.*;
import engine.support.Vec2d;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.lang.reflect.InvocationTargetException;

public class CollisionComponent extends Component {

    protected ShapeEnum s;
    protected Shape shape;
    public int layer;
    public Circle a;
    public Rectangle b;

    public boolean stat;
    public CollisionComponent(GameObject g, ShapeEnum s, int layer, boolean stat) {
        super(g);
        this.s = s;
        this.tag = Tag.COLLISION;
        initShape();
        this.layer = layer;
        this.stat = stat;
    }

    public CollisionComponent(Element e, GameObject g) {
        super(e,g);
        this.parent = g;
        this.tag = Tag.COLLISION;
        this.layer = Integer.parseInt(e.getAttribute("layer"));
        this.stat = Boolean.parseBoolean(e.getAttribute("static"));
        //System.out.println(e.getAttribute("shape"));
        this.s = ShapeEnum.valueOf(e.getAttribute("shape"));
        initShape();
    }

    @Override
    public Element savify(Document d) {
        Element e = d.createElement("CollisionComponent");
        e.setAttribute("layer", String.valueOf(this.layer));
        e.setAttribute("static", String.valueOf(this.stat));
        e.setAttribute("shape", s.name());
        return e;
    }


    public Vec2d collides(CollisionComponent c){
        initShape();
        return shape.collides(c.shape);
    }

    public void initShape(){
        if(s == ShapeEnum.AAB) {
            this.shape = new ColAAB(parent.getTransform().pos, parent.getTransform().size, parent.getTransform().def);

        }
        else {
            this.shape = new ColCircle(parent.getTransform().pos, parent.getTransform().size);
        }
    }
    public void onCollision(GameObject x2, Vec2d collides) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {}

}

