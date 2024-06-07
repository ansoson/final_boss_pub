package engine.components;

import engine.hierarchy.GameObject;
import engine.support.Vec2d;
import finalboss.screens.PCViewport;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class CenterComponent extends Component {

    PCViewport v;

    public CenterComponent(GameObject g, PCViewport v) {
        super(g);
        this.tag = Tag.CENTER;
        this.v = v;
    }

    public CenterComponent(Element e, GameObject g){
        super(e,g);
        this.parent = g;
        this.tag = Tag.CENTER;
        this.v = (PCViewport) parent.parent.parent.viewport;
    }


    @Override
    public Element savify(Document d) {
        return d.createElement("CenterComponent");
    }

    @Override
    public void onLateTick(){
        v.setCenter(new Vec2d(parent.getTransform().pos.x + 100, parent.getTransform().pos.y + 25));
    }
}
