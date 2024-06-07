package engine.components;

import engine.hierarchy.GameObject;
import engine.support.Vec2d;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class TransformComponent extends Component {

    public Vec2d pos;
    public Vec2d size;
    public Vec2d def;
    Vec2d ogPos;
    Vec2d ogSize;
    public TransformComponent(GameObject g, Vec2d pos, Vec2d size, Vec2d def) {
        super(g);
        this.tag = Tag.TRANSFORM;
        this.pos = pos; this.ogPos = pos;
        this.size = size; this.ogSize = size;
        this.def = def;
    }

    public TransformComponent(Element e, GameObject g) {
        super(e, g);
        this.parent = g;
        this.tag = Tag.TRANSFORM;
        this.pos = this.ogPos = new Vec2d(Double.parseDouble(e.getAttribute("pos_x")), Double.parseDouble(e.getAttribute("pos_y")));
        this.size = this.ogSize = new Vec2d(Double.parseDouble(e.getAttribute("size_x")), Double.parseDouble(e.getAttribute("size_y")));
        this.def = parent.parent.parent.windowSize;
    }

    public void setTransform(Vec2d pos){this.pos = pos;}

    @Override
    public Element savify(Document d) {
        Element e = d.createElement("TransformComponent");
        e.setAttribute("pos_x", String.valueOf(pos.x));
        e.setAttribute("pos_y", String.valueOf(pos.y));
        e.setAttribute("size_x", String.valueOf(size.x));
        e.setAttribute("size_y", String.valueOf(size.y));
        e.setAttribute("def_x", String.valueOf(def.x));
        e.setAttribute("def_y", String.valueOf(def.y));
        return e;
    }
}
