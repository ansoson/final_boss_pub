package finalboss.custom_components;

import engine.components.Component;
import engine.hierarchy.GameObject;
import engine.physics.PhysicsComponent;
import engine.support.Vec2d;
import engine.components.Tag;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class JumpComponent extends Component {

    public JumpComponent(GameObject g) {
        super(g);
        this.tag = Tag.OTHER;
    }

    public JumpComponent(Element e, GameObject g){
        super(e, g);
        this.parent = g;
        this.tag = Tag.OTHER;
    }

    @Override
    public Element savify(Document d) {
        return d.createElement("JumpComponent");
    }

    public void jump(){
        PhysicsComponent p = (PhysicsComponent) this.parent.getComponent(Tag.PHYSICS);
        p.applyImpulse(new Vec2d(0, -20));
    }

}
