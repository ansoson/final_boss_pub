package finalboss.custom_components.main_character;

import engine.components.AnimationComponent;
import engine.components.SpriteComponent;
import engine.hierarchy.GameObject;
import engine.components.Tag;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class MainCharacterAnimationComponent extends AnimationComponent {

    public SpriteComponent s;
    public MainCharacterAnimationComponent(GameObject g, SpriteComponent s, int max) {
        super(g, s, max);
        this.s = s;
        this.tag = Tag.UPDATE;
        this.s.LAC = new MainCharacterLayeredAnimationComponent(g, s);
    }

    public MainCharacterAnimationComponent(Element e, GameObject g){
        super(e,g);
        this.parent = g;
        this.tag = Tag.UPDATE;
        this.s = (SpriteComponent) this.parent.getComponent(Tag.GRAPHICS);
        this.max = Integer.parseInt(e.getAttribute("max"));
        this.interval = Integer.parseInt(e.getAttribute("interval"));
    }

    @Override
    public Element savify(Document d) {
        Element e = d.createElement("MainCharacterAnimationComponent");
        e.setAttribute("max", String.valueOf(max));
        e.setAttribute("interval", String.valueOf(interval));
        return e;
    }

    public void iterate(){
        int sx = s.getVcel();
        sx = sx + 1;
        if(sx > this.max){
            sx = 0;
        }
        s.setVcel(sx);
    }
}
