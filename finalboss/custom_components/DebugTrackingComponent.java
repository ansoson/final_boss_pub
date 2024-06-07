package finalboss.custom_components;

import engine.components.Component;
import engine.hierarchy.GameObject;
import engine.components.Tag;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class DebugTrackingComponent extends Component {
    public DebugTrackingComponent(GameObject g) {
        super(g);
        this.tag = Tag.AI;
    }

    public DebugTrackingComponent(Element n, GameObject g){
        super(n, g);
        this.tag = Tag.AI;
        this.parent = g;
    }

    @Override
    public Element savify(Document d){return d.createElement("DebugTrackingComponent");}

    @Override
    public void tick(long nanos){
        //System.out.println(parent.name + " at " + parent.getTransform().pos);
    }
}
