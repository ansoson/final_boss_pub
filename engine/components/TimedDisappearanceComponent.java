package engine.components;

import engine.hierarchy.GameObject;
import engine.hierarchy.GameWorld;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class TimedDisappearanceComponent extends Component {

    int disappear;
    int current;
    GameWorld gw;

    public TimedDisappearanceComponent(GameObject g, int dis, GameWorld gw) {
        super(g);
        this.tag = Tag.STAB;
        this.disappear = dis;
        this.current = 0;
        this.gw = g.parent;
    }


    public TimedDisappearanceComponent(Element e, GameObject g){
        super(e,g);
        this.parent = g;
        this.tag = Tag.STAB;
        this.disappear = Integer.parseInt(e.getAttribute("dis"));
        this.current = Integer.parseInt(e.getAttribute("current"));
        this.gw = parent.parent;
    }
    @Override
    public Element savify(Document d) {
        Element e = d.createElement("TimedDisappearanceComponent");
        e.setAttribute("dis", String.valueOf(disappear));
        e.setAttribute("current", String.valueOf(current));
        return e;
    }

    public void tick(long nanosSinceLastTick){
        this.current += 1;
        if(current > disappear){
            gw.throwAway(parent);
        }
    }
}
