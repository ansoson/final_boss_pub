package finalboss.custom_components;

import engine.components.Component;
import engine.hierarchy.GameObject;
import engine.physics.PhysicsComponent;
import engine.support.Vec2d;
import engine.components.Tag;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class GravityComponent extends Component {

    PhysicsComponent p;

    double gravConstant = 0.8;
    double mass;

    boolean grounded;
    public GravityComponent(GameObject g, PhysicsComponent p, double mass) {
        super(g);
        this.tag = Tag.PREPHYSICS;
        this.p = p;
        this.mass = mass;
        grounded = true;
    }

    @Override
    public Element savify(Document d) {
        Element e = d.createElement("GravityComponent");
        e.setAttribute("mass", String.valueOf(this.mass));
        e.setAttribute("grounded", String.valueOf(this.grounded));
        return e;
    }

    public GravityComponent(Element n, GameObject p){
        super(n, p);
        this.parent = p;
        this.tag = Tag.PREPHYSICS;
        this.mass = Double.parseDouble(n.getAttribute("mass"));
        this.grounded = Boolean.parseBoolean(n.getAttribute("grounded"));
        this.p = (PhysicsComponent) parent.getComponent(Tag.PHYSICS);
    }

    @Override
    public void tick(long tick) {
        if(!grounded) {
            double f = mass * gravConstant;
            p.applyForce(new Vec2d(0, f));
        }
    }

    public void setGrounded(boolean b) {
        grounded=b;
    }
}
