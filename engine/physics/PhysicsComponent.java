package engine.physics;

import engine.components.Component;
import engine.hierarchy.GameObject;
import engine.support.Vec2d;
import engine.components.Tag;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class PhysicsComponent extends Component {
    double mass;
    Vec2d pos, vel;
    Vec2d impulse, force;

    boolean stat;

    public PhysicsComponent(GameObject g, double mass, boolean stat){
        super(g);
        this.tag = Tag.PHYSICS;
        vel = impulse = force = new Vec2d(0, 0);
        this.stat = stat;
        this.mass = mass;
        this.pos = this.parent.getTransform().pos;
    }

    public PhysicsComponent(Element e, GameObject g){
        super(e,g);
        this.parent = g;
        this.tag = Tag.PHYSICS;
        this.impulse = this.force = new Vec2d(0,0);
        this.vel = new Vec2d(Double.parseDouble(e.getAttribute("vel_x")), Double.parseDouble(e.getAttribute("vel_y")));
        this.stat = Boolean.parseBoolean(e.getAttribute("static"));
        this.mass = Double.parseDouble(e.getAttribute("mass"));

    }

    @Override
    public Element savify(Document d) {
        Element e = d.createElement("PhysicsComponent");
        e.setAttribute("vel_x", String.valueOf(this.vel.x));
        e.setAttribute("vel_y", String.valueOf(this.vel.y));
        e.setAttribute("static", String.valueOf(this.stat));
        e.setAttribute("mass", String.valueOf(this.mass));
        return e;
    }

    public double getMass(){return this.mass;}

    public Vec2d getVel(){
        //System.out.println("GET VEL FOR " + parent.name + ": " + vel);
        return this.vel;
    }

    //applyForce() accumulates force
    public void applyForce(Vec2d f) {
        if(!stat) {
            force = force.plus(f);
        }
    }

    //applyImpulse() accumulates impulse
    public void applyImpulse(Vec2d p) {
        if(!stat) {
            //System.out.println("impulse?" + p);
            impulse = impulse.plus(p);
        }
    }

    //onTick() applies force and impulse, clearing them for next frame
    public void tick(long t) {
        t=1;
        vel = vel.plus(force.smult(t).smult(1.0/mass).plus(impulse.smult(1.0/mass)));
        //System.out.println("VEL!" + vel);
        this.parent.getTransform().pos = this.parent.getTransform().pos.plus(vel.smult(t));
        force = impulse = new Vec2d(0,0);
    }
}
