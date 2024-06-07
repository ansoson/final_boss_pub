package finalboss.custom_components;

import engine.components.Component;
import engine.hierarchy.GameObject;
import engine.support.Vec2d;
import engine.components.Tag;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class LinkageComponent extends Component {

    GameObject g2;
    public LinkageComponent(GameObject g, GameObject g2) {
        super(g);
        this.tag = Tag.UPDATE;
        this.g2 = g2;
    }

    public LinkageComponent(Element e, GameObject g){
        super(e, g);
        this.parent = g;
        this.tag = Tag.UPDATE;
        this.g2 = parent.parent.getGameObject(Integer.parseInt(e.getAttribute("g2")));
    }

    @Override
    public Element savify(Document d) {
        Element e = d.createElement("LinkageComponent");
        e.setAttribute("g2", String.valueOf(g2.id));
        return e;
    }


    public void tick(long nanosSinceLastTick){
        parent.setTransform(new Vec2d(g2.getTransform().pos.plus(new Vec2d(0, g2.getTransform().size.y))));
    }
}
