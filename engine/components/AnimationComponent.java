package engine.components;

import engine.hierarchy.GameObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class AnimationComponent extends Component {

    protected int interval;

    int current = 0;

    protected int max;
    public SpriteComponent s;


    public AnimationComponent(GameObject g, SpriteComponent s, int max) {
        super(g);
        this.tag = Tag.UPDATE;
        this.s = s;
        this.max = max;
        this.interval = 10;
    }

    public AnimationComponent(GameObject g, SpriteComponent s, int max, int interval) {
        super(g);
        this.tag = Tag.UPDATE;
        this.s = s;
        this.max = max;
        this.interval = interval;
    }

    public AnimationComponent(Element e, GameObject g) {
        super(e, g);
        this.parent = g;
        this.tag = Tag.UPDATE;
        this.s = (SpriteComponent) g.getComponent(Tag.GRAPHICS);
        this.max = Integer.parseInt(e.getAttribute("max"));
        this.interval = Integer.parseInt(e.getAttribute("interval"));
    }

    @Override
    public Element savify(Document d) {
        Element e = d.createElement("AnimationComponent");
        e.setAttribute("max", String.valueOf(max));
        e.setAttribute("interval", String.valueOf(interval));
        return e;
    }

    public void tick(long nanosSinceLastTick){
        if (s.LAC != null) {
            s.LAC.tick(nanosSinceLastTick);
        } else {
            current += 1;
            if(current > interval){
                current = 0;
                int sx = s.getHcel();
                sx = sx + 1;
                if(sx > max){
                    sx = 0;
                }
                s.setHcel(sx);
            }
        }
    }
}
